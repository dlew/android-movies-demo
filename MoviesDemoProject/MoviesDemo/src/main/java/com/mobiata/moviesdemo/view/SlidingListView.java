package com.mobiata.moviesdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import com.mobiata.moviesdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Allows SlidingPairViews to operate without having to layout constantly
 */
public class SlidingListView extends ListView {

	public SlidingListView(Context context) {
		super(context);
	}

	public SlidingListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SlidingListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	// For performance reasons, we want to set the slide directly on the views instead of
	// setting the slide in the adapter and notifying that the dataset changed.
	public void setSlide(float slide) {
		if (getWidth() == 0) {
			return;
		}

		for (SlidingPairView child : getAllSlidingPairViewChildren()) {
			child.setSlide(slide);
		}
	}

	public void setUseHardwareLayers(boolean useHardwareLayers) {
		for (SlidingPairView child : getAllSlidingPairViewChildren()) {
			child.setUseHardwareLayers(useHardwareLayers);
		}
	}

	private List<SlidingPairView> getAllSlidingPairViewChildren() {
		List<SlidingPairView> children = new ArrayList<SlidingPairView>();
		int childCount = getChildCount();
		for (int a = 0; a < childCount; a++) {
			View view = getChildAt(a).findViewById(R.id.sliding_pair);
			if (view != null) {
				children.add((SlidingPairView) view);
			}
		}
		return children;
	}

}
