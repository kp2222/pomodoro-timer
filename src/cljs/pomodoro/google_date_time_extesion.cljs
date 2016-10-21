(ns pomodoro.google-date-time-extesion
  (:import [goog.date DateTime]))

(extend-type DateTime
  IPrintWithWriter
  (-pr-writer [obj writer _opts]
    (let [normalize (fn [n len]
                      (loop [ns (str n)]
                        (if (< (count ns) len)
                          (recur (str "0" ns))
                          ns)))]
      (write-all writer
                 "#inst \""
                 (str (.getUTCFullYear obj))             "-"
                 (normalize (inc (.getUTCMonth obj)) 2)  "-"
                 (normalize (.getUTCDate obj) 2)         "T"
                 (normalize (.getUTCHours obj) 2)        ":"
                 (normalize (.getUTCMinutes obj) 2)      ":"
                 (normalize (.getUTCSeconds obj) 2)      "."
                 (normalize (.getUTCMilliseconds obj) 3) "-"
                 "00:00\""))))

