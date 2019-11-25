// IBatteryStats.aidl
package com.android.internal.app;

// Declare any non-default types here with import statements

interface IBatteryStats {

    // Return the computed amount of time remaining on battery, in milliseconds.
    // Returns -1 if nothing could be computed.
    long computeBatteryTimeRemaining();

    // Return the computed amount of time remaining to fully charge, in milliseconds.
    // Returns -1 if nothing could be computed.
    long computeChargeTimeRemaining();
}
