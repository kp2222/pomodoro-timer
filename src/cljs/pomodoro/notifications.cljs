(ns pomodoro.notifications)

(def *permission-granted?* (atom false))

(def notifier (atom nil))


(defn request-notification-permission[]
  (let [permission-promise (.requestPermission js/Notification)]
    (.then permission-promise (fn[result] (when (= result "granted") (do (reset! *permission-granted?* true)))))))


(defn register-sw
  []
  (let [registerer (.register (.-serviceWorker js/navigator) "js/sw.js")]
    (.then registerer (fn [r] (reset! notifier r)))))

(defn notify
  [title options]
  (if (and @*permission-granted?* @notifier)
    (try
      (.showNotification @notifier title (clj->js options))
      (catch :default e
        e))))


(defn setup-notifications[]
  (request-notification-permission)
  (register-sw))
