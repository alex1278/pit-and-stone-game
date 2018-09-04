import { combineReducers } from "redux";

import { reducers as busystate } from "./busystate";
import { reducers as activeGame } from "./activeGame";
import { reducers as games } from "./games";

const rootReducer = combineReducers({
  busystate,
  activeGame,
  games
});

export default rootReducer;
