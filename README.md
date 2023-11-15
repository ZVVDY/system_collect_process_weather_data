Java Spring Weather API Overview

Java Spring Weather API is a system designed for collecting and processing weather data. 
The system comprises two main components:

Server Weather: A RESTful service responsible for filtering and sorting weather data. 
It offers functionalities such as sensor registration, retrieval of information about 
active sensors, storage of received weather measurement data, and the ability to receive 
weather data from one or all sensors. The server sends special messages in case of errors.

SensorWeather: A service dedicated to collecting weather data and transmitting it to the server. 
Upon launch, the sensor registers with the server and sends weather measurement data every 3 to 15 seconds.

Dependencies
The server utilizes a MySQL database, which must be installed for the system to function correctly. 
Other project dependencies are specified in the pom.xml file, and running the project will automatically 
download them.

Running Locally
Clone the project:

bash
Copy code
git clone https://github.com/ZVVDY/system_collect_process_weather_data.git
Before starting the server and sensor, make changes to their application.properties files:

Server application.properties (server_weather/sensor_weather/src/main/resources/application.properties):

Change datasource URL, username, and password.
(Optional) Change the server port (default=8081).
Sensor application.properties (sensor_weather/sensor_weather/src/main/resources/application.properties):

Change the server address.
Change the sensor name.
(Optional) Change the sensor port (default=8080).
Start the Weather Server:

bash
Copy code
mvn -f ./sensor_weather/sensor_weather/pom.xml spring-boot:run
Start the Sensor Weather :

bash
Copy code
mvn -f ./sensor_weather/sensor_weather/pom.xml spring-boot:run
API Reference
The server provides Swagger API documentation. To access it, navigate to:

bash
Copy code
{server.ip}/swagger-ui/index.html

Development Timeline
Planning	Development	 Testing	Documentation
2 hours	    20 hours	 7 hours	    1.5 hours
