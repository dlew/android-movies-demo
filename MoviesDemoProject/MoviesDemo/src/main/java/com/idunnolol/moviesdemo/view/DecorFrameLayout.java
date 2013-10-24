package com.idunnolol.moviesdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * This is an unremarkable FrameLayout that is only special because it is
 * a Decor View, which makes ViewPager leave it around.
 */
public class DecorFrameLayout extends FrameLayout implements ViewPager.Decor {

	public DecorFrameLayout(Context context) {
		super(context);
	}

	public DecorFrameLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DecorFrameLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

}
