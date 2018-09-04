import { createActions, handleActions } from "redux-actions";

const actions = createActions({
  BUSYSTATE: {
    LOADING_GAMES: (busy = false) => busy,
    CREATING_GAME: (busy = false) => busy,
    STARTING_GAME: (busy = false) => busy
  }
}).busystate;

const reducers = handleActions(
  {
    [actions.loadingGames.toString()]: (state, action) =>
      Object.assign({}, state, { loadingGames: action.payload }),
    [actions.creatingGame.toString()]: (state, action) =>
      Object.assign({}, state, { creatingGame: action.payload }),
    [actions.startingGame.toString()]: (state, action) =>
      Object.assign({}, state, { startingGame: action.payload })
  },
  // Default State
  {
    loadingGames: false,
    creatingGame: false,
    startingGame: false
  }
);

export { actions, reducers };
