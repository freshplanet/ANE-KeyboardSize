package com.freshplanet.ane.KeyboardSize;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.adobe.fre.FREWrongThreadException;

public class getKeyboardY implements FREFunction{

	private static String tag = "ANE : getKeyboardY";
	@Override
	public FREObject call(FREContext arg0, FREObject[] arg1) {

		Rect r = new Rect();
		View rootview = arg0.getActivity().getWindow().getDecorView();
		rootview.getWindowVisibleDisplayFrame(r);

		FREObject Y = null;

		try {
			int bot = r.bottom;
			if (arg0.getActivity().getActionBar() != null)
			{
			    bot -= arg0.getActivity().getActionBar().getHeight();
			}
			bot -= r.top;

			Y = FREObject.newObject(bot);
		} catch (IllegalStateException e) {
			Log.d(tag, e.getMessage());
			e.printStackTrace();
		} catch (FREWrongThreadException e) {
			e.printStackTrace();
		}

		return Y;
	}
}



