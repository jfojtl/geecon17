package com.fojtl.geecon.server.domain;

import org.hibernate.search.annotations.*;

import java.io.Serializable;

@Indexed
public class Job implements Serializable {
    private final String jobId;
    @Field(analyze = Analyze.NO, index = Index.YES, store = Store.NO)
    private final String owner;
    private final String jobTitle;

    public Job(String jobId, String owner, String jobTitle) {
        this.jobId = jobId;
        this.owner = owner;
        this.jobTitle = jobTitle;
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
}
