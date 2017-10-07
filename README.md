[![Build Status](https://travis-ci.org/ravikalla/bdd-springboot-virtualization.svg?branch=master)](https://travis-ci.org/ravikalla/bdd-springboot-virtualization)
[![CircleCI](https://circleci.com/gh/ravikalla/bdd-springboot-virtualization.svg?style=svg)](https://circleci.com/gh/ravikalla/bdd-springboot-virtualization)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/7fb2edfd4a8d4147a5f647bc7feeeffb)](https://www.codacy.com/app/ravikalla/springboot-mongo-fongo?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=ravikalla/springboot-mongo-fongo&amp;utm_campaign=Badge_Grade)
[![Issue Count](https://codeclimate.com/github/ravikalla/bdd-springboot-virtualization/badges/issue_count.svg)](https://codeclimate.com/github/ravikalla/bdd-springboot-virtualization)
[![Issues](https://img.shields.io/github/issues/ravikalla/bdd-springboot-virtualization.svg?style=flat-square)](https://github.com/ravikalla/bdd-springboot-virtualization/issues)
[![License](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

# BDD with virtualization

#### Before starting the application, start MongoDB(Installation reference at the end of this file):
 * sudo service mongod start
 * sudo service mongod restart
 * sudo service mongod stop

### Direct APIs:
#### Reference class:
 - PersonRepository.java

#### Create data:
    cmd> curl -i -X POST -H "Content-Type:application/json" -d "{  \"firstName\" : \"Ravi\",  \"lastName\" : \"Kalla\" }" http://localhost:8010/people

#### Get Data:
    cmd> curl http://localhost:8010/people
    cmd> curl http://localhost:8010/people/search
    cmd> curl http://localhost:8010/people/search/findByLastName?name=Kalla

#### Replace/Update/Delete Data (Use PUT, PATCH, DELETE in the REST calls):
    cmd> curl -X PUT -H "Content-Type:application/json" -d "{ \"firstName\": \"Ravi\", \"lastName\": \"Kalla\" }" http://localhost:8010/people/588982406f35e668d2c10a98
    cmd> curl -X PATCH -H "Content-Type:application/json" -d "{ \"firstName\": \"Ravi Shankar\" }" http://localhost:8010/people/588982406f35e668d2c10a98
    cmd> curl -X DELETE http://localhost:8010/people/588982406f35e668d2c10a98

##### References:
Direct access APIs - https://github.com/spring-guides/gs-accessing-mongodb-data-rest

### Install MongoDB:
 * https://docs.mongodb.com/manual/tutorial/install-mongodb-on-ubuntu/
 * https://docs.mongodb.com/manual/tutorial/install-mongodb-on-windows/
 * https://docs.mongodb.com/manual/tutorial/install-mongodb-on-os-x/

#### In-Code Virtualization Options:

Tool / Technology | Virtualization option | Implemented?
------------ | ------------- | -------------
MongoDB | [Fongo](https://github.com/fakemongo/fongo.git) | Yes
Oracle<br/>SQL Server<br/>MySQL<br/>DB2<br/>Apache Derby<br/>HSQLDB<br/>PostgreSQL | [H2 Database](https://github.com/h2database/h2database.git) | No
REST API | [Hoverfly](https://github.com/SpectoLabs/hoverfly.git), [Hoverfly-Java](https://github.com/SpectoLabs/hoverfly-java.git) | Yes
MessageQueues | [ActiveMQ](http://activemq.apache.org/how-to-unit-test-jms-code.html) | No
