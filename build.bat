javac Servers/Repository/Initializer.java
java Servers/Repository/Initializer

javac Servers/DepartureAirport/Initializer.java
java Servers/DepartureAirport/Initializer

javac Servers/Plane/Initializer.java
java Servers/Plane/Initializer

javac Servers/ArrivalAirport/Initializer.java
java Servers/ArrivalAirport/Initializer

javac Clients/Hostess/Initializer.java
java Clients/Hostess/Initializer

javac Clients/Pilot/Initializer.java
java Clients/Pilot/Initializer

javac Clients/Passenger/Initializer.java
FOR /L %%A IN (1, 1, 21) DO (
    java Clients/Passenger/Initializer
)

pause
del /s *.class