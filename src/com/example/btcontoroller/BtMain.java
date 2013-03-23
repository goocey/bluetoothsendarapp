package com.example.btcontoroller;


import android.os.Bundle;
import android.app.Activity;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Spinner;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;


//https://gist.github.com/1718672.git

public class BtMain extends Activity implements OnClickListener {
    // Debugging
    private static final String TAG = "BluetoothController";
    private static final boolean D = true;
    private int hoge = 0;
	
    // BluetoothíËêî
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;
    
    // Bluetoothä÷òA
    // Name of the connected device
    private String mConnectedDeviceName = null;
    // Array adapter for the conversation thread
    private ArrayAdapter<String> mConversationArrayAdapter;
    // String buffer for outgoing messages
    private StringBuffer mOutStringBuffer;
    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(D) Log.e(TAG, "+++ ON CREATE +++");
        
        setContentView(R.layout.activity_bt_main);
        
        // spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        View button1 = findViewById(R.id.button1);
        button1.setOnClickListener(this);
        
        ArrayAdapter<CharSequence> mAdapter = ArrayAdapter.createFromResource(this, R.array.Planets,
                android.R.layout.simple_spinner_dropdown_item);

        /*
         * Attach the mLocalAdapter to the spinner.
         */

        spinner.setAdapter(mAdapter);
        
        if(D) Log.e(TAG, "++create array adapter");
//        // Get local Bluetooth adapter
//        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//
//        // If the adapter is null, then Bluetooth is not supported
//        if (mBluetoothAdapter == null) {
//            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
//            finish();
//            return;
//        }
	}
	
    @Override
    public void onStart() {
        super.onStart();
        if(D) Log.e(TAG, "++ ON START ++");

//        // If BT is not on, request that it be enabled.
//        // setupChat() will then be called during onActivityResult
//        if (!mBluetoothAdapter.isEnabled()) {
//            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
//        // Otherwise, setup the chat session
//        } else {
//            //if (mChatService == null) setupChat();
//        }
    }
    
    public void sendmessage() {
        hoge++;
        Toast.makeText(this, String.valueOf(hoge), Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void onClick(View v) {
    	if(D) Log.e(TAG, "button click " + v.getId());
    	switch(v.getId()) {
    	case R.id.button1:
    		sendmessage();
    		break;
    	case R.id.spinner1:
    		break;
    	}
    }

    
  
    
    
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.bt_main, menu);
//		return true;
//	}

}
