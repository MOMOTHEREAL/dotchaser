package dev.momo.chaser.servers;

import dev.momo.chaser.servers.exception.InvalidServerNameException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class ServerCreator {
    /**
     *
     * @param serverName The server identifier
     * @param player The player hosting the server
     * @return The host's response after creation
     * @throws InvalidServerNameException if the host returns an invalidName response
     */
    public static String createServer(String serverName, String player) throws InvalidServerNameException {
        try {
            String url = "http://teambreadzone.altervista.org/dotchaser/servers/createServer.php";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            String urlParameters = "serverName=" + serverName + "&player=" + player;

            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + urlParameters);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            if (response.toString().equalsIgnoreCase("invalidName:exists")) {
                throw new InvalidServerNameException("Server already exists");
            } else if (response.toString().equalsIgnoreCase("invalidName:char")) {
                throw new InvalidServerNameException("Server name cannot contain special characters");
            } else if (response.toString().equalsIgnoreCase("invalidName:size")) {
                throw new InvalidServerNameException("Server name must be between 4 and 10 characters long");
            }

            return response.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
