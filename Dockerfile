
FROM java:8
VOLUME /tmp
ADD target/refillCard-0.0.1-SNAPSHOT.jar refill.jar
ENTRYPOINT ["java","-jar","/refill.jar"]