(ns tech.ardour.negotiator.edn
  (:refer-clojure :exclude [read])
  (:require
    [clojure.edn :as edn]))

(def mime-type "application/edn")

(defn read [^String s]
  (edn/read-string s))

(defn write [o]
  (pr-str o))
