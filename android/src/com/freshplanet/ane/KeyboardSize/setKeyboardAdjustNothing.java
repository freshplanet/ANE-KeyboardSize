package com.freshplanet.ane.KeyboardSize;

import android.view.WindowManager;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.adobe.fre.FREWrongThreadException;

public class setKeyboardAdjustNothing implements FREFunction{
	
	@Override
	public FREObject call(FREContext arg0, FREObject[] arg1) {
				
		arg0.getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);;
		return null;
	}

}