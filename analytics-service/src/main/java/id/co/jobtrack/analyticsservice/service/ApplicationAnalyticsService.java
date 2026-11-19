package id.co.jobtrack.analyticsservice.service;

import id.co.jobtrack.analyticsservice.dto.response.ApplicationStatusSummary;
import id.co.jobtrack.analyticsservice.dto.response.ApplicationSummary;
import id.co.jobtrack.analyticsservice.event.ApplicationCreatedEvent;
import id.co.jobtrack.analyticsservice.event.ChangeStatusEvent;

import java.util.List;

public interface ApplicationAnalyticsService {

    ApplicationSummary getSummary();

    List<ApplicationStatusSummary> getStatusSummary();

    void saveCreatedEvent(ApplicationCreatedEvent message);

    void saveUpdatedEvent(ChangeStatusEvent message);
}
