import { call, put, takeLatest } from "redux-saga/effects";
import { Api } from "../service";
import { GameActions, BusyActions } from "../state";

export function* handle() {
  yield put(BusyActions.creatingGame(true));
  const result = yield call(Api.createGame);
  yield put(GameActions.addGameToList(result.data));
  yield put(BusyActions.creatingGame(false));
}

export function* createGame() {
  yield takeLatest(GameActions.createGame.toString(), handle);
}
