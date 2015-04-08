(defproject lispyway "0.1.0"

  :description "FIXME: write description"
  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.7.0-alpha6"]
                 [ring-server "0.4.0"]
                 [selmer "0.8.2"]
                 [com.taoensso/timbre "3.4.0"]
                 [com.taoensso/tower "3.0.2"]
                 [markdown-clj "0.9.65"]
                 [environ "1.0.0"]
                 [im.chit/cronj "1.4.3"]
                 [compojure "1.3.3"]
                 [ring/ring-defaults "0.1.4"]
                 [ring/ring-session-timeout "0.1.0"]
                 [ring-middleware-format "0.5.0"]
                 [noir-exception "0.2.3"]
                 [bouncer "0.3.2"]
                 [prone "0.8.1"]
                 [org.clojure/clojurescript "0.0-3178" :scope "provided"]
                 [reagent "0.5.0"]
                 [reagent-forms "0.5.0"]
                 [reagent-utils "0.1.4"]
                 [secretary "1.2.3"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [cljs-ajax "0.3.10"]
                 [org.immutant/web "2.0.0-beta2"]
                 [couchbase-clj "0.2.0"]]

  :min-lein-version "2.0.0"
  :uberjar-name "lispyway.jar"
  :repl-options {:init-ns lispyway.handler}
  :jvm-opts ["-server"]

  :main lispyway.core

  :plugins [[lein-ring "0.9.3"]
            [lein-environ "1.0.0"]
            [lein-ancient "0.6.6"]
            [lein-cljsbuild "1.0.5"]]
  

  

  :ring {:handler lispyway.handler/app
         :init    lispyway.handler/init
         :destroy lispyway.handler/destroy
         :uberwar-name "lispyway.war"}
  
  
  :clean-targets ^{:protect false} ["resources/public/js"]
  
  
  :cljsbuild
  {:builds
   {:app
    {:source-paths ["src-cljs"]
     :compiler
     {:output-dir "resources/public/js/out"
      :externs ["react/externs/react.js"]
      :optimizations :none
      :output-to "resources/public/js/app.js"
      :pretty-print true}}}}
  
  
  :profiles
  {:uberjar {:omit-source true
             :env {:production true}
              :hooks [leiningen.cljsbuild]
              :cljsbuild
              {:jar true
               :builds
               {:app
                {:source-paths ["env/prod/cljs"]
                 :compiler {:optimizations :advanced :pretty-print false}}}} 
             
             :aot :all}
   :dev {:dependencies [[ring-mock "0.1.5"]
                        [ring/ring-devel "1.3.2"]
                        [pjstadig/humane-test-output "0.7.0"]
                        [leiningen "2.5.1"]
                        [figwheel "0.2.5"]
                        [weasel "0.6.0"]
                        [com.cemerick/piggieback "0.2.0"]
                        [org.clojure/tools.nrepl "0.2.10"]]
         :source-paths ["env/dev/clj"]
         
         :plugins [[lein-figwheel "0.2.5"]]
         
          :cljsbuild
          {:builds
           {:app
            {:source-paths ["env/dev/cljs"] :compiler {:source-map true}}}} 
         
         
         :figwheel
         {:http-server-root "public"
          :server-port 3449
          :css-dirs ["resources/public/css"]
          :ring-handler lispyway.handler/app}
         
         
         :repl-options {:init-ns lispyway.repl}
         :injections [(require 'pjstadig.humane-test-output)
                      (pjstadig.humane-test-output/activate!)]
         :env {:dev true}}})
