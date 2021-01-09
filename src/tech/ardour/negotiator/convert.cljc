(ns tech.ardour.negotiator.convert
  (:require
    [clojure.string :as str]))

(def ^:private change-case-pattern (re-pattern "[A-Z]{2,}(?=[A-Z][a-z]+[0-9]*|\b)|[A-Z]?[a-z]+[0-9]*|[A-Z]{1,}|[0-9]+"))

(defn- change-case
  ([separator transform-fn]
   (change-case separator transform-fn transform-fn))
  ([separator first-transform-fn rest-transform-fn]
   (fn [s]
     (if (string? s)
       (let [[head & rest] (re-seq change-case-pattern s)]
         (str/join separator (into [(first-transform-fn head)]
                               (map rest-transform-fn)
                               rest)))
       s))))

(def ->camelCase (change-case nil str/lower-case str/capitalize))
(def ->snake_case (change-case \_ str/lower-case))
(def ->kebab-case (change-case \- str/lower-case))
(def ->SNAKE_CASE (change-case \_ str/upper-case))
