import { createActions, handleActions } from "redux-actions";

const actions = createActions({
  GAMES: {
    DISPLAY_GAME_LIST: games => games,
    CREATE_GAME: () => {},
    ADD_GAME_TO_LIST: game => game
  }
}).games;

const reducers = handleActions(
  {
    [actions.displayGameList.toString()]: (state, action) => {
      return action.payload;
    },
    [actions.addGameToList.toString()]: (state, action) => [
      ...state,
      action.payload
    ]
  },
  // Default State
  []
);

export { actions, reducers };
