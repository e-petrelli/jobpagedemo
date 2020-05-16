package com.smartrecruiters.contentbuilders;

import com.smartrecruiters.Constants;
import com.smartrecruiters.api.posting.invoker.ApiException;
import com.smartrecruiters.api.posting.model.PostingItem;
import com.smartrecruiters.contents.DetailPageContent;
import com.smartrecruiters.dao.PostingsDAO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class DetailPageContentBuilder {

    public DetailPageContent getContent(HttpServletRequest httpServletRequest) throws ApiException {

        DetailPageContent detailPageContent = new DetailPageContent();
        PostingsDAO postingsDAO = new PostingsDAO(httpServletRequest);

        detailPageContent.setCompanyIdentifier(postingsDAO.getCompanyIdentifier());
        detailPageContent.setLanguage(httpServletRequest.getParameter(Constants.PARAMTER_LANGUAGE));

        List<PostingItem> postingItems = postingsDAO.getPostingListFiltered();
        for(PostingItem postingItem : postingItems) {
            if(postingItem.getRefNumber().equals(httpServletRequest.getParameter(Constants.PARAMETER_REFNUMBER))) {
                detailPageContent.setPosting(postingsDAO.getPosting(postingItem.getId()));
            }
        }

        return detailPageContent;
    }
}