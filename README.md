# Read Me doc

Code Structure
Following is the code structure in the project :
•	Src/main/java – This folder consists of two classes 
 o	APICallHandler.java – contains methods to handler the POST,PUT and GET request against the endpoint
 o	Helper methods.java – contains  methods to place Order, take Order etc which could be directly used in the test cases. 
•	Src/test/java --  This folder contains Test classes for different API operations and a total of 22 test cases.
•	Testng is the test execution engine used here.

Functionality
•	All the test cases are passing
•	Endpointurl is read from the properties file and is stored in a global variable before execution begins
•	Test cases use helper methods to perform different operations as test steps

Test scenarios covered 
PlaceOrder cases
TakeOrder cases
Complete Order cases
Fetch Order cases
Cancel Order cases
 
Execution 
•	A runnable jar has been created to execute the test cases.
•	On completion test-output folder will be created to view the testing reports for execution
Things not covered so far
•	Exception Handling is there but not to its completeness. This could be improved with more time.
•	Although comments have been added at all the relevant places it might not be in accordance with reviewer’s coding standards.
•	Scenarios for night charges in case of place order are covered for only Advance orders and not for current orders
•	For scenarios of incorrect flow order, tests are written for some of the common cases and not all.


