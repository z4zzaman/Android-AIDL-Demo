// IRemote.aidl
package com.monir.demoserver;
import com.monir.demoserver.ITimer;

// Declare any non-default types here with import statements

interface IRemote {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */

    int add(int a, int b);
    int subtract(int a, int b);
    double multiply(int a, int b);

    void onData(ITimer timer);
}
