package core;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.scribe.model.Token;

import com.google.gson.Gson;
@Path("/serviceDB")
public class ServiceDB {
	@POST
	@Path("/GetAuthURL")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetAuthURL() {
		boolean status = false;
		String url = AppDB.getAuthUrl();
		if(url!=null){
			status = true;
		}
		JsonResponse object = new JsonResponse(url, status);
		return object.toJson();
	}
	@GET
	@Path("/verify")
	@Produces(MediaType.APPLICATION_JSON)
	public String VerifyAuth(@QueryParam("code") String code) throws IOException{
		String message = "Verify fail";
		Token token = AppDB.getToken(code); 
		if(token !=null){
			AppDB.StoreToken(token);
			message = "Verify successful";
		}
		return message;
	}
	
//	@GET
//	@Path("/getFile")
//	@Produces(MediaType.APPLICATION_JSON)
//	public String GetFile() {
//		Token object = AppDB.GetToken();
//		Gson gson = new Gson();
//		return gson.toJson(object);
//	}
//	
	@POST
	@Path("/getAbout")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetAbout() {
		return AppDB.GetAPI("https://api.dropbox.com/1/account/info");
	}

	@POST
	@Path("/getDir")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetDir() {
		return AppDB.GetAPI("https://api.dropbox.com/1/metadata/auto/");
	}
	
	@POST
	@Path("/getFile/{path}")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetFile(@PathParam("path") String path) {
		String url = "https://api-content.dropbox.com/1/files/auto/"+path;
		return AppDB.GetAPI(url);
	}
}