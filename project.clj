(defproject tech.ardour/negotiator "0.1.0-SNAPSHOT"
  :description "Ardour Tech Content Negotiation Library"
  :url "https://github.com/ArdourTech/negotiator"
  :license {:name         "Eclipse Public License - v 1.0"
            :url          "http://www.eclipse.org/legal/epl-v10.html"
            :distribution :repo
            :comments     "same as Clojure"}
  :dependencies [[org.clojure/clojure "1.10.1" :scope "provided"]
                 [metosin/jsonista "0.3.0"]]
  :profiles {:dev  {:dependencies [[org.clojure/clojurescript "1.10.597" :scope "provided"]]}
             :test {:dependencies [[org.clojure/data.json "1.0.0"]
                                   [lambdaisland/kaocha "1.0.732"]]}}
  :source-paths ["src"]
  :test-paths ["test"]
  :deploy-repositories [["clojars" {:url           "https://clojars.org/repo"
                                    :sign-releases false}]]
  :aliases {"test" ["with-profile" "+test" "run" "-m" "kaocha.runner"]})
