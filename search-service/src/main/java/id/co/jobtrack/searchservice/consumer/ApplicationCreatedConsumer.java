package id.co.jobtrack.searchservice.consumer;

import id.co.jobtrack.searchservice.event.ApplicationCreatedEvent;
import id.co.jobtrack.searchservice.service.ApplicationSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class ApplicationCreatedConsumer {

    private final ObjectMapper objectMapper;

    private final ApplicationSearchService applicationSearchService;

    @KafkaListener(
            topics = "${event.topic.application-created}",
            groupId = "${event.group.application-search-group}"
    )
    @RetryableTopic(
            attempts = "3",
            autoCreateTopics = "true"
    )
    public void applicationCreatedConsumer(String event) {
        var message = objectMapper.readValue(event, ApplicationCreatedEvent.class);
        applicationSearchService.createDocument(message);
    }
}
