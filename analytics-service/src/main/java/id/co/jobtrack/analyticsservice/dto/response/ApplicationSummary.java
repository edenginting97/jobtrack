package id.co.jobtrack.analyticsservice.dto.response;

import lombok.Builder;

@Builder
public record ApplicationSummary(
        Integer totalApplications,
        Integer applied,
        Integer screening,
        Integer interview,
        Integer rejected
) {
}
