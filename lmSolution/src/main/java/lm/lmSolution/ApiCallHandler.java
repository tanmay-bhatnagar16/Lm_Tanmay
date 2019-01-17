package lm.lmSolution;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.Charsets;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 * Hello world!
 *
 */
public class ApiCallHandler 

{
	public static int statusCode;
	public static JSONObject responseJSON;
	public static String url = "http://localhost:51544/v1/orders";
    @SuppressWarnings("deprecation")
	public static JSONObject postRequest( String endPoint,JSONObject inputJSon  ) throws ParseException, IOException
    {
    	HttpClient client = new DefaultHttpClient();
    	HttpPost post = new HttpPost(endPoint);

    	post.setHeader("Content-type", "application/json");

    	// if you need any parameters
    	StringEntity se = new StringEntity( inputJSon.toString());    	
    	post.setEntity(se);

    	HttpResponse response = client.execute(post);
    	statusCode = response.getStatusLine().getStatusCode();

    	HttpEntity entity = response.getEntity();
    	//Header encodingHeader = entity.getContentEncoding();

    	// you need to know the encoding to parse correctly
    	//Charset encoding = encodingHeader == null ? StandardCharsets.UTF_8 : 
    	//Charsets.toCharset(encodingHeader.getValue());

    	// use org.apache.http.util.EntityUtils to read json as string
    	String json = EntityUtils.toString(entity, StandardCharsets.UTF_8);

    	JSONObject o = new JSONObject(json);
		return o;
	
    }
    
    @SuppressWarnings("deprecation")
  	public static JSONObject getRequest( String endPoint,String urlParameters ) throws ParseException, IOException
      {
    	HttpClient client = new DefaultHttpClient();
    	  HttpGet request = new HttpGet(endPoint+"/"+urlParameters);
    	  HttpResponse response = client.execute(request);
    	  HttpEntity entity = response.getEntity();
    	  statusCode = response.getStatusLine().getStatusCode();
    	  String json = EntityUtils.toString(entity, StandardCharsets.UTF_8);
    	  
      	JSONObject o = new JSONObject(json);
  		return o;
      }
    
    @SuppressWarnings("deprecation")
  	public static JSONObject putRequest( String endPoint,String urlParameters  ) throws ParseException, IOException
      {
    	HttpClient client = new DefaultHttpClient();
    	  HttpPut request = new HttpPut(endPoint+"/"+urlParameters);
    	  HttpResponse response = client.execute(request);
    	  statusCode = response.getStatusLine().getStatusCode();
    	  HttpEntity entity = response.getEntity();
    	  
    	  String json = EntityUtils.toString(entity, StandardCharsets.UTF_8);
      	JSONObject o = new JSONObject(json);
  		return o;
      }
}
