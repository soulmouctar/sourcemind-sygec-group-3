package com.sygec.sygec.Api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sygec.sygec.dto.OrangeTokenDto;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


@Configuration
public class ApiSms {
	
	static Logger logger = LoggerFactory.getLogger(ApiSms.class);
	
	private static final String HEADER_AUTORIZE="Basic amZvY24zcFcwT0djYWxSeUU3dEdhZ3BUb3ZVR0FOaXM6WWp3dTNHVVpuaEJ4dXdOUg==";

	//@Bean
	public OrangeTokenDto generateToken() {
		OrangeTokenDto orangeToken = new OrangeTokenDto();

		Map<String, Object> claims = new HashMap<>();
		String res = null;
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials");
		Request request = new Request.Builder().url("https://api.orange.com/oauth/v3/token").get().method("POST", body)
				.addHeader("Authorization", HEADER_AUTORIZE)
				.addHeader("Content-Type", "application/x-www-form-urlencoded").build();
		// String jsonString = null;
		try {
			Response response = client.newCall(request).execute();

			final int code = response.code(); // can be any value

			if (code == 200) {
				final ResponseBody body1 = response.body(); // body exists, I have to close iterator
				// s logger.info("type {}", body);
				res = body1.string();
				JsonObject jsonObject = new JsonParser().parse(res).getAsJsonObject();
				logger.info("type {}", jsonObject.get("token_type"));
				//
				// res.charAt(code);
				body1.close(); // I close it explicitly
				orangeToken.setAccessToken(jsonObject.get("access_token").getAsString());
				orangeToken.setExpiresIn(jsonObject.get("expires_in").getAsInt());
				orangeToken.setTokenType(jsonObject.get("token_type").getAsString());
				logger.info("hello {}", orangeToken.getAccessToken());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return orangeToken;

	}
	
	//@Bean
	public void sendSms(String telephone,String message,String token) {
//		String telephone = "+224621989100";
//		String message = "hello word";
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				MediaType mediaType = MediaType.parse("application/json");
				RequestBody body = RequestBody.create(mediaType, "{\n\t\"outboundSMSMessageRequest\": {\n\t\t\"address\": \"tel:"+telephone+"\",\n        "
						+ "\n\t\t\"senderAddress\":\"tel:+621989100\",\n\t\t\"outboundSMSTextMessage\": "
						+ "{\n\t\t\t\"message\": \""+message+"\"\n\t\t}\n\t}\n} ");
				Request request = new Request.Builder()
				  .url("https://api.orange.com/smsmessaging/v1/outbound/tel%3A%2B621989100/requests")
				  .method("POST", body)
				  .addHeader("Content-Type", "application/json")
				  .addHeader("Authorization", "Bearer "+token)
				  .build();
				try {
					Response response = client.newCall(request).execute();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
//	@Bean
//	public void sendMail() {
//		OkHttpClient client = new OkHttpClient().newBuilder()
//			    .build();
//			MediaType mediaType = MediaType.parse("application/json");
//			RequestBody body = RequestBody.create(mediaType, "{\"from\":{\"email\":\"mailtrap@www.polyclinique-mohkante.com\",\"name\":\"Mailtrap Test\"},\"to\":[{\"email\":\"amaracamara52@gmail.com\"}],\"subject\":\"You are awesome!\",\"text\":\"Congrats for sending test email with Mailtrap!\",\"category\":\"Integration Test\"}");
//			Request request = new Request.Builder()
//			    .url("https://send.api.mailtrap.io/api/send")
//			    .method("POST", body)
//			    .addHeader("Authorization", "Bearer 74a55247b4b350ab026725e034b47f27")
//			    .addHeader("Content-Type", "application/json")
//			    .build();
//			try {
//				Response response = client.newCall(request).execute();
//				response.body().close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	}


}
