package com.smartrecruiters.dao;

import com.smartrecruiters.api.job.JobsApi;
import com.smartrecruiters.api.job.invoker.ApiException;
import com.smartrecruiters.api.job.model.JobDetails;
import com.smartrecruiters.api.job.model.JobSummary;
import com.smartrecruiters.api.job.model.Jobs;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class JobDao {

    private static Logger logger = Logger.getLogger("JobDao");

    private JobsApi jobsApi = new JobsApi();

    private List<JobSummary> jobSummaries = new ArrayList<>();
    private JobDetails jobDetails;

    public JobDao(String sSmartToken) {
        this.jobsApi.getApiClient().setApiKey(sSmartToken);
    }

    public void loadJobs(HttpServletRequest httpServletRequest) throws ApiException {

        String sSearch = httpServletRequest.getParameter("q");
        Integer limit = 100;
        int offset = 0;

        List<String> cities = null;
        String city = httpServletRequest.getParameter("city");
        if(StringUtils.isNotEmpty(city)) {
            cities = new ArrayList<>(1);
            cities.add(city);
        }
        // cities.add();

        List<String> departments = null; // new ArrayList<>(1);
        String department = httpServletRequest.getParameter("department");
        if(StringUtils.isNotEmpty(department)) {
            departments = new ArrayList<>(1);
            departments.add(department);
        }

        String language = httpServletRequest.getParameter("language");

        String postingStatus = "PUBLIC";

        int totalFound = 1;
        int iJobs = 0;
        while (totalFound > 0  && iJobs < totalFound) {
            logger.info("Searching jobs: offset " + offset);
            Jobs jobs = jobsApi.jobsAll(sSearch, limit, offset, cities, departments, null, null, language, postingStatus);
            totalFound = (jobs.getTotalFound()!=null?jobs.getTotalFound():0);
            logger.info("found " + totalFound + " jobs");

            List<JobSummary> jobSummariesPage = jobs.getContent();
            if (jobSummariesPage != null) {
                for (JobSummary jobSummary : jobSummariesPage) {
                    this.jobSummaries.add(jobSummary);
                    iJobs++;
                }
            }
            offset = iJobs;
        }
        logger.info("loaded " + iJobs + " jobs.");
    }

    public void loadJobDetail(String jobId) throws ApiException {
        this.jobDetails = jobsApi.jobsGet(jobId);
    }

    public List<JobSummary> getJobSummaries() {
        return jobSummaries;
    }

    public JobDetails getJobDetails() {
        return jobDetails;
    }
}
