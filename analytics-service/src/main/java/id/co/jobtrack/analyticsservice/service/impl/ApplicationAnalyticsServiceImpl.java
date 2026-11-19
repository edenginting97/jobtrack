package id.co.jobtrack.analyticsservice.service.impl;

import id.co.jobtrack.analyticsservice.dto.response.ApplicationStatusSummary;
import id.co.jobtrack.analyticsservice.dto.response.ApplicationSummary;
import id.co.jobtrack.analyticsservice.event.ApplicationCreatedEvent;
import id.co.jobtrack.analyticsservice.event.ChangeStatusEvent;
import id.co.jobtrack.analyticsservice.model.ApplicationAnalytics;
import id.co.jobtrack.analyticsservice.repository.ApplicationAnalyticsRepository;
import id.co.jobtrack.analyticsservice.service.ApplicationAnalyticsService;
import id.co.jobtrack.analyticsservice.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationAnalyticsServiceImpl implements ApplicationAnalyticsService {

    private final ApplicationAnalyticsRepository applicationAnalyticsRepository;

    private final RedisService redisService;

    @Override
    public ApplicationSummary getSummary() {
        var applicationSummary = applicationAnalyticsRepository.getApplicationSummary();
        return ApplicationSummary
                .builder()
                .totalApplications(applicationSummary.getTotalApplications())
                .applied(applicationSummary.getApplied())
                .screening(applicationSummary.getScreening())
                .interview(applicationSummary.getInterview())
                .rejected(applicationSummary.getRejected())
                .build();
    }

    @Override
    public List<ApplicationStatusSummary> getStatusSummary() {
        return applicationAnalyticsRepository.getApplicationStatusSummary()
                .stream()
                .map(s -> ApplicationStatusSummary
                        .builder()
                        .status(s.getStatus())
                        .count(s.getCount())
                        .build()
                )
                .toList();
    }

    @Override
    public void saveCreatedEvent(ApplicationCreatedEvent message) {
        var analytics = new ApplicationAnalytics();
        analytics.setCompanyName(message.companyName());
        analytics.setApplicationId(message.id());
        analytics.setPosition(message.position());
        analytics.setStatus(message.status());
        analytics.setAppliedAt(message.appliedAt());
        applicationAnalyticsRepository.save(analytics);

        redisService.evictAnalyticsCaches();
    }

    @Override
    public void saveUpdatedEvent(ChangeStatusEvent message) {
        applicationAnalyticsRepository.updateStatusByApplicationId(Long.valueOf(message.id()), message.status());

        redisService.evictAnalyticsCaches();
    }
}
