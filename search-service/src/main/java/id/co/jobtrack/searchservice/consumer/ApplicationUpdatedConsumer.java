package id.co.jobtrack.searchservice.consumer;

import id.co.jobtrack.searchservice.event.ChangeStatusEvent;
import id.co.jobtrack.searchservice.service.ApplicationSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class ApplicationUpdatedConsumer {

    private final ObjectMapper objectMapper;

    private final ApplicationSearchService applicationSearchService;

    @KafkaListener(
            topics = "${event.topic.application-updated}",
            groupId = "${event.group.application-search-group}"
    )
    @RetryableTopic(
            attempts = "3",
            autoCreateTopics = "true"
    )
    public void applicationUpdatedConsumer(String event) {
        var message = objectMapper.readValue(event, ChangeStatusEvent.class);
        applicationSearchService.updateDocument(message);
    }
}
