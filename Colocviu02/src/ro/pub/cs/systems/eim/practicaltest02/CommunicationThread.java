package ro.pub.cs.systems.eim.practicaltest02;

/**
 * Created by student on 5/20/16.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class CommunicationThread extends Thread {

    private ServerThread serverThread;
    private Socket socket;

    public CommunicationThread(ServerThread serverThread, Socket socket) {
        this.serverThread = serverThread;
        this.socket = socket;
    }
    static long last_time = -1;
    @Override
    public void run() {

        if (socket != null) {
            try {
                BufferedReader bufferedReader = Utilities.getReader(socket);
                PrintWriter printWriter = Utilities.getWriter(socket);
                if ( printWriter != null) {


                    ClockString data = serverThread.getData();
                    ClockString weatherForecastInformation = null;
                    if (true) {

                        {
                            Log.i(Constants.TAG, "[COMMUNICATION THREAD] Getting the information from the webservice...");
                            HttpClient httpClient = new DefaultHttpClient();
                            HttpGet httpGet = new HttpGet(Constants.WEB_SERVICE_ADDRESS);
                            List<NameValuePair> params = new ArrayList<NameValuePair>();
                            ResponseHandler<String> responseHandler = new BasicResponseHandler();
                            String content = httpClient.execute(httpGet, responseHandler);

                            Log.i(Constants.TAG, content);
                            if (last_time ==-1)
                            {
                            data = new ClockString(content);
                            serverThread.setData(data);
                            printWriter.println(data);
                            printWriter.flush();
                            last_time=  System.currentTimeMillis();
                            }
                            else if ( System.currentTimeMillis()-last_time <60*1000)
                            {
                                String timp = Integer.toString( (int)(60-(System.currentTimeMillis()-last_time)/1000));
                                data = new ClockString("You have to wait for " +  timp + " seconds");
                                serverThread.setData(data);
                                printWriter.println(data);
                                printWriter.flush();

                            }

                        }



                    }
                } else {
                    Log.e(Constants.TAG, "[COMMUNICATION THREAD] BufferedReader / PrintWriter are null!");
                }
                socket.close();
            } catch (IOException ioException) {
                Log.e(Constants.TAG, "[COMMUNICATION THREAD] An exception has occurred: " + ioException.getMessage());
                if (Constants.DEBUG) {
                    ioException.printStackTrace();
                }
            }
        } else {
            Log.e(Constants.TAG, "[COMMUNICATION THREAD] Socket is null!");
        }
    }

}
