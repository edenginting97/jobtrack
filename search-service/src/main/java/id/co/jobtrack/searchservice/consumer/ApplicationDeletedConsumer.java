package id.co.jobtrack.searchservice.consumer;

import id.co.jobtrack.searchservice.service.ApplicationSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationDeletedConsumer {

    private final ApplicationSearchService applicationSearchService;

    @KafkaListener(
            topics = "${event.topic.application-deleted}",
            groupId = "${event.group.application-search-group}"
    )
    @RetryableTopic(
            attempts = "3",
            autoCreateTopics = "true"
    )
    public void applicationDeletedConsumer(String id) {
        applicationSearchService.deleteDocument(id);
    }
}
