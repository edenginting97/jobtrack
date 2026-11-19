package id.co.jobtrack.applicationservice.dto.request;

import java.math.BigDecimal;

public record UpdateJobApplication(
        String companyName,
        String position,
        String location,
        String employmentType,
        BigDecimal salary
) {
}
