cd Servers/Repository
javac Initializer.java
java Initializer
cd ../..

cd Servers/DepartureAiport
javac Initializer.java
java Initializer
cd ../..

cd Servers/Plane
javac Initializer.java
java Initializer
cd ../..

cd Servers/ArrivalAirport
javac Initializer.java
java Initializer
cd ../..

cd Clients/Hostess
javac Initializer.java
java Initializer
cd ../..

cd Clients/Pilot
javac Initializer.java
java Initializer
cd ../..

cd Clients/Passenger
javac Initializer.java
FOR /L %%A IN (1, 1, 21) DO (
    java Initializer
)
cd ../..

pause
del /s *.class