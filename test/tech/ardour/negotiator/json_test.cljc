(ns tech.ardour.negotiator.json-test
  (:require
    #?(:clj [clojure.data.json :as data-json])
    [clojure.test :refer [deftest is testing]]
    [tech.ardour.negotiator.json :as json]))

(deftest json-test
  (let [x {:case-conversion {:SCREAMING-KEBAB-CASE "SCREAMING-KEBAB-CASE"
                             :SCREAMING_SNAKE_CASE "SCREAMING_SNAKE_CASE"
                             :lowercase            "lowercase"
                             :UPPERCASE            "UPPERCASE"
                             :TitleCase            "TitleCase"
                             "camelCaseStr"        "camelCaseStr"
                             :camelCase            "camelCase"
                             :snake_case           "snake_case"}
           :data-structures {:vector [1 2 3 "a" "b" "c" {:d "e"}]
                             :list   '(1 2 3 "a" "b" "c" {:d "e"})
                             :string "string"
                             :number 1}}]

    (testing "writing"
      (let [json (json/write x)
            expected {"caseConversion" {"screamingKebabCase" "SCREAMING-KEBAB-CASE"
                                        "screamingSnakeCase" "SCREAMING_SNAKE_CASE"
                                        "lowercase"          "lowercase"
                                        "uppercase"          "UPPERCASE"
                                        "titleCase"          "TitleCase"
                                        "camelCaseStr"       "camelCaseStr"
                                        "camelCase"          "camelCase"
                                        "snakeCase"          "snake_case"}
                      "dataStructures" {"vector" [1 2 3 "a" "b" "c" {"d" "e"}]
                                        "list"   [1 2 3 "a" "b" "c" {"d" "e"}]
                                        "string" "string"
                                        "number" 1}}]
        (is (= expected
               #?(:clj  (data-json/read-str json)
                  :cljs (js->clj (js/JSON.parse json)))))))

    (testing "reading"
      (let [json (json/write x)
            expected {:case-conversion {:screaming-kebab-case "SCREAMING-KEBAB-CASE"
                                        :screaming-snake-case "SCREAMING_SNAKE_CASE"
                                        :lowercase            "lowercase"
                                        :uppercase            "UPPERCASE"
                                        :title-case           "TitleCase"
                                        :camel-case-str       "camelCaseStr"
                                        :camel-case           "camelCase"
                                        :snake-case           "snake_case"}
                      :data-structures {:vector [1 2 3 "a" "b" "c" {:d "e"}]
                                        :list   [1 2 3 "a" "b" "c" {:d "e"}]
                                        :string "string"
                                        :number 1}}]
        (is (= expected
               (json/read json)))))))
