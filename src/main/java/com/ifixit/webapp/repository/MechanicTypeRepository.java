package com.ifixit.webapp.repository;

import com.ifixit.webapp.domain.MechanicType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MechanicType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MechanicTypeRepository extends JpaRepository<MechanicType, Long> {

}
