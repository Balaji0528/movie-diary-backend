package com.balaji.movie_diary.dto;

public class WatchlistResponse {
	private boolean exists;
	private Long watchlistId;

	public WatchlistResponse(boolean exists, Long watchlistId) {
		super();
		this.exists = exists;
		this.watchlistId = watchlistId;
	}

	public boolean isExists() {
		return exists;
	}

	public Long getWatchlistId() {
		return watchlistId;
	}
}
