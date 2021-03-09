(ns tech.ardour.negotiator.js
  (:refer-clojure :exclude [read])
  (:require
    [clojure.string :as str]
    [tech.ardour.negotiator :as neg]
    [tech.ardour.negotiator.convert :as convert]))

(def read-defaults {:key?   (complement str/blank?)
                    :key-fn (comp keyword convert/->kebab-case)})

(def write-defaults {:key?   keyword?
                     :key-fn (comp convert/->camelCase name)})

(defn read
  "Reads a Javascript Object into a Clojure Data Structure"
  ([^js v]
   (read v nil))
  ([^js v opts]
   (let [{:keys [key? key-fn]} (merge read-defaults opts)]
     (->> v
          (js->clj)
          (neg/transform-keys key? key-fn)))))

(defn write
  "Reads a Javascript Object into a Clojure Data Structure"
  ([v] (write v nil))
  ([v opts]
   (let [{:keys [key? key-fn]} (merge write-defaults opts)]
     (->> v
          (neg/transform-keys key? key-fn)
          clj->js))))
