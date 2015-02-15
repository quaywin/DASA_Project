package core.api;


public class DropBoxApi20 extends Api20Base {

    @Override
    public String getAccessTokenEndpoint() {
        return "https://api.dropbox.com/1/oauth2/token";
    }

    @Override
    protected String getAuthorizeUrlFormat() {
        return "https://www.dropbox.com/1/oauth2/authorize?client_id=%s&redirect_uri=%s&response_type=code";
    }

}
