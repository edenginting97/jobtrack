package id.co.jobtrack.analyticsservice.event;

import id.co.jobtrack.analyticsservice.enums.ApplicationStatus;
import lombok.Builder;

@Builder
public record ChangeStatusEvent(
        String id,
        ApplicationStatus status
) {
}
