package core.api;

/**
 *
 * @author Admin
 */
public class DriveApi extends Api20Base {

    @Override
    public String getAccessTokenEndpoint() {
        return "https://accounts.google.com/o/oauth2/token";
    }

    @Override
    protected String getAuthorizeUrlFormat() {
        return "https://accounts.google.com/o/oauth2/auth?client_id=%s&redirect_uri=%s&response_type=code";
    }

}
