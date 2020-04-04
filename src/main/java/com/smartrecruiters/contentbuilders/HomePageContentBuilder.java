package com.smartrecruiters.contentbuilders;

import com.smartrecruiters.Constants;
import com.smartrecruiters.api.users.invoker.ApiException;
import com.smartrecruiters.contents.HomePageContent;
import com.smartrecruiters.dao.PostingsDAO;

import javax.servlet.http.HttpServletRequest;

public class HomePageContentBuilder {

    private static PostingsDAO postingsDAO = new PostingsDAO();

    public HomePageContent getContent(HttpServletRequest httpServletRequest) throws ApiException {

        HomePageContent homePageContent = new HomePageContent();

        homePageContent.setCompanyIdentifier(httpServletRequest.getParameter(Constants.PARAMTER_COMPANYIDENTIFIER));
        homePageContent.setLanguage(httpServletRequest.getParameter(Constants.PARAMTER_LANGUAGE));
        homePageContent.setQ(httpServletRequest.getParameter(Constants.PARAMTER_SEARCHTEXT));

        homePageContent.setPostings(postingsDAO.getPostings(httpServletRequest));

        return  homePageContent;
    }
}
