import createSagaMiddleWare from "redux-saga";
import { createStore, applyMiddleware, compose } from "redux";

import sagas from "../saga";
import { reducers } from "../state";

const sagaMiddleWare = createSagaMiddleWare();

let store;

//In development - expose redux to redux dev tools and assign the store to the window for debugging
if (process.env.NODE_ENV === "development") {
  const composeEnhancers =
    (window && window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__) || compose;
  store = createStore(
    reducers,
    composeEnhancers(applyMiddleware(sagaMiddleWare))
  );
  window.store = store;
} else {
  store = createStore(reducers, applyMiddleware(sagaMiddleWare));
}
sagaMiddleWare.run(sagas);

export default store;
