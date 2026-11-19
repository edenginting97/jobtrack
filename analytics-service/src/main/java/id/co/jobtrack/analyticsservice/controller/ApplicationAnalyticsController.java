package id.co.jobtrack.analyticsservice.controller;

import id.co.jobtrack.analyticsservice.dto.response.ApplicationStatusSummary;
import id.co.jobtrack.analyticsservice.dto.response.ApplicationSummary;
import id.co.jobtrack.analyticsservice.service.ApplicationAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class ApplicationAnalyticsController {

    private final ApplicationAnalyticsService applicationAnalyticsService;

    @GetMapping("/summary")
    @Cacheable(
            value = "application-summary",
            key = "'all'"
    )
    public ApplicationSummary getSummary() {
        return applicationAnalyticsService.getSummary();
    }

    @GetMapping("/status")
    @Cacheable(
            value = "application-status-summary",
            key = "'all'"
    )
    public List<ApplicationStatusSummary> getStatusSummary() {
        return applicationAnalyticsService.getStatusSummary();
    }
}
