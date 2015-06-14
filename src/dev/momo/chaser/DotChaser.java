package dev.momo.chaser;

import dev.momo.chaser.utils.PingThread;

import javax.swing.*;

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

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public long getPing() {
        return ping;
    }

    public void setPing(long ping) {
        this.ping = ping;
    }

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
