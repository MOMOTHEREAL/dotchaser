package dev.momo.chaser.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * Used when downloading a file from the server into a folder
 */
public class DownloadThread extends Thread {
    private String url;
    private File destination;
    public boolean done = false;

    /**
     * Creates a new DownloadThread instance
     * @param url The address of the file to download
     * @param destination The destination file (after download)
     */
    public DownloadThread(String url, File destination) {
        this.url = url;
        this.destination = destination;
    }

    @Override
    public void run() {
        try {
            URL website = new URL(url);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(destination);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

            done = true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
