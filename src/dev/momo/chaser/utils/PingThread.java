package dev.momo.chaser.utils;

import dev.momo.chaser.DotChaser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Pings the host to test the connection
 */
public class PingThread extends Thread {

    @Override
    public void run() {
        while (true) {
            try {
                URL url = new URL("http://teambreadzone.altervista.org");
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setConnectTimeout(2000);
                long startTime = System.currentTimeMillis();
                urlConnection.connect();
                long endTime = System.currentTimeMillis();
                long ping = endTime-startTime;
                DotChaser.getInstance().setPing(ping);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                long ping = 2000l;
                DotChaser.getInstance().setPing(ping);
            }
        }
    }
}
