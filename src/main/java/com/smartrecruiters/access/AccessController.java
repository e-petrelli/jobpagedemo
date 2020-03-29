package com.smartrecruiters.access;

import com.smartrecruiters.Constants;
import com.smartrecruiters.api.users.UsersApi;
import com.smartrecruiters.api.users.invoker.ApiException;
import com.smartrecruiters.api.users.model.UserEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

public class AccessController {

    private static Logger logger = Logger.getLogger(AccessController.class.getName());

    public Boolean hasAccess(HttpServletRequest request) {

        String sSmartToken = request.getParameter(Constants.PARAMTER_SMARTTOKEN);

        if (sSmartToken != null) {
            logger.info("Parameter found on request: " + sSmartToken);

            // Checks Token:
            UsersApi usersApi = new UsersApi();
            usersApi.getApiClient().setApiKey(sSmartToken);
            try {
                UserEntity userEntity = usersApi.usersMe();
                logger.info("success: user is " + userEntity.getEmail());
                return Boolean.TRUE;
            } catch (ApiException e) {
                logger.info("failure getting user: " + e.getResponseBody());
            }
        }
        return Boolean.FALSE;
    }
}