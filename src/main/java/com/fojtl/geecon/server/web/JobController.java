package com.fojtl.geecon.server.web;

import com.fojtl.geecon.server.dal.Job;
import com.fojtl.geecon.server.dal.JobDetail2D;
import com.fojtl.geecon.server.web.models.JobStatus;
import com.fojtl.geecon.server.web.models.JobTicket;
import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.query.Search;
import org.infinispan.query.dsl.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("{userId}/jobs")
public class JobController {
    private final Cache<String, Job> jobCache;
    private final Cache<String, JobDetail2D> jobDetailsCache;

    @Value("${app.node}")
    private String node;

    @Autowired
    public JobController(final DefaultCacheManager cacheManager) {
        jobCache = cacheManager.getCache("jobs");
        jobDetailsCache = cacheManager.getCache("job-2d-details");
    }

    @RequestMapping(method = POST)
    public ResponseEntity<?> accept(@PathVariable String userId, @RequestBody JobTicket jobTicket) {
        Job job = createJobFromJobTicket(jobTicket, userId);
        JobDetail2D jobDetail = createJobDetailFromJobTicket(jobTicket);

        jobCache.putIfAbsent(job.getJobId(), job);

        if ("node0".equals(node))
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();

        jobDetailsCache.putIfAbsent(jobDetail.getJobId(), jobDetail);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(job.getJobId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @RequestMapping(method = GET)
    public ResponseEntity<List<JobTicket>> getAllJobs(@PathVariable String userId) {
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

    @RequestMapping(path = "/{jobId>", method = GET)
    public ResponseEntity<JobTicket> getSingleJob(@PathVariable String userId, @PathVariable String jobId) {
        Job job = jobCache.get(jobId);
        JobDetail2D jobDetail = jobDetailsCache.get(jobId);
        if (job == null || jobDetail == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(createJobTicketFromJobAndJobDetail(job, jobDetail));
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
