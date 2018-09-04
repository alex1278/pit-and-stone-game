import { createSelector } from "reselect";

const pitSelector = state => state.activeGame.pits;
const activityStateSelector = state => state.activeGame.activityState;

export const gameStarted = createSelector(activityStateSelector, state => {
  return state === "PLAYING";
});

export const boardLayoutSelector = createSelector(pitSelector, pits => {
  let layout = {};
  pits.forEach(pit => {
    if (typeof layout[pit.ownerId] === "undefined") {
      layout[pit.ownerId] = {
        small: [],
        large: {}
      };
    }
    if (pit.type.toLowerCase() === "small") {
      layout[pit.ownerId].small.push(pit);
    } else {
      layout[pit.ownerId].large = pit;
    }
  });
  let surface = {};
  Object.keys(layout).map((key, index) => {
    surface["player" + (index + 1)] = {
      small: layout[key].small,
      large: layout[key].large
    };
  });
  return surface;
});
