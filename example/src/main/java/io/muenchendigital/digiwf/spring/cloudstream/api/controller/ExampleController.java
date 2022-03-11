package io.muenchendigital.digiwf.spring.cloudstream.api.controller;

import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.GenericPayloadSender;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
public class ExampleController {

    private final GenericPayloadSender genericPayloadSender;

    @PostMapping(value = "/sendMessage")
    public void sendMessage(@RequestBody @NotNull final String payload) {
        this.genericPayloadSender.sendPayload(payload, "processEvent");
    }

}
