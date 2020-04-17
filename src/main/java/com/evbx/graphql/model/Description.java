package com.evbx.graphql.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Description {

    @JsonProperty("modelId")
    private Long modelId;

    @JsonProperty("descriptionLine")
    private String descriptionLine;
}
