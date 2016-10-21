(ns pomodoro.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :as re-frame]
              [taoensso.timbre :as timbre]))

(re-frame/reg-sub
 :name
 (fn [db]
   (:name db)))

(re-frame/reg-sub
 :active-panel
 (fn [db _]
   (:active-panel db)))


(re-frame/reg-sub
 :settings
 (fn [db _]
   (:settings db)))


(re-frame/reg-sub
 :timer
 (fn [db _]
   (:timer db)))

(re-frame/reg-sub
 :pomodoro-history
 (fn [db _]
   (reverse (take-last 50 (:history db)))))
