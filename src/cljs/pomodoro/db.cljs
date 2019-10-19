(ns pomodoro.db
  (:require [re-frame.core :as re-frame]
            [cognitect.transit :as t]
            [cljs.spec.alpha :as s]))

;; spec for validating the schema of app-db.
;;
;; This would ideally be used from the interceptors
;; to make sure the integrity of the app-db is maintained by event processing

;; settings spec
(s/def ::pomodoro-time integer? )
(s/def ::notifications-enabled #{true false})
(s/def ::break-time integer?)
(s/def ::settings (s/keys :req-un [::pomodoro-time ::break-time ::notifications-enabled]))

;; pomodoro history entry
(s/def ::task-description string?)
(s/def ::duration integer?)
(s/def ::time #(or
                (instance? goog.date.UtcDateTime %)
                (instance? js/Date %)))
(s/def ::history-entry (s/keys :req-un [::task-description ::duration ::time]))
(s/def ::history (s/coll-of ::history-entry))

;; app-db
(s/def ::app-db (s/keys :req-un [::history ::settings]))






(def default-db {:history []
                 :settings {:pomodoro-time 30
                            :break-time 30
                            :notifications-enabled true}})



;; -- Local Storage  ----------------------------------------------------------
;;
;; Store History in LocalStorage,
;; and on app startup, reload from when the program was last run.


(def ls-key "pomodoro-history")                                ;; localstore key

(defn history->local-store
  "Puts todos into localStorage"
  [{:keys [history]}]
  (.setItem js/localStorage ls-key (pr-str history)))            ;; sorted-map writen as an EDN map

(re-frame/reg-cofx
  :local-store-history
  (fn [cofx _]
      "Read in history from localstore, and process into a map we can merge into app-db."
      (assoc cofx :local-store-history
             (into []
                   (some->> (.getItem js/localStorage ls-key)
                            (cljs.reader/read-string)       ;; stored as an EDN map.
                            )))))




;; Validators
(defn always-valid[value]
  true)
