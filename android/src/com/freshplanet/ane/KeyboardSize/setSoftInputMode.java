package com.freshplanet.ane.KeyboardSize;

import android.view.WindowManager;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FREWrongThreadException;

public class setSoftInputMode implements FREFunction{

	@Override
	public FREObject call(FREContext context, FREObject[] args) {
		
		int layoutParam = 0;
		try{
			layoutParam = args[0].getAsInt();
		}
		catch (FRETypeMismatchException e) {
			e.printStackTrace();
		}
		catch (FREInvalidObjectException e) {
			e.printStackTrace();
		}
		catch (FREWrongThreadException e) {
			e.printStackTrace();
		}

		context.getActivity().getWindow().setSoftInputMode(layoutParam);
		return null;
	}

}