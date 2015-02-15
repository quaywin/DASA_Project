package core.oauth;

import core.api.DropBoxApi20;
import org.scribe.builder.ServiceBuilder;
import org.scribe.oauth.OAuthService;

/**
 *
 * @author Admin
 */
public class ServiceFactory {
    
    public static final String DROPBOX = "dropbox";
    public static final String DRIVE = "drive";
    
    private static final String DROPBOX_API_KEY = "9231a0npejt32sk";
    private static final String DROPBOX_API_SECRET = "i2v1wh2kv0o0wb8";
    private static final String DROPBOX_CALLBACK = "http://localhost";
    
    public static OAuthService create(String type) {
        switch (type) {
            case DROPBOX:
                return createDropboxAuthService();
            default:
                throw new UnsupportedOperationException(type);
        }
    }
    
    private static OAuthService createDropboxAuthService() {
        return new ServiceBuilder()
                .provider(DropBoxApi20.class)
                .apiKey(DROPBOX_API_KEY)
                .apiSecret(DROPBOX_API_SECRET)
                .callback(DROPBOX_CALLBACK)
                .build();
    }
}
