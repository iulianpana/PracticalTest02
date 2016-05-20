package ro.pub.cs.systems.eim.practicaltest02;
    import android.util.Log;
        import android.widget.TextView;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.PrintWriter;
        import java.net.Socket;

        import ro.pub.cs.systems.eim.practicaltest02.Constants;
        import ro.pub.cs.systems.eim.practicaltest02.Utilities;

public class ClientThread extends Thread {

    private String address;
    private int port;
    
    private TextView responseView;

    private Socket socket;

    public ClientThread(
            String address,
            int port,
            TextView responseView) {
        this.address = address;
        this.port = port;
       
        this.responseView = responseView;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(address, port);
            if (socket == null) {
                Log.e(Constants.TAG, "[CLIENT THREAD] Could not create socket!");
            }

            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);
            if (bufferedReader != null && printWriter != null) {

                String data;
                while ((data = bufferedReader.readLine()) != null) {
                    final String finalizedWeatherInformation = data;
                    responseView.post(new Runnable() {
                        @Override
                        public void run() {
                            responseView.append(finalizedWeatherInformation + "\n");
                        }
                    });
                }
            } else {
                Log.e(Constants.TAG, "[CLIENT THREAD] BufferedReader / PrintWriter are null!");
            }
            socket.close();
        } catch (IOException ioException) {
            Log.e(Constants.TAG, "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
            if (Constants.DEBUG) {
                ioException.printStackTrace();
            }
        }
    }

}