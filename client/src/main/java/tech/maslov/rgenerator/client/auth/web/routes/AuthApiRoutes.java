package tech.maslov.rgenerator.client.auth.web.routes;

import com.rcore.restapi.routes.BaseApiRoutes;

public class AuthApiRoutes {
    public static final String ROOT = BaseApiRoutes.V1 + "/auth";
    public static final String LOGIN = ROOT + "/login";
    public static final String REFRESH = ROOT + "/refresh";
    public static final String LOGOUT = ROOT + "/logout";
    public static final String SIGN_UP = ROOT + "/sign-up";
    public static final String FORGOT_PASSWORD = ROOT + "/forgot-password";
    public static final String FORGOT_PASSWORD_START = FORGOT_PASSWORD + "/start";
    public static final String FORGOT_PASSWORD_CHANGE = FORGOT_PASSWORD + "/change";

}
