package game;

public interface Playable {

    /**
     * This method makes the step method
     * step along the Playable
     */
    void play();

    /**
     * This method makes the step method
     * do an alternative action
     */
    void pause();

    /**
     * After play is called, this method will
     * step along the Playable, but after pause is
     * called, the step method does some alternative
     * action
     */
    void step();
}
