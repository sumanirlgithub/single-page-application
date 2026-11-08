package com.neo.payments.hub.model.web;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Environment {

    /** itp **/
    RWP("RWP"),

    /** ROS **/
    ROS("ros"),

    /** LPT **/
    LPT("lpt"),

    THIRD_PARTY("thirdParty");

    private String path;

    public static Environment forCode(String code) {
        for (Environment env: Environment.values()) {
            if (env.name().equalsIgnoreCase(code)) {
                return env;
            }
        }
        return null;
    }

}
