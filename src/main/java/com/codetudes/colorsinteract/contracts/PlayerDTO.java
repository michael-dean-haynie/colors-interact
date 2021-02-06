package com.codetudes.colorsinteract.contracts;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PlayerDTO {
    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // internal. do not include automatically in responses
    private String secret;
}
