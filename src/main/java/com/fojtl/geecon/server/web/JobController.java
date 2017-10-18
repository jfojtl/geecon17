package com.fojtl.geecon.server.web;

import com.fojtl.geecon.server.domain.Job;
import com.fojtl.geecon.server.domain.JobDetail2D;
import com.fojtl.geecon.server.web.models.JobStatus;
import com.fojtl.geecon.server.web.models.JobTicket;
import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.query.Search;
import org.infinispan.query.dsl.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
@RequestMapping("{userId}/jobs")
public class JobController {
    private final Cache<String, Job> jobCache;
    private final Cache<String, JobDetail2D> jobDetailsCache;
    private final Cache<Object, Object> jobEventsCache;

    @Autowired
    public JobController(final DefaultCacheManager cacheManager) {
        jobCache = cacheManager.getCache("jobs");
        jobDetailsCache = cacheManager.getCache("job-2d-details");
        jobEventsCache = cacheManager.getCache("job-events");
    }

    @RequestMapping(method = POST)
    ResponseEntity<?> accept(@PathVariable String userId, @RequestBody JobTicket jobTicket) {
        Job job = createJobFromJobTicket(jobTicket, userId);
        JobDetail2D jobDetail = createJobDetailFromJobTicket(jobTicket);

        jobCache.putIfAbsent(job.getJobId(), job);
        jobDetailsCache.putIfAbsent(jobDetail.getJobId(), jobDetail);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(job.getJobId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @RequestMapping(method = GET)
    ResponseEntity<List<JobTicket>> getAllJobs(@PathVariable String userId) {
        ArrayList<JobTicket> jobTickets = new ArrayList<>();

        List<Job> jobsByOwner = Search.getQueryFactory(jobCache)
                .from(Job.class)
                .having(Expression.property("owner")).eq(userId)
                .build()
                .list();

        for (Job job : jobsByOwner) {
            JobDetail2D jobDetail = jobDetailsCache.get(job.getJobId());

            if (jobDetail != null) {
                jobTickets.add(createJobTicketFromJobAndJobDetail(job, jobDetail));
            }
        }

        return ResponseEntity.ok(jobTickets);
    }

    @RequestMapping(path = "/{jobId}", method = PUT)
    ResponseEntity<?> print(@PathVariable String userId, @PathVariable String jobId) {

        return ResponseEntity.ok().build();
    }

    private static JobDetail2D createJobDetailFromJobTicket(JobTicket jobTicket) {
        return new JobDetail2D(jobTicket.getJobId(),
                jobTicket.getNumberOfPages(),
                jobTicket.getSides(),
                jobTicket.getColor());
    }

    private static Job createJobFromJobTicket(JobTicket jobTicket, String userId) {
        return new Job(jobTicket.getJobId(), userId, jobTicket.getJobTitle());
    }

    private static JobTicket createJobTicketFromJobAndJobDetail(Job job, JobDetail2D jobDetail) {
        return new JobTicket(job.getJobId(), job.getOwner(), job.getJobTitle(), jobDetail.getNumberOfPages(),
                jobDetail.getSides(), jobDetail.getColor(), JobStatus.NEW);
    }
}
