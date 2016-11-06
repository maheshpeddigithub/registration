This project is implemented using Java8 (JDK1.8), Maven, Spring-Boot, Intellij, GIT
It is implemented based on Microservices architecture
Main class to launch registration REST service is com.myorg.registration.RegistrationMain

Commond to compile, package and unit tests execution:
% mvn clean package

Command to launch registration REST service:
% java -jar target/registration.jar

- After above command execution REST service is available at http://localhost:8080/register
- Make sure port 8080 is free before executing above commands otherwise execution will fail
- Jetty plugin is used to launch in-build webserver of spring-boot

Possible improvements:
- If time permits I would like to implement BDD (Cucumber/JBehave) scenarios for above REST service
- I am having good knowledge in BDD implementations, I would like to discuss more on BDD in face to face interview
- I would like to implement all the remaining methods (PUT & DELETE) but the main focus is on POST hence dropped it for now
- User already registered status is determined based on user name, it can be further extended and can decide based on DOB & SSN
- Logging can be further extended
- Comments can be added at method level and at critical points for better/futur maintenance

- I have tested registration REST (http://localhost:8080/register) functionality using POSTMAN app of Chrome Web Store
- Below is the sample payload I have used in testing above REST service POST call:
  {
    "userName": "jsmith1",
    "password": "Password1",
    "dob": "1985-11-11",
    "ssn": "123-45-6789"
  }