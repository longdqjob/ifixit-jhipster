package com.ifixit.webapp.repository;

import com.ifixit.webapp.domain.Company;
import com.ifixit.webapp.service.dto.CompanyDTO;
import java.util.List;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the Company entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("SELECT c.id FROM Company c")
    public List<Long> getAllId();
    
    @Query("SELECT c FROM Company c WHERE c.parent.id = :parentId")
    public List<Company> find(@Param("parentId") Long parentId);

    @Query(value = "SELECT GetCompanyTree(?1,7)", nativeQuery = true)
    String getChild(Long parentId);

    @Query(value = "SELECT c FROM Company c WHERE c.id in :lstChild")
    public List<Company> getListChild(@Param("lstChild") List<Long> lstChild);

    @Query(value = "SELECT c FROM Company c WHERE c.parent.id in :id")
    public List<Company> getTree(@Param("id") Long id);

//    @Query(value = "SELECT new CompanyDTO(c.id,c.code,c.complete_code,c.name,c.description,c.state,c.parent_id, p.name, GetCompanyTree(c.id,7) as child)"
//            + "FROM company c LEFT JOIN company p ON c.parent_id=p.id", nativeQuery = true)
//    @Query("SELECT new com.ifixit.webapp.service.dto.CompanyDTO(c.id,c.code,c.completeCode,c.name,c.description,c.state,c.parent, p.name, GetCompanyTree(c.id,7) as child)"
//            + "FROM Company c LEFT JOIN Company p ON c.parent=p.id")
//    @Query("SELECT new com.ifixit.webapp.service.dto.CompanyDTO(c.id,c.code,c.completeCode,c.name,c.description,c.state,p.id, p.name) "
//            + "FROM Company c LEFT JOIN Company p ON c.parent=p.id")
    @Query(nativeQuery = true)
    List<CompanyDTO> getGroupDetails(@Param("id") Long id);
}
