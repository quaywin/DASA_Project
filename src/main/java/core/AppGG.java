package core;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import core.api.DriveApi;


public class AppGG {
	private static String CLIENT_ID = "108834868087-barvsgdd52aj1lee4v4k1nebvrkof0qq.apps.googleusercontent.com";
	private static String CLIENT_SECRET = "V6Ii9s26olyjFuZUQtXZtLye";
	/** Global Drive API client. */
	private static OAuthService service = new ServiceBuilder()
    .provider(DriveApi.class)
    .apiKey(CLIENT_ID)
    .apiSecret(CLIENT_SECRET)
    .callback("http://localhost:8080/rest/serviceGG/verify")
    .scope("https://www.googleapis.com/auth/drive")
    .build();
    public static String getAuthUrl() {
		return service.getAuthorizationUrl(null);
	}
    public static Token getToken(String url) {
		Verifier verifier = new Verifier(url);
		Token accessToken = service.getAccessToken(null, verifier);
		return accessToken;
	}
    public static void StoreToken(Token accessToken) throws IOException {
    	 ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                 new FileOutputStream("token.txt"));
    	 objectOutputStream.writeObject(accessToken);
    	 objectOutputStream.flush();
    	 objectOutputStream.close();
	}
    public static Token GetToken(){
    	Token token = null;
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(
	    	        new FileInputStream("token.txt"));
			token = (Token)objectInputStream.readObject();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return token;
	}
    public static String GetAPI(String url) {
		Token token =  GetToken();
		OAuthRequest request = new OAuthRequest(Verb.GET, url);
   	 	service.signRequest(token, request);
   	 	Response response = request.send();
   	 	return response.getBody();
	}
    public static String PostAPI(String url) {
		Token token =  GetToken();
		OAuthRequest request = new OAuthRequest(Verb.POST, url);
   	 	service.signRequest(token, request);
   	 	Response response = request.send();
   	 	return response.getBody();
	}
    
    public static String DeleteAPI(String url) {
		Token token =  GetToken();
		OAuthRequest request = new OAuthRequest(Verb.DELETE, url);
   	 	service.signRequest(token, request);
   	 	Response response = request.send();
   	 	return response.getBody();
	}
}
