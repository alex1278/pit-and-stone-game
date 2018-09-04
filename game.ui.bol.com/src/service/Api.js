import http from "./http";

export default class Api {
  static getGames() {
    return http.get("/games");
  }

  static createGame() {
    return http.post("/game");
  }
}
