package id.co.jobtrack.analyticsservice.projection;

public interface ApplicationSummaryProjection {

    Integer getTotalApplications();

    Integer getApplied();

    Integer getScreening();

    Integer getInterview();

    Integer getRejected();
}
