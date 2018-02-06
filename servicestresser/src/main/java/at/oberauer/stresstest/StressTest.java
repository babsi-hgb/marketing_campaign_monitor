package at.oberauer.stresstest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by michael on 07.01.18.
 */
public class StressTest implements Runnable{

    private String urlString = "";
    private long numRequests = 0;
    private int testLengthSeconds;


    public StressTest(String urlString, int testLengthSeconds){
        this.urlString = urlString;
        this.testLengthSeconds = testLengthSeconds;
    }

    public long getNumRequests(){
        return numRequests;
    }

    @Override
    public void run() {
        long testStart = System.currentTimeMillis();
        try {
            while(System.currentTimeMillis() < testStart + testLengthSeconds*1000) {
                URL url = new URL(urlString);
                URLConnection conn = url.openConnection();
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                conn.getInputStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null)
                    ;
                in.close();
                numRequests++;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
