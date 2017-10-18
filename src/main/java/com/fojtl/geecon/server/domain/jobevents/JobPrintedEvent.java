package com.fojtl.geecon.server.domain.jobevents;

import java.util.Date;

public class JobPrintedEvent extends JobEvent {
    public JobPrintedEvent(String jobId, Date created) {
        super(jobId, created);
    }
}
