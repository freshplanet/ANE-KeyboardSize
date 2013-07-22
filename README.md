Air Native Extension to measure soft keyboard size (Android)
======================================

This is an [Air native extension](http://www.adobe.com/devnet/air/native-extensions-for-air.html) that measures the soft keyboard size, because the actionscript method `flash.display.Stage.softKeyboardRect():Rectangle` doesn't return a correct value in Android.

Usage
-----

```actionscript
//get the height of the keyboard
var keyboardHeight:Number = MeasureKeyboard.getInstance().getKeyboardHeight() as Number;

//get the y (vertical position) of the keyboard
var keyboardY:Number = MeasureKeyboard.getInstance().getKeyboardY() as Number;

//by default, soft keyboard will push the screen upward, this method can disable it.
MeasureKeyboard.getInstance().setKeyboardAdjustNothing();
```

Installation
-----

The ANE binary (KeyboardSize.ane) is located in the *bin* folder. You should add it to your application project's Build Path and make sure to package it with your app.

Build script
------

Should you need to edit the extension source code and/or recompile it, you will find an ant build script (build.xml) in the *build* folder:

    cd /path/to/the/ane/build
    mv example.build.config build.config
    #edit the build.config file to provide your machine-specific paths
    ant

Authors
------

This ANE has been written by [Lizhao Hou](https://github.com/lizhaofreshplanet). It belongs to [FreshPlanet Inc.](http://freshplanet.com) and is distributed under the [Apache Licence, version 2.0](http://www.apache.org/licenses/LICENSE-2.0).
