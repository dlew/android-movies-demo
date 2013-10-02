package com.mobiata.moviesdemo.ui;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.mobiata.moviesdemo.R;
import com.mobiata.moviesdemo.data.Movie;
import com.mobiata.moviesdemo.view.SlidingListView;
import com.mobiata.moviesdemo.view.ViewPager;
import com.mobiata.moviesdemo.widget.MovieAdapter;

import java.util.Locale;

public class MoviesActivity extends FragmentActivity implements ActionBar.TabListener,
		MovieAdapter.MovieAdapterListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	// ListView on screen
	private SlidingListView mListView;
	private MovieAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movies);

		getWindow().setBackgroundDrawable(null);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setOffscreenPageLimit(3);

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(
					actionBar.newTab()
							.setText(mSectionsPagerAdapter.getPageTitle(i))
							.setTabListener(this));
		}

		mViewPager.setCurrentItem(1);
		actionBar.setSelectedNavigationItem(1);

		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			private int mLastState;

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				if (position == 0) {
					setSlide(positionOffset - 1);
				}
				else if (position == 1) {
					setSlide(positionOffset);
				}
				else if (position == 2) {
					setSlide(1);
				}
			}

			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrollStateChanged(int state) {
				if (state == ViewPager.SCROLL_STATE_IDLE) {
					mListView.setUseHardwareLayers(false);
				}
				else if (mLastState == ViewPager.SCROLL_STATE_IDLE) {
					mListView.setUseHardwareLayers(true);
				}

				mLastState = state;
			}
		});

		mListView = (SlidingListView) findViewById(R.id.sliding_list_view);

		// Add some spacing views above/below the rest of the rows; this keeps us from having
		// to customize the first/last rows to add some extra padding
		LayoutInflater inflater = LayoutInflater.from(this);
		mListView.addHeaderView(inflater.inflate(R.layout.include_header_footer_space, mListView, false));
		mListView.addFooterView(inflater.inflate(R.layout.include_header_footer_space, mListView, false));

		MoviesApplication app = (MoviesApplication) getApplication();
		mAdapter = new MovieAdapter(this, app.getDemoData(), this);
		mListView.setAdapter(mAdapter);
	}

	private void setSlide(float slide) {
		mAdapter.setSlide(slide);
		mListView.setSlide(slide);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.movies, menu);
		return true;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onBackPressed() {
		// If the user is looking at detailed rows, put them back to the other screen instead
		// of leaving the page entirely
		if (mViewPager.getCurrentItem() != 1) {
			mViewPager.setCurrentItem(1, true);
		}
		else {
			super.onBackPressed();
		}
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return new SpaceFragment();
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public long getItemId(int position) {
			return super.getItemId(position);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A simple Fragment that just takes up space; We just want to use
	 * the Decor View on top for display.
	 */
	public static class SpaceFragment extends Fragment {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			return inflater.inflate(R.layout.fragment_space, container, false);
		}
	}

	//////////////////////////////////////////////////////////////////////////
	// MovieAdapterListener

	@Override
	public void onMovieClicked(Movie movie, boolean isOnLeft) {
		int targetItem = isOnLeft ? 0 : 2;
		if (mViewPager.getCurrentItem() != targetItem) {
			mViewPager.setCurrentItem(targetItem, true);
		}
	}
}
