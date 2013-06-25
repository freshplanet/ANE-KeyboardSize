package com.freshplanet.ane.KeyboardSize;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

public class initFunction implements FREFunction {

	@Override
	public FREObject call(FREContext arg0, FREObject[] arg1) {
		
		System.out.println("initFunction successfully called.");
		return null;
	}

}
