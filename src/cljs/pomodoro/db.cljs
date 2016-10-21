(ns pomodoro.db
  (:require [re-frame.core :as re-frame]
            [cognitect.transit :as t]))

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
