package com.smartrecruiters.contents;

import com.smartrecruiters.api.posting.model.Posting;

import java.util.Hashtable;

public class DetailPageContent {

    private String companyIdentifier;
    private String language;
    private String refNumber;
    private Hashtable<String,String> languages;

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
    public Posting getPosting() {
        return posting;
    }

    public Hashtable<String, String> getLanguages() {
        return languages;
    }

    public void setLanguages(Hashtable<String, String> languages) {
        this.languages = languages;
    }

    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }
}
