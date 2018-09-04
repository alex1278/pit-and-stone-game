export default class BolGameClient {
  socket;
  constructor(gameId, handler) {
    this.socket = new WebSocket("ws://localhost:4000/games/" + gameId);
    this.socket.onopen = () => handler.onReady();
    this.socket.addEventListener("message", function(evt) {
      const event = JSON.parse(evt.data);
      if (event.hasError) {
        handler.onError(event.errorMessage);
        return;
      }
      handler.onMessage(event.gameState);
    });
    this.socket.onerror = error => handler.onError(error);
  }

  close() {
    this.socket.close();
  }

  send(message) {
    this.socket.send(message);
  }
}
