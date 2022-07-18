package com.example.shard.configuration;

import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.util.Collection;
import java.util.Properties;

public final class ShardingOnNodes implements StandardShardingAlgorithm<Long> {

    private final int countNodes = 2;

    private Properties properties;

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
//        @NotNull String value = shardingValue.getValue();
//
//        String valueInBase64 = new String(Base64.getEncoder().encode(value.getBytes(StandardCharsets.UTF_8)));
//        long hash = HashCode.fromString(valueInBase64).asLong();
//
//        String indexNode = String.valueOf(hash % countNodes);

        String s = String.valueOf(shardingValue.getValue() % 3);
        for (String nodeName : availableTargetNames) {
            if (nodeName.contains(s)) {
                return  nodeName;
            }
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Long> rangeShardingValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Properties getProps() {
        return this.properties;
    }

    @Override
    public void init(Properties properties) {
        this.properties = properties;
    }
}
