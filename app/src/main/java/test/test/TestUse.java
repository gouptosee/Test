package test.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

/**
 * Created by admin on 2017/6/12.
 */

public class TestUse {
    public static void main(String[] args) {
        for (;;) {
            for(int i = 0; i < 4; i++) {
//               final String url = "http://119.23.153.105/redistest/normal/test" + (i + 1) + ".php";
				final String url = "http://119.23.153.105/redistest/instance/test" + (i + 1) + ".php";
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            Random r = new Random();
                            int s = r.nextInt(5);
                            Thread.sleep(s);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        try {
                            System.out.println(url);
                            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                            connection.connect();
                            int code = connection.getResponseCode();
                            System.out.println(code);
                            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                            String str = reader.readLine();
                            System.out.println(str);
                            reader.close();
                            connection.disconnect();
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }).start();
            }
            try {
                Thread.sleep(20);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
