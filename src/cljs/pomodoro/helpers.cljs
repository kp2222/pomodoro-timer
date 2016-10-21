(ns pomodoro.helpers
  (:require [clojure.string :as string]
            [goog.string :as gstring]
            [pomodoro.reagent-material-ui  :refer [MuiThemeProvider
                                              lightBaseTheme
                                              darkBaseTheme
                                              getMuiTheme]]
            [goog.string.format]
            [clojure.contrib.humanize :as humanize]))

(defn duration-in-hours-minutes-seconds[duration-in-seconds]
  (let [int-seconds (int duration-in-seconds)
        days        (quot int-seconds 86400)
        days-r      (rem int-seconds 86400)
        hours       (quot days-r 3600)
        hours-r     (rem days-r 3600)
        minutes     (quot hours-r 60)
        seconds     (rem hours-r 60)]
    (string/join ":"
                 (conj []
                       (gstring/format "%02d" hours)
                       (gstring/format "%02d" minutes)
                       (gstring/format "%02d" seconds)))))



(defn describe-pomodoro-session
  [{:keys [task-description duration]}]
  (if (clojure.string/blank? task-description)
    (str "You had a Pomodoro session for " (humanize/duration duration))
    (str "You worked on \""task-description "\" for " (humanize/duration duration))))





(defn wrap-component-in-mui
  [component & args]
  (fn[]
    [MuiThemeProvider  {:muiTheme (getMuiTheme lightBaseTheme)}
     [:div
      [apply component args]]]))

