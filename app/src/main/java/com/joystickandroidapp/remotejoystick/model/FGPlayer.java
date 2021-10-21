package com.joystickandroidapp.remotejoystick.model;

import android.widget.Toast;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class FGPlayer {
    Socket fgSocket = null;
    PrintWriter fgOut = null;

    /* the function open socket with the flight gear simulator */
    public void openSocket(String ipAddress, int portNumber) throws Exception {
        this.fgSocket = new Socket();
        fgSocket.connect(new InetSocketAddress(ipAddress, portNumber), 6000);
        //this.fgSocket = new Socket(ipAddress, portNumber);
        this.fgOut = new PrintWriter(fgSocket.getOutputStream(), true);
    }

    /* the function send to the simulator the new value of aileron */
    public void sendAileronValue(String aileronVal) {
        if (fgOut != null) {
            fgOut.print("set /controls/flight/aileron " + aileronVal + " \r\n");
            fgOut.flush();
        }
    }

    /* the function send to the simulator the new value of elevator */
    public void sendElevatorValue(String elevatorVal) {
        if (fgOut != null) {
            fgOut.print("set /controls/flight/elevator " + elevatorVal + " \r\n");
            fgOut.flush();
        }
    }

    /* the function send to the simulator the new value of rudder */
    public void sendRudderValue(String rudderVal) {
        if (fgOut != null) {
            try {
                fgOut.print("set /controls/flight/rudder " + rudderVal + " \r\n");
                fgOut.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /* the function send to the simulator the new value of throttle */
    public void sendThrottleValue(String throttleVal) {
        if (fgOut != null) {
            try {
                fgOut.print("set /controls/engines/current-engine/throttle " + throttleVal + " \r\n");
                fgOut.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
