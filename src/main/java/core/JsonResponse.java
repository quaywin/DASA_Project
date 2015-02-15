package core;

import com.google.gson.Gson;

public class JsonResponse {
	private Object Data = null;
	private boolean Status = false;
	public JsonResponse(Object data, boolean status) {
		Data = data;
		Status = status;
	}
	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
