package com.example.btcontoroller;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.example.btcontoroller.BluetoothChatService;
import com.example.btcontoroller.DeviceListActivity;

public class BtMain extends Activity implements OnClickListener, OnTouchListener {
	// Debugging
	private static final String TAG = "BluetoothController";
	private static final boolean D = true;

	// Bluetooth定数
	private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
	private static final int REQUEST_ENABLE_BT = 3;

	// Key names received from the BluetoothChatService Handler
	public static final String DEVICE_NAME = "device_name";
	public static final String TOAST = "toast";

	// Message types sent from the BluetoothChatService Handler
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;

	// Bluetooth関連
	// Name of the connected device
	private String mConnectedDeviceName = null;
	// String buffer for outgoing messages
	private StringBuffer mOutStringBuffer;
	// Local Bluetooth adapter
	private BluetoothAdapter mBluetoothAdapter = null;

	private BluetoothChatService mChatService = null;

	// UI関連
	private ImageButton rrbutton;
	private ImageButton rlbutton;
	private ImageButton frbutton;
	private ImageButton flbutton;
	private View upbutton;
	private View downbutton;
	private View buttonscan;
	private View carimg;
	private TextView sendtext;


	// 送信ビット保持
	private int frst;
	private int flst;
	private int rrst;
	private int rlst;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (D)
			Log.e(TAG, "+++ ON CREATE +++");

		setContentView(R.layout.activity_bt_main);
		
		// UIの取得
		frbutton = (ImageButton)findViewById(R.id.frbutton);
		flbutton = (ImageButton)findViewById(R.id.flbutton);
		rrbutton = (ImageButton)findViewById(R.id.rrbutton);
		rlbutton = (ImageButton)findViewById(R.id.rlbutton);
		rlbutton.setPressed(true);
		upbutton = findViewById(R.id.upbotton);
		downbutton = findViewById(R.id.downbutton);
		carimg = findViewById(R.id.carimg);
		buttonscan = findViewById(R.id.button_scan);
		sendtext = (TextView)findViewById(R.id.sendtext);
		
		frbutton.setVisibility(View.INVISIBLE);
		flbutton.setVisibility(View.INVISIBLE);
		rrbutton.setVisibility(View.INVISIBLE);
		rlbutton.setVisibility(View.INVISIBLE);
		downbutton.setVisibility(View.INVISIBLE);
		upbutton.setVisibility(View.INVISIBLE);
		carimg.setVisibility(View.INVISIBLE);
		sendtext.setVisibility(View.INVISIBLE);
		
		buttonscan.setOnClickListener(this);

