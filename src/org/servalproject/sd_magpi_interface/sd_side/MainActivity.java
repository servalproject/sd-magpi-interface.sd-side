package org.servalproject.sd_magpi_interface.sd_side;

import java.io.InputStream;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

	TextView uuidTextView = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        uuidTextView = (TextView) findViewById(R.id.lastReceivedUUID);
        ReceiveNewMagpiRecord.setMainActivity(this);
        
        final Button button = (Button) findViewById(R.id.simulateMagpiRecordRX);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent();
            	intent.putExtra("recordUUID","UUID-of-completed-record-as-a-string");
            	intent.putExtra("recordData","xml of completed form record goes here as one long string");
            	intent.putExtra("recordBundle","contents of ZIP file or other representation of completed record, including any images and other large media");
            	intent.putExtra("formSpecification","xml of form specification file goes here as one long string");
            	
                intent.setAction("org.servalproject.succinctdata.ReceiveNewMagpiRecord");
                sendBroadcast(intent);
            }            
        });
        
        final Button buttonTXSim = (Button) findViewById(R.id.simulateMagpiRecordTXdBySD);
        buttonTXSim.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent();
            	intent.putExtra("recordUUID",uuidTextView.getText());            	
                intent.setAction("org.servalproject.succinctdata.SuccinctDataTXNotification");
                sendBroadcast(intent);
            }            
        });
        
        final Button buttonNewFormSim = (Button) findViewById(R.id.simulateMagpiNewFormSpecification);
        buttonNewFormSim.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	try {
            		InputStream is = getAssets().open("sampleform.xhtml");
            		int size = is.available();
            		byte[] buffer = new byte[size];
            		is.read(buffer);
            		final String formData = new String(buffer);
            		Intent intent = new Intent();
            		intent.putExtra("formData",formData);            	
            		intent.setAction("org.servalproject.succinctdata.SuccinctDataNewFormNotification");
            		sendBroadcast(intent);
            	} catch (Exception e) {            	
            	}
            }            
        });
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
    
    private Handler mHandler = new Handler();

    public void updateLastUUIDLabel(String uuid) {
    	final String s = uuid;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                uuidTextView.setText(s);
            }
        });
    }
}    
