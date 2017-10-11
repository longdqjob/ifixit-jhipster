package com.ifixit.webapp.repository;

import com.ifixit.webapp.domain.GroupEngineer;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the GroupEngineer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GroupEngineerRepository extends JpaRepository<GroupEngineer, Long> {

}
