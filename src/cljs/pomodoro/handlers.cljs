(ns pomodoro.handlers
    (:require [re-frame.core :as re-frame]
              [pomodoro.db :as db]
              [taoensso.timbre :as timbre]))

(def ->localstore (re-frame/after db/history->local-store))


(re-frame/reg-event-fx
 :initialize-db
 [(re-frame/inject-cofx :local-store-history) ]
 (fn  [{:keys [db local-store-history]} _]
   (let [new-db (assoc db/default-db :history local-store-history)]
     {:db new-db
     :dispatch [:some-shit]})))

(re-frame/reg-event-db
 :set-active-panel
 (fn [db [_ active-panel]]
   (assoc db :active-panel active-panel)))


(re-frame/reg-event-fx
 :save-promodoro-history
 [->localstore]
 (fn [{:keys [db]} [_ history-item]]
   (timbre/debug (str "saving: " history-item))
   {:db (update-in db [:history] conj history-item)}))




(re-frame/reg-event-db
  :input-edited
  (fn [db [_ path validate-fn-name new-val]]
    (assoc-in db path new-val)))
