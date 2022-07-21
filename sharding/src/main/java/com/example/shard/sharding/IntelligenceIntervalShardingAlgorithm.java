package com.example.shard.sharding;

import com.example.shard.model.dto.Node;
import com.example.shard.util.NodeInfoParserUtil;
import com.google.common.collect.Range;
import lombok.SneakyThrows;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;


public class IntelligenceIntervalShardingAlgorithm implements StandardShardingAlgorithm<Timestamp> {

    private final String CLUSTER_CONFIG_PATH_PARAM = "clusterConfigPath";
    private final String DATE_FORMAT_PARAM = "dateFormat";

    private Properties properties;
    private Map<Range<Timestamp>, Node> rangeDateToNodeMap;
    private Map<Range<Timestamp>, Node> activeNodeMap;

    @Override
    @SneakyThrows
    public void init(Properties props) {
        this.properties = props;

        rangeDateToNodeMap = NodeInfoParserUtil.getClusterInfoMap(
                props.getProperty(CLUSTER_CONFIG_PATH_PARAM),
                props.getProperty(DATE_FORMAT_PARAM)
        );

        activeNodeMap = rangeDateToNodeMap.entrySet().stream()
                .filter(entry -> entry.getValue().isActive())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Timestamp> preciseShardingValue) {
        Timestamp value = preciseShardingValue.getValue();
        for (Map.Entry<Range<Timestamp>, Node> entry : activeNodeMap.entrySet()) {
            if (entry.getKey().contains(value)) {
                return entry.getValue().getName();
            }
        }
        throw new UnsupportedOperationException("node not found");
    }

    @Override
    public Collection<String> doSharding(Collection<String> collection,
                                         RangeShardingValue<Timestamp> rangeShardingValue) {
        Range<Timestamp> targetInterval = rangeShardingValue.getValueRange();

        final List<String> nodes = new ArrayList<>();
        for (Map.Entry<Range<Timestamp>, Node> entry : rangeDateToNodeMap.entrySet()) {
            if (entry.getKey().isConnected(targetInterval)) {
                nodes.add(entry.getValue().getName());
            }
        }
        return nodes;
    }

    @Override
    public Properties getProps() {
        return properties;
    }
}