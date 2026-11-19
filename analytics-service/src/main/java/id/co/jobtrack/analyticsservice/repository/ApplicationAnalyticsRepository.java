package id.co.jobtrack.analyticsservice.repository;

import id.co.jobtrack.analyticsservice.enums.ApplicationStatus;
import id.co.jobtrack.analyticsservice.model.ApplicationAnalytics;
import id.co.jobtrack.analyticsservice.projection.ApplicationStatusSummaryProjection;
import id.co.jobtrack.analyticsservice.projection.ApplicationSummaryProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ApplicationAnalyticsRepository extends JpaRepository<ApplicationAnalytics, Long> {

    @Query("""
            SELECT
            COUNT(aa) AS totalApplications,
            COUNT(CASE WHEN aa.status = id.co.jobtrack.analyticsservice.enums.ApplicationStatus.APPLIED THEN 1 END) AS applied,
            COUNT(CASE WHEN aa.status = id.co.jobtrack.analyticsservice.enums.ApplicationStatus.SCREENING THEN 1 END) AS screening,
            COUNT(CASE WHEN aa.status = id.co.jobtrack.analyticsservice.enums.ApplicationStatus.INTERVIEW THEN 1 END) AS interview,
            COUNT(CASE WHEN aa.status = id.co.jobtrack.analyticsservice.enums.ApplicationStatus.REJECTED THEN 1 END) AS rejected
            FROM ApplicationAnalytics aa
            """)
    ApplicationSummaryProjection getApplicationSummary();

    @Query("""
            SELECT aa.status AS status, COUNT(aa) AS count
            FROM ApplicationAnalytics aa
            GROUP BY aa.status
            """)
    List<ApplicationStatusSummaryProjection> getApplicationStatusSummary();

    @Transactional
    @Query("UPDATE ApplicationAnalytics a SET a.status = :status WHERE a.applicationId = :id")
    @Modifying
    void updateStatusByApplicationId(
            @Param("id") Long id,
            @Param("status") ApplicationStatus status
    );
}
