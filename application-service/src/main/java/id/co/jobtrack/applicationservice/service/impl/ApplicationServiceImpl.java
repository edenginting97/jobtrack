package id.co.jobtrack.applicationservice.service.impl;

import id.co.jobtrack.applicationservice.dto.request.CreateJobApplication;
import id.co.jobtrack.applicationservice.dto.response.GetJobApplication;
import id.co.jobtrack.applicationservice.dto.response.GetStatusHistory;
import id.co.jobtrack.applicationservice.enums.ApplicationStatus;
import id.co.jobtrack.applicationservice.event.ApplicationCreatedEvent;
import id.co.jobtrack.applicationservice.event.ChangeStatusEvent;
import id.co.jobtrack.applicationservice.model.ApplicationStatusHistory;
import id.co.jobtrack.applicationservice.model.JobApplication;
import id.co.jobtrack.applicationservice.properties.KafkaTopicProperties;
import id.co.jobtrack.applicationservice.repository.ApplicationStatusHistoryRepository;
import id.co.jobtrack.applicationservice.repository.JobApplicationRepository;
import id.co.jobtrack.applicationservice.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final JobApplicationRepository jobApplicationRepository;

    private final ApplicationStatusHistoryRepository statusHistoryRepository;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final ObjectMapper objectMapper;

    private final KafkaTopicProperties kafkaTopicProperties;

    @Override
    public void createApplication(CreateJobApplication request) {
        var newApplication = new JobApplication();
        newApplication.setCompanyName(request.companyName());
        newApplication.setPosition(request.position());
        newApplication.setLocation(request.location());
        newApplication.setEmploymentType(request.employmentType());
        newApplication.setStatus(ApplicationStatus.APPLIED);
        newApplication.setAppliedAt(LocalDate.now());
        newApplication.setSalary(request.salary());
        JobApplication application = jobApplicationRepository.save(newApplication);

        var event = ApplicationCreatedEvent
                .builder()
                .id(application.getId())
                .companyName(application.getCompanyName())
                .position(application.getPosition())
                .location(application.getPosition())
                .employmentType(application.getEmploymentType())
                .salary(application.getSalary())
                .status(application.getStatus())
                .appliedAt(LocalDate.now())
                .build();

        var history = new ApplicationStatusHistory();
        history.setApplication(application);
        history.setPreviousStatus(null);
        history.setNewStatus(ApplicationStatus.APPLIED);
        statusHistoryRepository.save(history);

        kafkaTemplate.send(
                kafkaTopicProperties.applicationCreated().name(),
                application.getId().toString(),
                objectMapper.writeValueAsString(event)
        );
    }

    @Override
    public GetJobApplication getApplication(Long id) {
        var application = jobApplicationRepository.findById(id).orElseThrow(() -> new RuntimeException("Application not found"));
        return GetJobApplication.builder()
                .id(application.getId())
                .companyName(application.getCompanyName())
                .position(application.getPosition())
                .location(application.getPosition())
                .employmentType(application.getEmploymentType())
                .salary(application.getSalary())
                .build();
    }

    @Override
    public Page<GetJobApplication> getApplications(Pageable pageable) {
        return jobApplicationRepository.findAll(pageable)
                .map(a -> GetJobApplication
                        .builder()
                        .id(a.getId())
                        .companyName(a.getCompanyName())
                        .position(a.getPosition())
                        .location(a.getLocation())
                        .employmentType(a.getEmploymentType())
                        .salary(a.getSalary())
                        .build()
                );
    }

    @Override
    @Transactional
    public void changeStatus(Long id, ApplicationStatus status) {
        var application = jobApplicationRepository.findById(id).orElseThrow(() -> new RuntimeException("Application not found"));
        var previousStatus = application.getStatus();

        if (status == previousStatus) return;

        application.setStatus(status);
        jobApplicationRepository.save(application);

        var history = new ApplicationStatusHistory();
        history.setApplication(application);
        history.setPreviousStatus(previousStatus);
        history.setNewStatus(status);
        statusHistoryRepository.save(history);

        var event = ChangeStatusEvent
                .builder()
                .id(application.getId().toString())
                .status(status)
                .build();
        kafkaTemplate.send(
                kafkaTopicProperties.applicationUpdated().name(),
                application.getId().toString(),
                objectMapper.writeValueAsString(event)
        );
    }

    @Override
    public void deleteApplication(Long id) {
        int deleted = jobApplicationRepository.deleteByApplicationId(id);
        if (deleted > 0) {
            kafkaTemplate.send(
                    kafkaTopicProperties.applicationDeleted().name(),
                    id.toString(),
                    id.toString()
            );
        }
    }

    @Override
    public List<GetStatusHistory> getStatusHistory(Long id) {
        return statusHistoryRepository.findAllByOrderByChangedAtDesc()
                .stream()
                .map(h -> GetStatusHistory
                        .builder()
                        .previousStatus(h.getPreviousStatus())
                        .newStatus(h.getNewStatus())
                        .changedAt(h.getChangedAt())
                        .build()
                )
                .toList();
    }
}
