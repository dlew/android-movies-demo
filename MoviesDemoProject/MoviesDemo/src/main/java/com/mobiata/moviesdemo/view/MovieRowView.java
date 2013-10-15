package com.mobiata.moviesdemo.view;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobiata.moviesdemo.R;
import com.mobiata.moviesdemo.data.Movie;
import com.mobiata.moviesdemo.util.BitmapCache;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

public class MovieRowView extends SlidingRevealViewGroup {

	private ImageView mPosterView;
	private TextView mTitleView;
	private TextView mSubtitleView;

	private ViewGroup mContentContainer;
	private TextView mContentTitleView;
	private TextView mFilmRatingTextView;

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
		mSubtitleView = (TextView) findViewById(R.id.subtitle_view);
		mContentContainer = (ViewGroup) findViewById(R.id.content_container);
		mContentTitleView = (TextView) findViewById(R.id.content_title_view);
		mFilmRatingTextView = (TextView) findViewById(R.id.film_rating_text_view);
	}

	public void bind(Movie movie) {
		// Only bind if we're not mid-reveal
		float revealPercent = getRevealPercent();
		if (revealPercent == 0 || revealPercent == 1) {
			mPosterView.setImageBitmap(BitmapCache.getBitmap(movie.getPosterResId()));
			mTitleView.setText(movie.getTitle());

			SpannableString ss;
			int highlightStart, highlightEnd;
			if (movie.getShowTimes() != null) {
				List<String> strings = new ArrayList<String>();

				for (LocalTime time : movie.getShowTimes()) {
					DateTime utcDateTime = time.toDateTimeToday(DateTimeZone.UTC);
					strings.add(DateUtils.formatDateTime(getContext(), utcDateTime.getMillis(),
							DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_UTC));
				}

				highlightStart = 0;
				highlightEnd = strings.get(0).length();
				ss = new SpannableString(TextUtils.join(", ", strings));
			}
			else {
				int numDays = movie.getDaysTillRelease();
				highlightStart = 0;
				highlightEnd = Integer.toString(numDays).length();
				ss = new SpannableString(getResources().getQuantityString(R.plurals.numberOfDays, numDays, numDays));
			}

			// Setup highlight for subtitle
			ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.greenText)), highlightStart,
					highlightEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

			mSubtitleView.setText(ss);

			mContentTitleView.setText(movie.getTitle());
			mFilmRatingTextView.setText(movie.getFilmRating());
		}
	}

	@Override
	protected void onUpdateSlide() {
		super.onUpdateSlide();

		float revealPercent = getRevealPercent();
		boolean isSliding = revealPercent != 0 && revealPercent != 1;

		mTitleView.setAlpha(1 - revealPercent);
		mSubtitleView.setAlpha(1 - revealPercent);
	}

	@Override
	public void setUseHardwareLayers(boolean useHardwareLayers) {
		// TODO: While this helps performance, it does result in a spike when you first start sliding
		// Maybe there's some solution where some of the Views remain in a HW layer throughout?

		int toLayerType = useHardwareLayers ? View.LAYER_TYPE_HARDWARE : View.LAYER_TYPE_NONE;
		if (mPosterView.getLayerType() != toLayerType) {
			mPosterView.setLayerType(toLayerType, null);
			mTitleView.setLayerType(toLayerType, null);
			mSubtitleView.setLayerType(toLayerType, null);
		}

		// Only HW layer the content container if it's even visible
		if (mContentContainer.getVisibility() == View.VISIBLE && mContentContainer.getLayerType() != toLayerType) {
			mContentContainer.setLayerType(toLayerType, null);
		}
	}
}
