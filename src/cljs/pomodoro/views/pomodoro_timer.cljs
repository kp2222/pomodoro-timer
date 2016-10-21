(ns pomodoro.views.pomodoro-timer
  (:require-macros
   [devcards.core :as dc :refer [defcard-rg deftest]])
  (:require [reagent.core :as r]
            [pomodoro.reagent-material-ui :refer [TextField
                                                  RaisedButton
                                                  LinearProgress
                                                  IconButton
                                                  ActionCheckCircle
                                                  ActionPowerSettingsNew
                                                  CircularProgress
                                                  Paper]]
            [pomodoro.helpers :as h]
            [re-frame.core :as re-frame]
            [cljs-time.core :as time]
            [pomodoro.notifications :as notifications]
            [pomodoro.helpers :as helpers]
            [taoensso.timbre :as timbre]))


(declare stop-counter)
(declare tick-counter)

(defn create-notification
  [title pomodoro]
  (notifications/notify title
                        {:body (helpers/describe-pomodoro-session pomodoro)
                         :silent false
                         :vibrate [1000 500 1000]}))

(def timer-idle-state
  {:state "idle"
   :counter nil
   :scheduler nil})

(defn init-running-state[config state]
  (merge @state
         {:state "running"
          :scheduler (js/setInterval #(tick-counter state config) 1000)
          :counter (* 60 (:pomodoro-time config))}))



(defn tick-counter
  [state config]
  (swap! state update-in [:counter] dec)
  (timbre/debug (str "ticking counter: " @state))
  (if (>= 0 (:counter @state))
    (stop-counter state config)
    true))


(defn start-counter
  [state config]
  (reset! state (init-running-state config state))
  (timbre/debugf (str "The current state in start counter: " @state)))

(defn clear-timer
  [state]
  (js/clearInterval (:scheduler @state)))

(defn state->pomodoro
  [state config]
  {:task-description (:task-description @state)
   :time (time/now)
   :duration (* (- (* 60 (:pomodoro-time config)) (:counter @state)) 1000)})

(defn stop-counter[state config]
  (let [pomodoro (state->pomodoro state config)]
    (if (:notifications-enabled config)
      (create-notification "Pomodoro complete!!" pomodoro))
    ((get-in config [:onTimerStop] #(timbre/debug "Noop")) pomodoro)
    (clear-timer state)
    (reset! state timer-idle-state)))

(defn timer-running?[state]
  (= "running" (:state @state)))


(defn timer-render[state config]
  (timbre/debug (str "configuration" @state))
  [:Card
   [:div {:class "timer-container"} 
    [TextField {:id "task-description"
                :floatingLabelText "Task Description"
                :fullWidth true :multiLine true
                :rows 2
                :onChange #(swap! state update-in [:task-description] (fn[] (-> % .-target .-value)))
                :disabled (timer-running? state)}]
    (if (timer-running? state)
      [Paper {:zDepth 5
              :class "progress-indicator-container"}
       [:h2 (h/duration-in-hours-minutes-seconds (:counter @state))]
       [LinearProgress {:mode "determinate"
                        :size 0.5
                        :max (* 60 (:pomodoro-time config))
                        :min 0
                        :value (- (* 60 (:pomodoro-time config)) (:counter @state))}]])
    [:div {:class "actions-container"}
     [RaisedButton {:primary true
                    :icon (r/as-element [ActionPowerSettingsNew])
                    :disabled (timer-running? state)
                    :onClick #(start-counter state config)}]
     [RaisedButton {:secondary true
                    :icon (r/as-element [ActionCheckCircle])
                    :disabled (not (timer-running? state))
                    :onClick #(stop-counter state config)}]]]])


(defn timer
  [config]
  (let [state (r/atom timer-idle-state)]
    (r/create-class
     {:reagent-render (fn[config] (timer-render state config))
      ::component-will-unmount #(clear-timer state)
      :display-name "pomodoro-timer"})))



(defcard-rg the-timer
  (fn [data _] [helpers/wrap-component-in-mui timer  {:name "user ":onTimerStop #(js/alert "completed!")} ] ) {:inspect-data true
                                                                                                               :watch-atom true })
