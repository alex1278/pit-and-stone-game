package engine.behavior;

import engine.utility.IdentityCreator;

import java.util.Objects;

public class Identifiable {

    public final String id;

    public Identifiable(String prefix, int idLength) {
        id = prefix + "." + createId(idLength);
    }

    private String createId(int length) {
        return IdentityCreator.get().newId(length);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identifiable that = (Identifiable) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
