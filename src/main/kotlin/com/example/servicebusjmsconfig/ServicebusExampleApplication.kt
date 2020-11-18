package com.example.servicebusjmsconfig

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.jms.annotation.EnableJms
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableJms
@EnableScheduling
class ServicebusExampleApplication
	fun main(args: Array<String>) {
		runApplication<ServicebusExampleApplication>(*args)
	}

