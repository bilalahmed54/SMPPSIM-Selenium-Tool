rem $Header: /var/cvsroot/SMPPSim2/startsmppsim.bat,v 1.3 2003/06/11 07:36:36 martin Exp $
rem 
rem Run this script to start instances of SMPPSim required to support the
rem JUnit test suite. Run the tests by invoking runtests from the command line
rem 
start java -Djava.net.preferIPv4Stack=true -Djava.util.logging.config.file=conf\logging.properties.test1 -jar smppsim.jar conf\props.std_test
start java -Djava.net.preferIPv4Stack=true -Djava.util.logging.config.file=conf\logging.properties.test2 -jar smppsim.jar conf\props.test1
start java -Djava.net.preferIPv4Stack=true -Djava.util.logging.config.file=conf\logging.properties.test3 -jar smppsim.jar conf\props.mo
