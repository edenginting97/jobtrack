package id.co.jobtrack.applicationservice.controller;

import id.co.jobtrack.applicationservice.dto.request.ChangeStatus;
import id.co.jobtrack.applicationservice.dto.request.CreateJobApplication;
import id.co.jobtrack.applicationservice.dto.response.GetJobApplication;
import id.co.jobtrack.applicationservice.dto.response.GetStatusHistory;
import id.co.jobtrack.applicationservice.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @GetMapping
    public Page<GetJobApplication> getApplications(Pageable pageable) {
        return applicationService.getApplications(pageable);
    }

    @GetMapping("/{id}/history")
    public List<GetStatusHistory> getStatusHistory(@PathVariable Long id) {
        return applicationService.getStatusHistory(id);
    }

    @GetMapping("/{id}")
    public GetJobApplication getApplication(@PathVariable Long id) {
        return applicationService.getApplication(id);
    }

    @PostMapping
    public void createApplication(@RequestBody CreateJobApplication request) {
        applicationService.createApplication(request);
    }

    @PatchMapping("/{id}")
    public void changeStatus(@PathVariable Long id, @RequestBody ChangeStatus request) {
        applicationService.changeStatus(id, request.status());
    }

    @DeleteMapping("/{id}")
    public void deleteApplication(@PathVariable Long id) {
        applicationService.deleteApplication(id);
    }
}
