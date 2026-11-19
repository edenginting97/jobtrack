package id.co.jobtrack.applicationservice.event;

import id.co.jobtrack.applicationservice.enums.ApplicationStatus;
import lombok.Builder;

@Builder
public record ChangeStatusEvent(
        String id,
        ApplicationStatus status
) {
}
