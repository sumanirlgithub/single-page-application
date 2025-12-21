package com.neo.payment.connect.request;

import lombok.Data;

@Data
public class OauthToken {

    private String grantType;
    private String scope;

}
