(ns tech.ardour.negotiator
  (:require
    [tech.ardour.negotiator.json :as json]
    [tech.ardour.negotiator.edn :as edn]))

(defmulti decode (fn [content-type body]
                   content-type))

(defmulti encode (fn [content-type body]
                   content-type))

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
