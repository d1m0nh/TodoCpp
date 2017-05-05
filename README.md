## Build Instructions
To build the TodoCpp library for iOS and Android follow the instructions below to generate the appropriate `TodoCpp.framework` (iOS) or proper Android Studio CMake integration (Android). The `CMakeLists.txt` file should take care of generating the libraries and handling all internal linking of the sub-dependencies (such as djinni)

### Requirements
- A Mac computer
- A version of the [Java JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) must be installed for djinni to run properly
- [CMake](https://cmake.org/install/)
    - Specifically remember to install CMake to use in the Command Line once you've downloaded and installed the CMake app in your applications directory. You can do this using this command in the terminal:
    ```
    sudo "/Applications/CMake.app/Contents/bin/cmake-gui" --install
    ```
- [A valid "iPhone Developer" code signing identity](http://polly.readthedocs.io/en/latest/toolchains/ios/errors/polly_ios_development_team.html) - this is super important because compiling the iOS and OSX frameworks must be codesigned.
- If you've never built a project with Xcode be sure to run an empty project at least once with your codesigning team enabled to prompt the "Enable Developer Mode" window. Once you enable this `xcodebuild` will be able to find your `iPhone Development` certificate.

### Cloning the repo
You're going to want to clone this repo recursively to get the submodules for djinni and polly (we're using custom forks from the Mobile C++ organization)
```
git clone --recursive https://github.com/d1m0nh/TodoCpp
```

If you've already cloned the repo then you can update the submodules like so:
```
git submodule update --init --recursive
```

### iOS

There's a helper script to build a universal `TodoCpp.framework` that can be used for all the valid device and simulator architectures (x86_64 i386 armv7 armv7s arm64).
```
cd scripts && ./build_ios.sh
```

#### Requirements
- CMake 3.1 (minimum)
- Xcode 8
- iOS 10.0
- macOS 10.12

Once successfully built the universal `TodoCpp.framework` and `libdjinni.a` library will be available in the `build/ios/lib` folder under the target device (iOS or macos). From this point the two can simply be dragged and dropped into a project or added by reference.

#### Notes

Ensure the `TodoCpp.framework ` for iOS or macOS is in the `Embedded Binaries`.

Additionally, if you aren't copying the `TodoCpp.framework ` and `libdjinni.a` library into your project then you're going to need to modify the `Search Paths` in the `Build Settings` to set the framework and library search paths to the libs/ios or lib/macos output directory.

**Note:** You only need to do this if you're adding the `TodoCpp.framework ` and `libdjinni.a` library by *reference* and not copying it into your app.

Alternatively, you can just run the CMakeLists.txt to generate a `TodoCpp.framework ` target for whatever toolchain you'd like to compile against.


### Android

For Android all you need to do is simply copy or add this repo as a submodule to your existing Android Studio project. There are, however, a couple things to note.

#### Requirements
- Android Studio 2.3 or later
- Project must have C++ support enabled (checkmark when you're creating the project)
- You must enable C++11 instead of the default toolchain
- Enable exception and rtti

#### Notes
```
git submodule update --init --recursive
```

Go into the `TodoCpp` folder and run the djinni script.
```
cd scripts && ./run_djinni.sh
```

Next go into your app's `build.gradle` file and add the java source sets from the todocpp. If you have placed the TodoCpp folder in a different location than your app's deps folder like above then you should modify the paths below to reflect your project's structure.
Once this is complete you will need to resync gradle. Afterwards, you should see the java classes from the TodoCpp in your project.

In the top menu go to Build and click Refresh Linked C++ Projects. You should now see the libraries in the cpp folder and additional `CMakeLists.txt` files in the `External Build Files` section of the project pane.

Next, we actually load the library into the TodoApplication by adding the following to the top of the class.
```java
static {
    System.loadLibrary("TodoCpp");
}
```

See the Android sample in the examples folder to see how to use another path structure incase you'd like to keep the TodoCpp folder in a different location.