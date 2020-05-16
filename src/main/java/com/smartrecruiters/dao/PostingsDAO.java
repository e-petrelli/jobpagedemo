package com.smartrecruiters.dao;

import com.smartrecruiters.Constants;
import com.smartrecruiters.api.posting.PostingsApi;
import com.smartrecruiters.api.posting.invoker.ApiException;
import com.smartrecruiters.api.posting.model.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Logger;

public class PostingsDAO {

    private final static Logger logger = Logger.getLogger("PostingsDAO");
    private final static PostingsApi postingsApi = new PostingsApi();
    private final String companyIdentifier;
    private final JobAdLanguageCode acceptLanguage;
    private final List<JobAdLanguageCode> language;

    private final String department;
    private final String country;
    private final String region;
    private final String city;
    private final String q;

    private List<PostingItem> postingListAll = null;
    private List<PostingItem> postingListFiltered= null;
    private List<PostingItem> postingListCurrentLanguage = null;

    public PostingsDAO(HttpServletRequest request) {
        postingsApi.getApiClient().setBasePath("https://api.smartrecruiters.com");
        this.companyIdentifier = request.getParameter(Constants.PARAMTER_COMPANYIDENTIFIER);
        this.acceptLanguage = JobAdLanguageCode.fromValue(request.getParameter(Constants.PARAMTER_LANGUAGE));
        this.language = new ArrayList<>(1);
        this.language.add(acceptLanguage);

        this.department = StringUtils.stripToNull(request.getParameter(Constants.PARAMTER_DEPARTMENT));
        this.country = StringUtils.stripToNull(request.getParameter(Constants.PARAMTER_COUNTRY));
        this.region = StringUtils.stripToNull(request.getParameter(Constants.PARAMTER_REGION));
        this.city = StringUtils.stripToNull(request.getParameter(Constants.PARAMTER_CITY));
        this.q = StringUtils.stripToNull(request.getParameter(Constants.PARAMTER_SEARCHTEXT));

        this.loadPostingsAll();
        this.loadPostingsFiltered();
    }

    /**
     * Loads all postings: The full list is required to generate lists of available languages.
     * The second list is used to generate the list of countries and cities for the current language.
     */
    private void loadPostingsAll() {
        int limit = 100;
        int offset = 0;
        String destination = "PUBLIC";

        try {
            this.postingListAll = postingsApi.v1ListPostings(
                    companyIdentifier,
                    null,
                    null,
                    limit,
                    offset,
                    destination,
                    null,
                    null,
                    null,
                    null,
                    null).getContent();
        } catch (ApiException e) {
            e.printStackTrace();
        }

    }

    private void loadPostingsFiltered() {

        logger.info("loadPostingsFiltered...");

        logger.info("parameters:");
        logger.info(" - companyIdentifier: " + this.companyIdentifier);
        logger.info(" - acceptLanguage: " + this.acceptLanguage.getValue());
        logger.info(" - q: " + q);
        logger.info(" - limit: " + Constants.DEFAULT_LIMIT);
        logger.info(" - offset: " + Constants.DEFAULT_OFFSET);
        logger.info(" - destination: " + Constants.DEFAULT_DESTINATION);
        logger.info(" - country: " + country);
        logger.info(" - region: " + region);
        logger.info(" - city: " + city);
        logger.info(" - department: " + department);
        logger.info(" - language: " + this.language.get(0).getValue());

        PostingList postingList = null;
        try {
            postingList = postingsApi.v1ListPostings(
                    this.companyIdentifier,
                    this.acceptLanguage,
                    this.q,
                    Constants.DEFAULT_LIMIT,
                    Constants.DEFAULT_OFFSET,
                    Constants.DEFAULT_DESTINATION,
                    this.country,
                    this.region,
                    this.city,
                    this.department,
                    this.language);
        } catch (ApiException e) {
            e.printStackTrace();
        }

        if (postingList!=null && postingList.getContent() != null) {
            logger.info("loadPostingsFiltered got " + postingList.getContent().size() + " posting(s)");
            this.postingListFiltered = postingList.getContent();
        }
    }

    private void loadPostingsCurrentLanguage() {

        logger.info("loadPostingsCurrentLanguage...");

        logger.info("parameters:");
        logger.info(" - companyIdentifier: " + this.companyIdentifier);
        logger.info(" - acceptLanguage: " + this.acceptLanguage.getValue());
        logger.info(" - limit: " + Constants.DEFAULT_LIMIT);
        logger.info(" - offset: " + Constants.DEFAULT_OFFSET);
        logger.info(" - destination: " + Constants.DEFAULT_DESTINATION);
        logger.info(" - language: " + this.language.get(0).getValue());

        PostingList postingList = null;
        try {
            postingList = postingsApi.v1ListPostings(
                    this.companyIdentifier,
                    this.acceptLanguage,
                    null,
                    Constants.DEFAULT_LIMIT,
                    Constants.DEFAULT_OFFSET,
                    Constants.DEFAULT_DESTINATION,
                    null,
                    null,
                    null,
                    this.department,
                    this.language);
        } catch (ApiException e) {
            e.printStackTrace();
        }

        if (postingList!=null && postingList.getContent() != null) {
            logger.info("loadPostingsCurrentLanguage got " + postingList.getContent().size() + " posting(s)");
            this.postingListCurrentLanguage = postingList.getContent();
        }
    }

