package com.thelmkay.bluetoothrover.bluetooth;

import java.io.IOException;

/**
 * Created by adeptues on 18/01/14.
 */
public interface BluetoothInterface {
    public void configure();
    public void disconnect();
    public void forward();
    public void backwards();
    public void turnLeft();
    public void turnRight();
    public boolean connect();
}
