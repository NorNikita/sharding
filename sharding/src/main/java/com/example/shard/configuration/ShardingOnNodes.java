package com.example.shard.configuration;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

public final class ShardingOnNodes implements PreciseShardingAlgorithm<Long> {

    private final int countNodes = 2;

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
//        @NotNull String value = shardingValue.getValue();
//
//        String valueInBase64 = new String(Base64.getEncoder().encode(value.getBytes(StandardCharsets.UTF_8)));
//        long hash = HashCode.fromString(valueInBase64).asLong();
//
//        String indexNode = String.valueOf(hash % countNodes);

        String s = String.valueOf(shardingValue.getValue() % 2);
        for (String nodeName : availableTargetNames) {
            if (nodeName.contains(s)) {
                return  nodeName;
            }
        }
        throw new UnsupportedOperationException();
    }
}
