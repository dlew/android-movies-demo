package com.idunnolol.moviesdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * As this is translated, its children get translated back in the opposite direction
 * This has the effect of making it look "centered" even though it is sliding.
 */
public class CenteringRelativeLayout extends RelativeLayout {

	public CenteringRelativeLayout(Context context) {
		super(context);
	}

	public CenteringRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CenteringRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void setTranslationX(float translationX) {
		super.setTranslationX(translationX);

		float childCounter = -translationX / 2;
		int childCount = getChildCount();
		for (int a = 0; a < childCount; a++) {
			View child = getChildAt(a);
			child.setTranslationX(childCounter);
		}
	}

}
