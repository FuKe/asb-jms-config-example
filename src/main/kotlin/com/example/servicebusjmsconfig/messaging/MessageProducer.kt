package com.example.servicebusjmsconfig.messaging

import org.springframework.jms.core.JmsTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class MessageProducer(private val jmsTemplate: JmsTemplate) {

    @Scheduled(fixedRate = 2500)
    fun sendMessage() {
        jmsTemplate.convertAndSend("message-topic", "Hello World")
    }
}
