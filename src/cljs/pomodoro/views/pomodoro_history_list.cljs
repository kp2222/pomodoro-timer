(ns pomodoro.views.pomodoro-history-list
  (:require-macros
   [devcards.core :as dc :refer [defcard-rg ]])
  (:require [re-frame.core :as re-frame]
            [reagent.core :as r]
            [pomodoro.reagent-material-ui :refer [Card
                                                  CardHeader
                                                  Paper
                                                  List
                                                  Divider
                                                  Subheader
                                                  ListItem
                                                  ]]
            [clojure.contrib.humanize :as humanize]
            [pomodoro.helpers :as helpers]
            [taoensso.timbre :as timbre]
            [cljs-time.coerce :as time]))








(defn pomodoro-history-new
  [items]
  (fn[]
    (timbre/debug (str "items received " (count @items) (map  #(type (:time %)) @items) @items))
    [Paper {:zDepth 2 :class "history-container"}
     [List
      [Subheader "History"]
      (if (seq @items)
        (for [item @items]
          (let [{:keys [text-description time duration]} item]
            ^{:key item}[ListItem {:primaryText (helpers/describe-pomodoro-session item)
                                   :secondaryText (r/as-element [:p (humanize/datetime  time)])}]))
        [ListItem {:secondaryText"Nothing to see here yet!"}])]]))


;; Dev cards
(defcard-rg timer-history-empty
  (fn [data _]
    [helpers/wrap-component-in-mui pomodoro-history-new data]) (r/atom []) {:inspect-data true})

(defcard-rg timer-history-non-empty
  (fn [data _]
    [helpers/wrap-component-in-mui pomodoro-history-new data]) (r/atom [{:task-description "test" :time (js/Date. ) :duration 2000}]) {:inspect-data true})
