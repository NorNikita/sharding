package com.example.shard.configuration.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NodeInfo {

    private String nameNode;
    private boolean active;
}
