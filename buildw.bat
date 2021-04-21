cd src
javac Initializer.java
FOR /L %%A IN (1, 1, 50) DO (
    java Initializer
)
cd ..
pause
del /s *.class