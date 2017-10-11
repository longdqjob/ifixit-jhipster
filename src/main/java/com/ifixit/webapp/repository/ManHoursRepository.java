package com.ifixit.webapp.repository;

import com.ifixit.webapp.domain.ManHours;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ManHours entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ManHoursRepository extends JpaRepository<ManHours, Long> {

}
