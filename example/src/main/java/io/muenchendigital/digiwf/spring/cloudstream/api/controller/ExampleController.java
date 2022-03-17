package io.muenchendigital.digiwf.spring.cloudstream.api.controller;

import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.PayloadSender;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
public class ExampleController {

    private final PayloadSender genericPayloadSender;

    @PostMapping(value = "/sendMessage")
    public void sendMessage(@RequestBody @NotNull final String payload,
                            @RequestParam(defaultValue = "processMessage") String type) {
        this.genericPayloadSender.sendPayload(payload, type);
    }

}
