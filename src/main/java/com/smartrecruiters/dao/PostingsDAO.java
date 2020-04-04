package com.smartrecruiters.dao;

import com.smartrecruiters.api.users.PostingsApi;
import com.smartrecruiters.api.users.invoker.ApiException;
import com.smartrecruiters.api.users.model.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PostingsDAO {

    private static Logger logger = Logger.getLogger("PostingsDAO");
    private static PostingsApi postingsApi = new PostingsApi();

    public PostingsDAO() {
        postingsApi.getApiClient().setBasePath("https://api.smartrecruiters.com");
    }

    public List<Posting> getPostings(HttpServletRequest request) throws ApiException {

        logger.info("loadPostings...");
        List<Posting> postingsOut = null;

        String companyIdentifier = request.getParameter("companyIdentifier");
        JobAdLanguageCode acceptLanguage = JobAdLanguageCode.fromValue(request.getParameter("language"));
        String q = request.getParameter("q");
        int limit = 100;
        int offset = 0;
        String destination = "PUBLIC";
        String country = request.getParameter("country");
        String region = request.getParameter("region");
        String city = request.getParameter("city");
        String department = request.getParameter("department");
        List<JobAdLanguageCode> language = new ArrayList<>(1);
        language.add(JobAdLanguageCode.fromValue(request.getParameter("language")));

        logger.info("parameters:");
        logger.info(" - companyIdentifier: " + companyIdentifier);
        logger.info(" - acceptLanguage: " + acceptLanguage.getValue());
        logger.info(" - q: " + q);
        logger.info(" - limit: " + limit);
        logger.info(" - offset: " + offset);
        logger.info(" - destination: " + destination);
        logger.info(" - country: " + country);
        logger.info(" - region: " + region);
        logger.info(" - city: " + city);
        logger.info(" - department: " + department);
        logger.info(" - language: " + language.get(0).getValue());

        PostingList postingList = postingsApi.v1ListPostings(
                companyIdentifier,
                acceptLanguage,
                q,
                limit,
                offset,
                destination,
                country,
                region,
                city,
                department,
                language);

        if (postingList.getContent() != null) {
            logger.info("loadPostings got " + postingList.getContent().size() + " posting(s)");
            postingsOut = postingList.getContent();
        }
        return postingsOut;
    }

    public List<Department> getDepartments(HttpServletRequest request) throws ApiException {

        List<Department> departmentsOut = null;
        logger.info("loadDepartments...");

        String companyIdentifier = request.getParameter("companyIdentifier");
        JobAdLanguageCode acceptLanguage = JobAdLanguageCode.fromValue(request.getParameter("language"));

        Departments departments = postingsApi.v1ListDepartments(companyIdentifier, acceptLanguage);

        if (departments.getContent() != null) {
            logger.info("loadDepartments got " + departments.getContent().size() + " department(s).");
            departmentsOut = departments.getContent();
        }
        return departmentsOut;
    }

    public Posting getPosting(HttpServletRequest request, String id) throws ApiException {

        logger.info("getPosting...");

        String companyIdentifier = request.getParameter("companyIdentifier");
        JobAdLanguageCode acceptLanguage = JobAdLanguageCode.fromValue(request.getParameter("language"));

        logger.info("parameters:");
        logger.info(" - companyIdentifier: " + companyIdentifier);
        logger.info(" - id: " + id);
        logger.info(" - acceptLanguage: " + acceptLanguage.getValue());

        return postingsApi.v1GetPosting(companyIdentifier, id, acceptLanguage, null, null);
    }

}
