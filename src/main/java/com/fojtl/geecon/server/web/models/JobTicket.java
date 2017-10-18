package com.fojtl.geecon.server.web.models;

import com.fojtl.geecon.server.domain.Color;
import com.fojtl.geecon.server.domain.Sides;

public class JobTicket {
    private final String jobId;
    private final String owner;
    private final String jobTitle;
    private final int numberOfPages;
    private final Sides sides;
    private final Color color;
    private final JobStatus status;

    public JobTicket(String jobId, String owner, String jobTitle, int numberOfPages, Sides sides, Color color, JobStatus status) {
        this.jobId = jobId;
        this.owner = owner;
        this.jobTitle = jobTitle;
        this.numberOfPages = numberOfPages;
        this.sides = sides;
        this.color = color;
        this.status = status;
    }

    public String getJobId() {
        return jobId;
    }

    public String getOwner() {
        return owner;
    }

    public String getJobTitle() {
        return jobTitle;
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

    public JobStatus getStatus() {
        return status;
    }
}
