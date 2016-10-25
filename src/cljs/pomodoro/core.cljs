(ns pomodoro.core
  (:require-macros
   [devcards.core :as dc :refer [defcard deftest]])
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [devtools.core :as devtools]
            [pomodoro.handlers]
            [pomodoro.subs]
            [pomodoro.routes :as routes]
            [pomodoro.views :as views]
            [pomodoro.config :as config]
            [pomodoro.notifications :as notifications]
            [pomodoro.google-date-time-extesion  ]))


(defn dev-setup []
  (enable-console-print!)
  (println "dev mode")
  (devtools/install!))

(defn mount-root []

  ;; Only mount if app node present.
  ;; This to enable running dev cards in a different html
  
  (if-let [node (.getElementById js/document "app")] 
    (reagent/render [views/main-panel] node)))

(defn ^:export init []
  (dev-setup)
  (re-frame/dispatch-sync [:initialize-db])
  (routes/app-routes)
  (mount-root)
  (notifications/setup-notifications))



  
