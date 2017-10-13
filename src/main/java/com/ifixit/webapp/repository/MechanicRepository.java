package com.ifixit.webapp.repository;

import com.ifixit.webapp.domain.Mechanic;
import com.ifixit.webapp.service.dto.MechanicDTO;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the Mechanic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MechanicRepository extends JpaRepository<Mechanic, Long> {

    @Query(nativeQuery = true)
    MechanicDTO getData(@Param("id") Long id);

    @Query(nativeQuery = true)
    public Page<MechanicDTO> getMechanics(@Param("listSys") List<Long> listSys, Pageable pgbl);
}
