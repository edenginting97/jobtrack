package id.co.jobtrack.searchservice.service;

import id.co.jobtrack.searchservice.document.ApplicationDocument;
import id.co.jobtrack.searchservice.dto.request.ApplicationSearch;
import id.co.jobtrack.searchservice.event.ApplicationCreatedEvent;
import id.co.jobtrack.searchservice.event.ChangeStatusEvent;

import java.util.List;

public interface ApplicationSearchService {

    List<ApplicationDocument> search(ApplicationSearch request);

    void createDocument(ApplicationCreatedEvent message);

    void updateDocument(ChangeStatusEvent message);

    void deleteDocument(String id);
}
