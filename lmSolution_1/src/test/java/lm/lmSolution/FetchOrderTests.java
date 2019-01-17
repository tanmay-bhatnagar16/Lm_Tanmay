package lm.lmSolution;

import org.testng.annotations.Test;

import lm.lmSolution.ApiCallHandler;
import lm.lmSolution.HelperMethods;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.http.ParseException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;;

public class FetchOrderTests {

	/**
	 * description : Verifies fetch order functionality when valid order Id is
	 * provided as input
	 */
	@Test()
	public void TestCase_Fetch_Order_VerifyStatusCode_ValidID() {

		JSONObject inputJSon, outputJSon = null;
		int id = 0, statusCode = 0;
		try {
			// Create test data
			inputJSon = HelperMethods.getDefaultJSON(false);

			// Place order
			id = HelperMethods.placeOrder(inputJSon);

			// Fetch order
			outputJSon = HelperMethods.fetchOrder(id);
			// Get status code
			statusCode = HelperMethods.getStatusCode();
			System.out.println("Valid Status Code" + statusCode);

			if (outputJSon == null || statusCode != 200) {
				Assert.fail("Request Failed with status code : " + statusCode);
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * description : Verifies fetch order functionality when invalid order Id is
	 * provided as input
	 */
	@Test()
	public void TestCase_Fetch_Order_VerifyStatusCode_InvalidID() {
		JSONObject inputJSon;
		int id = 0, statusCodeExpected = 404, actualStatus;
		try {
			// Create test data
			inputJSon = HelperMethods.getDefaultJSON(false);
			
			// Place order
			id = HelperMethods.placeOrder(inputJSon);
			
			// fetch order with incorrect id
			HelperMethods.fetchOrder((id + 1));
			
			// Get status code
			actualStatus = HelperMethods.getStatusCode();
			
			System.out.println("InValid Status Code" + actualStatus);

			if (actualStatus != statusCodeExpected) {
				Assert.assertEquals(actualStatus, statusCodeExpected, "Status do not match ");
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@BeforeMethod
	public void beforeMethod(Method method) {
		System.out.println("Starting test case :" + method.getName());
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {

		if (result.isSuccess())
			System.out.println(result.getMethod().getMethodName() + " PASSED.");
		else
			System.out.println(result.getMethod().getMethodName() + " FAILED");

	}

}
