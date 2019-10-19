(defproject pomodoro "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/clojurescript "1.10.520"]
                 [reagent "0.6.0" :exclusions [cljsjs/react]]
                 [ring "1.4.0"]
                 [ring/ring-defaults "0.2.0"]
                 [bk/ring-gzip "0.1.1"]
                 [ring.middleware.logger "0.5.0"]
                 [compojure "1.5.0"]
                 [environ "1.0.3"]
                 [binaryage/devtools "0.8.2"]
                 [re-frame "0.8.0" :exclusions [cljsjs/react]]
                 [cljsjs/material-ui "0.15.4-0"]
                 [secretary "1.2.3"]
                 [clojure-humanize "0.2.2"]
                 [com.andrewmcveigh/cljs-time "0.5.2"]
                 [com.taoensso/timbre "4.7.4"]
                 [com.cognitect/transit-cljs "0.8.239"]
                 [ring-logger-timbre "0.7.5"]
                 
                 [devcards "0.2.2" :exclusions [cljsjs/react]]
                 [reanimated "0.5.0"]]
 

  :plugins [[lein-cljsbuild "1.1.4"]
            [lein-heroku "0.5.3"]]

  :git-dependencies [["https://github.com/trhura/clojure-humanize.git"]]

  :min-lein-version "2.5.3"


  :source-paths ["src/clj"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :figwheel {:css-dirs ["resources/public/css"]}

  :heroku {:app-name "shielded-falls-51511"}
  :main pomodoro.server
  :aot [pomodoro.server]

  :profiles
  {:dev
   {:dependencies []

    :plugins      [[lein-figwheel "0.5.19"]]
    }
   :uberjar
   {:source-paths ^:replace ["src/clj" "src/cljc"]
    :prep-tasks ["compile" ["cljsbuild" "once" "min"]]
    :hooks []
    :omit-source true
    :aot :all}}

  :cljsbuild
  {:builds
   [{:id           "dev"
     :source-paths ["src"]
     :figwheel     {
                    :on-jsload "pomodoro.core/mount-root"}
     :compiler     {:main                 pomodoro.core
                    :output-to            "resources/public/js/compiled/app.js"
                    :output-dir           "resources/public/js/compiled/out"
                    :asset-path           "js/compiled/out"
                    :source-map-timestamp true}}
    {:id           "devcards"
     :source-paths ["src"]
     :figwheel     {:devcards true
                    :on-jsload "pomodoro.core/mount-root"}
     :compiler     {:main                 pomodoro.core
                    :output-to            "resources/public/js/devcards/compiled/app.js"
                    :output-dir           "resources/public/js/devcards/compiled/out"
                    :asset-path           "js/devcards/compiled/out"
                    :source-map-timestamp true}}

    {:id           "min"
     :source-paths ["src/cljs"]
     :compiler     {:main            pomodoro.core
                    :output-to       "resources/public/js/compiled/app.js"
                    :optimizations   :advanced
                    :closure-defines {goog.DEBUG false}
                    :pretty-print    false}}

    ]}

  )
