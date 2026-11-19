package id.co.jobtrack.applicationservice.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record GetJobApplication(
        Long id,
        String companyName,
        String position,
        String location,
        String employmentType,
        BigDecimal salary
) {
}
