import React from "react";
import ReactDOM from "react-dom";

import { Router, Route, Switch } from "react-router-dom";
import { Provider } from "react-redux";

import createBrowserHistory from "history/createBrowserHistory";
import registerServiceWorker from "./registerServiceWorker";

import { routes } from "./constant";
import store from "./store";
import { GameSurface, GameList } from "./component";

import "./index.css";

const history = createBrowserHistory();

ReactDOM.render(
  <Provider store={store}>
    <Router history={history}>
      <div className="backdrop">
        <Switch>
          <Route path={routes.HOME} exact={true} component={GameList} />
          <Route path={routes.GAME} exact={true} component={GameSurface} />
        </Switch>
      </div>
    </Router>
  </Provider>,
  document.getElementById("root")
);

registerServiceWorker();
