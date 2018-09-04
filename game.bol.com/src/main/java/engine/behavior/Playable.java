package engine.behavior;

import org.jetbrains.annotations.Nullable;

public interface Playable<Action, State> {

    /**
     * start should be invoked to begin a a game
     * If for some reason the state of the playable instance prevents it
     * from starting, the call to start will throw an Error
     *
     * @throws Exception
     */
    State start() throws Exception;

    /**
     * play is called with an action
     * and in event of successful invocation, the updated state of the game
     * is returned, otherwise it will throw an error
     *
     * @param action
     * @return
     * @throws Exception
     */
    State play(Action action) throws Exception;

    /**
     * end is called to the complete the respective Playable instance,
     * and will throw an Exception if play cannot be properly
     * ended
     * @throws Exception
     */
    State end() throws Exception;

    /**
     * the response from a call to isOver is a boolean indicating if the
     * playable instance is over
     *
     * @return boolean
     */
    boolean isOver();

    /**
     * pits return the current state of the game
     * @return
     */
    @Nullable State currentState();
}
