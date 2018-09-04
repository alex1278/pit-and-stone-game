import { put, take } from "redux-saga/effects";
import { BolGameClient } from "../service";
import { ActiveGameActions, BusyActions } from "../state";
import store from "../store";

export function* playGame(gameId) {
  yield put(BusyActions.startingGame(true));
  const client = new BolGameClient(gameId, {
    onReady: () => store.dispatch(BusyActions.startingGame(false)),
    onMessage: message =>
      store.dispatch(ActiveGameActions.updateGameState(message)),
    onError: message => alert(message)
  });
  yield take(ActiveGameActions.endGame.toString());
  client.close();
}
