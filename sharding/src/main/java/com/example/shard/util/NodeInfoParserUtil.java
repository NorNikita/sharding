package com.example.shard.util;

import com.example.shard.model.dto.Cluster;
import com.example.shard.model.dto.Node;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.collect.Range;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;



@Slf4j
@UtilityClass
public class NodeInfoParserUtil {

    private final static ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
    private final static String NOW = "now";

    static {
        objectMapper.findAndRegisterModules();
    }

    public static Map<Range<Timestamp>, Node> getClusterInfoMap(final String pathToClusterConfig,
                                                                final String dateTimeFormat) throws IOException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimeFormat);

        Cluster cluster = objectMapper.readValue(ResourceUtils.getFile(pathToClusterConfig), Cluster.class);

        final Map<Range<Timestamp>, Node> intervalToNodeMap = new HashMap<>();

        for(Cluster.NodeInfo info: cluster.getNodes()) {

            Timestamp begin = Timestamp.valueOf(LocalDateTime.parse(info.getPeriod().getFrom(), formatter));
            Range<Timestamp> interval;

            if (info.getPeriod().getTo().equals(NOW)) {
                interval = Range.atLeast(begin);
            } else {
                Timestamp end = Timestamp.valueOf(LocalDateTime.parse(info.getPeriod().getTo(), formatter));
                interval = Range.closedOpen(begin, end);
            }

            Node build = new Node(info.getName(), info.isActive());

            intervalToNodeMap.put(interval, build);
        }

        return intervalToNodeMap;
    }
}
