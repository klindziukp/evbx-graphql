package com.evbx.graphql.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@NoArgsConstructor
public class ItemData<T> {

    @JsonProperty("items")
    private List<T> items;
    @JsonProperty("total")
    private long total;

    public ItemData(List<T> items){
        this.items = Optional.ofNullable(items).orElse(Collections.emptyList());
        this.total = this.items.size();
    }
}