package com.example.servicebusjmsconfig

import org.apache.qpid.jms.JmsConnectionFactory
import org.apache.qpid.jms.policy.JmsDefaultPrefetchPolicy
import org.apache.qpid.jms.policy.JmsPrefetchPolicy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.jms.config.DefaultJmsListenerContainerFactory
import org.springframework.jms.config.JmsListenerContainerFactory
import org.springframework.jms.connection.CachingConnectionFactory
import org.springframework.jms.listener.DefaultMessageListenerContainer
import org.springframework.stereotype.Component
import javax.jms.ConnectionFactory
import javax.jms.Session

@Component
class JmsConfig {

    @Bean
    @Primary
    fun transactedListenerContainerFactory(
            connectionFactory: ConnectionFactory, prefetchPolicy: JmsPrefetchPolicy
    ): JmsListenerContainerFactory<DefaultMessageListenerContainer> {
        // Overwrite the default PrefetchPolicy already set in JmsConnectionFactory from org.apache.qpid.jms
        ((connectionFactory as CachingConnectionFactory).targetConnectionFactory as JmsConnectionFactory)
                .prefetchPolicy = prefetchPolicy

        val listenerContainerFactory = DefaultJmsListenerContainerFactory()
        listenerContainerFactory.setConnectionFactory(connectionFactory)

        // Make the subscription durable / tell we're dealing with a topic
        listenerContainerFactory.setSubscriptionDurable(true)
        // Enable transactions during message handling
        listenerContainerFactory.setSessionTransacted(true)
        // Use CLIENT_ACKNOWLEDGE to postpone the message acknowledgement to after the message listener
        // has successfully returned and allow message redelivery in case of an exception.
        // The default AUTO_ACKNOWLEDGE applies automatic message acknowledgement before listener execution,
        // with no redelivery in case of an exception.
        listenerContainerFactory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE)

        return listenerContainerFactory
    }


    @Bean
    fun prefetchPolicy(): JmsPrefetchPolicy {
        // Adjust the prefetch size to a number of messages that can be processed before
        // the default message lock of 30 seconds is exceeded. The default prefetch size is 1000.
        val preferredPrefetchSize = 100
        val prefetchPolicy = JmsDefaultPrefetchPolicy()
        prefetchPolicy.queuePrefetch = preferredPrefetchSize
        prefetchPolicy.durableTopicPrefetch = preferredPrefetchSize
        prefetchPolicy.topicPrefetch = preferredPrefetchSize
        prefetchPolicy.queueBrowserPrefetch = preferredPrefetchSize

        return prefetchPolicy
    }
}
