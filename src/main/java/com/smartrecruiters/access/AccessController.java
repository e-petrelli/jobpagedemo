package com.smartrecruiters.access;

import com.smartrecruiters.Constants;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

public class AccessController {

    private static Logger logger = Logger.getLogger(AccessController.class.getName());

    public Boolean hasCompanyIdentifierParameter(HttpServletRequest request) {

        String companyIdentifier = request.getParameter(Constants.PARAMTER_COMPANYIDENTIFIER);

        if (StringUtils.isNotEmpty(companyIdentifier)) {
            logger.info("Parameter companyIdentifier found on request: " + companyIdentifier);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

}