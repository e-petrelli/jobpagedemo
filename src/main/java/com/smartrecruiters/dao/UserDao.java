package com.smartrecruiters.dao;

import com.smartrecruiters.api.users.UsersApi;
import com.smartrecruiters.api.users.invoker.ApiException;
import com.smartrecruiters.api.users.model.UserEntity;

public class UserDao {

    private UsersApi usersApi = new UsersApi();

    public UserDao(String sSmartToken) {
        this.usersApi.getApiClient().setApiKey(sSmartToken);
    }

    public UserEntity getMe() throws ApiException {
        return this.usersApi.usersMe();
    }
}
