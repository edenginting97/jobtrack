package id.co.jobtrack.applicationservice.config;

import id.co.jobtrack.applicationservice.properties.KafkaTopicProperties;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@RequiredArgsConstructor
public class KafkaTopicConfig {

    private final KafkaTopicProperties kafkaTopicProperties;

    @Bean
    public NewTopic applicationCreatedTopic() {
        return buildTopic(kafkaTopicProperties.applicationCreated());
    }

    @Bean
    public NewTopic applicationUpdatedTopic() {
        return buildTopic(kafkaTopicProperties.applicationUpdated());
    }

    @Bean
    public NewTopic applicationDeletedTopic() {
        return buildTopic(kafkaTopicProperties.applicationDeleted());
    }

    private NewTopic buildTopic(KafkaTopicProperties.Topic topic) {
        return TopicBuilder.name(topic.name())
                .partitions(topic.partitions())
                .replicas(topic.replicas())
                .build();
    }
}
