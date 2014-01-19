package com.thelmkay.bluetoothrover.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.util.Log;

import com.thelmkay.bluetoothrover.MainActivity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;


import static android.support.v4.app.ActivityCompat.startActivityForResult;

/**
 * interfaces with the arduino bluesmirf
 * Created by adeptues on 18/01/14.
 */
public class BlueSmirf  implements BluetoothInterface{
private static final Logger logger = LoggerFactory.getLogger(BlueSmirf.class);
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice bluetoothDevice;
    private BluetoothSocket bluetoothSocket;
   private MainActivity view;
    private InputStream is;
    private OutputStream os;
    private static final String NAME = "FireFly-2E3C";
    private static final UUID DEVICE_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");//TODO find device uuid

    /**
     * Bluetooth is assumed to be available and enabled at this point if not things wont work
     * connects to an already paired device
     * @param bluetoothAdapter
     */
    public BlueSmirf(BluetoothAdapter bluetoothAdapter,MainActivity view){//TODO this is very dirty should use proper observer pattern or message broadcast fime me
        this.bluetoothAdapter = bluetoothAdapter;
        this.view = view;
        logger.info("Gettings paired devices");
       Set<BluetoothDevice> devices =  bluetoothAdapter.getBondedDevices();
        printDevices(devices);
        for(BluetoothDevice device: devices){
            if(device.getName().equals(NAME)){
                bluetoothDevice = device;
            }
        }
    }

    private void printDevices(Set<BluetoothDevice> devices){
        logger.info("Device found "+devices.size());
        for(BluetoothDevice device: devices){
            logger.info("Found "+device.getName()+", "+device.getAddress());
        }
    }


    @Override
    public void configure() {

    }

    @Override
    public void disconnect() {
        try {
            is.close();
            os.close();
            bluetoothSocket.close();
        } catch (IOException e) {
            logger.error("Somthing horrible happened",e);
        }

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

    @Override
    public boolean connect() {
        BluetoothSocket socket = null;
        try {
            socket = bluetoothDevice.createRfcommSocketToServiceRecord(DEVICE_UUID);
            socket.connect();
            bluetoothSocket = socket;
            is = bluetoothSocket.getInputStream();
            os = bluetoothSocket.getOutputStream();
            logger.info("ready for IO");
            //TODO remove this. its for quick test not part of actual code
            while(true){//TODO create actual serial montior object thats threaded and every thing
                logger.debug("Waiting for data to Reading io");
                int a = is.read();
                logger.debug("Data has been read ");
                logger.debug("Data is "+a);
                view.displayMessgae(Integer.toString(a));
            }
        } catch (IOException e) {
            logger.error("Failed to connect",e);
            try {
                socket.close();
            } catch (IOException e1) {  }
        }

        return false;
    }
}
