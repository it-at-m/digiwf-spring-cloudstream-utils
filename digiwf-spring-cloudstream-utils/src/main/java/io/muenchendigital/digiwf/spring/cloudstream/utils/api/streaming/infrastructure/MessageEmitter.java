package io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.infrastructure;

import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.dto.CorrelateMessageDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Supplier;

@Configuration
public class MessageEmitter {

    @Bean
    public Sinks.Many<Message<Object>> sendMessageSink() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    public Supplier<Flux<Message<Object>>> sendMessage(final Sinks.Many<Message<Object>> sink) {
        return sink::asFlux;
    }

    @Bean
    public Sinks.Many<Message<CorrelateMessageDto>> sendCorrelateMessageSink() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    public Supplier<Flux<Message<CorrelateMessageDto>>> sendCorrelateMessage(final Sinks.Many<Message<CorrelateMessageDto>> sink) {
        return sink::asFlux;
    }

}
