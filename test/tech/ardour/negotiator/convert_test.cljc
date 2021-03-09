(ns tech.ardour.negotiator.convert-test
  (:require
    [clojure.test :refer [deftest is testing]]
    [tech.ardour.negotiator.convert :as convert]))

(deftest ->camelCase-test
  (let [->camelCase convert/->camelCase]

    (testing "from lodash"
      (is (= "fooBar" (->camelCase "Foo Bar")))
      (is (= "fooBar" (->camelCase "__FOO_BAR__")))
      (is (= "fooBar" (->camelCase "--foo-bar--")))

      (testing "should work with numbers"
        (is (= "12Feet" (->camelCase "12 feet")))
        (is (= "enable6HFormat" (->camelCase "enable 6h format")))
        (is (= "enable24HFormat" (->camelCase "enable 24H format")))
        (is (= "tooLegit2Quit" (->camelCase "too legit 2 quit")))
        (is (= "walk500Miles" (->camelCase "walk 500 miles")))
        (is (= "xhr2Request" (->camelCase "xhr2 request"))))

      (testing "should handle acronyms"
        (is (= "safeHtml" (->camelCase "safe HTML")))
        (is (= "safeHtml" (->camelCase "safeHTML")))
        (is (= "escapeHtmlEntities" (->camelCase "escape HTML entities")))
        (is (= "escapeHtmlEntities" (->camelCase "escapeHTMLEntities")))
        (is (= "xmlHttpRequest" (->camelCase "XMLHttpRequest")))
        (is (= "xmlHttpRequest" (->camelCase "XmlHTTPRequest"))))

      (testing "should handle hyphenated values"
        (is (= "requestId" (->camelCase "request-id")))))))
