package core.oauth;

import java.util.Map;
import org.codehaus.jackson.map.ObjectMapper;
import org.scribe.exceptions.OAuthException;
import org.scribe.extractors.AccessTokenExtractor;
import org.scribe.model.Token;

/**
 *
 * @author Admin
 */
public class JacksonTokenExtractor implements AccessTokenExtractor {

    public Token extract(String response) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            String code = mapper
                    .readValue(response, Map.class)
                    .get("access_token")
                    .toString();
            return new Token(code, "", response);
        } catch (Exception e) {
            throw new OAuthException("Cannot extract an access token. Response was: " + response);
        }

    }

}
