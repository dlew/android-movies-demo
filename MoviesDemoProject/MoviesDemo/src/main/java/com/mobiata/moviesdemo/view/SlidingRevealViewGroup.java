package com.mobiata.moviesdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Holds two views which "slide" open
 *
 * The first child is assumed to be the "cover", while the second child
 * is assumed to be the "slide" which reveals/hides itself
 */
public class SlidingRevealViewGroup extends RelativeLayout {

	public enum Reveal {
		LEFT,
		RIGHT
	}

	// Which side to reveal
	private Reveal mReveal;

	// Represents how far in we are sliding; 0% == totally revealed, 100% == totally hidden
	private float mRevealPercent;

	public SlidingRevealViewGroup(Context context) {
		this(context, null);
	}

	public SlidingRevealViewGroup(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SlidingRevealViewGroup(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);

		if (changed) {
			updateSlide();
		}
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		if (getChildCount() != 2) {
			throw new RuntimeException("Need two (and only two) children!");
		}
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();

		View coverView = getChildAt(0);
		coverView.bringToFront();
	}

	public void setReveal(Reveal reveal) {
		mReveal = reveal;
	}

	public void setRevealPercent(float revealPercent) {
		mRevealPercent = revealPercent;

		updateSlide();
	}

	public float getSlideRevealX() {
		View revealView = getChildAt(0);
		return revealView.getWidth() - Math.abs(revealView.getTranslationX());
	}

	private void updateSlide() {
		int width = getWidth();
		if (width == 0) {
			// We haven't measured yet; wait until measurement to slide
			return;
		}

		View revealView = getChildAt(0);
		float translationX = (1 - mRevealPercent) * revealView.getWidth();
		revealView.setTranslationX(mReveal == Reveal.RIGHT ? -translationX : translationX);
	}
}
