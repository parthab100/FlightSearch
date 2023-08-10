# Project Description

## FLIGHT search

This Flight search api finds flights based on origin and destination.
And it can sort the result on duration and price in the ascending/descending order.




## Parameters:

This API returns a list of flights based on input parameters to  the endpoint /flightFinder/flights

Below are the request parameters:
	* String flightOrigin : Required
	* String flightDestination: Required
	* String priceOrder (ASC/DESC): Optional
	* String durationOrder(ASC/DESC) : Optional


## Prerequisite To Run 

For building and running the application you need:
	* [JDK 11] or higher
	* [Maven 3.x]
	* Spring 3 or Higher

## Procedure to Run :

This spring boot application can be run directly from STS
* Clone this repository
* Must have JDK 11 or higher and Maven 3.x
* Maven -> Update Project 
* Build the project using maven:

						mvn clean install
* Once successfully built, it can be run using Run as Spring boot Application directly from IDE

It can be run from Commandprompt using below command, Provided Maven is properly configured in local 
					mvn spring-boot:run


## END POINTS 

* To see the data in H2 database: http://localhost:8080/h2-console/login.jsp
	user:sa , Pass: sa

* The service is exposed to : http://localhost:8080/flightFinder/flights

* To test the API /flightFinder/flights We need to pass the required flightOrigin, flightDestination and optionally durationOrder or priceOrder. 
* 

Following are the few samples:

* http://localhost:8080/flightFinder/flights
 
{
    "flightOrigin": "BOM",
    "flightDestination": "DEL"
 }

* http://localhost:8080/flightFinder/flights
{
    "flightOrigin": "BOM",
    "flightDestination": "DEL",
    "priceOrder": "DESC"
}
* http://localhost:8080/flightFinder/flights
{
    "flightOrigin": "BOM",
    "flightDestination": "DEL",
    "durationOrder": "ASC"
}
## Test Case Implementation

Test cases for Controler, Service and Repository are in their respective test packages.

## License

None.


