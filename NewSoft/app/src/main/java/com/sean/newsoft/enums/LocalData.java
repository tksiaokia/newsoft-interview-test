package com.sean.newsoft.enums;


import com.sean.newsoft.model.UserToken;

public enum LocalData{
    userToken(UserToken.class);

    Class type;

    LocalData(Class type) {
        this.type = type;
    }

    public Class getType() {
        return type;
    }
}
