From java:8
EXPOSE 9095
RUN mkdir -p /usr/local/resources
RUN mkdir -p /home/afcs
ADD masters.zip /home/afcs
RUN mkdir -p /usr/local/afcs
ADD afcs-server-0.0.1.jar /usr/local/afcs/afcs-server-0.0.1.jar
ADD application.properties /usr/local/resources
ENTRYPOINT ["java","-jar","/usr/local/afcs/afcs-server-0.0.1.jar", "--spring.config.location=/usr/local/resources/application.properties"]
