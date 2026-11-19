package id.co.jobtrack.applicationservice.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "event.topic")
public record KafkaTopicProperties(
        Topic applicationCreated,
        Topic applicationUpdated,
        Topic applicationDeleted
) {
    public record Topic(
            String name,
            int partitions,
            int replicas
    ) {
    }
}
