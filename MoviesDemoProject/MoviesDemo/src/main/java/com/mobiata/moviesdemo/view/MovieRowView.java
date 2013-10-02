package com.mobiata.moviesdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobiata.moviesdemo.R;
import com.mobiata.moviesdemo.data.Movie;
import com.mobiata.moviesdemo.util.BitmapCache;

public class MovieRowView extends SlidingRevealViewGroup {

	private ImageView mPosterView;
	private TextView mTitleView;

	private TextView mContentTitleView;

	public MovieRowView(Context context) {
		super(context);
	}

	public MovieRowView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MovieRowView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		mPosterView = (ImageView) findViewById(R.id.poster_view);
		mTitleView = (TextView) findViewById(R.id.title_view);
		mContentTitleView = (TextView) findViewById(R.id.content_title_view);
	}

	public void bind(Movie movie) {
		// Only bind if we're not mid-reveal
		float revealPercent = getRevealPercent();
		if (revealPercent == 0 || revealPercent == 1) {
			mPosterView.setImageBitmap(BitmapCache.getBitmap(movie.getPosterResId()));
			mTitleView.setText(movie.getTitle());
			mContentTitleView.setText(movie.getTitle());
		}
	}

	@Override
	protected void onUpdateSlide() {
		super.onUpdateSlide();

		float revealPercent = getRevealPercent();
		mTitleView.setAlpha(1 - revealPercent);
	}
}
