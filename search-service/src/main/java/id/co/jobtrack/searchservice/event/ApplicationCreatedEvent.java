package id.co.jobtrack.searchservice.event;

import id.co.jobtrack.searchservice.enums.ApplicationStatus;
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
