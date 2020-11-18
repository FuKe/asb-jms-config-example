# Azure Service Bus JMS Config Example
This project is showcasing a production ready JMS/AMQP topic producer/consumer configuration for Azure Service Bus,
using the `com.microsoft.azure:azure-servicebus-jms-spring-boot-starter` package. 

## Prerequisites
Before launching the application, make sure you've created the following on Azure:
* A Service Bus on the `Standard` pricing tier, as the `Basic` pricing tier doesn't include topics)
* A topic named `message-topic`, on the service bus you've just created.
* A subscription on the topic named `consumer-service`
* A Shared Access Policy on the topic named `topic-consumer-service` with `Listen` and `Send` claims 
(write down the primary connection string)

## Getting Started
The configuration of this example project depends on an environment variable named `ASB_CONNECTION_STRING` 
with the value of the primary connection string from the shared access policy 
you've created in the **Prerequisites** section.

Alternatively, you can set the connection string value directly on the `spring.jms.servicebus.connectionstring`
configuration attribute in `src/main/resources/application.yml`

### Reference Documentation
For further reference, please consider the following sections:

* [Service Bus JMS Spring Boot Starter Documentation](https://docs.microsoft.com/en-us/java/api/overview/azure/servicebus-jms-spring-boot-starter-readme?view=azure-java-stable)
* [Service Bus JMS Spring Boot Starter Github](https://github.com/Azure/azure-sdk-for-java/tree/master/sdk/spring/azure-spring-boot-starter-servicebus-jms)

