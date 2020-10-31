From java:8 AS build
EXPOSE 9012
RUN mkdir -p /usr/local/resources
RUN mkdir -p /usr/local/transit
ADD transit-web.war /usr/local/transit/

FROM build
ENTRYPOINT ["java","-jar","/usr/local/transit/transit-web.war"]
