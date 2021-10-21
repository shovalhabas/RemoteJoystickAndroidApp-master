# Remote Joystick Android App

**Introduction:**

This project is an android application which demonstrates a remote joystick to control flights.

The application uses draggable joystick and seek bar - we can control the aircraft's aileron and elevator using draggable joystick and control the aircraft's rudder and throttle using seek bars.

The connection to the flight simulator is established via conntection to the FlightGear server.


## Simulation
![](demo.gif)


## Instructions - FlightGear:


At the settings section insert:
--telnet=socket,in,10,127.0.0.1,6400,tcp

After the connection between the application with the server:
Tool bar -> Cessna C172P -> Autostart 

change the view options: press 'v'.


## Instructions -  Android Studio:

For proper visual representation of the app please follow:
1. Go to AVD manger 
2. click on "Create virtual decive" 
3. Choose a device defention "Pixel 3 XL" 
4. select a system image - the recommended is R. 
5. verify configuration and finish.

**When application opens:**
1. insert your public IPV4 address
2. insert port 6400 (or the matching port number according to the instruction given to FlightGear)
3. press connect



## UML Class Diagram (detailed):

![uml](
https://i.imgur.com/VcwYQql.jpg
)


## Video explanation and example:

You can find [here](https://youtu.be/0-AW_JWXDUY) the link to a video explanation of the application



