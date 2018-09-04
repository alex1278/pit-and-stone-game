import sagaRouter from "./router.saga";
import { createGame } from "./creategame.saga";
import { all } from "redux-saga/effects";

export default function* rootSaga() {
  yield all([sagaRouter(), createGame()]);
}
