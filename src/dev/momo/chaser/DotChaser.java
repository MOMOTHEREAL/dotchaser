package dev.momo.chaser;

import dev.momo.chaser.utils.PingThread;

import javax.swing.*;

/**
 * The games main data, used by all requiring classes
 */
public class DotChaser {
    private static DotChaser instance;
    public static JFrame frame;
    private Stage stage;
    private long ping = 1000l;

    public DotChaser() {
        instance = this;
        stage = Stage.INSTALL;
        new PingThread().start();
    }

    public static DotChaser getInstance() {
        return instance;
    }

    /**
     * Gets the active stage the application is in (used for displaying)
     * @return the active Stage to render
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Changes the active stage to render
     * @param stage the new Stage to render
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Gets the last ping result from PingThread
     * @return the last ping result, as a long
     */
    public long getPing() {
        return ping;
    }

    /**
     * Updates the last ping result, only used by PingThread
     * @param ping the new ping result to set to
     */
    public void setPing(long ping) {
        this.ping = ping;
    }

    /**
     * The ping level, from 0 to 4, using the last ping result
     * @return a digit between 0 and 4, representing the ping quality
     */
    public int getPingLevel() {
        if (getPing() < 50l) {
            return 4;
        } else if (getPing() < 70l) {
            return 3;
        } else if (getPing() < 100l) {
            return 2;
        } else if (getPing() < 200l) {
            return 1;
        } else {
            return 0;
        }
    }
}
