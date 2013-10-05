package com.mobiata.moviesdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.mobiata.moviesdemo.R;

/**
 * Has two children - a left and right sliding pair.
 */
public class SlidingPairView extends FrameLayout {

	private float mSlide;

	// This one starts on the left, and reveals TO the right
	private SlidingRevealViewGroup mSlideRight;

	// This one starts on the right, reveals TO the left
	private SlidingRevealViewGroup mSlideLeft;

	public SlidingPairView(Context context) {
		super(context);
	}

	public SlidingPairView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SlidingPairView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		mSlideRight = (SlidingRevealViewGroup) findViewById(R.id.slide_reveal_right);
		mSlideLeft = (SlidingRevealViewGroup) findViewById(R.id.slide_reveal_left);

		mSlideRight.setReveal(SlidingRevealViewGroup.Reveal.RIGHT);
		mSlideLeft.setReveal(SlidingRevealViewGroup.Reveal.RIGHT);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);

		if (changed) {
			updateSlide();
		}
	}

	public void setSlide(float slide) {
		mSlide = slide;
		updateSlide();
	}

	private void updateSlide() {
		// TODO: Clean up math around here
		float slideHideX = mSlideLeft.getSlideHideX();
		float paddingLeftAndRight = mSlideRight.getPaddingLeft() + mSlideRight.getPaddingRight();

		if (mSlide < 0) {
			mSlideRight.setRevealPercent(-mSlide);
			mSlideRight.setTranslationX(0);
			mSlideLeft.setRevealPercent(0);
			mSlideLeft.setTranslationX(slideHideX + mSlideRight.getSlideRevealX() - paddingLeftAndRight);
		}
		else if (mSlide == 0) {
			mSlideRight.setRevealPercent(0);
			mSlideRight.setTranslationX(0);
			mSlideLeft.setRevealPercent(0);
			mSlideLeft.setTranslationX(slideHideX - paddingLeftAndRight);
		}
		else {
			mSlideLeft.setRevealPercent(mSlide);
			mSlideLeft.setTranslationX((1 - mSlide) * slideHideX - (1 - mSlide) * paddingLeftAndRight);
			mSlideRight.setRevealPercent(0);
			mSlideRight.setTranslationX(-mSlideLeft.getSlideRevealX());
		}
	}

	public void setUseHardwareLayers(boolean useHardwareLayers) {
		mSlideRight.setUseHardwareLayers(useHardwareLayers);
		mSlideLeft.setUseHardwareLayers(useHardwareLayers);
	}

}
