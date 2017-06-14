package com.freshplanet.ane.KeyboardSize
{
    import flash.external.ExtensionContext;
    import flash.events.EventDispatcher;
    import flash.events.StatusEvent;
    import flash.system.Capabilities;

    public class MeasureKeyboard extends EventDispatcher
    {
        public static var extContext:ExtensionContext = null;
        private static var _instance:MeasureKeyboard;

        //All Android variables, but iOS is not using the corresponding functinos, so it's easier to put them here and call setKeyboard layout with an arg
        private static const SOFT_INPUT_STATE_UNCHANGED:int = 1;
        private static const SOFT_INPUT_ADJUST_NOTHING:int = 48;
        private static const SOFT_INPUT_ADJUST_PAN:int = 32;
        private static const SOFT_INPUT_ADJUST_RESIZE:int = 16;

        public static function getInstance():MeasureKeyboard
        {
            return _instance ? _instance : new MeasureKeyboard();
        }

        public static function get isSupported() : Boolean
        {
            return Capabilities.manufacturer.indexOf("iOS") > -1 || Capabilities.manufacturer.indexOf("Android") > -1;
        }

        public function MeasureKeyboard(fullScreen:Boolean = false)
        {
            if(!isSupported) {
                return;
            }
            if (!_instance) {
                extContext = ExtensionContext.createExtensionContext("com.freshplanet.KeyboardSize", null);
                if (!extContext) {
                    trace("ERROR - Extension context is null. Please check if extension.xml is setup correctly.");
                    return;
                }
                extContext.call("init", fullScreen);
                extContext.addEventListener(StatusEvent.STATUS, onStatusEvent, false, 0, true);
                _instance = this;
            }
            else {
                throw Error("This is a singleton, use getInstance(), do not call the constructor directly.");
            }
        }

        public function onStatusEvent(event:StatusEvent):void
        {
            if(hasEventListener(event.code)) {
                dispatchEvent(new MeasureKeyboardEvent(event.code, event.level));
            }
        }

        public function resetFullScreen(showBars:Boolean = false):void
        {
            if(Capabilities.manufacturer.indexOf("Android") > -1) {
                extContext.call("resetFullScreen", showBars);
            }
        }

        public function getKeyboardHeight():Object
        {
            if(!isSupported) {
                return null;
            }
            var retHeight:Object = extContext.call("getKeyboardHeight");
            return retHeight;
        }

        public function getKeyboardY():Object
        {
            if(!isSupported) {
                return null;
            }
            var retY:Object = extContext.call("getKeyboardY");
            return retY;
        }

        public function setKeyboardAdjustDefault():void
        {
            if(!isSupported) {
                return;
            }
            extContext.call("setSoftInputMode", SOFT_INPUT_ADJUST_PAN);
            return;
        }

        public function setKeyboardAdjustNothing():void
        {
            if(!isSupported) {
                return;
            }
            extContext.call("setSoftInputMode", SOFT_INPUT_ADJUST_NOTHING);
            return;
        }

        public function setKeyboardStateUnchanged():void
        {
            if(!isSupported) {
                return;
            }
            extContext.call("setSoftInputMode", SOFT_INPUT_STATE_UNCHANGED);
            return;
        }


        public function removeClearButtonForiOS():void
        {
            if(!isSupported) {
                return;
            }
            extContext.call("removeClearButtonForiOS");
            return;
        }

        public function getMultilineTextViewHeight():Object
        {
            if(!isSupported) {
                return null;
            }
            var retTextViewHeight:Object = extContext.call("getMultilineTextViewHeight");
            return retTextViewHeight;
        }

        public function getScreenHeight():int
        {
            if(!isSupported || Capabilities.manufacturer.indexOf("Android") == -1) {
                return Capabilities.screenResolutionY;
            }
            return extContext.call("getScreenHeight") as int;
        }

    }
}