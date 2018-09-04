package engine.component;

import engine.behavior.Identifiable;

public class Stone extends Identifiable {

    public Stone() {
        super("ST", 4);
    }

    @Override
    public String toString() {
        return "STONE - " + id;
    }
}
