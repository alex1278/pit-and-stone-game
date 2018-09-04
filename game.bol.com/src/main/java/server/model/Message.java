package server.model;

public class Message<T> {
    public Boolean hasError;
    public String errorMessage;
    public T gameState;

    public Message(Boolean hasError, String errorMessage) {
        this.hasError = hasError;
        this.errorMessage = errorMessage;
        this.gameState = null;
    }

    public Message(T gameState) {
        hasError = false;
        errorMessage = "";
        this.gameState = gameState;
    }
}
