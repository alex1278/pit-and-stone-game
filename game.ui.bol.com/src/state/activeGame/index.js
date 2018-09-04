import { createActions, handleActions } from "redux-actions";

const actions = createActions({
  GAMEPLAY: {
    MOVE: (playerId, stoneId, fromPitId, toPitId) => ({
      playerId,
      stoneId,
      fromPitId,
      toPitId
    }),

    UPDATE_GAME_STATE: gameState => gameState,
    END_GAME: () => {}
  }
}).gameplay;

const reducers = handleActions(
  {
    [actions.updateGameState.toString()]: (state, action) => {
      console.log(action.payload);
      return Object.assign({}, state, action.payload);
    }
  },
  // Default State
  {
    players: [],
    currentPlayerId: null,
    roundNumber: 0,
    winnerId: null,
    pits: []
  }
);

export { actions, reducers };
