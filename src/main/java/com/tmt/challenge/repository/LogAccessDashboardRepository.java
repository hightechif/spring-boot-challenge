package com.tmt.challenge.repository;

import com.tmt.challenge.model.LogAccessDashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LogAccessDashboardRepository extends JpaRepository<LogAccessDashboard, Long> {

    @Query(value = "SELECT COUNT(*) FROM log_access_dashboard lad " +
            "WHERE YEAR(lad.created_date) < YEAR(CURDATE()) " +
            "OR MONTH(lad.created_date) < (MONTH(CURDATE()) - (:number - 1)) " +
            "ORDER BY created_date DESC",
            nativeQuery = true)
    long findLogsBeforeTheLastNMonth(@Param("number") int number);

    @Modifying
    @Query(value = "DELETE FROM log_access_dashboard " +
            "WHERE YEAR(created_date) < YEAR(CURDATE()) " +
            "OR MONTH(created_date) < (MONTH(CURDATE()) - (:number - 1))",
            nativeQuery = true)
    void cleanLogsBeforeTheLastNNMonth(@Param("number") int number);
}
