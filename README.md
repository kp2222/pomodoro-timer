# pomodoro

A Pomodoro timer written in Clojurescript, Re-frame, React etc

A [Demo](https://shielded-falls-51511.herokuapp.com/) might be available here!

## Development Mode

### Run application:

```
lein clean
lein figwheel dev
```

Figwheel will automatically push cljs changes to the browser.

Wait a bit, then browse to [http://localhost:3449](http://localhost:3449).

## Production Build

deploy a uberjar where ever you want to host this.

```
lein uberjar
```

To compile clojurescript to javascript:

```
lein clean
lein cljsbuild once min
```
