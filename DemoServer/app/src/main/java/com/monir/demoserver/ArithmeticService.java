package com.monir.demoserver;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;

import com.monir.demoserver.util.NotificationUtil;

import java.util.ArrayList;

public class ArithmeticService extends Service {
    public static final String ACTION_START_FOREGROUND_SERVICE = "ACTION_START_FOREGROUND_SERVICE";

    public static final String ACTION_STOP_FOREGROUND_SERVICE = "ACTION_STOP_FOREGROUND_SERVICE";

    public static final String ACTION_STOP = "ACTION_STOP";

    private ArrayList<ITimer> iTimerArrayList;
    TimeReceivedCallBack timeReceivedCallBack;

    ITimer mItimer;

    public ArithmeticService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return iRemote;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        if (intent != null) {
            String action = intent.getAction();

            switch (action) {
                case ACTION_START_FOREGROUND_SERVICE:
                    startForegroundService();
                    break;
                case ACTION_STOP_FOREGROUND_SERVICE:
                    stopForegroundService();
//                    Toast.makeText(getApplicationContext(), "Foreground service is stopped.", Toast.LENGTH_LONG).show();
                    break;
                case ACTION_STOP:
                    stopForegroundService();
//                    Toast.makeText(getApplicationContext(), "Service stopped", Toast.LENGTH_LONG).show();
                    break;

            }
        }
        return START_STICKY;
    }

    /* Used to build and start foreground service. */
    private void startForegroundService() {
        // Create notification default intent.
/*        Intent intent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // Create notification builder.
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        // Make notification show big text.
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle("AESService is running a background service.");
        bigTextStyle.bigText("You can stop the service by clicking on 'Stop Service' button. ");
        // Set big text style.
        builder.setStyle(bigTextStyle);

        builder.setWhen(System.currentTimeMillis());
        builder.setSmallIcon(R.drawable.notification);

        // Add Play button intent in notification.
        Intent playIntent = new Intent(this, ArithmeticService.class);
        playIntent.setAction(ACTION_STOP);
        PendingIntent pendingPlayIntent = PendingIntent.getService(this, 0, playIntent, 0);
        NotificationCompat.Action playAction = new NotificationCompat.Action(android.R.drawable.btn_star, "Stop Service", pendingPlayIntent);
        builder.addAction(playAction);


        // Build the notification.
        Notification notification = builder.build();

        // Start foreground service.
        startForeground(1, notification);*/


        String stopServiceText = "Stop Service";
        // Make notification show big text.
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle("Arithmetic server is running a background service.");
        bigTextStyle.bigText("You can stop the service by clicking on 'Stop Service' button.");

        Bitmap largeIconBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        // Add Play button intent in notification.
        Intent stopServiceIntent = new Intent(this, ArithmeticService.class);
        stopServiceIntent.setAction(ACTION_STOP_FOREGROUND_SERVICE);
        PendingIntent pendingStopServiceIntent = PendingIntent.getService(this, 0, stopServiceIntent, 0);
        NotificationCompat.Action stopServiceAction = new NotificationCompat.Action
                (R.drawable.notification, stopServiceText, pendingStopServiceIntent);

        // Create notification builder.
        NotificationCompat.Builder builder = NotificationUtil.getBuilder(this);
        builder.setContentTitle("Arithmetic Service is running as background service").setWhen(System.currentTimeMillis()).
                setSmallIcon(R.drawable.notification).setLargeIcon(largeIconBitmap).
                addAction(stopServiceAction).setStyle(bigTextStyle).
                setContentText("Expand to stop Telemesh service").
                setAutoCancel(false);//This one can be true as Activity Pause/Resume would re appear
        // the notification normally unless developer modify the behavior

        // Build the notification.
        Notification notification = builder.build();

        // Start foreground service.
        startForeground(1, notification);
    }

    private void stopForegroundService() {
        // Stop foreground service and remove the notification.
        stopForeground(true);

        // Stop the foreground service.
        stopSelf();
    }

    private final IRemote.Stub iRemote = new IRemote.Stub() {
        @Override
        public int add(int a, int b) throws RemoteException {
            return (a + b);
        }

        @Override
        public int subtract(int a, int b) throws RemoteException {
            return (a - b);
        }

        @Override
        public double multiply(int a, int b) throws RemoteException {
            return (a * b);
        }

        @Override
        public void onData(ITimer timer) throws RemoteException {
      /*     iTimerArrayList = new ArrayList<>();
           iTimerArrayList.add(timer);
*/
            mItimer = timer;
            AppDatabaseManager.getAppDatabaseManager().setTimerCallback(new TimeReceivedCallBack());
        }
    };

    private class TimeReceivedCallBack implements ITimer {
        @Override
        public void onTime(long time) throws RemoteException {
     /*       if (iTimerArrayList !=null){
                iTimerArrayList.get(0).onTime(time);
            }*/
            if (mItimer != null) {
                mItimer.onTime(time);
            }
        }

        @Override
        public IBinder asBinder() {
            return null;
        }
    }


}
