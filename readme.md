# Steps to test and deploy the application:
```java
1. Clone the project into your local machine.
2. Run the CustomerRewardsApplication.
3. The server is up and running on port 8008.
4. Login into h2 console:
   a. http://localhost:8008/h2
   b. Driver Class: org.h2.Driver
   c. JDBC URL: jdbc:h2:~/rewardsdb
   d. User Name: sa
   e. Password:
5. Tables get created in the db by springboot.
6. Run the queries listed in the location:
   src/main/resources/queries.txt
   Commit your insert queries or press save button in the h2 console.
7. Import the postman collection located at: 
   src/test/resources/Rewards_Api.postman_collection.json
   into your local machine.
8. Run and test using the postman test suite.
9. When you clean install the project make sure all the tests pass in the build.

Test coverage:
Controller: 100%
Service: 100%
Domain: 100%
OverAll: 92% classes and 70% code coverage.