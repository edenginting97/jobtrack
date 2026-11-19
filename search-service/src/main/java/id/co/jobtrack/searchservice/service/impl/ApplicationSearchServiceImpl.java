package id.co.jobtrack.searchservice.service.impl;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import id.co.jobtrack.searchservice.document.ApplicationDocument;
import id.co.jobtrack.searchservice.dto.request.ApplicationSearch;
import id.co.jobtrack.searchservice.event.ApplicationCreatedEvent;
import id.co.jobtrack.searchservice.event.ChangeStatusEvent;
import id.co.jobtrack.searchservice.repository.ApplicationSearchRepository;
import id.co.jobtrack.searchservice.service.ApplicationSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationSearchServiceImpl implements ApplicationSearchService {

    private final ApplicationSearchRepository applicationSearchRepository;

    private final ElasticsearchOperations operations;

    @Override
    public List<ApplicationDocument> search(ApplicationSearch request) {
        var builder = getBuilder(request);

        var query = NativeQuery.builder()
                .withQuery(builder.build()._toQuery())
                .build();

        return operations.search(query, ApplicationDocument.class)
                .stream()
                .map(SearchHit::getContent)
                .toList();
    }

    @Override
    public void createDocument(ApplicationCreatedEvent message) {
        var applicationDocument = new ApplicationDocument();
        applicationDocument.setId(message.id().toString());
        applicationDocument.setCompanyName(message.companyName());
        applicationDocument.setPosition(message.position());
        applicationDocument.setLocation(message.location());
        applicationDocument.setEmploymentType(message.employmentType());
        applicationDocument.setSalary(message.salary());
        applicationDocument.setStatus(message.status());
        applicationDocument.setAppliedAt(message.appliedAt());
        applicationSearchRepository.save(applicationDocument);
    }

    @Override
    public void updateDocument(ChangeStatusEvent message) {
        log.info("Retrieve message: {}", message);

        var applicationDocument = applicationSearchRepository.findById(message.id()).orElseThrow(() -> new RuntimeException("Document not found"));
        applicationDocument.setStatus(message.status());
        applicationSearchRepository.save(applicationDocument);
    }

    @Override
    public void deleteDocument(String id) {
        applicationSearchRepository.deleteById(id);
    }

    private static BoolQuery.Builder getBuilder(ApplicationSearch request) {
        var builder = new BoolQuery.Builder();
        if (request.companyName() != null && !request.companyName().isBlank()) {
            builder.must(m -> m
                    .match(t -> t
                            .field("companyName")
                            .query(request.companyName())
                    )
            );
        }

        if (request.position() != null && !request.position().isBlank()) {
            builder.must(m -> m
                    .match(t -> t
                            .field("position")
                            .query(request.position())
                    )
            );
        }

        if (request.location() != null && !request.location().isBlank()) {
            builder.must(m -> m
                    .match(t -> t
                            .field("location")
                            .query(request.location())
                    )
            );
        }

        if (request.employmentType() != null && !request.employmentType().isBlank()) {
            builder.must(m -> m
                    .match(t -> t
                            .field("employmentType")
                            .query(request.employmentType())
                    )
            );
        }

        if (request.minSalary() != null || request.maxSalary() != null) {
            builder.filter(f -> f
                    .range(r -> r
                            .number(n -> {
                                n.field("salary");

                                if (request.minSalary() != null) {
                                    n.gte(request.minSalary().doubleValue());
                                }

                                if (request.maxSalary() != null) {
                                    n.lte(request.maxSalary().doubleValue());
                                }

                                return n;
                            })
                    )
            );
        }

        if (request.status() != null) {
            builder.filter(f -> f
                    .term(t -> t
                            .field("status")
                            .value(request.status().name())
                    )
            );
        }

        if (request.fromDate() != null || request.toDate() != null) {
            builder.filter(f -> f
                    .range(r -> r
                            .date(n -> {
                                n.field("appliedAt");

                                if (request.fromDate() != null) {
                                    n.gte(request.fromDate().toString());
                                }

                                if (request.toDate() != null) {
                                    n.lte(request.toDate().toString());
                                }

                                return n;
                            })
                    )
            );
        }

        return builder;
    }
}
