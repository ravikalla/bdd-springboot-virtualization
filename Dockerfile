FROM maven:3.5.0-jdk-8
COPY . /usr/src/app
WORKDIR /usr/src/app
RUN mvn clean install
RUN mvn test