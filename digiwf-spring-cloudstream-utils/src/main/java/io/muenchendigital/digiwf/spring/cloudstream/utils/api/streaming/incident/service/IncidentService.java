package io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.incident.service;

import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.incident.dto.IncidentDto;
import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.infrastructure.StreamingHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

import static io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.infrastructure.StreamingHeaders.DIGIWF_MESSAGE_NAME;
import static io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.infrastructure.StreamingHeaders.DIGIWF_PROCESS_INSTANCE_ID;

@Slf4j
@Service
@RequiredArgsConstructor
public class IncidentService {

    private static final String MESSAGE_TYPE = "incident";

    private final Sinks.Many<Message<IncidentDto>> incidentSink;

    /**
     * Sends an incident to the bus that will be forwarded to the process instance.
     *
     * @param messageHeaders   The incoming messages' header.
     * @param errorMessage the specific error message
     */
    public boolean sendIncident(final MessageHeaders messageHeaders, final String errorMessage) {
        final IncidentDto.IncidentDtoBuilder builder = IncidentDto.builder()
                .processInstanceId((String) messageHeaders.get(DIGIWF_PROCESS_INSTANCE_ID))
                .messageName((String) messageHeaders.get(DIGIWF_MESSAGE_NAME))
                .errorMessage(errorMessage);


        final Message<IncidentDto> message = MessageBuilder
                .withPayload(builder.build())
                .setHeader(StreamingHeaders.TYPE, MESSAGE_TYPE)
                .build();

        final Sinks.EmitResult emitResult = this.incidentSink.tryEmitNext(message);

        if (emitResult.isSuccess()) {
            log.info("The incident {} was successfully delivered to the eventbus.", message.getHeaders().get(MessageHeaders.ID));
        } else {
            log.error("The incident {} couldn't be delivered to the eventbus.", message.getHeaders().get(MessageHeaders.ID));
        }
        log.debug("Message: {}", message);
        return emitResult.isSuccess();
    }

}