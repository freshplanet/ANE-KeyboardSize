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

		int YNumber = r.bottom - getNavigationBarSize(arg0.getActivity().getApplicationContext()).y;
		FREObject Y = null;

		try {
			Y = FREObject.newObject(YNumber);
		} catch (IllegalStateException e) {
			Log.d(tag, e.getMessage());
			e.printStackTrace();
		} catch (FREWrongThreadException e) {
			e.printStackTrace();
		}

		Log.d(tag, YNumber+" ");

		return Y;
	}

	public static Point getNavigationBarSize(Context context) {
		Point appUsableSize = getAppUsableScreenSize(context);
		Point realScreenSize = getRealScreenSize(context);

		// navigation bar on the right
		if (appUsableSize.x < realScreenSize.x) {
			return new Point(realScreenSize.x - appUsableSize.x, appUsableSize.y);
		}

		// navigation bar at the bottom
		if (appUsableSize.y < realScreenSize.y) {
			return new Point(appUsableSize.x, realScreenSize.y - appUsableSize.y);
		}

		// navigation bar is not present
		return new Point();
	}

	public static Point getAppUsableScreenSize(Context context) {
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		return size;
	}

	public static Point getRealScreenSize(Context context) {
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		Point size = new Point();

		if (Build.VERSION.SDK_INT >= 17) {
			display.getRealSize(size);
		} else if (Build.VERSION.SDK_INT >= 14) {
			try {
				size.x = (Integer) Display.class.getMethod("getRawWidth").invoke(display);
				size.y = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
			} catch (IllegalAccessException e) {} catch (InvocationTargetException e) {} catch (NoSuchMethodException e) {}
		}

		return size;
	}
}



