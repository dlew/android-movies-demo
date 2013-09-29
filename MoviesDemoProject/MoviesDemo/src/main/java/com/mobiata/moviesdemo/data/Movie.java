package com.mobiata.moviesdemo.data;

import java.util.ArrayList;
import java.util.List;

public class Movie {

	private String mTitle;

	private String mSynopsis;

	private String mStoryline;

	private String mPosterUrl;

    // For the purposes of a demo, pre-generated content
	public static List<Movie> generateDemoMovies() {
        List<Movie> movies = new ArrayList<Movie>();
        movies.add(new Movie());
        movies.add(new Movie());
        movies.add(new Movie());
        movies.add(new Movie());
        movies.add(new Movie());
        movies.add(new Movie());
        movies.add(new Movie());
        movies.add(new Movie());
		return movies;
	}
}
