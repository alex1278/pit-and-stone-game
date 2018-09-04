package engine.behavior;

import java.util.List;

public interface Modifyable<Item> {
    void add(Item item) throws Error;
    Item remove(Item item);
    List<Item> removeAll();
}
