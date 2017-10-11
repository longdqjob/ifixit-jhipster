package com.ifixit.webapp.repository;

import com.ifixit.webapp.domain.WorkOrder;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the WorkOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {

}
