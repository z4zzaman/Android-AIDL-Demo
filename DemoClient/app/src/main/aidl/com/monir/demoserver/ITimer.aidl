// ITimer.aidl
package com.monir.demoserver;

// Declare any non-default types here with import statements

interface ITimer {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void onTime(long time);
}