		// Get local Bluetooth adapter
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		
		// If the adapter is null, then Bluetooth is not supported
		if (mBluetoothAdapter == null) {
			Toast.makeText(this, "Bluetooth is not available",
					Toast.LENGTH_LONG).show();
			finish();
			return;
		}
	}

	/**
	 * set use tireType
	 * 
	 * @param int tireType choose change tire(fr:1 fl:2 rr:3 rl:4)
	 * @return boolean tyre condition true:on false:off
	 */
	private boolean setChangeTire(int tireType) {
		switch (tireType) {
		case 1:
			frst = getReverseCond(frst);
			if (frst == 0) {
				return false;
			}
			break;
		case 2:
			flst = getReverseCond(flst);
			if (flst == 0) {
				return false;
			}
			break;
		case 3:
			rrst = getReverseCond(rrst);
			if (rrst == 0) {
				return false;
			}
			break;
		case 4:
			rlst = getReverseCond(rlst);
			if (rlst == 0) {
				return false;
			}
			break;
		default:
			break;
		}
		return true;
	}

	/**
	 * return tire reverse condition
	 * 
	 * @param int tireCond tire condition
	 * @return reverse integer
	 */
	private int getReverseCond(final int tireCond) {
		if (tireCond == 1) {
			return 0;
		} else if (tireCond == 0) {
			return 1;
		}
		return 0;
	}
	


	/**
	 * Validates a chess move.
	 * 
	 * @param int typeStr down:10 up:20
	 * @return string condition
	 */
	public String getConditionStr(int typestr) {
		// (fr:1 fl:2 rr:3 rl:4)
		String fr = String.valueOf(frst);
		String fl = String.valueOf(flst);
		String rr = String.valueOf(rrst);
		String rl = String.valueOf(rlst);
		String returnStr = "";
		switch (typestr) {
		case 10:
			// down
			returnStr = "0010" + rr + rl + fr + fl;
			break;
		case 20:
			// up
			returnStr = "0001" + rr + rl + fr + fl;
			break;
		}
		return returnStr;
	}
	
	public void onClick(View v) {
		ImageButton ImageButton = null;
		switch (v.getId()) {
		case R.id.frbutton:
			Log.e(TAG, "fr click");
			if (setChangeTire(1)) {	
				ImageButton = (ImageButton)findViewById(v.getId());
				ImageButton.setPressed(true);
				Log.e(TAG,String.valueOf(ImageButton));
			} else {
				ImageButton = (ImageButton)findViewById(v.getId());
				ImageButton.setPressed(false);
			}
			break;
		case R.id.flbutton:
			Log.e(TAG, "fl click");
			if (setChangeTire(2)) {
				ImageButton = (ImageButton)findViewById(v.getId());
				Log.e(TAG,String.valueOf(ImageButton));
				ImageButton.setPressed(true);
			} else {
				ImageButton = (ImageButton)findViewById(v.getId());
				Log.e(TAG,String.valueOf(ImageButton));
				ImageButton.setPressed(false);
			}
			break;
		case R.id.rrbutton:
			Log.e(TAG, "rr click");
			if (setChangeTire(3)) {
				ImageButton = (ImageButton)findViewById(v.getId());
				ImageButton.setPressed(true);
			} else {
				ImageButton = (ImageButton)findViewById(v.getId());
				ImageButton.setPressed(false);
			}
			break;
		case R.id.rlbutton:
			Log.e(TAG, "rl click");
			if (setChangeTire(4)) {
				ImageButton = (ImageButton)findViewById(v.getId());
				ImageButton.setPressed(true);
			} else {
				ImageButton = (ImageButton)findViewById(v.getId());
				ImageButton.setPressed(false);
			}
			break;
		case R.id.button_scan:
			Log.e(TAG, "connect click");
			Intent serverIntent = null;
			// Launch the DeviceListActivity to see devices and do scan
			serverIntent = new Intent(this, DeviceListActivity.class);
			startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
			break;
		}
	}
	/**
	 * return sendmessage tire condition and press keycode or finalbit(11111111)
	 * @param int buttontype(10 or 20)
	 * @param int mode up:10 / release:20
	 * @return string bitcode
	 */
	private String getSendMessage(int buttontype, int mode) {
		String message = "";
		if (mode == 10) {
			message = getConditionStr(buttontype);			
		} else if (mode == 20) {
			message = "11111111";
		}
		return message;
	}
	public boolean onTouch(View v, MotionEvent e) {
		String message = "";
		Log.e(TAG, "touch" + String.valueOf(e.getAction()));
        if (e.getAction() == MotionEvent.ACTION_DOWN){
		switch (v.getId()) {
		case R.id.downbutton:
			Log.e(TAG, "down click");
			message = getSendMessage(10, 10);
			Log.e(TAG, message);
			sendtext.setText(message);
			sendMessage(message);
			break;
		case R.id.upbotton:
			Log.e(TAG, "up click");
			message = getSendMessage(20,10);
			Log.e(TAG, message);
			sendtext.setText(message);
			sendMessage(message);
			break;
		}
        } else if(e.getAction() == MotionEvent.ACTION_UP) {
    		switch (v.getId()) {
    		case R.id.downbutton:
    			Log.e(TAG, "down click");
    			message = getSendMessage(10, 20);
    			Log.e(TAG, message);
    			sendtext.setText(message);
    			sendMessage(message);
    			break;
    		case R.id.upbotton:
    			Log.e(TAG, "up click");
    			message = getSendMessage(20,20);
    			Log.e(TAG, message);
    			sendtext.setText(message);
    			sendMessage(message);
    			break;
    		}
        }
		return false;
	}
	private void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }

        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            mChatService.write(send);

            // Reset out string buffer to zero and clear the edit text field
            mOutStringBuffer.setLength(0);
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
			Intent enableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
			// Otherwise, setup the chat session
		} else {
			if (mChatService == null) setupChat();
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (D)
			Log.d(TAG, "onActivityResult " + resultCode);
		switch (requestCode) {
		case REQUEST_CONNECT_DEVICE_SECURE:
			// When DeviceListActivity returns with a device to connect
			if (resultCode == Activity.RESULT_OK) {
				connectDevice(data, true);
			}
			break;
		case REQUEST_ENABLE_BT:
			// When the request to enable Bluetooth returns
			if (resultCode == Activity.RESULT_OK) {
				// Bluetooth is now enabled, so set up a chat session
				setupChat();
			} else {
				// User did not enable Bluetooth or an error occurred
				Log.d(TAG, "BT not enabled");
				Toast.makeText(this, R.string.bt_not_enabled_leaving,
						Toast.LENGTH_SHORT).show();
				finish();
			}
		}
	}
	
    @Override
    public synchronized void onPause() {
        super.onPause();
        if(D) Log.e(TAG, "- ON PAUSE -");
    }

    @Override
    public void onStop() {
        super.onStop();
        if(D) Log.e(TAG, "-- ON STOP --");
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop the Bluetooth chat services
        if (mChatService != null) mChatService.stop();
        if(D) Log.e(TAG, "--- ON DESTROY ---");
    }

	private final void setStatus(int resId) {
        Toast.makeText(getApplicationContext(), 
                getText(resId), Toast.LENGTH_SHORT).show();
	}

	private final void setStatus(CharSequence subTitle) {
        Toast.makeText(getApplicationContext(), "Connected to "
                + subTitle, Toast.LENGTH_SHORT).show();
	}
	private void showCarButton() {

		// クリックイベント取得
		frbutton.setOnClickListener(this);
		flbutton.setOnClickListener(this);
		rrbutton.setOnClickListener(this);
		rlbutton.setOnClickListener(this);
		upbutton.setOnTouchListener(this);
		downbutton.setOnTouchListener(this);

		// タイヤの状態を初期化
		rrst = 0;
		rlst = 0;
		flst = 0;
		frst = 0;
		frbutton.setVisibility(View.VISIBLE);
		flbutton.setVisibility(View.VISIBLE);
		rrbutton.setVisibility(View.VISIBLE);
		rlbutton.setVisibility(View.VISIBLE);
		upbutton.setVisibility(View.VISIBLE);
		downbutton.setVisibility(View.VISIBLE);
		buttonscan.setVisibility(View.VISIBLE);
		carimg.setVisibility(View.VISIBLE);
		sendtext.setVisibility(View.VISIBLE);
	}


    // The Handler that gets information back from the BluetoothChatService
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MESSAGE_STATE_CHANGE:
                if(D) Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                switch (msg.arg1) {
                case BluetoothChatService.STATE_CONNECTED:
                    setStatus(getString(R.string.title_connected_to, mConnectedDeviceName));
                    showCarButton();
                    break;
                case BluetoothChatService.STATE_CONNECTING:
                    setStatus(R.string.title_connecting);
                    break;
                case BluetoothChatService.STATE_LISTEN:
                case BluetoothChatService.STATE_NONE:
                    setStatus(R.string.title_not_connected);
                    break;
                }
                break;
            case MESSAGE_WRITE:
                byte[] writeBuf = (byte[]) msg.obj;
                // construct a string from the buffer
                String writeMessage = new String(writeBuf);
                break;
            case MESSAGE_READ:
                byte[] readBuf = (byte[]) msg.obj;
                // construct a string from the valid bytes in the buffer
                String readMessage = new String(readBuf, 0, msg.arg1);
                break;
            case MESSAGE_DEVICE_NAME:
                // save the connected device's name
                mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                Toast.makeText(getApplicationContext(), "Connected to "
                               + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                break;
            case MESSAGE_TOAST:
                Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                               Toast.LENGTH_SHORT).show();
                break;
            }
        }
    };

	private void setupChat() {
		Log.d(TAG, "setupChat()");

//		// Initialize the BluetoothChatService to perform bluetooth connections
		mChatService = new BluetoothChatService(this, mHandler);

//		// Initialize the buffer for outgoing messages
		mOutStringBuffer = new StringBuffer("");
	}

    @Override
    public synchronized void onResume() {
        super.onResume();
        if(D) Log.e(TAG, "+ ON RESUME +");

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mChatService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
              // Start the Bluetooth chat services
              mChatService.start();
            }
        }
    }

	private void connectDevice(Intent data, boolean secure) {
		// Get the device MAC address
		String address = data.getExtras().getString(
				DeviceListActivity.EXTRA_DEVICE_ADDRESS);
		// Get the BluetoothDevice object
		BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
		// Attempt to connect to the device
		mChatService.connect(device, secure);
	}

	public void sendmessage(String message) {
		// Check that we're actually connected before trying anything
		if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
			Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT)
					.show();
			return;
		}

		// Check that there's actually something to send
		if (message.length() > 0) {
			// Get the message bytes and tell the BluetoothChatService to write
			byte[] send = message.getBytes();
			mChatService.write(send);
		}
	}
}
