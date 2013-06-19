package com.vadymlotar.demo.controller.model;

public class RequestStatistics {
	private long totalCount;
	private double avgDuration;
	private double minDuration;
	private double maxDuration;
	private double medianDuration;

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public double getAvgDuration() {
		return avgDuration;
	}

	public void setAvgDuration(double avgDuration) {
		this.avgDuration = avgDuration;
	}

	public double getMinDuration() {
		return minDuration;
	}

	public void setMinDuration(double minDuration) {
		this.minDuration = minDuration;
	}

	public double getMaxDuration() {
		return maxDuration;
	}

	public void setMaxDuration(double maxDuration) {
		this.maxDuration = maxDuration;
	}

	public double getMedianDuration() {
		return medianDuration;
	}

	public void setMedianDuration(double medianDuration) {
		this.medianDuration = medianDuration;
	}

}
