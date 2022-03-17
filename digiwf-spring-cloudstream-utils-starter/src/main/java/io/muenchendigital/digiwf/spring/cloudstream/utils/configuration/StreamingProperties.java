package io.muenchendigital.digiwf.spring.cloudstream.utils.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "io.muenchendigital.digiwf.streaming")
public class StreamingProperties {

    @NotNull
    private Map<String, @NotBlank String> typeMappings;

}