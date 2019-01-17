package lm.lmSolution;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 * Hello world!
 *
 */
public class ApiCallHandler 

{
	private static  int statusCode_private;
	private  static JSONObject responseJSON_private;
	private static String url_private = "http://localhost:51544/v1/orders";
	
    public static JSONObject postRequest( String endPoint,JSONObject inputJSon  ) throws ParseException, IOException
    {
    	CloseableHttpClient client = HttpClientBuilder.create().build();
    	HttpPost post = new HttpPost(endPoint);

    	post.setHeader("Content-type", "application/json");

    	
    	StringEntity se = new StringEntity( inputJSon.toString());    	
    	post.setEntity(se);

    	HttpResponse response = client.execute(post);
    	setstatusCode(response.getStatusLine().getStatusCode());

    	HttpEntity entity = response.getEntity();    	

    	String json = EntityUtils.toString(entity, StandardCharsets.UTF_8);

    	JSONObject o = new JSONObject(json);
    	client.close();
		return o;
	
    }
    
    public static JSONObject getRequest( String endPoint,String urlParameters ) throws ParseException, IOException
      {
    	CloseableHttpClient client = HttpClientBuilder.create().build();
    	  HttpGet request = new HttpGet(endPoint+"/"+urlParameters);
    	  HttpResponse response = client.execute(request);
    	  HttpEntity entity = response.getEntity();
    	  setstatusCode(response.getStatusLine().getStatusCode());
    	  String json = EntityUtils.toString(entity, StandardCharsets.UTF_8);
    	  
      	JSONObject o = new JSONObject(json);
      	client.close();
  		return o;
      }
    
    public static JSONObject putRequest( String endPoint,String urlParameters  ) throws ParseException, IOException
      {
    	CloseableHttpClient client = HttpClientBuilder.create().build();
    	  HttpPut request = new HttpPut(endPoint+"/"+urlParameters);
    	  HttpResponse response = client.execute(request);
    	  setstatusCode(response.getStatusLine().getStatusCode());
    	  HttpEntity entity = response.getEntity();
    	  
    	  String json = EntityUtils.toString(entity, StandardCharsets.UTF_8);
      	JSONObject o = new JSONObject(json);
      	client.close();
  		return o;
      }
    
    
    // get and Set methods for private members
    public static int getstatusCode() {return statusCode_private;}
    public static void setstatusCode(int code) {statusCode_private= code;}
    
    public static JSONObject getJSONObject() {return responseJSON_private;}
    public static void setJSONObject(JSONObject json) {responseJSON_private= json;}
    
    public static String getEndPoint() {return url_private;}
    public static void setEndPoint(String url) {url_private= url;}
    
}
