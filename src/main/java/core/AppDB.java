package core;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.servlet.http.HttpSession;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import com.dropbox.core.DbxEntry;

import core.api.DropBoxApi20;

public class AppDB {
	private static String CLIENT_ID = "498cokxmtfj5yto";
	private static String CLIENT_SECRET = "rsjfoufh31x54bw";
//	private static String PROTECTED_RESOURCE_URL = "https://www.googleapis.com/drive/v2/files";
	/** Global Dropbox API client. */
	private static OAuthService service = new ServiceBuilder()
    .provider(DropBoxApi20.class)
    .apiKey(CLIENT_ID)
    .apiSecret(CLIENT_SECRET)
    .callback("http://localhost:8080/rest/serviceDB/verify")
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
                 new FileOutputStream("tokenDB.txt"));
    	 objectOutputStream.writeObject(accessToken);
    	 objectOutputStream.flush();
    	 objectOutputStream.close();
	}
    public static Token GetToken(){
    	Token token = null;
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(
	    	        new FileInputStream("tokenDB.txt"));
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
//    public static String DownloadFile(String url) {
//    	DbxEntry.File downloadedFile = client.getFile("/magnum-opus.txt", null,
//    	        outputStream);
//     	 	return response.getBody();
//  	}
}

