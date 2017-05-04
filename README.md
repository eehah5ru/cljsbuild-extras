# cljsbuild-extras

A Leiningen plugin that extends lein-cljsbuild functionality

## Usage

Use this for user-level plugins:

Put `[cljsbuild-extras "0.1.0"]` into the `:plugins` vector of your `:user`
profile.

Use this for project-level plugins:

Put `[cljsbuild-extras "0.1.0"]` into the `:plugins` vector of your project.clj.

Now you can use functions as values of `:cljsbuild` map. Function takes one arg: `project`:

    :cljsbuild {:builds {:app {:id "uberjar"
                               :source-paths ["src/cljs"]
                               :compiler {:main example-project.core
                               :output-to #=(eval (fn [p] (str "resources/public/js/compiled/app_" (:version p) ".js")))}}}}

## License

Copyleft (c) 2017 Nicolay Spesivtsev

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
