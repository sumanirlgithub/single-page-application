# Getting Started

Building REST services with Spring
https://spring.io/guides/tutorials/rest/


employeeservice API - Spring Boot REST Spring Data JPA Maven Project - Java 8

# Build and Run
mvn clean install -DskipTests
mvn clean install
mvn test
mvn test -Dtest=EmployeeRepositoryTest
mvn test -Dspring.profiles.active=local -Dtest=EmployeeRepositoryTest


java -jar target/employeeservice-0.0.1-SNAPSHOT.jar
#Run the application with passing a VM Option e.g. ENV=LOCAL
java -Xms256m -Xmx512m -DENV=LOCAL -jar target/employeeservice-0.0.1-SNAPSHOT.jar

Please also see Build_Run_IntelliJ_EmployeeService.docx for how to build and run employeeservice api and execute Junit tests 

# Set an active spring profile
You can set an active spring profile in multiple ways:
	- Using src\main\resources\application.yml or src\test\resources\application.yml as below
	spring:
		profiles:
			active: dev
	- Using -Dspring.profiles.active=prod in VM Arguments
	- Using @ActiveProfile in Tests @ActiveProfiles("dev")
		@ActiveProfiles("dev")
		@SpringBootTest
		public class EmployeeServiceApplicationTests {
			@Test
			void contextLoads() {
			}
		}

# Database Configuration and Connection

# Database Connection: MySQL
Go to Windows Services, find MySQL instance and start it
Create a Database connection using using MySQL workbench
 -	connection method TCP/IP
	hostname 127.0.0.1
	port 3306
	username root
	password root
	Default Schema sumandb

# Junit 5 with Spring boot 2 - Junit Test class  - EmployeeRepositoryTest, EmployeeServiceTest
Used @SpringBootTest, @Autowired and @MockBean annotations
Ref: https://howtodoinjava.com/spring-boot2/testing/junit5-with-spring-boot2/


