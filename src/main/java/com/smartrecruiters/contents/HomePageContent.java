package com.smartrecruiters.contents;

import com.smartrecruiters.Constants;
import com.smartrecruiters.api.job.JobsApi;
import com.smartrecruiters.api.job.model.JobSummary;
import com.smartrecruiters.api.job.model.Jobs;
import com.smartrecruiters.api.users.UsersApi;
import com.smartrecruiters.api.users.invoker.ApiException;
import com.smartrecruiters.api.users.model.UserEntity;
import com.smartrecruiters.dao.JobDao;
import com.smartrecruiters.dao.UserDao;
import org.threeten.bp.OffsetDateTime;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class HomePageContent {

    private static Logger logger = Logger.getLogger("HomePageContent");

    private String smartToken;
    private String language;
    private String searchText;
    private UserEntity userEntity;
    private List<JobSummary> jobSummaries;


    public HomePageContent(HttpServletRequest httpServletRequest) {

        this.smartToken = httpServletRequest.getParameter(Constants.PARAMTER_SMARTTOKEN);
        this.language = httpServletRequest.getParameter(Constants.PARAMTER_LANGUAGE);
        this.searchText = httpServletRequest.getParameter(Constants.PARAMTER_SEARCHTEXT);

        UserDao userDao = new UserDao(smartToken);
        JobDao jobDao = new JobDao(smartToken);

        try {
            logger.info("Getting /me...");
            this.setUserEntity(userDao.getMe());
            logger.info("Getting /me succeded: " + userEntity.getEmail());

            logger.info("Getting /jobs...");
            jobDao.loadJobs(httpServletRequest);

            this.setJobSummaries(jobDao.getJobSummaries());
            logger.info("Getting /jobs succeded: " + this.jobSummaries.size() + "jobs");

        } catch (com.smartrecruiters.api.users.invoker.ApiException e) {
            logger.info("error on users api:");
            e.printStackTrace();
        } catch (com.smartrecruiters.api.job.invoker.ApiException e) {
            logger.info("error on jobs api:");
            e.printStackTrace();
        }


    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    private void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public List<JobSummary> getJobSummaries() {
        return jobSummaries;
    }

    private void setJobSummaries(List<JobSummary> jobSummaries) {
        this.jobSummaries = jobSummaries;
    }

    public String getSmartToken() {
        return smartToken;
    }

    public String getLanguage() {
        return language;
    }

    public String getSearchText() {
        return searchText;
    }

}
