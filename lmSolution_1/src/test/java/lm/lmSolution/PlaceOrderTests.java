package lm.lmSolution;

import org.testng.annotations.Test;

import lm.lmSolution.ApiCallHandler;
import lm.lmSolution.HelperMethods;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;

import java.lang.reflect.Method;

import org.apache.http.ParseException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class PlaceOrderTests {


	/**
	 * description : Verifies place order functionality and checks generation of valid ID
	 */
	@Test
	public void Testcase_placeOrder_verifyID() {

		JSONObject inputJSon = new JSONObject();
		int id = 0;
		try {

			// Create test data
			inputJSon = HelperMethods.getDefaultJSON(false);

			//Place Order
			id = HelperMethods.placeOrder(inputJSon);

			System.out.println("ID is : " + id);
			if (id == 0) {
				Assert.fail("ID Not Generated ");
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * description : Verifies place order functionality and checks Number of distance counts are correct as per provided json
	 */
	@Test()
	public void Testcase_placeOrder_verifyDistancesCount() {

		JSONObject inputJSon = new JSONObject();
		int expectedCount = 2;
		try {

			new JSONArray();
			JSONArray array2 = new JSONArray();
			// Create test data
			inputJSon = HelperMethods.getDefaultJSON(false);

			//Place Order
			HelperMethods.placeOrder(inputJSon);
			// Fetch driving distance from response
			array2 = ApiCallHandler.responseJSON.getJSONArray("drivingDistancesInMeters");
			System.out.println("Length is : " + array2.length());
			if (array2.length() != expectedCount) {
				Assert.assertEquals(array2.length(), expectedCount, "Number of distances do not match ");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * description : Verifies place order functionality and checks that fare is correctly generated if distance is less than 2 kms
	 */
	@Test()
	public void Testcase_placeOrder_verifyFareBelowTwoKms() {

		JSONObject inputJSon = new JSONObject(), outputJSon1;
		Float expectedFare = new Float(20.00);
		float actualFare;
		try {

			// Create test data
			JSONArray array1 = new JSONArray();
			new JSONArray();
			JSONObject item = new JSONObject(), item1 = new JSONObject();
			item.put("lat", 22.344674);
			item.put("lng", 114.124651);
			item1.put("lat", 22.344674);
			item1.put("lng", 114.124351);
			array1.put(item);
			array1.put(item1);
			inputJSon.put("stops", array1);			

			//Place Order
			HelperMethods.placeOrder(inputJSon);
			
			//fetch fare from response
			outputJSon1 = ApiCallHandler.responseJSON.getJSONObject("fare");
			actualFare = outputJSon1.getFloat("amount");
			System.out.println("Fare is : " + actualFare);

			if (actualFare != expectedFare) {
				Assert.assertEquals(expectedFare, actualFare, "Fare amount do not match ");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * description : Verifies place order functionality and that fare is correctly generated if distance is more than 2 kms
	 */
	@Test()
	public void Testcase_placeOrder_verifyFareAboveTwoKms() {
		JSONObject inputJSon = new JSONObject(), outputJSon1;
		float expectedFare = (float) (20 + 5 * (1224.00 / 200.00));
		float actualFare;
		try {

			// Create test data
			JSONArray array1 = new JSONArray();

			JSONObject item = new JSONObject(), item1 = new JSONObject();
			item.put("lat", 22.344674);
			item.put("lng", 114.124651);
			item1.put("lat", 22.344674);
			item1.put("lng", 114.126651);
			array1.put(item);
			array1.put(item1);
			inputJSon.put("stops", array1);

			//Place Order
			HelperMethods.placeOrder(inputJSon);
			
			//fetch fare
			outputJSon1 = ApiCallHandler.responseJSON.getJSONObject("fare");
			actualFare = outputJSon1.getFloat("amount");
			System.out.println("Additional Fare is : " + actualFare);

			if (actualFare != expectedFare) {
				Assert.assertEquals(actualFare, expectedFare, "Fare amount do not match ");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * description : Verifies place advance  order functionality and checks generation of valid IDs
	 */
	@Test()
	public void Testcase_placeAdvanceOrder_VerifyId() {
		JSONObject inputJSon = new JSONObject();
		int id = 0;
		try {
			
			// Create test data
			inputJSon = HelperMethods.getDefaultJSON(true);			

			//Place Order
			id = HelperMethods.placeOrder(inputJSon);

			System.out.println("Advance ID is : " + id);
			if (id == 0) {
				Assert.fail("ID Not Generated ");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * description : Verifies place advance order functionality and checks Number of distance counts are correct as per provided json
	 */
	@Test()
	public void Testcase_placeAdvanceOrder_verifyDistancesCount() {

		JSONObject inputJSon = new JSONObject();
		int expectedCount = 2;
		
		try {
			// Create test data
			inputJSon = HelperMethods.getDefaultJSON(true);

			//Place Order
			 HelperMethods.placeOrder(inputJSon);
			 
			 //Fetch distance
			 JSONArray array2 = ApiCallHandler.responseJSON.getJSONArray("drivingDistancesInMeters");
			System.out.println("Length for Advance order is : " + array2.length());
			if (array2.length() != expectedCount) {
				Assert.assertEquals(array2.length(), expectedCount, "Number of distances do not match ");
			}
		} catch (ParseException e) { // TODO Auto-generated catch block e.printStackTrace();
		}
	}

	/**
	 * description : Verifies place advance order functionality and checks that fare is correctly generated if distance is less than 2 kms
	 */
	@Test()
	public void Testcase_placeAdvanceOrder_verifyFareBelowTwoKms() {

		JSONObject inputJSon = new JSONObject(), outputJSon1;
		float expectedFare = (float) 20.00;
		float actualFare;
		try {
			// Create test data
			JSONArray array1 = new JSONArray();
			JSONObject item = new JSONObject(), item1 = new JSONObject();
			item.put("lat", 22.344674);
			item.put("lng", 114.124651);
			item1.put("lat", 22.344674);
			item1.put("lng", 114.124351);
			array1.put(item);
			array1.put(item1);
			inputJSon.put("orderAT", "2019-03-03T13:00:00.000Z");
			inputJSon.put("stops", array1);

			//Place Order
			HelperMethods.placeOrder(inputJSon);
			
			//Fetch fare
			outputJSon1 = ApiCallHandler.responseJSON.getJSONObject("fare");
			actualFare = outputJSon1.getFloat("amount");
			System.out.println("Fare for Advance booking is : " + actualFare);

			if (actualFare != expectedFare) {
				Assert.assertEquals(actualFare, expectedFare, "Fare amount do not match ");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * description : Verifies place advance order functionality and that fare is correctly generated if distance is more than 2 kms
	 */
	@Test()
	public void Testcase_placeAdvanceOrder_verifyFareAboveTwoKms() {
		JSONObject inputJSon = new JSONObject(), outputJSon1; 
		// expected fare calculation
		float expectedFare = (float) (20 + 5 * (1224.00 / 200.00));
		float actualFare;
		try {

			// Create test data
			JSONArray array1 = new JSONArray();
			JSONObject item = new JSONObject(), item1 = new JSONObject();
			item.put("lat", 22.344674);
			item.put("lng", 114.124651);
			item1.put("lat", 22.344674);
			item1.put("lng", 114.126651);
			array1.put(item);
			array1.put(item1);
			inputJSon.put("orderAT", "2019-03-03T13:00:00.000Z");
			inputJSon.put("stops", array1);

			//Place Order
			HelperMethods.placeOrder(inputJSon);
			
			//Fetch fare
			outputJSon1 = ApiCallHandler.responseJSON.getJSONObject("fare");
			actualFare = outputJSon1.getFloat("amount");
			System.out.println("Additional Fare for Advance booking is : " + actualFare);
			if (actualFare != expectedFare) {
				Assert.assertEquals(actualFare, expectedFare, "Fare amount do not match ");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}
	}

	/**
	 * description : Verifies place advance order functionality and that NIGHT fare is correctly generated if distance is less than 2 kms
	 */
	@Test()
	public void Testcase_placeAdvanceOrder_verifyFareBelowTwoKms_NightCharges() {

		JSONObject inputJSon = new JSONObject(),  outputJSon1;
		float expectedFare = (float) 30.00;
		float actualFare;
		try {
			// Create test data
			JSONArray array1 = new JSONArray();
			JSONObject item = new JSONObject(), item1 = new JSONObject();
			item.put("lat", 22.344674);
			item.put("lng", 114.124651);
			item1.put("lat", 22.344674);
			item1.put("lng", 114.124351);
			array1.put(item);
			array1.put(item1);// array1.put(item2);
			inputJSon.put("orderAT", "2019-03-03T02:00:00.000Z");
			inputJSon.put("stops", array1);

			//Place Order
			HelperMethods.placeOrder(inputJSon);
			
			// fetch fare
			outputJSon1 = ApiCallHandler.responseJSON.getJSONObject("fare");
			actualFare = outputJSon1.getFloat("amount");
			System.out.println("Night Fare for Advance booking is : " + actualFare);
			if (actualFare != expectedFare) {
				Assert.assertEquals(actualFare, expectedFare, "Fare amount do not match ");
			}
		} catch (ParseException e) { //
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * description : Verifies place advance order functionality and that NIGHT fare is correctly generated if distance is less than 2 kms
	 */
	@Test()
	public void Testcase_placeAdvanceOrder_verifyFareAboveTwoKms_NightCharges() {
		JSONObject inputJSon = new JSONObject(), outputJSon1;
		// fare calculation
		float expectedFare = (float) (30 + 8 * (1224.00 / 200.00));
		float actualFare;
		try {

			// Create test data
			JSONArray array1 = new JSONArray();
			JSONObject item = new JSONObject(), item1 = new JSONObject();
			item.put("lat", 22.344674);
			item.put("lng", 114.124651);
			item1.put("lat", 22.344674);
			item1.put("lng", 114.126651);
			array1.put(item);
			array1.put(item1);
			inputJSon.put("orderAT", "2019-03-03T02:00:00.000Z");
			inputJSon.put("stops", array1);

			//Place Order
			HelperMethods.placeOrder(inputJSon);
			//Fetch fare
			outputJSon1 = ApiCallHandler.responseJSON.getJSONObject("fare");
			actualFare = outputJSon1.getFloat("amount");
			System.out.println("Additional  Night Fare for Advance booking is : " + actualFare);

			if (actualFare != expectedFare) {
				Assert.assertEquals(actualFare, expectedFare, "Fare amount do not match ");
			}
		} catch (ParseException e) { //
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@BeforeMethod
	public void beforeMethod(Method method) {
		System.out.println("Starting test case :" + method.getName() );
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {

		if(result.isSuccess())
			System.out.println(result.getMethod().getMethodName()+  " PASSED.");
		else
			System.out.println(result.getMethod().getMethodName()+  " FAILED");
			
	}



}
