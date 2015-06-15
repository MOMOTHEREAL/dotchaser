package dev.momo.chaser;

/**
 * Stages are the value of the current rendering task
 */
public enum Stage {
    /**
     * The verification and application of the current application
     */
    INSTALL,

    /**
     * The start menu
     */
    MAIN_MENU,

    /**
     * The server action menu (connect/create)
     */
    SERVER_ACTION,
}
