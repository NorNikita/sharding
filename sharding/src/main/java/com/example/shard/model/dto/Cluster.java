package com.example.shard.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class Cluster {

    List<NodeInfo> nodes;

    @Getter
    @AllArgsConstructor
    public static class NodeInfo {

        private String name;
        private boolean active;
        private Period period;

        @Data
        public static class Period {
            private final String from;
            private final String to;
        }
    }
}
