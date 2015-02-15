package core;

import java.io.IOException;

import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.scribe.model.Token;

import com.google.gson.Gson;
@Path("/serviceGG")
public class ServiceGG {
	@POST
	@Path("/GetAuthURL")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetAuthURL() {
		boolean status = false;
		String url = AppGG.getAuthUrl();
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
		Token token = AppGG.getToken(code); 
		if(token !=null){
			AppGG.StoreToken(token);
			message = "Verify successful";
		}
		return message;
	}
	
	@GET
	@Path("/getFile")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetFile() {
		Token object = AppGG.GetToken();
		Gson gson = new Gson();
		return gson.toJson(object);
	}
	
	@POST
	@Path("/getAbout")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetAbout() {
		return AppGG.GetAPI("https://www.googleapis.com/drive/v2/about");
	}
	
	@POST
	@Path("/getDir")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetDir() {
		return AppGG.GetAPI("https://www.googleapis.com/drive/v2/files");
	}
	
	@POST
	@Path("/deleteFile/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String DeleteFile(@PathParam("id") String id) {
		String url = "https://www.googleapis.com/drive/v2/files/"+id;
		return AppGG.DeleteAPI(url);
	}
}