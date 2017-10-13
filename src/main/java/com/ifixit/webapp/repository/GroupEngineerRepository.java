package com.ifixit.webapp.repository;

import com.ifixit.webapp.domain.GroupEngineer;
import java.util.List;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the GroupEngineer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GroupEngineerRepository extends JpaRepository<GroupEngineer, Long> {

    @Query("SELECT c.id FROM GroupEngineer c")
    public List<Long> getAllId();

    @Query(value = "SELECT GetEngineerTree(?1,7)", nativeQuery = true)
    String getChild(Long parentId);
}
