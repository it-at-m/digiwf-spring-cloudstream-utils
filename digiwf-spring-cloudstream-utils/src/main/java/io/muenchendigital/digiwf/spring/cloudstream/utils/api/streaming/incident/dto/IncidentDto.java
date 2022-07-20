package io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.incident.dto;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class IncidentDto {

    @NotNull
    public String processInstanceId;

    @NotNull
    public String messageName;

    @Nullable
    public String errorMessage;

}
