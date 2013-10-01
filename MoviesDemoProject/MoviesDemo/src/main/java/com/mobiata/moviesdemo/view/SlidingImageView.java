package com.mobiata.moviesdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SlidingImageView extends ImageView {

	public SlidingImageView(Context context) {
		super(context);
	}

	public SlidingImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SlidingImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(-getTranslationX() / 2, 0);
        super.onDraw(canvas);
        canvas.restore();
	}
}
