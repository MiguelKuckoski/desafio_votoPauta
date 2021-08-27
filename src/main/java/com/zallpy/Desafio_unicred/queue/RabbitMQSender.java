package com.zallpy.Desafio_unicred.queue;

import com.zallpy.Desafio_unicred.holder.PautaHolder;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Value("${config.rabbitmq.exchange}")
    private String exchange;

    @Value("${config.rabbitmq.routingkey}")
    private String routingkey;

    public void send(PautaHolder pauta) {
        rabbitTemplate.convertAndSend(exchange, routingkey, pauta);

    }
}
