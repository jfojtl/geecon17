package com.fojtl.geecon.server.dal;

import java.io.Serializable;

public class JobDetail2D implements Serializable {
    private final String jobId;
    private final int numberOfPages;
    private final Sides sides;
    private final Color color;

    public JobDetail2D(String jobId, int numberOfPages, Sides sides, Color color) {
        this.jobId = jobId;
        this.numberOfPages = numberOfPages;
        this.sides = sides;
        this.color = color;
    }

    public String getJobId() {
        return jobId;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public Sides getSides() {
        return sides;
    }

    public Color getColor() {
        return color;
    }
}
