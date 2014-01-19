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
import java.util.Arrays;
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
    private boolean connected = false;
    private OutputMonitor monitor;
    private InputStream is;
    private OutputStream os;
    private static final String NAME = "FireFly-2E3C";
    private static final UUID DEVICE_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

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
        logger.info("Creating output monitor thread");
        this.monitor = new OutputMonitor(is,view);
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
        if(connected){
            try {
                monitor.stop();
                logger.info("Success: stoped monitor");
                is.close();
                os.close();
                bluetoothSocket.close();
                connected = false;
            } catch (IOException e) {
                logger.error("Somthing horrible happened",e);
            }
        }else{
            logger.info("Unable to disconnect, Need to be connected first");
        }
    }

    @Override
    public void forward() {
        if(connected){
            String hello = "Hello world";
            logger.info("Attempting to send Data to device");
            byte [] buffer =  hello.getBytes();


            try {
                os.write(buffer);
            } catch (IOException e) {
                logger.error("Failed to send bytes to device: "+hello,e);
            }
        }else{
            logger.debug("Unable to send packet, Not connected");
        }

    }

    @Override
    public void backwards() {
        if(connected){
            logger.info("Not implemented");
        }else{
            logger.debug("Unable to send packet, Not connected");
            view.displayMessgae("Unable to send packet, Not connected");
        }
    }

    @Override
    public void turnLeft() {
        if(connected){
            logger.info("Not implemented");
        }else{
            logger.debug("Unable to send packet, Not connected");
            view.displayMessgae("Unable to send packet, Not connected");
        }
    }

    @Override
    public void turnRight() {
        if(connected){
            logger.info("Not implemented");
        }else{
            logger.debug("Unable to send packet, Not connected");
            view.displayMessgae("Unable to send packet, Not connected");
        }

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
            logger.info("Starting serial monitor");
        //    Thread thread = new Thread(monitor);//TODO might need to maintain reference to the thread to check its status
        //    thread.start(); //TODO also thread monitor is broken
            logger.info("Success: Thread started");
            connected = true;
            return true;
        } catch (IOException e) {
            logger.error("Failed to connect",e);
            try {
                socket.close();
            } catch (IOException e1) {  }
        }
        connected = false;
        return false;
    }
}
