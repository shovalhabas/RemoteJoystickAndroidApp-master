package com.joystickandroidapp.remotejoystick.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.joystickandroidapp.remotejoystick.R;
import com.joystickandroidapp.remotejoystick.model.FGPlayer;
import com.joystickandroidapp.remotejoystick.view_model.ViewModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    public ViewModel vm;
    private SeekBar rudderBar;
    private SeekBar throttleBar;
    public ExecutorService executorService;
    public boolean connectFlag = false;
    public FGPlayer FGPlayerModel = null;
    public Joystick joystick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FGPlayerModel = new FGPlayer();
        /* single thread for running task */
        executorService = Executors.newSingleThreadExecutor();
        vm = new ViewModel(FGPlayerModel, executorService);
        joystick = (Joystick) findViewById(R.id.joystick);
        /* set the onMoved function */
        joystick.joystickListener = (x, y) -> {
            vm.setAileron(x);
            vm.setElevator(y);
        };
        /* initialize listeners */
        connectButtonListener();
        rudderBarListener();
        throttleBarListener();
    }

    /* define listener for the throttle bar */
    void throttleBarListener() {
        throttleBar = (SeekBar) findViewById(R.id.throttleBar);
        /* listener for throttle bar changes */
        throttleBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                /* the progValue is the progress after convert to the -1 - 1 range*/
                //NewValue = (((OldValue - OldMin) * (NewMax - NewMin)) / (OldMax - OldMin)) + NewMin
                double progValue = (((double) progress * 2) / 100) + -1;
                /* round two digits after point */
                progValue = (int)(Math.round(progValue * 100)) / 100.0;
                double finalProgValue = progValue;
                vm.setThrottle(finalProgValue);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    /* define listener for the rudder bar */
    void rudderBarListener() {
        rudderBar = (SeekBar) findViewById(R.id.rudderBar);
        /* listener for rudder bar changes */
        rudderBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                /* the progValue is the progress after convert to the -1 - 1 range*/
                //NewValue = (((OldValue - OldMin) * (NewMax - NewMin)) / (OldMax - OldMin)) + NewMin
                double progValue = (((double) progress * 2) / 100) + -1;
                /* round two digits after point */
                progValue = (int)(Math.round(progValue * 100)) / 100.0;
                double finalProgValue = progValue;
                vm.setRudder(finalProgValue);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
    /* define listener for the connect button */
    void connectButtonListener() {
        Button connect = findViewById(R.id.connectButton);
        EditText ip = findViewById(R.id.IPText);
        EditText port = findViewById(R.id.PORTText);
        /* show message when the button is clicked */
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* user's input of ip and port*/
                String ipStr = ip.getText().toString();
                String portStr = port.getText().toString();
                Runnable taskConnect = () -> {
                    if(FGPlayerModel != null) {
                        try {
                            FGPlayerModel.openSocket(ipStr, Integer.parseInt(portStr));
                            connectFlag = true;
                            /*  message of successful connection  */
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(getBaseContext(),"connection success :\n" + "Ip: " + ipStr + " Port: " + portStr,Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (Exception e) {
                            /*  message of failure connection  */
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(getBaseContext(),"connection failed. try again\n",Toast.LENGTH_SHORT).show();
                                }
                            });
                            e.printStackTrace();
                        }
                    }
                };
                executorService.execute(taskConnect);
            }
        });
    }
}