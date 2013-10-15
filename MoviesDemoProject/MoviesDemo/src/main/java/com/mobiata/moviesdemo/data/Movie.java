package com.mobiata.moviesdemo.data;

import org.joda.time.LocalTime;

import java.util.List;

public class Movie {

	private String mTitle;

	private int mPosterResId;

	// G, PG, PG-13, R
	private String mFilmRating;

	// Out of 5
	private int mScore;

	private List<LocalTime> mShowTimes;

	private int mDaysTillRelease;

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public int getPosterResId() {
		return mPosterResId;
	}

	public void setPosterResId(int posterResId) {
		mPosterResId = posterResId;
	}

	public String getFilmRating() {
		return mFilmRating;
	}

	public void setFilmRating(String filmRating) {
		mFilmRating = filmRating;
	}

	public int getScore() {
		return mScore;
	}

	public void setScore(int score) {
		mScore = score;
	}

	public List<LocalTime> getShowTimes() {
		return mShowTimes;
	}

	public void setShowTimes(List<LocalTime> showTimes) {
		mShowTimes = showTimes;
	}

	public int getDaysTillRelease() {
		return mDaysTillRelease;
	}

	public void setDaysTillRelease(int daysTillRelease) {
		mDaysTillRelease = daysTillRelease;
	}
}
