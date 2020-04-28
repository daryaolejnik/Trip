#!bin/bash

./SumoCollector.sh -q -varfile /home/sumo_credentials.txt -Vcollector.name=trip

java -jar *.jar
