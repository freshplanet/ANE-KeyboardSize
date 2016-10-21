package com.freshplanet.ane.KeyboardSize;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import android.view.WindowManager;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.adobe.fre.FREWrongThreadException;

public class getKeyboardHeight implements FREFunction {

	private static String tag = "ANE : getKeyboardHeight";
	@Override
	public FREObject call(FREContext arg0, FREObject[] arg1) {

		Rect r = new Rect();
		View rootview = arg0.getActivity().getWindow().getDecorView();
		rootview.getWindowVisibleDisplayFrame(r);

		int heightNumber = rootview.getHeight() - r.bottom;
		FREObject height = null;

		try {
			height = FREObject.newObject(heightNumber);
		} catch (IllegalStateException e) {
			Log.d(tag, e.getMessage());
			e.printStackTrace();
		} catch (FREWrongThreadException e) {
			e.printStackTrace();
		}

		Log.d(tag, heightNumber+" ");

		return height;
	}

}
