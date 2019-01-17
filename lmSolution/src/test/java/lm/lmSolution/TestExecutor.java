/**
 * 
 */
package lm.lmSolution;

import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;
import org.testng.log4testng.Logger;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

/**
 * @author tanmaybhatnagar
 *
 */
public class TestExecutor {
	private static Logger logger = Logger.getLogger(TestExecutor.class);

	/**
	 * description : Main method to execute the test cases from code
	 * @param args
	 */
	public static void main(String[] args) {
		
		try
		{
		
		XmlSuite suite = new XmlSuite();
		suite.setName("TmpSuite");
		
		XmlTest test = new XmlTest(suite);
		test.setName("TmpTest");
		List<XmlClass> classes = new ArrayList<XmlClass>();
		classes.add(new XmlClass("lm.lmSolution.PlaceOrderTests"));
		classes.add(new XmlClass("lm.lmSolution.FetchOrderTests"));
		classes.add(new XmlClass("lm.lmSolution.DriverTakeOrderTests"));
		classes.add(new XmlClass("lm.lmSolution.DriverCompleteOrderTests"));
		classes.add(new XmlClass("lm.lmSolution.CancelOrderTests"));
		test.setXmlClasses(classes) ;

		
		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		suites.add(suite);
		TestNG tng = new TestNG();
		tng.setXmlSuites(suites);
		tng.run();
		}
		catch(Exception e)
		{
			logger.error("Exception in main :" + e.getMessage());
		}

	
	}

}
