(ns pomodoro.handlers
    (:require [re-frame.core :as re-frame]
              [pomodoro.db :as db]
              [taoensso.timbre :as timbre]
              [cljs.spec :as s]))



(defn check-and-throw
  "throw an exception if db doesn't match the spec."
  [a-spec db]
  (when-not (s/valid? a-spec db)
    (throw (ex-info (str "spec check failed: " (s/explain-str a-spec db)) {}))))


(def check-spec-interceptor (re-frame/after (partial check-and-throw :pomodoro.db/app-db)))

(def ->localstore (re-frame/after db/history->local-store))


(re-frame/reg-event-fx
 :initialize-db
 [(re-frame/inject-cofx :local-store-history) check-spec-interceptor]
 (fn  [{:keys [db local-store-history]} _]
   (let [new-db (assoc db/default-db :history local-store-history)]
     {:db new-db})))

(re-frame/reg-event-db
 :set-active-panel
 [check-spec-interceptor]
 (fn [db [_ active-panel]]
   (assoc db :active-panel active-panel)))


(re-frame/reg-event-fx
 :save-promodoro-history
 [->localstore check-spec-interceptor]
 (fn [{:keys [db]} [_ history-item]]
   (timbre/debug (str "saving: " history-item))
   {:db (update-in db [:history] conj history-item)}))




(re-frame/reg-event-db
  :input-edited
  (fn [db [_ path validate-fn-name new-val]]
    (assoc-in db path new-val)))
