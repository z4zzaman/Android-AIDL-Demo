package com.monir.demoserver;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private IRemote iRemote;
    EditText mFirst, mSecond;
    Button mAdd, mSubtract, mMultiply, mReceiveButton;
    TextView mResultText;
    TextView mTimerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirst = (EditText) findViewById(R.id.firstValue);
        mSecond = (EditText) findViewById(R.id.secondValue);
        mResultText = (TextView) findViewById(R.id.resultText);
        mTimerText = (TextView) findViewById(R.id.timerText);

        mAdd = (Button) findViewById(R.id.add);
        mSubtract = (Button) findViewById(R.id.subtract);
        mMultiply = (Button) findViewById(R.id.multi);
        mReceiveButton = (Button) findViewById(R.id.receiveButton);

        mAdd.setOnClickListener(this);
        mSubtract.setOnClickListener(this);
        mMultiply.setOnClickListener(this);
        mReceiveButton.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        initService();
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iRemote = IRemote.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            iRemote = null;
        }
    };

    private void initService() {
        if (iRemote == null) {
            Intent intent = new Intent(IRemote.class.getName());
            intent.setAction("service.demoserver");
            intent.setPackage("com.monir.demoserver");
            bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add: {
                int a = Integer.parseInt(mFirst.getText().toString());
                int b = Integer.parseInt(mSecond.getText().toString());
                try {
                    mResultText.setText("Result -> Add ->"+ iRemote.add(a,b));
                    Log.d("IRemote", "Binding - Add operation");
                } catch (RemoteException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            break;
            case R.id.subtract: {
                int a = Integer.parseInt(mFirst.getText().toString());
                int b = Integer.parseInt(mSecond.getText().toString());
                try {
                    mResultText.setText("Result -> Subtract ->"+ iRemote.subtract(a,b));
                    Log.d("IRemote", "Binding - Subtract operation");
                } catch (RemoteException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            break;
            case R.id.multi: {
                int a = Integer.parseInt(mFirst.getText().toString());
                int b = Integer.parseInt(mSecond.getText().toString());
                try {
                    mResultText.setText("Result -> multiply ->"+ iRemote.multiply(a,b));
                    Log.d("IRemote", "Binding - multiply operation");
                } catch (RemoteException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            break;
            case R.id.receiveButton: {
                try {
                    iRemote.onData(iTimer);
                    Log.d("IRemote", "Binding - onData operation");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            break;
        }
    }

    ITimer.Stub iTimer = new ITimer.Stub() {
        @Override
        public void onTime(final long time) throws RemoteException {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTimerText.setText(""+ time);
                    mTimerText.setTextColor(Color.RED);
                }
            });

        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        unbindService(serviceConnection);
    }
}
