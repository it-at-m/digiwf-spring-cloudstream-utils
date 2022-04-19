package io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CorrelateMessageDto {

    @Nullable
    public String processInstanceId;

    @NotNull
    public String messageName;

    @Nullable
    public String businessKey;

    @Nullable
    public Map<String, Object> correlationVariables;

    @Nullable
    public Map<String, Object> correlationVariablesLocal;

    @Nullable
    public Map<String, Object> payloadVariables;

    @Nullable
    public Map<String, Object> payloadVariablesLocal;

}
