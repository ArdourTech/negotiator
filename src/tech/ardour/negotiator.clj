(ns tech.ardour.negotiator
  (:require
    [clojure.walk :as walk]
    [tech.ardour.negotiator.edn :as edn]
    [tech.ardour.negotiator.json :as json]
    [clojure.string :as str]))

(defn- mime-and-charset [content-type]
  (-> content-type
      (str/lower-case)
      (str/split #";")
      (map str/trim)))

(defn- mime-type [content-type]
  (first (mime-and-charset content-type)))

;TODO Support charsets
(defmulti decode (fn [content-type body]
                   (mime-type content-type)))

(defmulti encode (fn [content-type body]
                   (mime-type content-type)))

(defmethod decode nil [content-type body]
  (throw (ex-info "No decoder defined" {:content-type content-type})))

(defmethod encode :default [content-type body]
  (throw (ex-info "No encoder defined" {:content-type content-type})))

(defmethod decode json/mime-type [_ body]
  (json/read body))

(defmethod encode json/mime-type [_ body]
  (json/write body))

(defmethod decode edn/mime-type [_ body]
  (edn/read body))

(defmethod encode edn/mime-type [_ body]
  (edn/write body))

(defn transform-keys
  "Recursively transforms all map keys through the given transformer `f`, with optional predicate matching."
  ([transform-fn m] (transform-keys any? transform-fn m))
  ([pred? transform-fn m]
   (let [f (fn [[k v :as kv]] (if (pred? k) [(transform-fn k) v] kv))]
     (walk/postwalk (fn [x] (if (map? x) (into {} (map f x)) x)) m))))
