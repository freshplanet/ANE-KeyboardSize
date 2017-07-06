/*
 * Copyright 2017 FreshPlanet
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.freshplanet.ane.AirKeyboardSize {
	
	import flash.events.Event;
	import flash.geom.Rectangle;
	
	public class MeasureKeyboardEvent extends Event {
		
		public static const KEYBOARD_WILL_SHOW:String   = "KEYBOARD_WILL_SHOW";
		public static const KEYBOARD_WILL_CHANGE:String = "KEYBOARD_WILL_CHANGE";
		public static const KEYBOARD_WILL_HIDE:String   = "KEYBOARD_WILL_HIDE";
		public static const KEYBOARD_DID_SHOW:String    = "KEYBOARD_DID_SHOW";
		public static const KEYBOARD_DID_CHANGE:String  = "KEYBOARD_DID_CHANGE";
		public static const KEYBOARD_DID_HIDE:String    = "KEYBOARD_DID_HIDE";
		
		public var data:Object;
		
		private var _keyboardRect:Rectangle;
		
		/**
		 *
		 * @param typ
		 * @param json
		 */
		public function MeasureKeyboardEvent(typ:String, json:String = null) {
			
			super(typ);
			
			if (json && json.length) {
				
				data                = JSON.parse(json);
				var viewRect:Object = data.viewRect;
				
				if (viewRect) {
					
					var s:Number  = data.hasOwnProperty("nativeScale") ? data.nativeScale : 1;
					_keyboardRect = new Rectangle(viewRect.x * s, viewRect.y * s, viewRect.width * s,
												  viewRect.height * s);
				}
			}
		}
		
		/**
		 *
		 */
		public function get keyboardRect():Rectangle {
			return _keyboardRect;
		}
	}
}