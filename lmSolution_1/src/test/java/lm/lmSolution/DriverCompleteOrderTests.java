package lm.lmSolution;

import org.testng.annotations.Test;

import lm.lmSolution.HelperMethods;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.lang.reflect.Method;

import org.apache.http.ParseException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class DriverCompleteOrderTests {

	/**
	 * description : Verifies Complete order functionality when valid order Id is
	 * provided as input
	 */
	@Test()
	public void TestCase_Complete_Order_VerifyStatusCode_ValidID() {

		JSONObject inputJSon, outputJSon = null;
		int id = 0, statusCode = 0;
		try {
			// Create test data
			inputJSon = HelperMethods.getDefaultJSON(false);
			
			//Place Order
			id = HelperMethods.placeOrder(inputJSon);
			//Take Order
			HelperMethods.takeOrder(id);

			//Complete Order
			outputJSon = HelperMethods.completeOrder(id);
			
			//Get Status Code
			statusCode = HelperMethods.getStatusCode();
			System.out.println("Valid Status Code for Complete Order" + statusCode);

			if (outputJSon == null || statusCode != 200) {
				Assert.fail("Request Failed with status code : " + statusCode);
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * description : Verifies Complete order functionality when invalid order Id is
	 * provided as input
	 */
	@Test()
	public void TestCase_Complete_Order_VerifyStatusCode_InvalidID() {
		JSONObject inputJSon;
		int id = 0, statusCodeExpected = 404, actualStatus;
		try {
			// Create test data
			inputJSon = HelperMethods.getDefaultJSON(false);

			//Place Order
			id = HelperMethods.placeOrder(inputJSon);
			
			//take Order
			HelperMethods.takeOrder(id);

			//Complete Order for incorrect id
			HelperMethods.completeOrder((id + 1));
			
			//Get status
			actualStatus = HelperMethods.getStatusCode();
			System.out.println("InValid Status Code for Complete Order" + actualStatus);

			if (actualStatus != statusCodeExpected) {
				Assert.assertEquals(actualStatus, statusCodeExpected, "Status do not match ");
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * description : Verifies complete order functionality when logic flow is not followed :
	 * Cancelled to Completed
	 */
	@Test()
	public void TestCase_Complete_Order_VerifyStatusCode_InvalidFlow() {
		JSONObject inputJSon;
		int id = 0, statusCodeExpected = 422, actualStatus;
		try {
			// Create test data
			inputJSon = HelperMethods.getDefaultJSON(false);

			//Place Order
			id = HelperMethods.placeOrder(inputJSon);
			
			//Cancel Order
			HelperMethods.cancelOrder(id);

			//Complete Order
			HelperMethods.completeOrder(id);
			
			//Get status
			actualStatus = HelperMethods.getStatusCode();
			System.out.println("InValid Status Code for Incorrect flow of Complete Order" + actualStatus);

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
