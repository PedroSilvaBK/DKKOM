package dcom.websocketgateway.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Response<T> {
    private String type;
    private T data;
    private Map<String, Object> metadata;
}
