FROM adoptopenjdk:11-jdk-hotspot
ENV TZ="Asia/Ho_Chi_Minh"
EXPOSE 9012
ARG JAR_FILE=build/libs/gaumat.jar
#ARG JAR_FILE=/gaumat.jar
COPY ${JAR_FILE} gaumat.jar
# ADD ${JAR_FILE} gaumat.jar
VOLUME /logs
ENTRYPOINT ["java", "-jar", "/gaumat.jar"]