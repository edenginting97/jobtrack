package id.co.jobtrack.applicationservice.service;

import id.co.jobtrack.applicationservice.dto.request.CreateJobApplication;
import id.co.jobtrack.applicationservice.dto.response.GetJobApplication;
import id.co.jobtrack.applicationservice.dto.response.GetStatusHistory;
import id.co.jobtrack.applicationservice.enums.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ApplicationService {

    void createApplication(CreateJobApplication request);

    GetJobApplication getApplication(Long id);

    Page<GetJobApplication> getApplications(Pageable pageable);

    void changeStatus(Long id, ApplicationStatus status);

    void deleteApplication(Long id);

    List<GetStatusHistory> getStatusHistory(Long id);
}
