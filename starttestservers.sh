#! /bin/sh
CLASSPATH=$CLASSPATH:classes:lib/jakarta-regexp-1.2.jar
# Run this script to start instances of SMPPSim required to support the
# JUnit test suite. Run the tests by invoking runtests from the command line
nohup java -Djava.util.logging.config.file=conf/logging.properties.test1 com/seleniumsoftware/SMPPSim/SMPPSim conf/props.std_test &
nohup java -Djava.util.logging.config.file=conf/logging.properties.test2 com/seleniumsoftware/SMPPSim/SMPPSim conf/props.test1&

nohup java -Djava.util.logging.config.file=conf/logging.properties.test3 com/seleniumsoftware/SMPPSim/SMPPSim conf/props.mo &
