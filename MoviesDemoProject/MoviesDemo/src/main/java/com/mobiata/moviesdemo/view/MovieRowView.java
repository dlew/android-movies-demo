package com.mobiata.moviesdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.mobiata.moviesdemo.R;
import com.mobiata.moviesdemo.data.Movie;
import com.mobiata.moviesdemo.util.BitmapCache;

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
		mPosterView.setImageBitmap(BitmapCache.getBitmap(movie.getPosterResId()));
	}

    @Override
    protected void onUpdateSlide() {
        super.onUpdateSlide();
        mPosterView.invalidate();
    }
}
