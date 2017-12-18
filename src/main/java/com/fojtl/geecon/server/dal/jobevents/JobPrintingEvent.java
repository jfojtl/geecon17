package com.fojtl.geecon.server.dal.jobevents;

import java.util.Date;

public class JobPrintingEvent extends JobEvent {
    public JobPrintingEvent(String jobId, Date created) {
        super(jobId, created);
    }
}
