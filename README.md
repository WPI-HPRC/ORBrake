# ORBrake
An open rocket extension for controllable air brakes.

## Installing
1. To install ORBrake, click on `Releases` on the right.  
1. Under the most recent version, click on `Assets` and then download `ORBrake.jar`.  
1. Navigate to your OpenRocket Plugin folder.  On Windows this is likely `C:\Users\[USER]\AppData\Roaming\OpenRocket\Plugins`.  If this doesn't work, you can find the OpenRocket folder within your Application Data folder.  To get to the AppData folder, press `Windows + R` then type `%appdata%` and hit enter before navigating to `OpenRocket\Plugins`.
1. Move `ORBreak.jar` from your Downloads folder to the OpenRocket Plugin folder.

You can also build it yourself from the source code by following the Contributing section.

## Using the Plugin
To use ORBrake, it must be enabled on your simulation.  To do this, create a new simulation or edit an existing one.  In the `Edit Simulation` window, go to the `Simulation options` tab.  Finally, click `Add Extension` and select ORBrake under the `Flight` tab.  The plugin settings window will open.  Click `close` once you are done.  You can then execute the simulation. 

## Contributing
### Dependencies
1. [jdk 8](https://adoptopenjdk.net/): OpenRocket requires Java 8.  More recent versions are incompatible.  To compile code, jdk 8 is required.
1. [Apache Ant](https://mkyong.com/ant/how-to-install-apache-ant-on-windows/): Ant is used to automate the build process.
1. Eclipse *[Recomended]*: For convenience, you can use the Sloeber Eclipse that is used for HPRC's embedded firmware programs.  There is no reason you couldn't use other IDEs like IDEA or VIM (depending on how much of a hacker you are) but the environment setup will be up to you.
1. OpenRocket 15.03: For obvious reasons, this OpenRocket plugin requires OpenRocket to run.  Version 15.03, the most recent release, is included in this repo.

### Eclipse Setup
1. Start by importing the project into Eclipse via `File->Import->Git->Projects from Git`.
1. Add `OpenRocket-15.03.jar` to the class path via `Project->Properties->Java Build Path->Libraries->Add External JARs...`
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
