package id.co.jobtrack.analyticsservice.projection;

import id.co.jobtrack.analyticsservice.enums.ApplicationStatus;

public interface ApplicationStatusSummaryProjection {

    ApplicationStatus getStatus();

    Integer getCount();
}