    /**
     * @return All Company departments
     * @throws ApiException If an error occurs while using SR API
     */
    public List<Department> getDepartments() {

        List<Department> departmentsOut = null;
        logger.info("loadDepartments...");

        Departments departments = null;
        try {
            departments = postingsApi.v1ListDepartments(
                    this.companyIdentifier,
                    this.acceptLanguage);
        } catch (ApiException e) {
            e.printStackTrace();
        }

        if (departments.getContent() != null) {
            logger.info("loadDepartments got " + departments.getContent().size() + " department(s).");
            departmentsOut = departments.getContent();
        }
        return departmentsOut;
    }

    /**
     * @return The Posting with the given Id
     */
    public Posting getPosting(String postingId) throws ApiException {

        logger.info("getPosting(" + postingId + ")");

        logger.info("parameters:");
        logger.info(" - companyIdentifier: " + this.companyIdentifier);
        logger.info(" - id: " + postingId);
        logger.info(" - acceptLanguage: " + this.acceptLanguage.getValue());
        logger.info(" - default_sourceTypeId: " + Constants.DEFAULT_SOURCETYPEID);
        logger.info(" - default_sourceId: " + Constants.DEFAULT_SOURCEID);

        return postingsApi.v1GetPosting(
                this.companyIdentifier,
                postingId,
                this.acceptLanguage,
                Constants.DEFAULT_SOURCETYPEID,
                Constants.DEFAULT_SOURCEID);
    }

    /**
     * @return the list of available language codes, based on all postings
     */
    public Collection<String> getAvailableLanguages() {

        Hashtable<String, String> htLanguages = new Hashtable<>(0);
        if (this.postingListAll != null) {
            for (PostingItem postingItem : this.postingListAll) {

                // when the posting api doc will be correct, then getLanguage()getCode() will work.

            }
        }

        // FIXME as I am a temporary solution
        htLanguages.put("en", "en");
        htLanguages.put("fr", "fr");
        htLanguages.put("ru", "ru");

        return htLanguages.values();
    }

    /**
     * @return the list of available countriy codes, based on current filter
     */
    public Hashtable<String, Integer> getAvailableCountries() {

        if(this.postingListCurrentLanguage == null) {
            this.loadPostingsCurrentLanguage();
        }

        Hashtable<String, Integer> htCountries = new Hashtable<>(0);
        if (this.postingListCurrentLanguage != null) {
            for (PostingItem postingItem : this.postingListCurrentLanguage) {
                if(postingItem.getLocation()!=null && postingItem.getLocation().getCountry()!=null) {
                    String countryCode = postingItem.getLocation().getCountry();
                    int iCount = 1;
                    if (htCountries.containsKey(countryCode)) {
                        iCount = htCountries.get(countryCode) + 1;
                    }
                    htCountries.put(countryCode, iCount);
                }
            }
        }
        return htCountries;
    }

    /**
     * @return the list of available regions, based on current filter
     */
    public Hashtable<String, Integer> getAvailableCRegions() {

        if(this.postingListCurrentLanguage == null) {
            this.loadPostingsCurrentLanguage();
        }

        Hashtable<String, Integer> htRegions = new Hashtable<>(0);
        if (this.postingListCurrentLanguage != null) {
            for (PostingItem postingItem : this.postingListCurrentLanguage) {

                // if a country is selected, only regions in current country
                if(postingItem.getLocation()!=null) {
                    if(this.country == null || this.country.equals(postingItem.getLocation().getCountry())) {
                        if (postingItem.getLocation().getRegion() != null) {
                            String region = WordUtils.capitalizeFully(postingItem.getLocation().getRegion());
                            int iCount = 1;
                            if (htRegions.containsKey(region)) {
                                iCount = htRegions.get(region) + 1;
                            }
                            htRegions.put(region, iCount);
                        }
                    }
                }

            }
        }
        return htRegions;
    }

    /**
     * @return the list of available cities, based on current filter
     */
    public Hashtable<String, Integer> getAvailableCities() {

        if(this.postingListCurrentLanguage == null) {
            this.loadPostingsCurrentLanguage();
        }

        Hashtable<String, Integer> htCities = new Hashtable<>(0);
        if (this.postingListCurrentLanguage != null) {
            for (PostingItem postingItem : this.postingListCurrentLanguage) {

                // if a country or a region are selected, only cities in current country and eventually region
                if(postingItem.getLocation()!=null) {
                    if(this.country == null || this.country.equals(postingItem.getLocation().getCountry())) {
                        if(this.region == null || this.region.equals(postingItem.getLocation().getRegion())) {
                            if (postingItem.getLocation() != null && postingItem.getLocation().getCity() != null) {
                                String city = WordUtils.capitalizeFully(postingItem.getLocation().getCity());
                                int iCount = 1;
                                if (htCities.containsKey(city)) {
                                    iCount = htCities.get(city) + 1;
                                }
                                htCities.put(city, iCount);
                            }
                        }
                    }
                }
            }
        }
        return htCities;
    }


    public List<PostingItem> getPostingListFiltered() {
        return postingListFiltered;
    }

    public String getCompanyIdentifier() {
        return companyIdentifier;
    }

    public String getDepartment() {
        return department;
    }

    public String getCountry() {
        return country;
    }

    public String getRegion() {
        return region;
    }

    public String getCity() {
        return city;
    }

    public String getQ() {
        return q;
    }
}
