package id.co.jobtrack.applicationservice.dto.request;

import java.math.BigDecimal;

public record CreateJobApplication(
        String companyName,
        String position,
        String location,
        String employmentType,
        BigDecimal salary
) {
}
