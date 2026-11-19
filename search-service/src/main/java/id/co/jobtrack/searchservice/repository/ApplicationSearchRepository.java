package id.co.jobtrack.searchservice.repository;

import id.co.jobtrack.searchservice.document.ApplicationDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationSearchRepository extends ElasticsearchRepository<ApplicationDocument, String> {
}
