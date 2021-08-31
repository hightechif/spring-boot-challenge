package com.tmt.challenge.repository;

import com.tmt.challenge.model.LogAccessDashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LogAccessDashboardRepository extends JpaRepository<LogAccessDashboard, Long> {

    @Query(value = "SELECT COUNT(*) FROM log_access_dashboard " +
            "WHERE YEAR(created_date) = :year " +
            "AND MONTH(created_date) <= :month " +
            "OR YEAR (created_date) < :year " +
            "ORDER BY created_date DESC",
            nativeQuery = true)
    long findLogsBeforeTheLastNMonth(@Param("year") int year, @Param("month") int month);

    @Modifying
    @Query(value = "DELETE FROM log_access_dashboard " +
            "WHERE YEAR(created_date) = :year " +
            "AND MONTH(created_date) <= :month " +
            "OR YEAR (created_date) < :year",
            nativeQuery = true)
    void cleanLogsBeforeTheLastNNMonth(@Param("year") int year, @Param("month") int month);
}
