package id.co.jobtrack.applicationservice.dto.request;

import id.co.jobtrack.applicationservice.enums.ApplicationStatus;

public record ChangeStatus(
        ApplicationStatus status
) {
}
