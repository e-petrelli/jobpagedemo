package com.smartrecruiters.contents;

import com.smartrecruiters.api.posting.model.Department;
import com.smartrecruiters.api.posting.model.PostingItem;

import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Logger;

public class HomePageContent {

    private String companyIdentifier;
    private String language;
    private String department;
    private String country;
    private String region;
    private String city;
    private String q;

    private List<PostingItem> postings;
    private List<Department> departments;
    private Hashtable<String,String> languages;
    private Hashtable<String,Integer> countries;
    private Hashtable<String,Integer> regions;
    private Hashtable<String,Integer> cities;

    public String getCompanyIdentifier() {
        return companyIdentifier;
    }

    public void setCompanyIdentifier(String companyIdentifier) {
        this.companyIdentifier = companyIdentifier;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public List<PostingItem> getPostings() {
        return postings;
    }

    public void setPostings(List<PostingItem> postings) {
        this.postings = postings;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public Hashtable<String, Integer> getCountries() {
        return countries;
    }

    public void setCountries(Hashtable<String, Integer> countries) {
        this.countries = countries;
    }

    public Hashtable<String, Integer> getRegions() {
        return regions;
    }

    public void setRegions(Hashtable<String, Integer> regions) {
        this.regions = regions;
    }

    public Hashtable<String, Integer> getCities() {
        return cities;
    }

    public void setCities(Hashtable<String, Integer> cities) {
        this.cities = cities;
    }

    public Hashtable<String, String> getLanguages() {
        return languages;
    }

    public void setLanguages(Hashtable<String, String> languages) {
        this.languages = languages;
    }
}
