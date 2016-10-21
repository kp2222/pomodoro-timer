(ns pomodoro.reagent-material-ui
  (:refer-clojure :exclude [List
                            Stepper])
  (:require [cljsjs.material-ui]
            [reagent.core]
            [cljsjs.material-ui-svg-icons])
  (:require-macros [pomodoro.reagent-material-ui-macros :refer [export-material-ui-react-classes
                                                                export-material-ui-icon-classes]]))
  
  



(export-material-ui-react-classes)
(export-material-ui-icon-classes)


(def colors (-> js/MaterialUIStyles
                (aget "colors")))

(def lightBaseTheme (-> js/MaterialUIStyles
                        (aget "lightBaseTheme")))

(def darkBaseTheme (-> js/MaterialUIStyles
                       (aget "darkBaseTheme")))

(def getMuiTheme (-> js/MaterialUIStyles
                     (aget "getMuiTheme")))

(def MuiThemeProvider (-> js/MaterialUIStyles
                          (aget "MuiThemeProvider")
                          (reagent.core/adapt-react-class)))


