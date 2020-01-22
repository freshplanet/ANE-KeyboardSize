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
package com.freshplanet.ane.AirKeyboardSize;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREExtension;

/**
 *
 */
public class Extension implements FREExtension {
	
	static final String TAG = "AirKeyboardSize";
	
	/**
	 * Extension initialization.
	 */
	public void initialize() {
	
	}
	
	/**
	 * Called if the extension is unloaded from the process. Extensions
	 * are not guaranteed to be unloaded; the runtime process may exit without
	 * doing so.
	 */
	@Override
	public void dispose() {
	
	}
	
	/**
	 * @param extId
	 * @return
	 */
	public FREContext createContext(String extId) {
		
		return new ExtensionContext();
	}
}
