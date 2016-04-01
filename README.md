# director [![Build Status](https://travis-ci.org/Team-IO/director.svg?branch=master)](https://travis-ci.org/Team-IO/director)
Creeper Director, an experimental cutscene mod for Minecraft.

## Contributing
If you want to contribute, you can do so [by reporting bugs](https://github.com/Team-IO/director/wiki), [by helping fix the bugs](https://github.com/Team-IO/director/pulls) or by spreading the word!

You are also welcome to [support us on Patreon](https://www.patreon.com/Team_IO?ty=h)!

## Building the mod
Creeper Director uses a fairly simple implementation of ForgeGradle. To build a ready-to-use jar, you can use the gradle wrapper delivered with the rest of the source code.  
For Windows systems, run this in the console:
    gradlew.bat build
For *nix systems, run this in the terminal:
    ./gradlew build
Installed Gradle versions should also work fine.

## Some info on the internal structure:
Mod & Dependency versions are controlled in the build.gradle and according build.properties file. All mod metadata is done in code, with the version replaced by gradle on compile time.

### Package net.teamio.director.act
Contains classes responsible for acting out a recorded cutscene.

### Package net.teamio.director.cut
Contains classes responsible for recording and editing a cutscene.

### Package net.teamio.director.gui
Contains the external Swing-based GUI that displays as separate windows. Works together witht the .act and .cut classes.