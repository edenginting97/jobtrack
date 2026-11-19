package id.co.jobtrack.analyticsservice.dto.response;

import id.co.jobtrack.analyticsservice.enums.ApplicationStatus;
import lombok.Builder;

@Builder
public record ApplicationStatusSummary(
        ApplicationStatus status,
        Integer count
) {
}
