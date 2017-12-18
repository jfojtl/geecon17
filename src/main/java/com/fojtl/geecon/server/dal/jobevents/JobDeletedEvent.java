package com.fojtl.geecon.server.dal.jobevents;

import java.util.Date;

public class JobDeletedEvent extends JobEvent {
    public JobDeletedEvent(String jobId, Date created) {
        super(jobId, created);
    }
}
