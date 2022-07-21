package com.example.shard.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Node {

    private final String name;
    private final boolean isActive;
}
