(ns rf10x-bug.core
  (:require
   [reagent.dom :as rdom]
   [re-frame.core :as re-frame]
   [rf10x-bug.events :as events]
   [rf10x-bug.config :as config]
   [rf10x-bug.routes :refer [init-routes! app]]
   ))


(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (re-frame/clear-subscription-cache!)
  (init-routes!)
  (let [root-el (.getElementById js/document "app")]
    (rdom/unmount-component-at-node root-el)
    (rdom/render [app] root-el)))

(defn init []
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))
