package com.smartrecruiters.contents;

import com.smartrecruiters.Constants;
import com.smartrecruiters.api.users.invoker.ApiException;
import com.smartrecruiters.api.users.model.Department;
import com.smartrecruiters.api.users.model.Posting;
import com.smartrecruiters.dao.PostingsDAO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.logging.Logger;

public class HomePageContent {

    private static Logger logger = Logger.getLogger("HomePageContent");

    private String companyIdentifier;
    private String language;
    private String q;

    private List<Posting> postings;
    private List<Department> departments;

    @SuppressWarnings("unused")
    public String getCompanyIdentifier() {
        return companyIdentifier;
    }

    @SuppressWarnings("unused")
    public void setCompanyIdentifier(String companyIdentifier) {
        this.companyIdentifier = companyIdentifier;
    }

    @SuppressWarnings("unused")
    public String getLanguage() {
        return language;
    }

    @SuppressWarnings("unused")
    public void setLanguage(String language) {
        this.language = language;
    }

    @SuppressWarnings("unused")
    public String getQ() {
        return q;
    }

    @SuppressWarnings("unused")
    public void setQ(String q) {
        this.q = q;
    }

    @SuppressWarnings("unused")
    public List<Posting> getPostings() {
        return postings;
    }

    @SuppressWarnings("unused")
    public void setPostings(List<Posting> postings) {
        this.postings = postings;
    }

    @SuppressWarnings("unused")
    public List<Department> getDepartments() {
        return departments;
    }

    @SuppressWarnings("unused")
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }
}
