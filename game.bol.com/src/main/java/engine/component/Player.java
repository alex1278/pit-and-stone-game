package engine.component;

import engine.behavior.Identifiable;

public class Player extends Identifiable {

    public final String name;

    public Player(String name) {
        super("PLYR", 4);
        this.name = name;
    }



    @Override
    public String toString() {
        return "PLAYER - " + id + " - " + name;
    }
}
