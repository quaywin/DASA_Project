package core.api;

import core.oauth.JacksonTokenExtractor;
import core.oauth.OAuth20Service;
import org.scribe.builder.api.DefaultApi20;
import org.scribe.extractors.AccessTokenExtractor;
import org.scribe.model.OAuthConfig;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;
import org.scribe.utils.OAuthEncoder;

public abstract class Api20Base extends DefaultApi20 {

    protected abstract String getAuthorizeUrlFormat();

    protected String getScopeAuthorizeUrlFormat() {
        return getAuthorizeUrlFormat() + "&scope=%s";
    }

    @Override
    public Verb getAccessTokenVerb() {
        return Verb.POST;
    }

    @Override
    public AccessTokenExtractor getAccessTokenExtractor() {
        return new JacksonTokenExtractor();
    }

    @Override
    public String getAuthorizationUrl(OAuthConfig config) {
        if (config.hasScope()) {
            return String.format(getScopeAuthorizeUrlFormat(), config.getApiKey(), OAuthEncoder.encode(config.getCallback()), OAuthEncoder.encode(config.getScope()));
        } else {
            return String.format(getAuthorizeUrlFormat(), config.getApiKey(), OAuthEncoder.encode(config.getCallback()));
        }
    }

    @Override
    public OAuthService createService(OAuthConfig config) {
        return new OAuth20Service(this, config);
    }

}
