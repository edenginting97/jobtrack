package id.co.jobtrack.applicationservice.repository;

import id.co.jobtrack.applicationservice.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    @Modifying
    @Transactional
    @Query("""
            DELETE FROM JobApplication ja
            WHERE ja.id = :id
            """)
    int deleteByApplicationId(@Param("id") Long id);
}
