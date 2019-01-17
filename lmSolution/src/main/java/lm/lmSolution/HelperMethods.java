/**
 * 
 */
package lm.lmSolution;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author tanmaybhatnagar
 *
 */
/**
 * @author tanmaybhatnagar
 *
 */
public class HelperMethods {
	
	static {
		Properties prop = new Properties();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();           
		InputStream stream = loader.getResourceAsStream("Config.properties");
		try {
			prop.load(stream);
			
			ApiCallHandler.url = prop.getProperty("accUrl", "http://localhost:51544/v1/orders");
		} catch (Exception e) {
			
		}
		 
	}
	
	/**
	 * @param endPoint -- URL of the endpoint
	 * @param inputJSon -- JSON Paylod for POST request
	 * @return
	 */
	public static int placeOrder(JSONObject inputJSon)
	{
		int id =0;
	
		try
		{
			ApiCallHandler.responseJSON = ApiCallHandler.postRequest(ApiCallHandler.url, inputJSon);
			id = ApiCallHandler.responseJSON.getInt("id");
	
			
		}catch(Exception e)
		{
			
		}
		return id;
	}
	
	
	/**
	 * @param isAdvanceOrder
	 * @return
	 */
	public static JSONObject getDefaultJSON(boolean isAdvanceOrder)
	{
		
		JSONObject outputJSon = new JSONObject();
		try
		{
			JSONArray array1 = new JSONArray();
			JSONArray array2 = new JSONArray();
			JSONObject item = new JSONObject(), item1 = new JSONObject(), item2 = new JSONObject();
			item.put("lat", 22.344674);
			item.put("lng", 114.124651);
			item1.put("lat", 22.375384);
			item1.put("lng", 114.182446);
			item2.put("lat", 22.385669);
			item2.put("lng", 114.186962);
			array1.put(item);
			array1.put(item1);
			array1.put(item2);
			outputJSon.put("stops", array1);
			if(isAdvanceOrder)
			{
				outputJSon.put("orderAT", "2019-03-03T13:00:00.000Z");
			}
			
		}catch(Exception e)
		{
			
		}
		return outputJSon;
	}
	
	
	/**
	 * @return status code for previous request
	 */
	public static int getStatusCode()
	{
		
		return ApiCallHandler.statusCode;
		
	}

	public static JSONObject takeOrder( int id)
	{
		
		JSONObject outputJSon = null;
		try
		{
			outputJSon = ApiCallHandler.putRequest(ApiCallHandler.url, id+"/take");
			
			
		}catch(Exception e)
		{
			
		}
		return outputJSon;
	}

	public static JSONObject completeOrder(int id)
	{
		
		JSONObject outputJSon = null;
		try
		{
			outputJSon = ApiCallHandler.putRequest(ApiCallHandler.url, id+"/complete");
			
			
		}catch(Exception e)
		{
			
		}
		return outputJSon;
	}
	
	public static JSONObject fetchOrder(int id)
	{
		
		JSONObject outputJSon = null;
		try
		{
			outputJSon = ApiCallHandler.getRequest(ApiCallHandler.url, id+"");
			
			
		}catch(Exception e)
		{
			
		}
		return outputJSon;
	}

	public static JSONObject cancelOrder( int id)
	{
		
		JSONObject outputJSon = null;
		try
		{
			outputJSon = ApiCallHandler.putRequest(ApiCallHandler.url, id+"/cancel");
			
			
		}catch(Exception e)
		{
			
		}
		return outputJSon;
	}
 
    
}
