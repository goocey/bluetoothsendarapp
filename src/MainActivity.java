

import net.shisashi.android.widget.LongClickRepeatAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

    private TextView txtNumber;

    private int number = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        txtNumber = (TextView) findViewById(R.id.txtNumber);
        txtNumber.setText(String.valueOf(number));

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                increment();
            }
        });

        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                decrement();
            }
        });
        
        final ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        final CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);

        toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkBox.setChecked(isChecked);
            }
        });
        checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleButton.setChecked(isChecked);
            }
        });

        LongClickRepeatAdapter.bless(button);
        LongClickRepeatAdapter.bless(imageButton);
        LongClickRepeatAdapter.bless(toggleButton);
        LongClickRepeatAdapter.bless(checkBox);
    }

    private void increment() {
        number++;
        txtNumber.setText(String.valueOf(number));
    }

    private void decrement() {
        number--;
        txtNumber.setText(String.valueOf(number));
    }
}