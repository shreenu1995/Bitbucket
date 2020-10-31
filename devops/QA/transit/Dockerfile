From java:8
EXPOSE 9012
RUN mkdir -p /usr/local/resources
RUN mkdir -p /usr/local/transit
#ADD application.properties /usr/local/resources/
ADD transit-web.war /usr/local/transit/transit-web.war
ENTRYPOINT ["java","-jar","/usr/local/transit/transit-web.war"]
