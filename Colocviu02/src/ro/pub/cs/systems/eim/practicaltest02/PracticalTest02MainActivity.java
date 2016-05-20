package ro.pub.cs.systems.eim.practicaltest02;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class PracticalTest02MainActivity extends Activity {
	
	TextView serverTextView, clientTextView, response;
	EditText serverEditText, clientAddress,clientPort;
	Button serverConnect, Request;
	
	private ButtonListener buttonListener = new ButtonListener();
	
	private ClientThread clientThread = new ClientThread(clientAddress.getText().toString(),Integer.parseInt( clientPort.getText().toString()),response);
	private ServerThread serverThread = new ServerThread(Integer.parseInt( serverEditText.getText().toString()));
	 private class ButtonListener implements Button.OnClickListener {

	        @Override
	        public void onClick(View view) {
	            switch (view.getId()) {
	                case R.id.server_connect_button:
	                	serverThread.start();
	                    break;
	                case R.id.client_button:
	                   clientThread.start();
	                    break;
	            }
	        }
	    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02_main);
        
        serverConnect = (Button)findViewById(R.id.server_connect_button);
        serverConnect.setOnClickListener(buttonListener);
        
        Request = (Button)findViewById(R.id.client_button);
        Request.setOnClickListener(buttonListener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.practical_test02_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
