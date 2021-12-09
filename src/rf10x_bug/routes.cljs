(ns rf10x-bug.routes
  (:require [reitit.frontend.easy]
            [reitit.frontend.controllers]
            [reitit.frontend]
            [reitit.coercion.spec]
            [reitit.spec]
            [re-frame.core :as re-frame]
            [rf10x-bug.splash :refer [splash]]
            [rf10x-bug.spinner :refer [spinner]]))

(re-frame/reg-event-db
 :navigated
 (fn [db [_ new-match]]
   (let [old-match (:current-route db)
         controllers (reitit.frontend.controllers/apply-controllers (:controllers old-match) new-match)]
     (assoc db :current-route (assoc new-match :controllers controllers)))))

(re-frame/reg-fx
 :push-state
 (fn [route]
   (apply reitit.frontend.easy/push-state route)))

(re-frame/reg-fx
 :replace-state
 (fn [route]
   (apply reitit.frontend.easy/replace-state route)))

(re-frame/reg-event-fx
 :push-state
 (fn [_ [_ & route]]
   {:fx [[:push-state route]]}))

(re-frame/reg-event-fx
 :replace-state
 (fn [_ [_ & route]]
   {:fx [[:replace-state route]]}))

(re-frame/reg-sub
 :app-data
 (fn [db _]
   (map db [:current-route])))

(defn on-navigate [new-match]
  (re-frame/dispatch [:navigated new-match]))

(def require-authentication
  {:start #(re-frame/dispatch [:login-if-necessary])})

(def handle-auth-callback
  {:start #(re-frame/dispatch [:handle-auth-callback])})

(def router
  (reitit.frontend/router
   ["/"
    ["" {:name :root
         :view splash}]]
   {:data {:coercion reitit.coercion.spec/coercion}
    :validate reitit.spec/validate}))

(defn init-routes! []
  (reitit.frontend.easy/start! router on-navigate {:use-fragment false}))

(defn app []
  (let [[current-route] @(re-frame/subscribe [:app-data])
        view (get-in current-route [:data :view])
        params (get-in current-route [:parameters :query])]
     (if (nil? view)
       [spinner]
       [view params])))
