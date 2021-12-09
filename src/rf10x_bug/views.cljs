(ns rf10x-bug.views
  (:require
   [re-frame.core :as re-frame]
   [rf10x-bug.subs :as subs]
   ))

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [:h1
      "Hello from " @name]
     ]))
