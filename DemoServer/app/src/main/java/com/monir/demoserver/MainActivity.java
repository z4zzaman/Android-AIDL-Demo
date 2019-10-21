package com.monir.demoserver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        doBindService();
    }

    void doBindService() {
            Intent intent = new Intent(this, ArithmeticService.class);
            // this will start the service in foreground mode
            intent.setAction(ArithmeticService.ACTION_START_FOREGROUND_SERVICE);
            startService(intent);
        }
    }
