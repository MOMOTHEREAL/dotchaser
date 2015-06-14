package dev.momo.chaser;

import javax.swing.*;
import java.awt.*;

public class Start {
    private static FrameDisplay frame = new FrameDisplay();
    private static JFrame f;


    public Start() {

        new DotChaser();
        DotChaser.frame = f;
        while (true) {
            frame.repaint();
            try {
                Thread.sleep(100l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        f = new JFrame(".chaser");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(frame);
        f.pack();
        f.setResizable(false);
        f.setSize(new Dimension(640, 400));
        f.setVisible(true);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        f.setLocation(dim.width/2-f.getSize().width/2, dim.height/2-f.getSize().height/2);

        new Start();
    }
}
