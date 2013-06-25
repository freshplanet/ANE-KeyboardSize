package com.freshplanet.ane.KeyboardSize;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREExtension;

public class Extension implements FREExtension{

	/*
	 * Creates a new instance of ANESampleContext when the context is created 
	 * from the actionscript code.
	 */
	
	public static ExtensionContext context;
	
	public FREContext createContext(String extId) {	
		context = new ExtensionContext();
		return context;
	}
	
	/*
	 * Called if the extension is unloaded from the process. Extensions
	 * are not guaranteed to be unloaded; the runtime process may exit without
	 * doing so.
	 */
	@Override
	public void dispose() {
		context = null;
	}

	/*
 	 * Extension initialization.
 	 */  
	public void initialize( ) {
	}
	
}
