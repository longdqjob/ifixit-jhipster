package com.ifixit.webapp.repository;

import com.ifixit.webapp.domain.WorkType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the WorkType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkTypeRepository extends JpaRepository<WorkType, Long> {

}
