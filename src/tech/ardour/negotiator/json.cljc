(ns tech.ardour.negotiator.json
  (:refer-clojure :exclude [read])
  (:require
    #?(:clj  [jsonista.core :as j]
       :cljs [tech.ardour.negotiator.js :as js])
    [tech.ardour.negotiator.convert :as convert]))

(def mime-type "application/json")

#?(:clj (def ^:private mapper
          (j/object-mapper
            {:encode-key-fn (comp convert/->camelCase name)
             :decode-key-fn (comp keyword convert/->kebab-case)})))

(defn read
  ([^String s]
   (read s #?(:clj mapper :cljs nil)))
  ([^String s opts]
   #?(:clj  (j/read-value s opts)
      :cljs (-> s
                (js/JSON.parse)
                (js/read opts)))))

(defn write
  ([o] (write o #?(:clj mapper :cljs nil)))
  ([o opts]
   #?(:clj  (j/write-value-as-string o opts)
      :cljs (-> o
                (js/write opts)
                (js/JSON.stringify)))))
