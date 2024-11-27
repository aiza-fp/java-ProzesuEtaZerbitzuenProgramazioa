package sarekoZerbitzuakSortzea_4.http;

import java.io.*;
import java.net.*;

public class HTTP {
    public static void main(String[] args) {
        String urlString = "https://www.google.es/";

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            System.out.println("HTTP erantzun kodea: " + responseCode);

            if (responseCode == 200) { // 200: OK
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine).append("\n");
                }

                in.close();
                System.out.println("Edukia:\n" + content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
