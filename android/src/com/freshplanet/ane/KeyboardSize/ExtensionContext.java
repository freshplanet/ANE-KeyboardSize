package com.freshplanet.ane.KeyboardSize;

import android.app.ActionBar;
import android.content.res.Configuration;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.adobe.air.AndroidActivityWrapper;
import com.adobe.air.KeyboardSizeStateChangeCallback;
import com.adobe.fre.*;

import java.util.HashMap;
import java.util.Map;

public class ExtensionContext extends FREContext implements View.OnLayoutChangeListener, KeyboardSizeStateChangeCallback {

    private AndroidActivityWrapper wrapper;

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
        functionMap.put("getMultilineTextViewHeight", getMultilineTextViewHeight);
        functionMap.put("resetFullScreen", resetFullScreenFunction);
        functionMap.put("getScreenHeight", getScreenHeightFunction);
        functionMap.put("init", initFunction);

		return functionMap;
	}

    @Override
    public void onActivityStateChanged(AndroidActivityWrapper.ActivityState activityState) {
        resetFullScreen();
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        resetFullScreen();
    }

    public final FREFunction getMultilineTextViewHeight = new FREFunction() {
        @Override
        public FREObject call(FREContext freContext, FREObject[] freObjects) {
            double found = findTextHeight((ViewGroup)getActivity().getWindow().getDecorView().getRootView());
            try {
                return FREObject.newObject(found);
            } catch (FREWrongThreadException e) {
                e.printStackTrace();
            }
            return null;
        }
    };

    private double findTextHeight(ViewGroup parent)
    {
        for(int index=0; index<parent.getChildCount(); ++index) {
            View nextChild = parent.getChildAt(index);
            if(nextChild instanceof TextView) {
                Log.d("KeyboardSize", "Found a textview: " + ((TextView)nextChild).getText());
                nextChild.measure(0,0);
                return nextChild.getHeight();
            } else if (nextChild instanceof ViewGroup){
                double ret = findTextHeight((ViewGroup)nextChild);
                if(ret > 0) {
                    return ret;
                }
            }
        }
        return -1;
    }

    public final FREFunction initFunction = new FREFunction() {
        @Override
        public FREObject call(FREContext freContext, FREObject[] freObjects) {

            boolean fullscreen = false;
            if(freObjects.length > 0) {
                try {
                    fullscreen = freObjects[0].getAsBool();
                } catch (FRETypeMismatchException e) {
                    e.printStackTrace();
                } catch (FREInvalidObjectException e) {
                    e.printStackTrace();
                } catch (FREWrongThreadException e) {
                    e.printStackTrace();
                }
            }

            if(fullscreen) {
                AndroidActivityWrapper.GetAndroidActivityWrapper().addActivityStateChangeListner(ExtensionContext.this);
                freContext.getActivity().getWindow().getDecorView().addOnLayoutChangeListener(ExtensionContext.this);
            }

            return null;
        }
    };

    public final FREFunction getScreenHeightFunction = new FREFunction() {
        @Override
        public FREObject call(FREContext freContext, FREObject[] freObjects) {
            View rootview = freContext.getActivity().getWindow().getDecorView();
            try {
                return FREObject.newObject(rootview.getHeight());
            } catch (FREWrongThreadException e) {
                e.printStackTrace();
            }
            return null;
        }
    };

    private void resetFullScreen() {
        View decorView = getActivity().getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE);
        ActionBar actionBar = getActivity().getActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }
    }

    public final FREFunction resetFullScreenFunction = new FREFunction() {
        @Override
        public FREObject call(FREContext freContext, FREObject[] freObjects) {
            resetFullScreen();
            return null;
        }
    };

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

        resetFullScreen();
    }

    public void log(String message)
    {
        this.dispatchStatusEventAsync("LOG", message);
    }

}
