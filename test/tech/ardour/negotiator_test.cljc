(ns tech.ardour.negotiator-test
  (:require
    [clojure.set :as set]
    [clojure.string :as str]
    [clojure.test :refer [deftest is testing]]
    [tech.ardour.negotiator :as n]))

(deftest transform-keys-test
  (let [data {:a             1
              :within-vector [{:map-in-vec true}]
              :nested        {:map :transformed}
              "string-key"   "transformed-if-required"}]

    (testing "transforms all keys by passing them through the specified transformation fn"
      (let [actual (n/transform-keys (comp str/upper-case symbol) data)
            expected {"A"             1
                      "WITHIN-VECTOR" [{"MAP-IN-VEC" true}]
                      "NESTED"        {"MAP" :transformed}
                      "STRING-KEY"    "transformed-if-required"}]
        (is (= expected actual))))

    (testing "transforms keys matching pred by passing them through the specified transformation fn"
      (let [actual (n/transform-keys string? keyword data)
            expected (set/rename-keys data {"string-key" :string-key})]
        (is (= expected actual))))))
