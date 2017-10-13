package com.ifixit.webapp.repository;

import com.ifixit.webapp.domain.WorkOrder;
import com.ifixit.webapp.service.dto.WorkOrderDTO;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the WorkOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {

    @Query(nativeQuery = true)
    WorkOrderDTO getData(@Param("id") Long id);

    @Query(nativeQuery = true)
    public Page<WorkOrderDTO> getWorkOrders(@Param("listEng") List<Long> listEng, Pageable pgbl);
}
