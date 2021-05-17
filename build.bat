cd Servers/Repository
javac Repository.java
java Repository
cd ../..

cd Servers/DepartureAiport
javac DepartureAiport.java
java DepartureAiport
cd ../..

cd Servers/Plane
javac Plane.java
java Plane
cd ../..

cd Servers/ArrivalAirport
javac ArrivalAirport.java
java ArrivalAirport
cd ../..

cd Clients/Hostess
javac Hostess.java
java Hostess
cd ../..

cd Clients/Pilot
javac Pilot.java
java Pilot
cd ../..

cd Clients/Passenger
javac Passenger.java
FOR /L %%A IN (1, 1, 21) DO (
    java Passenger
)
cd ../..

pause
del /s *.class