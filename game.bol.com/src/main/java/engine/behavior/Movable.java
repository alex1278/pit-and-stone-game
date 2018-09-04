package engine.behavior;

public interface Movable {

    interface Starter<TStart, TEnd> {
        Ender<TEnd> from(TStart origin) throws Exception;
    }

    interface Ender<T> {
        void to(T destination);
    }
}
