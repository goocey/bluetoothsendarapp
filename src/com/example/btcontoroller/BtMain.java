package com.example.btcontoroller;

import net.shisashi.android.widget.LongClickRepeatAdapter;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//https://gist.github.com/1718672.git

public class BtMain extends Activity {
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

	//UIä÷òA
	private TextView textNumber;
	private Spinner spinner1;
	private View button1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (D)
			Log.e(TAG, "+++ ON CREATE +++");

		setContentView(R.layout.activity_bt_main);

		textNumber = (TextView) findViewById(R.id.textView2);

		// spinner
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		button1 = findViewById(R.id.button1);
		textNumber.setText(String.valueOf(0));

		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sendmessage();
			}
		});

		// í∑âüÇµÇµÇΩÇÁåJÇËï‘Çµë±ÇØÇÈÅB
		LongClickRepeatAdapter.bless(button1);

		ArrayAdapter<CharSequence> mAdapter = ArrayAdapter.createFromResource(this, R.array.Planets,
				android.R.layout.simple_spinner_dropdown_item);

		/*
		 * Attach the mLocalAdapter to the spinner.
		 */

		spinner1.setAdapter(mAdapter);

		// Get local Bluetooth adapter
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		// If the adapter is null, then Bluetooth is not supported
		if (mBluetoothAdapter == null) {
			Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
			finish();
			return;
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		if (D)
			Log.e(TAG, "++ ON START ++");

		// If BT is not on, request that it be enabled.
		// setupChat() will then be called during onActivityResult
		if (!mBluetoothAdapter.isEnabled()) {
			Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
			// Otherwise, setup the chat session
		} else {
//			if (mChatService == null) setupChat();
		}
	}

	public void sendmessage() {
		hoge++;
		textNumber.setText(String.valueOf(hoge));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bt_main, menu);
		return true;
	}
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent serverIntent = null;
        switch (item.getItemId()) {
        case R.id.secure_connect_scan:
            // Launch the DeviceListActivity to see devices and do scan
            serverIntent = new Intent(this, DeviceListActivity.class);
            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
            return true;
        case R.id.insecure_connect_scan:
            // Launch the DeviceListActivity to see devices and do scan
            serverIntent = new Intent(this, DeviceListActivity.class);
            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_INSECURE);
            return true;
        case R.id.discoverable:
            // Ensure this device is discoverable by others
            ensureDiscoverable();
            return true;
        }
        return false;
    }

}
