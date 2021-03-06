package dev.momo.chaser;
import dev.momo.chaser.utils.InstallThread;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

/**
 * The main rendering utility class (the JPanel)
 */
public class FrameDisplay extends JPanel {
    JLabel descriptionLabel = new JLabel("v1.0");
    JLabel pingLabel = new JLabel("0 ms");
    JLabel installingLabel = new JLabel("Verifying installation");
    static boolean click_MAIN_MENU = false; // The initialization state of the Main Menu mouse listener
    static boolean click_SERVER_ACTION = false; // The initialization state of the Server Action mouse listener

    final InstallThread installThread = new InstallThread(this);

    /**
     * Sets up the JPanel and its content
     */
    public FrameDisplay() throws HeadlessException {
        this.setSize(640, 400);
        this.setVisible(true);
        this.setLayout(null);

        descriptionLabel.setLocation(12, (int) getSize().getHeight() - 60);
        descriptionLabel.setSize(600, 30);
        add(descriptionLabel);

        pingLabel.setLocation((int) getSize().getWidth() - 82, 30);
        pingLabel.setSize(100, 30);
        pingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(pingLabel);

        installingLabel.setLocation(0, 140);
        installingLabel.setSize(640, 30);
        installingLabel.setFont(installingLabel.getFont().deriveFont(14f));
        installingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(installingLabel);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D)g;

        if (DotChaser.getInstance().getStage() == Stage.INSTALL) {
            draw_INSTALL(g2d);
        } else if (DotChaser.getInstance().getStage() == Stage.MAIN_MENU) {
            draw_MAIN_MENU(g2d);
        } else if (DotChaser.getInstance().getStage() == Stage.SERVER_ACTION) {
            draw_SERVER_ACTION(g2d);
        }

    }

    public void updateProgressInstallation(String value) {
        installingLabel.setText(value);
        installingLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }

    public void draw_INSTALL(Graphics2D g2d) {
        updateProgressInstallation("<html>Downloading missing assets (" + installThread.percent + ")");

        descriptionLabel.setVisible(false);
        pingLabel.setVisible(false);
        installingLabel.setVisible(true);

        if (!installThread.isStarted())
            installThread.start();

        if (installThread.done) {
            System.out.println(installThread.downloaded);
            DotChaser.getInstance().setStage(Stage.MAIN_MENU);
        }
    }

    public void draw_MAIN_MENU(Graphics2D g2d) {
        Point mouse = getMousePosition();

        descriptionLabel.setVisible(true);
        descriptionLabel.setText("v1.0");
        pingLabel.setVisible(true);
        installingLabel.setVisible(false);
        pingLabel.setText(DotChaser.getInstance().getPing() + " ms");

        if (DotChaser.getInstance().getPing() >= 2000l)
            pingLabel.setText("Error");

        try {
            // Image title = ImageIO.read(new File("C:\\DotChaser\\res\\title.png"));
            Image startButton = ImageIO.read(new File("C:\\DotChaser\\res\\start_button.png"));
            Image ping = ImageIO.read(new File("C:\\DotChaser\\res\\ping_" + DotChaser.getInstance().getPingLevel() + ".png"));

            final Rectangle startButtonMask = new Rectangle();
            startButtonMask.setBounds(((int) getSize().getWidth() - startButton.getWidth(null)) / 2 + 2, 150, 400, 75);

            if (mouse != null) {
                if (startButtonMask.contains(mouse.x, mouse.y))
                    startButton = ImageIO.read(new File("C:\\DotChaser\\res\\start_button_hover.png"));
            }

            // g2d.drawImage(title, ((int)getSize().getWidth() - title.getWidth(null))/2  + 2, 10, null);
            g2d.drawImage(startButton, ((int)getSize().getWidth() - startButton.getWidth(null))/2 + 2 , 150, null);
            g2d.drawImage(ping, (int) getSize().getWidth() - 50, 0, 50, 40, null);

            if (!click_MAIN_MENU)
                addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    click_MAIN_MENU = true;

                    Point mouse = getMousePosition();
                    if (mouse != null) {
                        if (startButtonMask.contains(mouse.x, mouse.y)) {
                            DotChaser.getInstance().setStage(Stage.SERVER_ACTION);
                            //try {
                            //    String res = ServerCreator.createServer("testing", "pl");
                            //    System.out.println(res);
                            //} catch (InvalidServerNameException e1) {
                            //    System.out.println(e1.getMessage());
                            //}
                        }
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw_SERVER_ACTION(Graphics2D g2d) {
        Point mouse = getMousePosition();
        descriptionLabel.setVisible(true);
        pingLabel.setVisible(false);
        installingLabel.setVisible(false);
        descriptionLabel.setText("");

        try {
            Image createServer = ImageIO.read(new File("C:\\DotChaser\\res\\createserver_button.png"));
            Image joinServer = ImageIO.read(new File("C:\\DotChaser\\res\\joinserver_button.png"));
            Image cancel = ImageIO.read(new File("C:\\DotChaser\\res\\cancel_button.png"));

            final Rectangle createServerMask = new Rectangle();
            createServerMask.setBounds(((int) getSize().getWidth() - createServer.getWidth(null)) / 2 + 2, 50, 400, 75);
            final Rectangle joinServerMask = new Rectangle();
            joinServerMask.setBounds(((int) getSize().getWidth() - joinServer.getWidth(null)) / 2 + 2, 150, 400, 75);
            final Rectangle cancelMask = new Rectangle();
            cancelMask.setBounds(((int) getSize().getWidth() - cancel.getWidth(null)) / 2 + 2, 250, 400, 75);

            if (mouse != null) {
                if (createServerMask.contains(mouse.x, mouse.y)) {
                    createServer = ImageIO.read(new File("C:\\DotChaser\\res\\createserver_button_hover.png"));
                    descriptionLabel.setText("Create a new server to get your friends connected");
                }

                else if (joinServerMask.contains(mouse.x, mouse.y)) {
                    joinServer = ImageIO.read(new File("C:\\DotChaser\\res\\joinserver_button_hover.png"));
                    descriptionLabel.setText("Connect to servers around the world");
                }

                else if (cancelMask.contains(mouse.x, mouse.y)) {
                    cancel = ImageIO.read(new File("C:\\DotChaser\\res\\cancel_button_hover.png"));
                    descriptionLabel.setText("Return to main menu");
                } else {
                    descriptionLabel.setText("");
                }
            } else {
                descriptionLabel.setText("");
            }

            g2d.drawImage(createServer, ((int)getSize().getWidth() - createServer.getWidth(null))/2 + 2 , 50, null);
            g2d.drawImage(joinServer, ((int)getSize().getWidth() - joinServer.getWidth(null))/2 + 2 , 150, null);
            g2d.drawImage(cancel, ((int)getSize().getWidth() - cancel.getWidth(null))/2 + 2 , 250, null);

            if (!click_SERVER_ACTION)
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);

                        click_SERVER_ACTION = true;

                        Point mouse = getMousePosition();
                        if (mouse != null) {
                            if (cancelMask.contains(mouse.x, mouse.y))
                                DotChaser.getInstance().setStage(Stage.MAIN_MENU);
                        }
                    }
                });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
