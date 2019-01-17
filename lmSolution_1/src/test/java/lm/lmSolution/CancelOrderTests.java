package lm.lmSolution;

import org.testng.annotations.Test;

import lm.lmSolution.HelperMethods;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeClass;

import java.lang.reflect.Method;

import org.apache.http.ParseException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class CancelOrderTests {

	/**
	 * description : Verifies Cancel order functionality when valid order Id is
	 * provided as input. Flow : ASSINGING to Cancelled
	 */
	@Test()
	public void TestCase_Cancel_Order_VerifyStatusCode_ValidID_AfterPlaceOrder() {

		JSONObject inputJSon, outputJSon = null;
		int id = 0, statusCode = 0;
		try {
			// Create test data
			inputJSon = HelperMethods.getDefaultJSON(false);
			
			//Place Order
			id = HelperMethods.placeOrder(inputJSon);

			//Cancel Order
			outputJSon = HelperMethods.cancelOrder(id);
			
			// Get status code
			statusCode = HelperMethods.getStatusCode();
			System.out.println("Valid Status Code for Cancel Order" + statusCode);

			if (outputJSon == null || statusCode != 200) {
				Assert.fail("Request Failed with status code : " + statusCode);
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * description : Verifies cancel order functionality when valid order Id is
	 * provided as input. Flow : ONGOING to Cancelled
	 */
	@Test()
	public void TestCase_Cancel_Order_VerifyStatusCode_ValidID_AfterTakeOrder() {

		JSONObject inputJSon, outputJSon = null;
		int id = 0, statusCode = 0;
		try {
			// Create test data
			inputJSon = HelperMethods.getDefaultJSON(false);
			
			//Place Order
			id = HelperMethods.placeOrder(inputJSon);
			
			//Take Order
			HelperMethods.takeOrder(id);

			//Cancel Order
			outputJSon = HelperMethods.cancelOrder(id);
			
			// Get status code
			statusCode = HelperMethods.getStatusCode();
			System.out.println("Valid Status Code for Cancel Order" + statusCode);

			if (outputJSon == null || statusCode != 200) {
				Assert.fail("Request Failed with status code : " + statusCode);
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * description : Verifies cancel order functionality when invalid order Id is
	 * provided as input
	 */
	@Test()
	public void TestCase_Cancel_Order_VerifyStatusCode_InvalidID() {
		JSONObject inputJSon;
		int id = 0, statusCodeExpected = 404, actualStatus;
		try {
			// Create test data
			inputJSon = HelperMethods.getDefaultJSON(false);

			//Place Order
			id = HelperMethods.placeOrder(inputJSon);
			
			//Cancel Order for invalid id
			HelperMethods.cancelOrder((id + 1));
			
			// Get status code
			actualStatus = HelperMethods.getStatusCode();
			System.out.println("InValid Status Code for Cancel Order" + actualStatus);

			if (actualStatus != statusCodeExpected) {
				Assert.assertEquals(actualStatus, statusCodeExpected, "Status do not match ");
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * description : Verifies cancel order functionality when invalid flow
	 * is followed : from Completed to Cancel
	 */
	@Test()
	public void TestCase_cancel_Order_VerifyStatusCode_InvalidFlow() {
		JSONObject inputJSon;
		int id = 0, statusCodeExpected = 422, actualStatus;
		try {
			// Create test data
			inputJSon = HelperMethods.getDefaultJSON(false);

			//Place Order
			id = HelperMethods.placeOrder(inputJSon);
			
			//Take Order
			HelperMethods.takeOrder(id);
			
			//Complete Order
			HelperMethods.completeOrder(id);

			//Cancel Order
			HelperMethods.cancelOrder(id);
			
			actualStatus = HelperMethods.getStatusCode();
			System.out.println("InValid Status Code for Incorrect flow of Cancel Order" + actualStatus);

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
