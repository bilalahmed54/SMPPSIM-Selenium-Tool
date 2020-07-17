FROM openjdk:6

WORKDIR /app

COPY conf/logging.properties /app/conf/logging.properties
COPY conf/smppsim.props /app/conf/smppsim.props
COPY dist/lib/jakarta-regexp-1.5.jar /app/lib/jakarta-regexp-1.5.jar
COPY dist/smppsim.jar /app/smppsim.jar

EXPOSE 88 2776 3333

ENTRYPOINT ["java", "-Djava.net.preferIPv4Stack=true", "-Djava.util.logging.config.file=conf/logging.properties", "-jar", "smppsim.jar", "conf/smppsim.props"]
