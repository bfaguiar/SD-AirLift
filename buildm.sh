cd src
javac Initializer.java
for i in {0..100}
do
    echo " ** SIM $i **"
    java Initializer
done
cd ..
find . -name '.class' -delete