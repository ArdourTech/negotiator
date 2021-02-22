(ns tech.ardour.negotiator
  (:require
    [clojure.walk :as walk]
    [clojure.string :as str]))

(defn mime-and-charset [content-type]
  (map str/trim (-> content-type
                    (str/lower-case)
                    (str/split #";"))))

(defn mime-type [content-type]
  (first (mime-and-charset content-type)))

(defn transform-keys
  "Recursively transforms all map keys through the given transformer `f`, with optional predicate matching."
  ([transform-fn m] (transform-keys any? transform-fn m))
  ([pred? transform-fn m]
   (let [f (fn [[k v :as kv]] (if (pred? k) [(transform-fn k) v] kv))]
     (walk/postwalk (fn [x] (if (map? x) (into {} (map f x)) x)) m))))
