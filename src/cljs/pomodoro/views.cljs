(ns pomodoro.views
  (:require [re-frame.core :as re-frame]
            [reagent.core :as r]
            [pomodoro.reagent-material-ui :refer [
                                                  AppBar
                                                  MuiThemeProvider
                                                  lightBaseTheme
                                                  darkBaseTheme
                                                  getMuiTheme
                                                  Divider
                                                  Card
                                                  CardHeader
                                                  CardText
                                                  Paper
                                                  List
                                                  ListItem
                                                  FlatButton
                                                  IconMenu
                                                  IconButton
                                                  SvgIcon
                                                  ActionAlarm
                                                  NavigationMoreVert
                                                  MenuItem
                                                  ]]
            [pomodoro.views.pomodoro-timer :as pomodoro]
            [pomodoro.views.pomodoro-history-list :as history]
            [taoensso.timbre :as timbre]
            [pomodoro.views.settings :as settings]))


(defn more-actions-menu[]
  []
  [IconMenu {:iconButtonElement (r/as-element [IconButton [NavigationMoreVert]])
             :targetOrigin #js{:horizontal "right", :vertical "top"}
             :anchorOrigin #js{:horizontal "right", :vertical "top"}}
   [MenuItem {:onTouchTap #(re-frame/dispatch [:set-active-panel :home-panel])} "Home"]
   [MenuItem {:onTouchTap #(re-frame/dispatch [:set-active-panel :settings-panel ])}"Settings"]])

(defn timer-panel []
  (let [history (re-frame/subscribe [:pomodoro-history])
        settings (re-frame/subscribe [:settings])
        timer-config (merge @settings {:onTimerStop #(re-frame/dispatch [:save-promodoro-history %])})]
    (fn []
      [:div
       [Paper {:zDepth 2 :class "timer"}
        [pomodoro/timer timer-config]]
       [Divider]
       [history/pomodoro-history-new history]])))


(defn settings-panel []
  (let [settings (re-frame/subscribe [:settings])]
    (fn []
      [settings/settings settings])))




;; main

(defmulti panels identity)
(defmethod panels :home-panel [] [timer-panel])
(defmethod panels :settings-panel [] [settings-panel])
(defmethod panels :default [] [:div])

(defn show-panel
  [panel-name]
  [MuiThemeProvider  {:muiTheme (getMuiTheme lightBaseTheme)}
   [:div
    [AppBar {:iconElementRight (r/as-element [more-actions-menu])
             :iconElementLeft (r/as-element [ActionAlarm {:style {:margin-top "11px"}}])} ]
    [panels panel-name]]])



(defn main-panel []
  (let [active-panel (re-frame/subscribe [:active-panel])]
    (fn []
      [show-panel @active-panel])))
