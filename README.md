# Park-Pirates Mobile
## HBV601G - Software Project 2
Android frontend for Park Pirates application.

Developers:
 - Alexander Sveinn G. Kristjánsson
 - David Francis Frigge
 - Guðrún Stella Ágústsdóttir
 
 ## Setup Instructions
 In order to run this project locally, we recommend setting up JetBrains IntelliJ if it is not already present.  Assuming you are familiar with the intricacies of the ADB setup on your machine, it should then be trivial to get this up and running on your test device.  No additional setup is required, it should run right out of the box.
 
 Note that two separate LaunchPad activities are currently present in this package.  ParkPiratesMobile is a bit of UI testing, while PpDebug is provided to demonstrate the internals of the architecture.
 
 This project targets Android version 21, and has been tested on an LG K11 mobile phone running Android version 25.
 
 Observe that this project currently runs entirely locally.  The remote end of the project has not yet been overhauled to meet the new interface specification, so functionality is being added piecemeal.  However, a placeholder service is provided which imitates the presence of a remote host for the application's purposes.
 
 For mapview you might need to install in file->settings->Android SDK and choose SDK Platforms and choose API number 14 Android 4.0(iceCreamSandwich)
 
 If the emulator does not work then you might need as well go again to file->settings->Android SDK and choose SDK Tools and choose for install: 
 Android SDK Buil-Tools 30-rc2
 GPU Debugging tools
 LLDB
 CMake
 Android Auto API Simulators
 Android Auto Desktop Head Unit emulator
 Android Emulator
 Android SDK Plarform-Tools
 Android SDK Tools
 Documanrtation for Android SDK
 Google Play APK Expansion library
 Google Play licensing library
 Google Play services
 