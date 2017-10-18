package com.fojtl.geecon.server.domain.jobevents;

import java.util.Date;

public class JobDeletedEvent extends JobEvent {
    public JobDeletedEvent(String jobId, Date created) {
        super(jobId, created);
    }
}
