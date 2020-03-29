package com.smartrecruiters.contents;

import com.smartrecruiters.Constants;
import com.smartrecruiters.api.job.model.JobDetails;
import com.smartrecruiters.api.job.model.JobSummary;
import com.smartrecruiters.api.users.invoker.ApiException;
import com.smartrecruiters.api.users.model.UserEntity;
import com.smartrecruiters.dao.JobDao;
import com.smartrecruiters.dao.UserDao;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.logging.Logger;

public class DetailPageContent {

    private static Logger logger = Logger.getLogger("HomePageContent");

    private String smartToken;
    private String language;
    private String searchText;
    private UserEntity userEntity;
    private JobDetails jobDetails;


    public DetailPageContent(HttpServletRequest httpServletRequest) {

        this.smartToken = httpServletRequest.getParameter(Constants.PARAMTER_SMARTTOKEN);
        this.language = httpServletRequest.getParameter(Constants.PARAMTER_LANGUAGE);
        this.searchText = httpServletRequest.getParameter(Constants.PARAMTER_SEARCHTEXT);

        UserDao userDao = new UserDao(smartToken);
        JobDao jobDao = new JobDao(smartToken);

        try {
            logger.info("Getting /me...");
            this.setUserEntity(userDao.getMe());
            logger.info("Getting /me succeded: " + userEntity.getEmail());

            logger.info("Getting detail...");
            String sRequestUrl = httpServletRequest.getRequestURI();
            String jobId = sRequestUrl.substring(10, sRequestUrl.length()-5);
            logger.info("Getting job " + jobId);
            jobDao.loadJobDetail(jobId);
            this.jobDetails = jobDao.getJobDetails();
            logger.info("Got job details for " + jobDetails.getRefNumber());
        } catch (ApiException e) {
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

    public String getSmartToken() {
        return smartToken;
    }

    public String getLanguage() {
        return language;
    }

    public String getSearchText() {
        return searchText;
    }

    public JobDetails getJobDetails() {
        return jobDetails;
    }
}
