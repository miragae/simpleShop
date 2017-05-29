# SimpleShop
Simple web application using JEE 7, JSF, Primefaces, EJB, JPA, Maven setup on WildFly server and MariaDB.
Allows customers to order products pre-defined in database.


## To successfully deploy simpleShop application you need to:

1. Download Wildfly version 10.1.0 final
	* extract Wildfly
	* add user using bin/add-user.bat
	* start Wildfly using bin/standalone.bat

2. Download MariaDB 10.2.6 GA
	* run provided script.sql

2. Download MariaDB driver (mariadb-java-client-2.0.1.jar)
	* go to localhost:9990
 	* deploy it using Wildfly

3. create datasource in Wildfly
	* go to localhost:9990
	* create Non-XA Custom Datasource with JNDI Name "java:/simpleShop/shopDS"
	* select mariadb driver
	* enter connection properties (jdbc:mysql://localhost:3306/shopdb)

4. Deploy application using maven
	* go to location of src folder
	* run command "mvn wildfly:deploy"

5. Go to localhost:8080/SimpleShop-1.0/
