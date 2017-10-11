package com.ifixit.webapp.repository;

import com.ifixit.webapp.domain.Mechanic;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Mechanic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MechanicRepository extends JpaRepository<Mechanic, Long> {

}
