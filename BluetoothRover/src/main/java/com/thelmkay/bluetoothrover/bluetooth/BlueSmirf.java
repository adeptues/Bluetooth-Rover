package com.thelmkay.bluetoothrover.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;

import java.util.Set;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

/**
 * interfaces with the arduino bluesmirf
 * Created by adeptues on 18/01/14.
 */
public class BlueSmirf  implements BluetoothInterface{
    private BluetoothAdapter bluetoothAdapter;

    /**
     * Bluetooth is assumed to be available and enabled at this point if not things wont work
     * connects to an already paired device
     * @param bluetoothAdapter
     */
    public BlueSmirf(BluetoothAdapter bluetoothAdapter){
        this.bluetoothAdapter = bluetoothAdapter;
       Set<BluetoothDevice> devices =  bluetoothAdapter.getBondedDevices();
    }


    @Override
    public void configure() {

    }

    @Override
    public void disconnect() {

    }

    @Override
    public void forward() {

    }

    @Override
    public void backwards() {

    }

    @Override
    public void turnLeft() {

    }

    @Override
    public void turnRight() {

    }
}
