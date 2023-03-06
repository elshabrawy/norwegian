### Norwegian Airport Senior Java Developer Assessment Documentation
## Introduction

This document provides the documentation for the assessment given by Norwegian Airport for the position Senior Java Developer. The assessment requires the development of a small calculator to calculate the price of customer purchases based on specific price structures. The requirements also include the development of a price engine and a small calculation feature, the creation of two GUIs, and the use of JUnit5 or Spock for testing.

## Price Structures

The following price structures apply to the rare product "Product1" and the product "Product2":

    Product1: 20 units per carton. A carton is 175.
    Product2: 5 units per carton. A carton is 825.

If purchasing single units, the price is acquired using the carton price multiplied by an increase of 30% (1.3) to compensate for manual labor. If purchasing 3 cartons or more, customers will receive a 10% discount off the carton price.

## Technologies Used

The application is built using Java 8, Spring-boot and MYSQL database.

## How to Run the Application

To run the application, follow these steps:

1. Clone the main project using this command: git clone https://github.com/elshabrawy/norwegian.git
2. From the terminal, go to ./norwegian and use Maven to build the project: mvn clean package
3. Run the application using this command: java -jar norwegian/target/norwegian-product.jar. It will run on port 8090 (you can change the port from application.properties).
4. Alternatively, you can use Docker. First, run this command to build the Docker image: docker build -t norwegian-product.jar.jar .
5. Then, run the Docker image using this command: docker run -p 8090:8090 norwegian-product.jar

You do not need to set up MYSQL as the application connects to an online server (sql7.freesqldatabase.com).

To view the APIs documentation, go to http://localhost:8090/swagger-ui/index.html (built using OpenAPI Swagger).

## APIs

1. To List of prices for specific Product, use GET API http://localhost:8090/api/product/{productId}/prices this will get the 50 record divided to pages each page contain 20 records
2. To get order details info, use GET API http://localhost:8090/api/product/{productId}/order-details and send input in json format like:
        {
            "quantity":"25",
            "email":"mohamed@gmail.com"
        }

and the output with be like this:

    - if you the user have orders in database with the same product name and user email we select it and add the number of order items to this items and calculate the whole items and return:
        {
            "totalPrice": 463.75,
            "numberOfCartons": 2,
            "numberOfUnits": 10,
            "productName": "Product1",
            "email": "mohamed@gmail.com"
        }

    - if not we calculate the items count in the request and return:
        {
            "totalPrice": 231.875,
            "numberOfCartons": 1,
            "numberOfUnits": 5,
            "productName": "Product1",
            "email": "mohamed@gmail.com"
        }

## Note
the Sample APIs Requests and Responses for postman attached in the application root source 

## Testing

* added Unit Test for repositories and service classes.
* added Integration Test for the controller Class.