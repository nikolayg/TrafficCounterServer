# TrafficCounterServer

**TrafficCounterServer** is a small java application
which accepts events from distributed automated traffic counters.

# Set Up

**TrafficCounterServer** requires Java 8 or later. I recommend you use
[SDK Man](http://sdkman.io/) to install it:

```bash
# On mosts systems this will work:
sdk install java 8.0.222.j9-adpt
```

**TrafficCounterServer** uses [Gradle](https://gradle.org/) 
for build and test automation. You can use the
provided Gradle wrapper to compile, build, and test with a single command:

```bash
./gradlew clean build
```

To execute the application, first we need to create a
runnable jar file, and then execute it:

```bash
# Build runnable jar
./gradlew installDist

# Execute the App
java -jar build/install/TrafficCounterServer/lib/TrafficCounterServer-1.0.SNAPSHOT.jar

# Starts the server at port 8080 and endpoint "trafficData":
curl -i --data "18673541_2016-12-01T05:15:34_M_58" http://localhost:8080/trafficData
```

# Tests

...

The application logic has been covered with JUnit tests. You can run them
with:

```bash
./gradlew test
```
