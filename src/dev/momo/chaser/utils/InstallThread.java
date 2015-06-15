package dev.momo.chaser.utils;
import dev.momo.chaser.FrameDisplay;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Used for checking and fixing the installation of assets
 */
public class InstallThread extends Thread {

    public boolean done = false;
    public String percent = "0%";
    public int downloaded = 0;
    public int quota = 0;
    private FrameDisplay src;
    private boolean started = false;

    /**
     * Creates the instance of the Install thread
     * @param src The FrameDisplay source to update
     */
    public InstallThread(FrameDisplay src) {
        this.src = src;
    }

    @Override
    public synchronized void start() {
        super.start();
        started = true;
    }

    public boolean isStarted() {
        return started;
    }

    @Override
    public void run() {
        HashMap<String, Integer> files = new HashMap<>(); // The files to download, with their exact file size in bytes
        files.put("cancel_button.png", 4350);
        files.put("cancel_button_hover.png", 4384);
        files.put("createserver_button.png", 5653);
        files.put("createserver_button_hover.png", 5782);
        files.put("joinserver_button.png", 5759);
        files.put("joinserver_button_hover.png", 5882);
        files.put("start_button.png", 4168);
        files.put("start_button_hover.png", 4195);
        files.put("ping_0.png", 4136);
        files.put("ping_1.png", 3150);
        files.put("ping_2.png", 3089);
        files.put("ping_3.png", 3097);
        files.put("ping_4.png", 2994);
        files.put("title.png", 4889);

        File resFolder = new File("C:\\DotChaser\\res");
        if (!resFolder.exists())
            resFolder.mkdirs();

        List<String> toDownload = new ArrayList<>();

        for (String s : files.keySet()) {
            File destination = new File("C:\\DotChaser\\res\\" + s);
            if (!destination.exists())
                toDownload.add(s);

            quota += files.get(s);
        }

        for (String s : toDownload) {
            String url = "http://teambreadzone.altervista.org/dotchaser/" + s;
            File destination = new File("C:\\DotChaser\\res\\" + s);

            DownloadThread downloadThread = new DownloadThread(url, destination);
            downloadThread.start();

            while (!downloadThread.done) {
                System.out.print("");
            }
            downloaded += destination.length();
            percent = (int) (((double) downloaded / (double) quota) * 100) + "%";

            src.updateProgressInstallation("Downloading missing assets (" + percent + ")");
        }

        done = true;
    }

}
