import { router } from "redux-saga-router";
import createBrowserHistory from "history/createBrowserHistory";
import { call, fork, put, cancel, cancelled } from "redux-saga/effects";

import { routes } from "../constant";
import { ActiveGameActions, GameActions, BusyActions } from "../state";
import { Api } from "../service";

import { playGame } from "./playgame.saga";

const routeSagas = {
  [routes.HOME]: function* home() {
    yield put(BusyActions.loadingGames(true));
    const games = yield call(Api.getGames);
    yield put(GameActions.displayGameList(games.data));
    yield put(BusyActions.loadingGames(false));
  },
  [routes.GAME]: function* game(param) {
    console.log(param);
    const task = yield fork(playGame, param.gameId);
    if (yield cancelled()) {
      yield put(ActiveGameActions.endGame);
      cancel(task);
    }
  }
};

export default function* sagaRouter() {
  yield fork(router, createBrowserHistory(), routeSagas);
}
