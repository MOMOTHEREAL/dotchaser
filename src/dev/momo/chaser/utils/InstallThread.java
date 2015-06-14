package dev.momo.chaser.utils;

import dev.momo.chaser.DotChaser;
import dev.momo.chaser.Stage;

import java.io.File;
import java.io.IOException;

public class InstallThread extends Thread {

    public boolean done = false;
    public String percent = "";

    @Override
    public void run() {
        File xFile = new File("C:\\DotChaser\\x.txt");
        boolean installed = xFile.exists();

        if (!installed) {
            xFile.getParentFile().mkdirs();

            File resFolder = new File("C:\\DotChaser\\res");
            resFolder.mkdirs();

            String[] files = new String[] {
                    "cancel_button.png",
                    "cancel_button_hover.png",
                    "createserver_button.png",
                    "createserver_button_hover.png",
                    "joinserver_button.png",
                    "joinserver_button_hover.png",
                    "start_button.png",
                    "start_button_hover.png",
                    "ping_0.png",
                    "ping_1.png",
                    "ping_2.png",
                    "ping_3.png",
                    "ping_4.png",
                    "title.png"
            };

            int quota = 62000;
            int downloaded = 0;

            for (String s : files) {
                String url = "http://teambreadzone.altervista.org/dotchaser/" + s;
                File destination = new File("C:\\DotChaser\\res\\" + s);

                DownloadThread downloadThread = new DownloadThread(url, destination);
                downloadThread.start();

                while (!downloadThread.done) {
                    System.out.print("");
                }


                downloaded += destination.length();

                percent = (int) (((double) downloaded / (double) quota) * 100) + "%";
            }

            try {
                xFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }


        } else {
            DotChaser.getInstance().setStage(Stage.MAIN_MENU);
        }


        done = true;
    }
}
