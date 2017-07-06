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
	
	import flash.events.EventDispatcher;
	import flash.events.StatusEvent;
	import flash.external.ExtensionContext;
	import flash.system.Capabilities;
	
	public class MeasureKeyboard extends EventDispatcher {
		
		public static var LOGGING:Boolean = true;
		
		private static const SOFT_INPUT_STATE_UNCHANGED:int = 1;
		private static const SOFT_INPUT_ADJUST_NOTHING:int  = 48;
		
		/*
		 * All Android variables, but iOS is not using the corresponding functions, so it's easier to put them here and
		 * call setKeyboard layout with an arg
		 */
		private static const SOFT_INPUT_ADJUST_PAN:int      = 32;
		private static const SOFT_INPUT_ADJUST_RESIZE:int   = 16;
		
		private static const EXTENSION_NAME:String = "AirKeyboardSize";
		private static const EXTENSION_ID:String = "com.freshplanet.ane." + EXTENSION_NAME;
		
		private static var _instance:MeasureKeyboard = null;
		
		private var extContext:ExtensionContext = null;
		
		public static function get isSupported():Boolean {
			return Capabilities.manufacturer.indexOf("iOS") > -1 || Capabilities.manufacturer.indexOf("Android") > -1;
		}
		
		public static function get instance():MeasureKeyboard {
			return _instance ? _instance : new MeasureKeyboard();
		}
		
		public function MeasureKeyboard(fullScreen:Boolean = false) {
			
			if (!isSupported)
				return;
			
			if (!_instance) {
				
				extContext = ExtensionContext.createExtensionContext(EXTENSION_ID, null);
				
				if (!extContext) {
					
					trace("ERROR - Extension context is null. Please check if extension.xml is setup correctly.");
					return;
				}
				
				extContext.addEventListener(StatusEvent.STATUS, _onStatusEvent, false, 0, true);
				
				extContext.call("init", fullScreen);
				_instance = this;
			}
			else {
				throw Error("This is a singleton, use getInstance(), do not call the constructor directly.");
			}
		}
		
		/**
		 *
		 * PUBLIC
		 *
		 */
		
		
		/**
		 *
		 */
		public function resetFullScreen(showBars:Boolean = false):void {
			
			if (Capabilities.manufacturer.indexOf("Android") > -1)
				extContext.call("resetFullScreen", showBars);
		}
		
		/**
		 *
		 * @return
		 */
		public function getKeyboardHeight():Object {
			
			if (!isSupported)
				return null;
			
			var retHeight:Object = extContext.call("getKeyboardHeight");
			return retHeight;
		}
		
		/**
		 *
		 * @return
		 */
		public function getKeyboardY():Object {
			
			if (!isSupported)
				return null;
			
			var retY:Object = extContext.call("getKeyboardY");
			return retY;
		}
		
		/**
		 *
		 */
		public function setKeyboardAdjustDefault():void {
			
			if (isSupported)
				extContext.call("setSoftInputMode", SOFT_INPUT_ADJUST_PAN);
		}
		
		/**
		 *
		 */
		public function setKeyboardAdjustNothing():void {
			
			if (isSupported)
				extContext.call("setSoftInputMode", SOFT_INPUT_ADJUST_NOTHING);
		}
		
		/**
		 *
		 */
		public function setKeyboardStateUnchanged():void {
			
			if (isSupported)
				extContext.call("setSoftInputMode", SOFT_INPUT_STATE_UNCHANGED);
		}
		
		/**
		 *
		 */
		public function removeClearButtonForiOS():void {
			
			if (isSupported)
				extContext.call("removeClearButtonForiOS");
		}
		
		/**
		 *
		 * @return
		 */
		public function getMultilineTextViewHeight():Object {
			
			if (!isSupported)
				return null;
			
			var retTextViewHeight:Object = extContext.call("getMultilineTextViewHeight");
			return retTextViewHeight;
		}
		
		/**
		 *
		 * @return
		 */
		public function getScreenHeight():int {
			
			if (!isSupported || Capabilities.manufacturer.indexOf("Android") == -1)
				return Capabilities.screenResolutionY;
			
			return extContext.call("getScreenHeight") as int;
		}
		
		/**
		 *
		 * PRIVATE
		 *
		 */
		
		/**
		 *
		 * @param event
		 */
		private function _onStatusEvent(event:StatusEvent):void {
			
			if (LOGGING)
				trace(EXTENSION_NAME, event.code, event.level);
			
			this.dispatchEvent(new MeasureKeyboardEvent(event.code, event.level));
		}
	}
}