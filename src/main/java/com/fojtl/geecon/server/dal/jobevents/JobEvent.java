package com.fojtl.geecon.server.dal.jobevents;

import java.util.Date;

public abstract class JobEvent {
    private final String jobId;
    private final Date created;

    JobEvent(String jobId, Date created) {
        this.jobId = jobId;
        this.created = created;
    }

    public String getJobId() {
        return jobId;
    }

    public Date getCreated() {
        return created;
    }
}
