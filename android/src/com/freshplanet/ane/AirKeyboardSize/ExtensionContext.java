/*
 * Copyright 2017 FreshPlanet
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.freshplanet.ane.AirKeyboardSize;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.TextView;
import com.adobe.air.AirKeyboardSizeStateChangeCallback;
import com.adobe.air.AndroidActivityWrapper;
import com.adobe.fre.*;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;

import java.util.HashMap;
import java.util.Map;

public class ExtensionContext extends FREContext implements AirKeyboardSizeStateChangeCallback, View.OnLayoutChangeListener, ViewTreeObserver.OnGlobalLayoutListener {
	
	private AndroidActivityWrapper _aaw = null;
	
	/*
	 *
	 * context setup + helpers
	 *
	 */
	
	ExtensionContext() {
		
		_aaw = AndroidActivityWrapper.GetAndroidActivityWrapper();
//		_aaw.addActivityStateChangeListner(this);
	}
	
	@Override
	public void dispose() {
		
		Activity activity = _aaw.getActivity();
		Window window = activity.getWindow();
		View decorView = window.getDecorView();
		
		decorView.removeOnLayoutChangeListener(this);
		
		if (_aaw != null) {
			
			_aaw.removeActivityStateChangeListner(this);
			_aaw = null;
		}
	}
	
	@Override
	public Map<String, FREFunction> getFunctions() {
		
		Map<String, FREFunction> functionMap = new HashMap<String, FREFunction>();
		functionMap.put("getKeyboardY", getKeyboardY);
		functionMap.put("getKeyboardHeight", getKeyboardHeight);
		functionMap.put("setSoftInputMode", setSoftInputMode);
		functionMap.put("removeClearButtonForiOS", removeClearButtonForiOS);
		functionMap.put("getMultilineTextViewHeight", getMultilineTextViewHeight);
		functionMap.put("resetFullScreen", resetFullScreenFunction);
		functionMap.put("getScreenHeight", getScreenHeightFunction);
		functionMap.put("init", initFunction);
		functionMap.put("isNavBarDisplayed", isNavBarDisplayed);

		return functionMap;
	}
	
	private void _log(String message) {
		
		_dispatchEvent("LOG", message);
	}
	
	private void _dispatchEvent(String type, String data) {
		
		try {
			dispatchStatusEventAsync(type, "" + data);
		}
		catch (Exception exception) {
			Log.e(Extension.TAG, "dispatchStatusEventAsync", exception);
		}
	}
	
	/**
	 * @param parent
	 * @return
	 */
	private double _findTextHeight(ViewGroup parent) {
		
		for (int index = 0; index < parent.getChildCount(); ++index) {
			
			View nextChild = parent.getChildAt(index);
			
			if (nextChild instanceof TextView) {
				
				_log("Found a textview: " + ((TextView) nextChild).getText());
				nextChild.measure(0, 0);
				
				return nextChild.getHeight();
			}
			else if (nextChild instanceof ViewGroup) {
				
				double ret = _findTextHeight((ViewGroup) nextChild);
				
				if (ret > 0)
					return ret;
			}
		}
		
		return -1;
	}
	
	/**
	 *
	 */
	private void _resetFullScreen() {
		
		Activity activity = _aaw.getActivity();
		ActionBar actionBar = activity.getActionBar();
		Window window = activity.getWindow();
		View decorView = window.getDecorView();
		
		decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LOW_PROFILE |
										View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
										View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
										View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
		
		if (actionBar != null)
			actionBar.hide();
	}
	
	private void _resetBars() {
		
		View decorView = getActivity().getWindow().getDecorView();
		ActionBar actionBar = getActivity().getActionBar();
		
		decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
		
		if (actionBar != null) {
			actionBar.show();
		}
	}
	
	/*
	 *
	 * listeners
	 *
	 */
	
	@Override
	public void onActivityStateChanged(AndroidActivityWrapper.ActivityState activityState) {
		
		switch (activityState) {
			case STARTED:
				break;
			
			case RESTARTED:
				break;
			
			case RESUMED:
				break;
			
			case PAUSED:
				break;
			
			case STOPPED:
				break;
			
			case DESTROYED:
				break;
			
			default:
				break;
		}
		
		_resetFullScreen();
	}
	
	@Override
	public void onConfigurationChanged(Configuration configuration) {
		
		_resetFullScreen();
	}
	
	@Override
	public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight,
							   int oldBottom) {
		
		_resetFullScreen();
	}
	
	@Override
	public void onGlobalLayout() {
		
		Rect r = new Rect();
		
		Activity activity = _aaw.getActivity();
		Window window = activity.getWindow();
		View decorView = window.getDecorView();
		
		decorView.getWindowVisibleDisplayFrame(r);
	}
	
	/*
	 *
	 * as3/native interface
	 *
	 */
	
	/**
	 *
	 */
	private final FREFunction getMultilineTextViewHeight = new FREFunction() {
		@Override
		public FREObject call(FREContext context, FREObject[] args) {
			
			Activity activity = _aaw.getActivity();
			Window window = activity.getWindow();
			View decorView = window.getDecorView();
			
			double found = _findTextHeight((ViewGroup) decorView.getRootView());
			
			try {
				return FREObject.newObject(found);
			}
			catch (FREWrongThreadException e) {
				e.printStackTrace();
			}
			
			return null;
		}
	};
	
	/**
	 *
	 */
	private final FREFunction initFunction = new FREFunction() {
		@Override
		public FREObject call(FREContext context, FREObject[] args) {
			
			boolean fullscreen = false;
			if (args.length > 0) {
				
				try {
					fullscreen = args[0].getAsBool();
				}
				catch (FRETypeMismatchException | FREWrongThreadException | FREInvalidObjectException e) {
					e.printStackTrace();
				}
			}
			
			if (fullscreen) {
				
				Activity activity = _aaw.getActivity();
				Window window = activity.getWindow();
				View decorView = window.getDecorView();
				
				_aaw.addActivityStateChangeListner(ExtensionContext.this);
				decorView.addOnLayoutChangeListener(ExtensionContext.this);
				decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
					@Override
					public void onSystemUiVisibilityChange(int visibility) {
						
						_resetFullScreen();
					}
				});
			}
			
			return null;
		}
	};
	
	/**
	 *
	 */
	private final FREFunction getScreenHeightFunction = new FREFunction() {
		@Override
		public FREObject call(FREContext context, FREObject[] args) {
			
			Activity activity = _aaw.getActivity();
			Window window = activity.getWindow();
			View rootview = window.getDecorView();
			
			try {
				return FREObject.newObject(rootview.getHeight());
			}
			catch (FREWrongThreadException e) {
				e.printStackTrace();
			}
			
			return null;
		}
	};
	
	/**
	 *
	 */
	private final FREFunction resetFullScreenFunction = new FREFunction() {
		@Override
		public FREObject call(FREContext context, FREObject[] args) {
			
			Boolean showUI = false;
			
			if (args.length > 0) {
				
				try {
					showUI = args[0].getAsBool();
				}
				catch (FRETypeMismatchException | FREInvalidObjectException | FREWrongThreadException e) {
					e.printStackTrace();
				}
			}
			
			if (showUI) {
				// _resetBars();
			}
			else {
				_resetFullScreen();
			}
			
			return null;
		}
	};

	/**
	 *
	 */
	private final FREFunction isNavBarDisplayed = new FREFunction() {
		@Override
		public FREObject call(FREContext context, FREObject[] args) {
			Activity activity = _aaw.getActivity();
			Display d = activity.getWindowManager().getDefaultDisplay();
			DisplayMetrics realDisplayMetrics = new DisplayMetrics();
			d.getRealMetrics(realDisplayMetrics);
			View rootView = activity.getWindow().getDecorView();
			int viewHeight = rootView.getHeight();
			boolean result = false;
			if (viewHeight == 0) {
				result = true;
			}
			else {
				int realHeight = realDisplayMetrics.heightPixels;
				result = realHeight != viewHeight;
			}

			FREObject freObject = null;
			try {
				freObject = FREObject.newObject(result);
			}
			catch (IllegalStateException e) {
				e.printStackTrace();
			}
			catch (FREWrongThreadException e) {
				e.printStackTrace();
			}
			return freObject;
		}
	};
	
	/**
	 *
	 */
	private final FREFunction removeClearButtonForiOS = new FREFunction() {
		@Override
		public FREObject call(FREContext context, FREObject[] args) {
			
			// does nothing
			return null;
		}
	};
	
	/**
	 *
	 */
	private final FREFunction setSoftInputMode = new FREFunction() {
		@Override
		public FREObject call(FREContext context, FREObject[] args) {
			
			int layoutParam = 0;
			
			try {
				layoutParam = args[0].getAsInt();
			}
			catch (FRETypeMismatchException | FREWrongThreadException | FREInvalidObjectException e) {
				e.printStackTrace();
			}
			
			context.getActivity().getWindow().setSoftInputMode(layoutParam);
			
			return null;
		}
	};
	
	/**
	 *
	 */
	private final FREFunction getKeyboardHeight = new FREFunction() {
		@Override
		public FREObject call(FREContext context, FREObject[] args) {
			
			Rect r = new Rect();
			View rootview = context.getActivity().getWindow().getDecorView();
			rootview.getWindowVisibleDisplayFrame(r);
			
			int heightNumber = rootview.getHeight() - r.bottom;
			if (context.getActivity().getActionBar() != null) {
				heightNumber += context.getActivity().getActionBar().getHeight();
			}
			heightNumber -= r.top;
			FREObject height = null;
			
			try {
				height = FREObject.newObject(heightNumber);
			}
			catch (IllegalStateException e) {
				Log.d("ANE : getKeyboardY", e.getMessage());
				e.printStackTrace();
			}
			catch (FREWrongThreadException e) {
				e.printStackTrace();
			}
			
			Log.d("ANE : getKeyboardY", heightNumber + " ");
			
			return height;
		}
	};
	
	/**
	 *
	 */
	private final FREFunction getKeyboardY = new FREFunction() {
		@Override
		public FREObject call(FREContext context, FREObject[] args) {
			
			Rect r = new Rect();
			View rootview = context.getActivity().getWindow().getDecorView();
			rootview.getWindowVisibleDisplayFrame(r);
			
			FREObject Y = null;
			
			try {
				int bot = r.bottom;
				if (context.getActivity().getActionBar() != null)
					bot -= context.getActivity().getActionBar().getHeight();
				bot -= r.top;
				
				Y = FREObject.newObject(bot);
			}
			catch (IllegalStateException e) {
				Log.d("ANE : getKeyboardY", e.getMessage());
				e.printStackTrace();
			}
			catch (FREWrongThreadException e) {
				e.printStackTrace();
			}
			
			return Y;
		}
	};
}
