package com.icthh.xm.tmf.ms.resourcepool.kafka;

import com.icthh.xm.tmf.ms.resourcepool.config.ApplicationProperties;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
@Slf4j
public class TopicInitService {

    private final KafkaAdmin kafkaAdmin;
    private final ApplicationProperties ap;

    @SneakyThrows
    @PostConstruct
    public void createTopics() {
        try (AdminClient admin = AdminClient.create(kafkaAdmin.getConfig())) {

            Set<String> existedTopics = admin
                .listTopics(new ListTopicsOptions().listInternal(true))
                .names().get();

            log.debug("Existed topics: {}", existedTopics);

            List<NewTopic> topics = ap.getKafkaTopics().stream()
                .filter(topic -> !existedTopics.contains(topic.getName()))
                .map(topic ->
                    TopicBuilder.name(topic.getName())
                        .partitions(topic.getPartitions())
                        .replicas(topic.getReplicas()).build()).collect(Collectors.toList());

            if (!topics.isEmpty()) {
                log.info("Topics to be created: {}", topics);
                admin.createTopics(topics).all().get();
            }
        }
    }
}
