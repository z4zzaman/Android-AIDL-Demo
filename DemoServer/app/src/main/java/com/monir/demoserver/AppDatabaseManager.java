package com.monir.demoserver;

/*
 *  ****************************************************************************
 *  * Created by : Md. Moniruzzaman Monir on 10/20/2019 at 10:33 PM.
 *  * Email : zzaman08@gmail.com
 *  *
 *  * Purpose:
 *  *
 *  * Last edited by : Md. Moniruzzaman Monir on 10/20/2019.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */

import android.os.RemoteException;
import android.util.Log;

import com.monir.demoserver.util.HandlerUtil;

public class AppDatabaseManager {
    private ITimer timerCallback;

    public static class AppDatabaseHolder {
        private static AppDatabaseManager appDatabaseManager = new AppDatabaseManager();
    }

    public static AppDatabaseManager getAppDatabaseManager() {
        return AppDatabaseHolder.appDatabaseManager;
    }

    public void setTimerCallback(ITimer timerCallback) {
        this.timerCallback = timerCallback;

        sendTimePeriodically();
    }

    private void sendTimePeriodically() {
        HandlerUtil.postBackground(new Runnable() {
            @Override
            public void run() {
                try {
                    timerCallback.onTime(System.currentTimeMillis());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                HandlerUtil.postBackground(this::run, 2000);
            }
        }, 1000);
    }

}
