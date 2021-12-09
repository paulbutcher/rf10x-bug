(ns rf10x-bug.splash)

(defn splash []
  [:div
   [:div.container-fluid.mb-5.mt-5.text-center
    [:img {:src "logo_horizontal_colour.svg" :alt "Race &amp; Improve" :style {:width "50%" :max-width "500px"}}]]
   [:div.container-fluid {:style {:max-width "720px"}}
    [:div.mx-auto.text-center.w-75
     [:p "Coming soon…"]]]])
