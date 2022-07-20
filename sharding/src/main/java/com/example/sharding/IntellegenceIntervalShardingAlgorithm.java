package com.example.sharding;

import com.example.shard.configuration.properties.NodeInfo;
import com.google.common.collect.Range;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ds0 - interval [2022-06-01 ~ 2022-06-30)
 * ds1 - interval [2022-06-30 ~ 2022-07-10)
 * ds2 - interval [2022-07-10 ~ now)
 *
 */
public final class IntellegenceIntervalShardingAlgorithm implements StandardShardingAlgorithm<Timestamp> {

    final Map<Range<Timestamp>, NodeInfo> intervalToNodeMap = new HashMap<>(){{
        put(
                Range.closedOpen(
                        Timestamp.valueOf(LocalDateTime.of(2022, 6, 1, 0, 0, 0)),
                        Timestamp.valueOf(LocalDateTime.of(2022, 6, 30, 0, 0, 0))
                ),
                new NodeInfo("ds0", false)
        );
        put(
                Range.closedOpen(
                        Timestamp.valueOf(LocalDateTime.of(2022, 6, 30, 0, 0, 0)),
                        Timestamp.valueOf(LocalDateTime.of(2022, 7, 10, 0, 0, 0))
                ),
                new NodeInfo("ds1", false)
        );
        put(
                Range.atLeast(
                        Timestamp.valueOf(LocalDateTime.of(2022, 7, 10, 0, 0, 0))
                ),
                new NodeInfo("ds2", true)
        );
    }};

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Timestamp> preciseShardingValue) {
        return getNodeForWrite(preciseShardingValue.getValue());
    }

    /**
     * по БЗ будет только одна нода, куда будет записываться операции
     * @param localDateTime дату котору надо вставить
     * @return имя ноды
     */
    private String getNodeForWrite(Timestamp localDateTime) {
        Map<Range<Timestamp>, NodeInfo> activeNodes = intervalToNodeMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue().isActive())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        for (Map.Entry<Range<Timestamp>, NodeInfo> entry : activeNodes.entrySet()) {
            if (entry.getKey().contains(localDateTime)) {
                return entry.getValue().getNameNode();
            }
        }
        throw new UnsupportedOperationException("node not found");
    }

    @Override
    public Collection<String> doSharding(Collection<String> collection,
                                         RangeShardingValue<Timestamp> rangeShardingValue) {

        Range<Timestamp> targetInterval = rangeShardingValue.getValueRange();
        final List<String> nodes = new ArrayList<>();
        for (Map.Entry<Range<Timestamp>, NodeInfo> entry : intervalToNodeMap.entrySet()) {
            if (entry.getKey().isConnected(targetInterval)) {
                nodes.add(entry.getValue().getNameNode());
            }
        }
        return nodes;
    }

    @Override
    public Properties getProps() {
        return null;
    }

    @Override
    public void init(Properties props) {

    }
}
