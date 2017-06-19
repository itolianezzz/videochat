package ru.spb.itolia.videochat.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class SessionInfo{

	@SerializedName("session_id")
	private String sessionId;

	@SerializedName("token")
	private String token;

	public void setSessionId(String sessionId){
		this.sessionId = sessionId;
	}

	public String getSessionId(){
		return sessionId;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}

	@Override
 	public String toString(){
		return 
			"SessionInfo{" + 
			"session_id = '" + sessionId + '\'' + 
			",token = '" + token + '\'' + 
			"}";
		}
}