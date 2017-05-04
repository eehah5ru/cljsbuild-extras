(ns leiningen.cljsbuild-extras
  (:require [robert.hooke]
            [leiningen.cljsbuild.config]))

(defn cljsbuild-extras
  "I don't do a lot."
  [project & args]
  (println "Hi!"))

(defn resolve-with-project [project m]
  ;; (println "cljsbuild options: " m)
  ;; (println "project: " project)
  (cond
    (map? m)
    (reduce-kv #(assoc %1 %2 (let [val %3]
                               (cond
                                 ;; array
                                 (or (seq? val)
                                     (vector? val))
                                 (map (fn [x] (resolve-with-project project x)) val)

                                 ;; func
                                 (and (instance? clojure.lang.IFn val)
                                      (not (keyword? val))
                                      (not (map? val)))
                                 (val project)

                                 ;; map
                                 (map? val)
                                 (resolve-with-project project val)

                                 ;; other
                                 :else
                                 val)))
               {}
               m)

    (or (seq? m)
        (vector? m))
    (map (fn [x] (resolve-with-project println x)) m)

    :else
    m))

(defn wrap-extract-options [f project]
  (let [t (f project)
        tt (resolve-with-project project t)]
    ;; (println "before: " t)
    ;; (println "after: " tt)
    tt))

(defn activate []
  (robert.hooke/add-hook #'leiningen.cljsbuild.config/extract-options
                         #'wrap-extract-options))
