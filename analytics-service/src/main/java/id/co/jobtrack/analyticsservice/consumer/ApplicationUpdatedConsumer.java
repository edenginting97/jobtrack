package id.co.jobtrack.analyticsservice.consumer;

import id.co.jobtrack.analyticsservice.event.ChangeStatusEvent;
import id.co.jobtrack.analyticsservice.service.ApplicationAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class ApplicationUpdatedConsumer {

    private final ObjectMapper objectMapper;

    private final ApplicationAnalyticsService applicationAnalyticsService;

    @KafkaListener(
            topics = "${event.topic.application-updated}",
            groupId = "${event.group.application-analytics-group}"
    )
    @RetryableTopic(
            attempts = "3",
            autoCreateTopics = "true"
    )
    public void applicationUpdatedConsumer(String event) {
        var message = objectMapper.readValue(event, ChangeStatusEvent.class);
        applicationAnalyticsService.saveUpdatedEvent(message);
    }
}
