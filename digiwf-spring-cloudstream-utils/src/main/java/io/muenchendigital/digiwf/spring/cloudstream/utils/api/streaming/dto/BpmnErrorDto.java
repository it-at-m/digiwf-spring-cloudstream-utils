package io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.dto;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BpmnErrorDto {

    @NotNull
    public String processInstanceId;

    @NotNull
    public String errorCode;

    @Nullable
    public String errorMessage;

}
