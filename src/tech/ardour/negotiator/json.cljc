(ns tech.ardour.negotiator.json
  (:refer-clojure :exclude [read])
  (:require
    [tech.ardour.negotiator.convert :as convert]
    #?(:clj [jsonista.core :as j])))

(def mime-type "application/json")

#?(:clj (def ^:private mapper
          (j/object-mapper
            {:encode-key-fn (comp convert/->camelCase name)
             :decode-key-fn (comp keyword convert/->kebab-case)})))

(defn read [^String s]
  #?(:clj  (j/read-value s mapper)
     :cljs (->> s
                (js/JSON.parse)
                (js->clj))))

(defn write [o]
  #?(:clj  (j/write-value-as-string o mapper)
     :cljs (->> o
                (clj->js)
                js/JSON.stringify)))
