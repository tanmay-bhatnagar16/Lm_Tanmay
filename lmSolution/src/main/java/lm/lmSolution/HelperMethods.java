/**
 * 
 */
package lm.lmSolution;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @author tanmaybhatnagar
 *
 */
public class HelperMethods {
	private static Logger logger;

	static {

		logger = Logger.getLogger("LOG");
		readProperties();

	}

	/**
	 * @param endPoint
	 *            -- URL of the endpoint
	 * @param inputJSon
	 *            -- JSON Paylod for POST request
	 * @return
	 */
	public static int placeOrder(JSONObject inputJSon) {
		int id = 0;

		try {
			ApiCallHandler.setResponseJson(ApiCallHandler
					.postRequest(ApiCallHandler.getHostUrl() + ApiCallHandler.getPlaceOrderEp(), inputJSon));
			id = ApiCallHandler.getResponseJson().getInt("id");

		} catch (Exception e) {
			logger.error("Exception occured : " + e.getMessage());
		}
		return id;
	}

	/**
	 * @param isAdvanceOrder
	 * @return
	 */
	public static JSONObject getDefaultJSON(boolean isAdvanceOrder) {

		JSONObject outputJSon = new JSONObject();
		try {

			String data = getExcelData("TestCaseParams", "Default");
			outputJSon = new JSONObject(data);
			if (isAdvanceOrder) {

				outputJSon.put("orderAT", getFuturePastDate(isAdvanceOrder, false, 0));
			}

		} catch (Exception e) {
			logger.error("Exception occured : " + e.getMessage());
		}
		return outputJSon;
	}

	
	
	public static JSONObject takeOrder(int id) {

		JSONObject outputJSon = null;
		try {
			String takeOrderResource = MessageFormat.format(ApiCallHandler.getTakeOrderEP(), String.valueOf(id));
			outputJSon = ApiCallHandler.putRequest(ApiCallHandler.getHostUrl(), takeOrderResource);

		} catch (Exception e) {
			logger.error("Exception occured : " + e.getMessage());
		}
		return outputJSon;
	}

	public static JSONObject completeOrder(int id) {

		JSONObject outputJSon = null;
		try {
			String completeResource = MessageFormat.format(ApiCallHandler.getCompleteOrderEP(), String.valueOf(id));
			outputJSon = ApiCallHandler.putRequest(ApiCallHandler.getHostUrl(), completeResource);

		} catch (Exception e) {
			logger.error("Exception occured : " + e.getMessage());
		}
		return outputJSon;
	}

	public static JSONObject fetchOrder(int id) {

		JSONObject outputJSon = null;
		try {
			String fetchOrderResource = MessageFormat.format(ApiCallHandler.getFetchOrderEP(), String.valueOf(id));
			outputJSon = ApiCallHandler.getRequest(ApiCallHandler.getHostUrl(), fetchOrderResource);

		} catch (Exception e) {
			logger.error("Exception occured : " + e.getMessage());
		}
		return outputJSon;
	}

	public static JSONObject cancelOrder(int id) {

		JSONObject outputJSon = null;
		try {
			String cancelOrderResource = MessageFormat.format(ApiCallHandler.getCancelOrderEP(), String.valueOf(id));
			outputJSon = ApiCallHandler.putRequest(ApiCallHandler.getHostUrl(), cancelOrderResource);

		} catch (Exception e) {
			logger.error("Exception occured : " + e.getMessage());
		}
		return outputJSon;
	}

	public static void readProperties() {
		Properties prop = new Properties();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream stream = loader.getResourceAsStream("Config.properties");
		try {
			prop.load(stream);
			ApiCallHandler.setHostUrl(prop.getProperty("host"));
			ApiCallHandler.setPlaceOrderEp(prop.getProperty("placeOrderEndPoint"));
			ApiCallHandler.setFetchOrderEp(prop.getProperty("fetchOrdersEndPoint"));
			ApiCallHandler.setTakeOrderEp(prop.getProperty("takeOrderEndPoint"));
			ApiCallHandler.setCompleteOrderEp(prop.getProperty("completeOrdersEndPoint"));
			ApiCallHandler.setCancelOrderEp(prop.getProperty("cancelOrdersEndPoint"));
			ExcelReader.fileName = prop.getProperty("configFile");

		} catch (Exception e) {
			logger.error("Exception occured : " + e.getMessage());

		}

	}

	public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
		final int RADIUS_EARTH = 6378137;

	    double dLat = deg2rad(lat2 - lat1);
	    double dLong = deg2rad(lon2 - lon1);

	    double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.sin(dLong / 2) * Math.sin(dLong / 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    return (RADIUS_EARTH * c) ;
		
		
		/*
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		dist = dist * 1.609344; // Distance in KMs
		dist = dist * 1000; // Distance in meters
*/
		//return (dist);
	}

	
	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	
	public static String getExcelData(String SheetName, String Key) throws IOException {
		// Call read file method of the class to read data
		ExcelReader objExcelFile = new ExcelReader();
		return objExcelFile.readExcel(SheetName, Key);

	}

	public static String getFuturePastDate(boolean isFuture, boolean isMidNight, int offsetInHours) throws IOException {

		String timeStamp = null;
		LocalDateTime now = LocalDateTime.now(); // current date and time
		LocalDateTime midnight = now.toLocalDate().atStartOfDay();
		int x = (int) (Math.random() * 10);

		// fetch midnight instant
		Instant instant = midnight.atZone(ZoneId.of("Z")).toInstant();

		if (isFuture)
			if (isMidNight) {
				timeStamp = instant.plus(x, ChronoUnit.DAYS).plus(offsetInHours, ChronoUnit.HOURS).toString();
			} else
				timeStamp = Instant.now().plus(x, ChronoUnit.DAYS).toString();

		else
			timeStamp = Instant.now().minus(x, ChronoUnit.DAYS).toString();

		return timeStamp;

	}

	public static double calculateFare(JSONObject inputJson, double baseFare, double fareRate,
			double SampleDistance) {
		JSONArray distanceArray = inputJson.getJSONArray("drivingDistancesInMeters");
		double distance = 0;
		for (int i = 0; i < distanceArray.length(); i++) {
			distance = distance
					+ distanceArray.getDouble(i);

		}

		return (baseFare + fareRate * ((distance-2000) / SampleDistance));

	}
}
