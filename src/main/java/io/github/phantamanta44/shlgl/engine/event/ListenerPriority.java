package io.github.phantamanta44.shlgl.engine.event;

/**
 * Delineates the levels of listener priority.
 * @author Evan Geng
 */
public enum ListenerPriority {

    /**
     * Highest priority; listener gets executed first.
     */
    HIGHEST,

    /**
     * High priority; listener gets executed after {@link #HIGHEST}.
     */
    HIGH,

    /**
     * Medium priority; listener gets executed after {@link #HIGH}.
     */
    NORMAL,

    /**
     * Low priority; listener gets executed after {@link #NORMAL}.
     */
    LOW,

    /**
     * Lowest priority; listener gets executed last.
     */
    LOWEST

}
