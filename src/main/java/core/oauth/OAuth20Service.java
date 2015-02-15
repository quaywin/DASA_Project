package core.oauth;

import java.io.IOException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.scribe.builder.api.DefaultApi20;
import org.scribe.exceptions.OAuthException;
import org.scribe.model.OAuthConfig;
import org.scribe.model.OAuthConstants;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

/**
 *
 * @author Admin
 */
public class OAuth20Service implements OAuthService {

    private static final String VERSION = "2.0";
    private static final String GRANT_TYPE = "grant_type";

    private final DefaultApi20 api;
    private final OAuthConfig config;

    /**
     * Default constructor
     *
     * @param api OAuth2.0 api information
     * @param config OAuth 2.0 configuration param object
     */
    public OAuth20Service(DefaultApi20 api, OAuthConfig config) {
        this.api = api;
        this.config = config;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Token getAccessToken(Token requestToken, Verifier verifier) {
        try {
            String content = Request.Post(api.getAccessTokenEndpoint())
                    .bodyForm(Form.form()
                            .add(OAuthConstants.CODE, verifier.getValue())
                            .add(OAuthConstants.CLIENT_ID, config.getApiKey())
                            .add(OAuthConstants.CLIENT_SECRET, config.getApiSecret())
                            .add(OAuthConstants.REDIRECT_URI, config.getCallback())
                            .add(GRANT_TYPE, "authorization_code")
                            .build())
                    .execute().returnContent().asString();
            return api.getAccessTokenExtractor().extract(content);

        } catch (IOException ex) {
            throw new OAuthException(ex.getMessage());
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Token getRequestToken() {
        throw new UnsupportedOperationException("Unsupported operation, please use 'getAuthorizationUrl' and redirect your users there");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getVersion() {
        return VERSION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void signRequest(Token accessToken, OAuthRequest request) {
        request.addQuerystringParameter(OAuthConstants.ACCESS_TOKEN, accessToken.getToken());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAuthorizationUrl(Token requestToken) {
        return api.getAuthorizationUrl(config);
    }

}
