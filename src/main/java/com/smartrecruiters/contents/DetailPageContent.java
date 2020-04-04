package com.smartrecruiters.contents;

import com.smartrecruiters.api.users.model.Posting;

public class DetailPageContent {

    private String companyIdentifier;
    private String language;
    private String refNumber;

    private Posting posting = null;

    public void setPosting(Posting posting) {
        this.posting = posting;
    }

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
    public String getRefNumber() {
        return refNumber;
    }

    @SuppressWarnings("unused")
    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    @SuppressWarnings("unused")
    public Posting getPosting() {
        return posting;
    }
}
