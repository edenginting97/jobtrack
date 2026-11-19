package id.co.jobtrack.analyticsservice.event;

import id.co.jobtrack.analyticsservice.enums.ApplicationStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record ApplicationCreatedEvent(
        Long id,
        String companyName,
        String position,
        String location,
        String employmentType,
        BigDecimal salary,
        ApplicationStatus status,
        LocalDate appliedAt
) {
}
