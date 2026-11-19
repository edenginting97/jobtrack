package id.co.jobtrack.searchservice.event;

import id.co.jobtrack.searchservice.enums.ApplicationStatus;
import lombok.Builder;

@Builder
public record ChangeStatusEvent(
        String id,
        ApplicationStatus status
) {
}
