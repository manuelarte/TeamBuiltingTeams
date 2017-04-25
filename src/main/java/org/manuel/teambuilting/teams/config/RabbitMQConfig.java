package org.manuel.teambuilting.teams.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Manuel Doncel Martos
 * @since 07/12/2016.
 */
@Configuration
public class RabbitMQConfig  {

    private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd\'T\'HH:mm:ss.SSSZ";

	private final String exchange;
	private final boolean durable;
	private final boolean autodelete;
	private final AmqpAdmin rabbitAdmin;

	public RabbitMQConfig(final @Value("${messaging.amqp.team.exchange.name}") String exchange, final @Value("${messaging.amqp.team.exchange.durable}") boolean durable,
		final @Value("${messaging.amqp.team.exchange.autodelete}") boolean autodelete, final AmqpAdmin rabbitAdmin) {
    	this.exchange = exchange;
    	this.durable = durable;
    	this.autodelete = autodelete;
		this.rabbitAdmin = rabbitAdmin;

	}

    @PostConstruct
    private void declareExchange() {
        rabbitAdmin.declareExchange(new TopicExchange(exchange, durable, autodelete));
    }

    @Bean(name = "eventMessageConverter")
    public MessageConverter messageConverter() {
        final Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        // Jackson deserialization point issue
        final ObjectMapper jsonObjectMapper = new ObjectMapper();
        jsonObjectMapper.configure(DeserializationFeature.ACCEPT_FLOAT_AS_INT, false);
        jsonObjectMapper.setDateFormat(new SimpleDateFormat(TIMESTAMP_FORMAT));
        converter.setJsonObjectMapper(jsonObjectMapper);
        return converter;
    }

}