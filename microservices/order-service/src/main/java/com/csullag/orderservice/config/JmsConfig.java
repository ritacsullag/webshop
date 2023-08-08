package com.csullag.orderservice.config;

import com.csullag.orderservice.model.ShipmentMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.ConnectionFactory;
import org.apache.activemq.artemis.api.core.TransportConfiguration;
import org.apache.activemq.artemis.core.remoting.impl.netty.NettyAcceptorFactory;
import org.apache.activemq.artemis.core.remoting.impl.netty.NettyConnectorFactory;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class JmsConfig {
    @Bean
    public MessageConverter jacksonJmsMessageConverter(ObjectMapper objectMapper) {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();

        Map<String, Class<?>> typeIdMappings = new HashMap<>();
        typeIdMappings.put("com.csullag.shippingservice.model.ShipmentMessage", ShipmentMessage.class);
        converter.setTypeIdMappings(typeIdMappings);

//      If typeMapping is needed should  be use only the consumer side
        converter.setObjectMapper(objectMapper);
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("JMS_TYPE");
        return converter;
    }

    @Bean
    public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory, DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        ((SingleConnectionFactory) connectionFactory).setClientId("shippingstatus");
        factory.setSubscriptionDurable(true);
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    @Configuration
    public class ArtemisConfig implements ArtemisConfigurationCustomizer {
        @Override
        public void customize(org.apache.activemq.artemis.core.config.Configuration configuration) {
            // Allow Artemis to accept tcp connections (Default port localhost:61616)
            configuration.addConnectorConfiguration("nettyConnector", new TransportConfiguration(NettyConnectorFactory.class.getName()));
            configuration.addAcceptorConfiguration(new TransportConfiguration(NettyAcceptorFactory.class.getName()));
        }
    }
}
