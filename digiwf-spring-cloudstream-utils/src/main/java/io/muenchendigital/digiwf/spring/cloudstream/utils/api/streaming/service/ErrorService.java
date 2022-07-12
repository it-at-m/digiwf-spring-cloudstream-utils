package io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.service;

import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.dto.ErrorDto;
import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.infrastructure.StreamingHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

import static io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.infrastructure.StreamingHeaders.DIGIWF_PROCESS_INSTANCE_ID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ErrorService {

    private static final String MESSAGE_TYPE = "error";

    private final Sinks.Many<Message<ErrorDto>> errorSink;

    /**
     * TODO: Sends an error to the bus that can be correlated by process instance ID.
     *
     * @param messageHeaders   The incoming messages' header.
     * @param errorCode
     * @param errorMessage
     */
    public boolean sendError(final MessageHeaders messageHeaders, final String errorCode, final String errorMessage) {
        final ErrorDto.ErrorDtoBuilder builder = ErrorDto.builder()
                .processInstanceId((String) messageHeaders.get(DIGIWF_PROCESS_INSTANCE_ID))
                .errorCode(errorCode)
                .errorMessage(errorMessage);


        final Message<ErrorDto> message = MessageBuilder
                .withPayload(builder.build())
                .setHeader(StreamingHeaders.TYPE, MESSAGE_TYPE)
                .build();

        final Sinks.EmitResult emitResult = this.errorSink.tryEmitNext(message);

        if (emitResult.isSuccess()) {
            log.info("The error {} was successfully delivered to the eventbus.", message.getHeaders().get(MessageHeaders.ID));
        } else {
            log.error("The error {} couldn't be delivered to the eventbus.", message.getHeaders().get(MessageHeaders.ID));
        }
        log.debug("Message: {}", message);
        return emitResult.isSuccess();
    }

}