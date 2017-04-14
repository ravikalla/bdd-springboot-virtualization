[![Build Status](https://travis-ci.org/ravikalla/springboot-mongo-fongo.svg?branch=master)](https://travis-ci.org/ravikalla/springboot-mongo-fongo)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/7fb2edfd4a8d4147a5f647bc7feeeffb)](https://www.codacy.com/app/ravikalla/springboot-mongo-fongo?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=ravikalla/springboot-mongo-fongo&amp;utm_campaign=Badge_Grade)
[![License](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

# SpringBoot with MongoDB and Fongo for integration testing
WIP - REST API virtualization 

#### Before starting the application, start MongoDB:
 * sudo service mongod start
 * sudo service mongod restart
 * sudo service mongod stop

#### Install MongoDB :
 * https://docs.mongodb.com/manual/tutorial/install-mongodb-on-ubuntu/

#### In-Code Virtualization Options:

Tool / Technology | Virtualization option
------------ | -------------
MongoDB | [Fongo](https://github.com/fakemongo/fongo.git)
Oracle<br/>SQL Server<br/>MySQL<br/>DB2<br/>Apache Derby<br/>HSQLDB<br/>PostgreSQL | [H2 Database](https://github.com/h2database/h2database.git)
REST API | [Hoverfly](https://github.com/SpectoLabs/hoverfly.git), [Hoverfly-Java](https://github.com/SpectoLabs/hoverfly-java.git)
MessageQueues | [ActiveMQ](http://activemq.apache.org/how-to-unit-test-jms-code.html), RabbitMQ
