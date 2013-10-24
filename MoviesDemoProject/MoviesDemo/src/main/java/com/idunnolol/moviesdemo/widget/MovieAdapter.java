package com.idunnolol.moviesdemo.widget;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.idunnolol.moviesdemo.R;
import com.idunnolol.moviesdemo.data.Movie;
import com.idunnolol.moviesdemo.view.MovieRowView;
import com.idunnolol.moviesdemo.view.SlidingPairView;

import java.util.List;

public class MovieAdapter extends BaseAdapter {

	private Context mContext;

	private MovieAdapterListener mListener;

	private List<Movie> mMovies;

	private float mSlide;

	private int mCellSize;

	public MovieAdapter(Context context, List<Movie> movies, MovieAdapterListener listener, int cellSize) {
		mContext = context;
		mMovies = movies;
		mListener = listener;
		mCellSize = cellSize;
	}

	public void setSlide(float slide) {
		mSlide = slide;
	}

	@Override
	public int getCount() {
		return (int) Math.ceil(mMovies.size() / 2.0f);
	}

	@Override
	public Pair<Movie, Movie> getItem(int position) {
		int index = position * 2;
		return Pair.create(mMovies.get(index), mMovies.get(index + 1));
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.row_movie_pair, parent, false);

			vh = new ViewHolder();
			vh.mSlidingPairView = (SlidingPairView) convertView.findViewById(R.id.sliding_pair);
			vh.mNowPlayingMovie = (MovieRowView) convertView.findViewById(R.id.slide_reveal_right);
			vh.mUpcomingMovie = (MovieRowView) convertView.findViewById(R.id.slide_reveal_left);

			// We set the cell size dynamically, because it's measured on the size of
			// the screen ahead of time
			vh.mNowPlayingMovie.setCellSize(mCellSize);
			vh.mUpcomingMovie.setCellSize(mCellSize);

			convertView.setTag(vh);
		}
		else {
			vh = (ViewHolder) convertView.getTag();
		}

		final Pair<Movie, Movie> moviePair = getItem(position);

		vh.mSlidingPairView.setSlide(mSlide);

		vh.mNowPlayingMovie.bind(moviePair.first);
		vh.mUpcomingMovie.bind(moviePair.second);

		vh.mNowPlayingMovie.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mListener.onMovieClicked(moviePair.first, true);
			}
		});

		vh.mUpcomingMovie.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mListener.onMovieClicked(moviePair.second, false);
			}
		});

		return convertView;
	}

	private static final class ViewHolder {
		SlidingPairView mSlidingPairView;
		MovieRowView mNowPlayingMovie;
		MovieRowView mUpcomingMovie;
	}

	//////////////////////////////////////////////////////////////////////////
	// Listener

	public interface MovieAdapterListener {
		public void onMovieClicked(Movie movie, boolean isOnLeft);
	}

}
