package com.mobiata.moviesdemo.view;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mobiata.moviesdemo.R;
import com.mobiata.moviesdemo.data.Movie;
import com.mobiata.moviesdemo.util.BitmapCache;
import com.mobiata.moviesdemo.util.FontCache;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class MovieRowView extends SlidingRevealViewGroup {

	private ViewGroup mCoverContainer;
	private ImageView mPosterView;
	private TextView mTitleView;
	private TextView mSubtitleView;

	private ViewGroup mContentContainer;
	private TextView mContentTitleView;
	private ViewGroup mShowtimesContainer;
	private TextView mShowtimesTextView;
	private ViewGroup mUpcomingContainer;
	private TextView mUpcomingDateTextView;
	private TextView mUpcomingDaysTextView;
	private RatingBar mRatingBar;
	private TextView mFilmRatingTextView;

	// Cached for faster binding
	private List<String> mStrArr = new ArrayList<String>();
	private DateFormat mDateFormat;

	public MovieRowView(Context context) {
		this(context, null);
	}

	public MovieRowView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MovieRowView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		mDateFormat = android.text.format.DateFormat.getTimeFormat(context);
		mDateFormat.setTimeZone(DateTimeZone.UTC.toTimeZone());
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		mCoverContainer = (ViewGroup) findViewById(R.id.cover_container);
		mPosterView = (ImageView) findViewById(R.id.poster_view);
		mTitleView = (TextView) findViewById(R.id.title_view);
		mSubtitleView = (TextView) findViewById(R.id.subtitle_view);
		mContentContainer = (ViewGroup) findViewById(R.id.content_container);
		mContentTitleView = (TextView) findViewById(R.id.content_title_view);
		mShowtimesContainer = (ViewGroup) findViewById(R.id.showtimes_container);
		mShowtimesTextView = (TextView) findViewById(R.id.showtimes_text_view);
		mUpcomingContainer = (ViewGroup) findViewById(R.id.upcoming_container);
		mUpcomingDateTextView = (TextView) findViewById(R.id.upcoming_date_text_view);
		mUpcomingDaysTextView = (TextView) findViewById(R.id.upcoming_days_text_view);
		mRatingBar = (RatingBar) findViewById(R.id.rating_bar);
		mFilmRatingTextView = (TextView) findViewById(R.id.film_rating_text_view);

		mFilmRatingTextView.setTypeface(FontCache.getTypeface(getContext(), "fonts/RobotoCondensed-Bold.ttf"));
	}

	public void setCellSize(int cellSize) {
		getLayoutParams().height = cellSize;
		mCoverContainer.getLayoutParams().width = cellSize;
	}

	public void bind(Movie movie) {
		mPosterView.setImageBitmap(BitmapCache.getBitmap(movie.getPosterResId()));
		mTitleView.setText(movie.getTitle());

		SpannableString ss;
		int highlightStart = 0;
		int highlightEnd;
		if (movie.getShowTimes() != null) {
			mStrArr.clear();
			for (long utcMillis : movie.getShowTimesInUtcMillis()) {
				mStrArr.add(mDateFormat.format(utcMillis));
			}
			highlightEnd = mStrArr.get(0).length();
			ss = new SpannableString(TextUtils.join(", ", mStrArr));
		}
		else {
			int numDays = movie.getDaysTillRelease();
			highlightEnd = Integer.toString(numDays).length();
			ss = new SpannableString(getResources().getQuantityString(R.plurals.numberOfDays, numDays, numDays));
		}

		// Setup highlight for subtitle
		ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.greenText)), highlightStart,
				highlightEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		mSubtitleView.setText(ss);

		mContentTitleView.setText(movie.getTitle());

		if (movie.getShowTimes() == null) {
			mShowtimesContainer.setVisibility(View.GONE);
		}
		else {
			mShowtimesContainer.setVisibility(View.VISIBLE);

			// Show our previous showtimes text
			mShowtimesTextView.setText(ss.toString());
		}

		if (movie.getDaysTillRelease() == 0) {
			mUpcomingContainer.setVisibility(View.GONE);
		}
		else {
			mUpcomingContainer.setVisibility(View.VISIBLE);

			LocalDate date = LocalDate.now().plusDays(movie.getDaysTillRelease());
			mUpcomingDateTextView.setText(makeTwoLineText(Integer.toString(date.getDayOfMonth()), date
					.monthOfYear().getAsShortText().toUpperCase()));

			// Split up our previous # of days text, as a convenience (not very robust I admit)
			String[] split = ss.toString().split(" ");
			mUpcomingDaysTextView.setText(makeTwoLineText(split[0], split[1]));
		}

		if (movie.getScore() == 0) {
			mRatingBar.setVisibility(View.GONE);
		}
		else {
			mRatingBar.setVisibility(View.VISIBLE);
			mRatingBar.setRating(movie.getScore());
		}

		mFilmRatingTextView.setText(movie.getFilmRating());
	}

	private CharSequence makeTwoLineText(String lineOne, String lineTwo) {
		SpannableString ss = new SpannableString(lineOne + "\n" + lineTwo);
		ss.setSpan(new RelativeSizeSpan(2f), 0, lineOne.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return ss;
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
			mContentContainer.setLayerType(toLayerType, null);
		}
	}
}
