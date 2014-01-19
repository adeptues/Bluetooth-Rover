package com.thelmkay.bluetoothrover.bluetooth;

import com.thelmkay.bluetoothrover.MainActivity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This is an output monitor which takes an input stream from the bluetooth socket and monitors
 * it for output comming from the device then updates the ui via a message update if possible.
 * Created by adeptues on 19/01/14.
 */
public class OutputMonitor implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(OutputMonitor.class);
    private InputStream is;
    private boolean RUN;
    private MainActivity view;

    public OutputMonitor(InputStream is, MainActivity view){//TODO remove this for proper observer.
        this.is = is;
        RUN = true;
        this.view = view;
    }

    /**
     * Stops the thread gracfully allowing it to become dead when run has finished executing
     */
    public void stop(){
        this.RUN = false;
    }


    /**
     * Threaded logic
     */
    @Override
    public void run() {
        try{
            while(RUN){
                int output = is.read();//TODO find some way to convert to ascii. also update ui
                logger.debug("Item read is "+output);
                //TODO update ui
                logger.debug("Makeing ui call");
                view.displayMessgae(Integer.toString(output));
                logger.debug("Call to ui made should now be up to date");
            }

        }catch (IOException ioe){
            logger.error("Exception when reading from bluetooth stream",ioe);
        }
    }

    /**
     * Closes the input stream
     */
    private void close() {
        try {
            is.close();
        } catch (IOException e) {
            logger.error("Failed to close stream something horrible happened",e);
        }
    }
}
