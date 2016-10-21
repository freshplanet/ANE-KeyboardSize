package com.freshplanet.ane.KeyboardSize;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.View;

import android.view.WindowManager;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.adobe.fre.FREWrongThreadException;

import java.lang.reflect.InvocationTargetException;

public class getKeyboardY implements FREFunction{

	private static String tag = "ANE : getKeyboardY";
	@Override
	public FREObject call(FREContext arg0, FREObject[] arg1) {

		Rect r = new Rect();
		View rootview = arg0.getActivity().getWindow().getDecorView();
		rootview.getWindowVisibleDisplayFrame(r);

		FREObject Y = null;

		try {
			Y = FREObject.newObject(r.bottom);
		} catch (IllegalStateException e) {
			Log.d(tag, e.getMessage());
			e.printStackTrace();
		} catch (FREWrongThreadException e) {
			e.printStackTrace();
		}

		return Y;
	}
}



