package com.mobiata.moviesdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.mobiata.moviesdemo.R;
import com.mobiata.moviesdemo.data.Movie;

public class MovieRowView extends SlidingRevealViewGroup {

	private ImageView mPosterView;

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
	}

	public void bind(Movie movie) {
		mPosterView.setImageResource(movie.getPosterResId());
	}
}
