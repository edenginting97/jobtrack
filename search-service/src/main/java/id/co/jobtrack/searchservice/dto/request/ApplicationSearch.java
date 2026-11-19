package id.co.jobtrack.searchservice.dto.request;

import id.co.jobtrack.searchservice.enums.ApplicationStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ApplicationSearch(
        String companyName,
        String position,
        String location,
        String employmentType,
        ApplicationStatus status,
        BigDecimal minSalary,
        BigDecimal maxSalary,
        LocalDate fromDate,
        LocalDate toDate
) {
}
