package lm.lmSolution;

import org.testng.annotations.Test;

import lm.lmSolution.HelperMethods;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import java.lang.reflect.Method;
import org.apache.log4j.Logger;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.ITestResult;

public class DriverCompleteOrderTests {
	private static Logger logger = Logger.getLogger("LOG");
	private static String field_status = "status";
	private static String field_message = "message";
	private static String field_id = "id";

	/**
	 * description : Verifies Complete order functionality when valid order Id is
	 * provided as input
	 */
	@Test()
	public void TestCase_Complete_Order_VerifyStatusCode_ValidID() {

		JSONObject inputJSon;
		int id = 0, statusCode = 0;
		try {
			// Create test data
			inputJSon = HelperMethods.getDefaultJSON(false);

			// Place Order
			id = HelperMethods.placeOrder(inputJSon);
			Assert.assertTrue(id != 0, "ID Not Generated ");
			// Take Order
			HelperMethods.takeOrder(id);

			Assert.assertTrue(ApiCallHandler.getResponseJson().has("id"), "Take Order failed");

			HelperMethods.completeOrder(id);

			// Get Status Code
			statusCode = ApiCallHandler.getstatusCode();
			logger.info("Valid Status Code for Complete Order" + statusCode);

			Assert.assertEquals(statusCode, HttpStatus.SC_OK, "Status do not match ");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			Assert.fail(e.getMessage());
		}

	}

	/**
	 * description : Verifies Complete order functionality when invalid order Id is
	 * provided as input
	 */
	@Test()
	public void TestCase_Complete_Order_VerifyStatusCode_InvalidID() {
		JSONObject inputJSon;
		int id = 0, statusCodeExpected = HttpStatus.SC_NOT_FOUND, actualStatus;
		try {
			// Create test data
			inputJSon = HelperMethods.getDefaultJSON(false);

			// Place Order
			id = HelperMethods.placeOrder(inputJSon);
			Assert.assertTrue(id != 0, "ID Not Generated ");
			// Take Order
			HelperMethods.takeOrder(id);
			Assert.assertTrue(ApiCallHandler.getResponseJson().has("id"), "Take Order failed");

			// Complete Order for incorrect id
			HelperMethods.completeOrder((id + 1));

			// Get status
			actualStatus = ApiCallHandler.getstatusCode();
			logger.info("InValid Status Code for Complete Order" + actualStatus);

			Assert.assertEquals(actualStatus, statusCodeExpected, "Status do not match ");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			Assert.fail(e.getMessage());
		}

	}

	/**
	 * description : Verifies complete order functionality when logic flow is not
	 * followed : Cancelled to Completed
	 */
	@Test()
	public void TestCase_Complete_Order_VerifyStatusCode_InvalidFlow() {
		JSONObject inputJSon;
		int id = 0, statusCodeExpected = HttpStatus.SC_UNPROCESSABLE_ENTITY, actualStatus;
		try {
			// Create test data
			inputJSon = HelperMethods.getDefaultJSON(false);

			// Place Order
			id = HelperMethods.placeOrder(inputJSon);
			Assert.assertTrue(id != 0, "ID Not Generated ");

			// Cancel Order
			HelperMethods.cancelOrder(id);
			Assert.assertTrue(ApiCallHandler.getResponseJson().has("id"), "Cancel Order failed");

			// Complete Order
			HelperMethods.completeOrder(id);

			// Get status
			actualStatus = ApiCallHandler.getstatusCode();
			logger.info("InValid Status Code for Incorrect flow of Complete Order" + actualStatus);

			Assert.assertEquals(actualStatus, statusCodeExpected, "Status do not match ");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			Assert.fail(e.getMessage());
		}

	}

	/**
	 * description : Verifies Order status for Complete order functionality when
	 * valid order Id is provided as input. Flow : ONGOING to Complete
	 */
	@Test()
	public void TestCase_Complete_Order_VerifyOrderStatus_ValidID() {

		JSONObject inputJSon, outputJson;
		int id = 0;
		String actualStatus, expectedStatus;
		try {
			// Create test data
			inputJSon = HelperMethods.getDefaultJSON(false);
			expectedStatus = HelperMethods.getExcelData("TestData", "Status_Completed");

			// Place Order
			id = HelperMethods.placeOrder(inputJSon);
			Assert.assertTrue(id != 0, "ID Not Generated ");
			// Take OrderF
			HelperMethods.takeOrder(id);
			Assert.assertTrue(ApiCallHandler.getResponseJson().has(field_id), "Take Order failed");

			// Complete Order and check status
			outputJson = HelperMethods.completeOrder(id);
			actualStatus = outputJson.getString(field_status);

			logger.info("Valid Status " + actualStatus);
			Assert.assertEquals(actualStatus, expectedStatus, "Status do not match ");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			Assert.fail(e.getMessage());
		}

	}

	/**
	 * description : Negative scenario : Verifies error message for Complete order
	 * functionality when valid order Id is provided as input. Invalid Flow :
	 * ASSIGNING to Complete
	 */
	@Test()
	public void TestCase_Complete_Order_VerifyOrderStatus_InvalidFlow_AssignedToComplete() {

		JSONObject inputJSon, outputJson;
		int id = 0;
		String actualStatus, expectedStatus;
		try {
			// Create test data
			inputJSon = HelperMethods.getDefaultJSON(false);
			expectedStatus = HelperMethods.getExcelData("ErrorMessages", "ErrorMessage_OrderNotOnGoing");

			// Place Order
			id = HelperMethods.placeOrder(inputJSon);
			Assert.assertTrue(id != 0, "ID Not Generated ");

			// Complete Order and check status
			outputJson = HelperMethods.completeOrder(id);
			actualStatus = outputJson.getString(field_message);

			logger.info("Error Message " + actualStatus);

			Assert.assertEquals(actualStatus, expectedStatus, "Error message mismatch ");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			Assert.fail(e.getMessage());
		}

	}

	/**
	 * description : Verifies Error Message for Complete order functionality when
	 * invalid order Id is provided as input
	 */
	@Test()
	public void TestCase_Complete_Order_VerifyErrorMessage_InvalidID() {
		JSONObject inputJSon;
		int id = 0;
		String actualStatus, expectedStatus;
		try {
			// Create test data
			inputJSon = HelperMethods.getDefaultJSON(false);
			expectedStatus = HelperMethods.getExcelData("ErrorMessages", "ErrorMessage_OrderNotFound");
			// Place order
			id = HelperMethods.placeOrder(inputJSon);
			Assert.assertTrue(id != 0, "ID Not Generated ");
			
			// Take Order
			HelperMethods.takeOrder(id);
			Assert.assertTrue(ApiCallHandler.getResponseJson().has("id"), "Take Order failed");

			// Complete Order for incorrect id
			HelperMethods.completeOrder((id + 1));

			// Get status message
			actualStatus = ApiCallHandler.getResponseJson().getString(field_message);

			logger.info("Error Message" + actualStatus);
			Assert.assertEquals(actualStatus, expectedStatus, "Error Message do not match ");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			Assert.fail(e.getMessage());
		}

	}

	@BeforeMethod
	public void beforeMethod(Method method) {
		logger.info("Starting test case :" + method.getName());
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {

		if (result.isSuccess())
			logger.info(result.getMethod().getMethodName() + " PASSED.");
		else
			logger.info(result.getMethod().getMethodName() + " FAILED");

	}
}
