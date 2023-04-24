# ORBrake
An OpenRocket extension for controllable air brakes.  When added to a simulation, a controller processes the rocket's state in order to select required drag in order to bring your vehicle's apogee as close as possible to a given target altitude.  This is primarily used for designing controllers for these systems to fly on actual target altitude rockets.

ORBrake was developed specifically for WPI HPRC's [Sirius Rocket](https://aiaa.wpi.edu/hprc/sirius).  Because of this, there are some parameters that are not exposed to the user, specificlly the surfConst array within ORBrakeSimulationListener.java which is computed using surfaces fit to CFD data.  If you're interested in making ORBrake work for your vehicle, consider contacting us.  There is the possibility the program will eventually be made to work for different rockets in the future, but as of now, there is no set plan for this.

Please direct inquiries about this program to the HPRC Officer Board at hprc-officers@wpi.edu.

**DISCLAIMER: While ORBrake simulates drag, the plugin DOES NOT simulate the motion of a rocket's center of aerodynamic pressure.  YOU ARE RESPONSIBLE for ensuring that your rocket remains stable and safe throughout flight.  There is no guarantee of accuracy or safety for simulations done with ORBrake as it has yet to be validated with experimental data.**

## Installing
1. To install ORBrake, click on `Releases` on the right.  
1. Under the most recent version, click on `Assets` and then download `ORBrake.jar`.  
1. Save `ORBrake.jar` to your OpenRocket Plugin folder.
    * On Windows, the OpenRocket Plugin folder is likely `C:\Users\[USER]\AppData\Roaming\OpenRocket\Plugins`.  
    * If this doesn't work, you can find the OpenRocket folder within your Application Data folder.  To get to the AppData folder, press `Windows + R` then type `%appdata%` and hit enter before navigating to `OpenRocket\Plugins`.

Alternativley can also build it yourself from the source code by following the Contributing section.  

## Using the Plugin
To use ORBrake, it must be enabled on your simulation.  To do this, create a new simulation or edit an existing one.  In the `Edit Simulation` window, go to the `Simulation options` tab.  Finally, click `Add Extension` and select ORBrake under the `Flight` tab.  The plugin settings window will open.  Click `close` once you are done.  You can then execute the simulation. 

## Contributing
ORBrake is written in Java.  This section explains what dependencies are required to build the project as well as how to configure the development environment.
### Dependencies
1. [jdk 8](https://adoptopenjdk.net/): OpenRocket requires Java 8.  More recent versions are incompatible.  To compile code, jdk 8 is required.
1. [Apache Ant](https://mkyong.com/ant/how-to-install-apache-ant-on-windows/): Ant is used to automate the build process.  Note that if you are using Eclipse as your IDE, Ant is built in and you don't need to download it seperetly.
1. [Eclipse](https://github.com/WPIRoboticsEngineering/ESP32ArduinoEclipseInstaller) *[Recomended]*: While not explicetly required for this project, you can use the Sloeber Eclipse that is used for HPRC's embedded firmware programs.  There is no reason you couldn't use other IDEs like IDEA or VIM (depending on how much of a hacker you are) but the environment setup will be up to you.
1. OpenRocket 15.03: For obvious reasons, this OpenRocket plugin requires OpenRocket to run.  Version 15.03, the most recent official release, is included in this repo.

### Eclipse Setup
_Note: Some of these steps my automatically be done by importing from the repository.  You may not have to do every one of them, but you should still verify they are completed.  These instructions should enable you to configure the environment from scratch or in a different IDE._
1. Start by importing the project into Eclipse via `File->Import->Git->Projects from Git`.
1. Add `OpenRocket-15.03.jar` to the class path via `Project->Properties->Java Build Path->Libraries->Add External JARs...`
1. Add `JUnit 5` to the class path via `Project->Properties->Java Build Path->Libraries->Add Library...`
1. Go to `Window->Preferences->Java->Installed JREs` and select jdk 8. 
    1. If jdk 8 is not on the list, select `Add...`
    1. Select `Standard VM` then click `Next >`
    1. Click `Directory...` then navigate to and select `C:\Program Files\AdoptOpenJDK\jdk-8.0.275.1-hotspot\jre` or where ever you installed jdk 8 to.
    1. Click `Finish`.
1. Configure Ant
    1. Go to `Run->External Tools->External Tools Configurations...`
    1. If there is an Ant Build, edit it, otherwise, create a new one by right clicking "Ant Build"
    1. Go to the `JRE` tab and ensure that jdk 8 is selected as the runtime JRE.
    1. *Optional* : Right click on your Ant build to duplicate it.  After selecting it, go to the `Targets` tab and select `export`.  This is useful for exporting a JAR file to share with others.  You may also want to rename your ant builds to "Run" and "Export" you do don't mix them up.
1. Finally, you can run the program by clicking the `Run->External Tools` and picking your Ant Build.  A button for this should also be more readily accessible on the hot bar just below that.  If you did everything correctly, Ant will compile the plugin and then automatically launch OpenRocket (assuming you are using the `run` target and not `export`).
