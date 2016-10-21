(ns pomodoro.views.settings
  (:require-macros
   [devcards.core :as dc :refer [defcard-rg deftest]])
  (:require [reagent.core :as r]
            [re-frame.core :as reframe]
            [pomodoro.helpers :as helpers]
            [pomodoro.reagent-material-ui :refer [Card
                                                  List
                                                  ListItem
                                                  Subheader
                                                  Divider
                                                  TextField
                                                  Toggle]]))


(defn settings
  [settings]
  [Card
   [Subheader "Settings"]
   [List
    [ListItem
     [TextField {:floatingLabelText "Pomodoro Time(in Minutes)"
                 :value (:pomodoro-time @settings)
                 :onChange #(reframe/dispatch-sync [:input-edited [:settings :pomodoro-time] :db/always-true (-> % .-target .-value)])}]]
    [ListItem
     [Toggle {:label "Enable Notifications"
              :toggled (:notifications-enabled @settings)
              :onToggle #(reframe/dispatch-sync [:input-edited [:settings :notifications-enabled] :db/always-true (not (:notifications-enabled @settings))])}]]]])


(defcard-rg the-settings
  (fn [data] [helpers/wrap-component-in-mui settings data]) (r/atom {:pomodoro-time 30
                                            :break-time 30
                                            :notifications-enabled false}) {:inspect-data true})

