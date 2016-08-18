package com.freshplanet.ane.KeyboardSize;

import java.util.HashMap;
import java.util.Map;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.adobe.fre.FREWrongThreadException;
import org.json.JSONException;
import org.json.JSONObject;

public class ExtensionContext extends FREContext implements View.OnLayoutChangeListener {


    @Override
    public void dispose() {
        getActivity().getWindow().getDecorView().removeOnLayoutChangeListener(this);
    }

	@Override
	public Map<String, FREFunction> getFunctions() {

		Map<String, FREFunction> functionMap = new HashMap<String, FREFunction>();
		functionMap.put("getKeyboardY", new getKeyboardY() );
		functionMap.put("getKeyboardHeight", new getKeyboardHeight() );
		functionMap.put("setSoftInputMode",new setSoftInputMode() );
		functionMap.put("removeClearButtonForiOS",new removeClearButtonForiOS() );
        functionMap.put("init", initFunction);

		return functionMap;
	}

    public final FREFunction initFunction = new FREFunction() {
        @Override
        public FREObject call(FREContext freContext, FREObject[] freObjects) {
           // freContext.getActivity().getWindow().getCurrentFocus().addOnLayoutChangeListener(ExtensionContext.this);
          //  freContext.getActivity().getWindow().getDecorView().addOnLayoutChangeListener(ExtensionContext.this);
            return null;
        }
    };

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        try {
            JSONObject output = new JSONObject();


            output.put("left", left);
            output.put("right", right);
            output.put("top", top);
            output.put("bottom", bottom);
            output.put("oldLeft", left);
            output.put("oldRight", right);
            output.put("oldTop", top);
            output.put("oldBottom", bottom);

            dispatchStatusEventAsync("LAYOUT_CHANGE", output.toString(2));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static String rectsToJSON (Rect rect1, Rect rect2) {
        JSONObject output = new JSONObject();
        JSONObject rect1JSON = new JSONObject();
        JSONObject rect2JSON = new JSONObject();

        try {
            rect1JSON.put("left", rect1.left);
            rect1JSON.put("top", rect1.top);
            rect1JSON.put("width", rect1.width());
            rect1JSON.put("height", rect1.height());
            output.put("rect1", rect1JSON);

            rect2JSON.put("left", rect2.left);
            rect2JSON.put("top", rect2.top);
            rect2JSON.put("width", rect2.width());
            rect2JSON.put("height", rect2.height());
            output.put("rect2", rect2JSON);

            return output.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "{}";

    }
}
