package com.freshplanet.ane.KeyboardSize
{
    import flash.events.Event;
    import flash.geom.Rectangle;

    public class MeasureKeyboardEvent extends Event
    {
        public static const KEYBOARD_DID_SHOW:String = "KEYBOARD_DID_SHOW";
        public static const KEYBOARD_DID_CHANGE:String = "KEYBOARD_DID_CHANGE";
        public static const KEYBOARD_DID_HIDE:String = "KEYBOARD_DID_HIDE";

        public var data:Object;

        private var _keyboardRect:Rectangle;
         
        public function MeasureKeyboardEvent(typ:String, json:String = null) 
        {
            super(typ);
            if(json && json.length) {
                data = JSON.parse(json);
                var viewRect:Object = data.viewRect;
                if(viewRect) {
                    var s:Number = data.hasOwnProperty("nativeScale") ? data.nativeScale : 1;
                    _keyboardRect = new Rectangle(viewRect.x * s, viewRect.y * s, 
                        viewRect.width * s, viewRect.height * s);
                }
            }
        }

        public function get keyboardRect():Rectangle { return _keyboardRect; }

    }
}