(ns pwa
  (:require [cheshire.core :as json]
            [clojure.java.browse :refer [browse-url]]
            [hiccup.core :refer [html]]
            [portal.colors :as c])
  (:import [java.util Base64]))

(defn encode [s]
  (.encodeToString
   (Base64/getEncoder) (if (string? s) (.getBytes s) s)))

(defn inline [content-type value]
  (str "data:" content-type ";base64," (encode value)))

(defn manifest [settings]
  (json/generate-string
   {:short_name "Portal"
    :name "portal"
    :description "A clojure tool to navigate through your data."
    :icons
    [{:type "image/svg+xml"
      :sizes "512x512"
      :src (inline "image/svg+xml" (slurp "resources/icon.svg"))}]
    :scope (::host settings)
    ;:scope_url "http://localhost:53454/"
    :start_url (::host settings)
    :display "standalone"}))

(defn pwa [settings]
  [:html
   [:head
    [:link {:rel :manifest
            :href (inline
                   "application/manifest+json"
                   (manifest settings))}]
    [:meta {:name "theme-color"
            :content (::c/background2 settings)}]]
   [:body
    {:style {:margin-right 0
             :margin-left 0
             :margin-bottom 0
             :margin-top "-1px"
             :background (::c/background settings)}}
    [:script {:src "pwa.js"}]]])

(defn -main []
  (->> {::host "http://localhost:4400"}
       (merge (::c/nord c/themes))
       pwa
       html
       (spit "target/pwa/index.html")))

(comment
  (browse-url "http://localhost:4400"))
