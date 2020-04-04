package com.smartrecruiters.contentbuilders;

import com.smartrecruiters.Constants;
import com.smartrecruiters.api.users.invoker.ApiException;
import com.smartrecruiters.api.users.model.Posting;
import com.smartrecruiters.contents.DetailPageContent;
import com.smartrecruiters.dao.PostingsDAO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class DetailPageContentBuilder {

    private static PostingsDAO postingsDAO = new PostingsDAO();

    public DetailPageContent getContent(HttpServletRequest httpServletRequest) throws ApiException {

        DetailPageContent detailPageContent = new DetailPageContent();
        detailPageContent.setCompanyIdentifier(httpServletRequest.getParameter(Constants.PARAMTER_COMPANYIDENTIFIER));
        detailPageContent.setLanguage(httpServletRequest.getParameter(Constants.PARAMTER_LANGUAGE));
        detailPageContent.setRefNumber(httpServletRequest.getParameter(Constants.REFNUMBER));


        List<Posting> postings = postingsDAO.getPostings(httpServletRequest);
        if (postings != null) {
            for (Posting posting : postings) {
                if (posting.getRefNumber()!= null && posting.getRefNumber().equals(detailPageContent.getRefNumber())) {
                    detailPageContent.setPosting(postingsDAO.getPosting(httpServletRequest, posting.getId()));
                }
            }
        }

        return detailPageContent;
    }
}