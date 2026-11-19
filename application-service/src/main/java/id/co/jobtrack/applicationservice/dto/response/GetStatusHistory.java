package id.co.jobtrack.applicationservice.dto.response;

import id.co.jobtrack.applicationservice.enums.ApplicationStatus;
import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record GetStatusHistory(
        ApplicationStatus previousStatus,
        ApplicationStatus newStatus,
        ZonedDateTime changedAt
) {
}
