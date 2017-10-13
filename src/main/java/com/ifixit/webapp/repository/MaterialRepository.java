package com.ifixit.webapp.repository;

import com.ifixit.webapp.domain.Material;
import com.ifixit.webapp.service.dto.MaterialDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the Material entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {

    @Query(nativeQuery = true)
    MaterialDTO getData(@Param("id") Long id);

    @Query(nativeQuery = true)
    public Page<MaterialDTO> getMaterials(Pageable pgbl);
}
