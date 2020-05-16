package com.smartrecruiters.contentbuilders;

import com.smartrecruiters.Constants;
import com.smartrecruiters.contents.HomePageContent;
import com.smartrecruiters.dao.PostingsDAO;

import javax.servlet.http.HttpServletRequest;

public class HomePageContentBuilder {

    public HomePageContent getContent(HttpServletRequest httpServletRequest) {

        HomePageContent homePageContent = new HomePageContent();
        PostingsDAO postingsDAO = new PostingsDAO(httpServletRequest);

        homePageContent.setCompanyIdentifier(postingsDAO.getCompanyIdentifier());
        homePageContent.setLanguage(httpServletRequest.getParameter(Constants.PARAMTER_LANGUAGE));
        homePageContent.setDepartment(postingsDAO.getDepartment());
        homePageContent.setCountry(postingsDAO.getCountry());
        homePageContent.setRegion(postingsDAO.getRegion());
        homePageContent.setCity(postingsDAO.getCity());
        homePageContent.setQ(postingsDAO.getQ());


        homePageContent.setPostings(postingsDAO.getPostingListFiltered());

        homePageContent.setLanguages(postingsDAO.getAvailableLanguages());
        homePageContent.setDepartments(postingsDAO.getDepartments());
        homePageContent.setCountries(postingsDAO.getAvailableCountries());
        homePageContent.setRegions(postingsDAO.getAvailableCRegions());
        homePageContent.setCities(postingsDAO.getAvailableCities());

        return  homePageContent;
    }
}
