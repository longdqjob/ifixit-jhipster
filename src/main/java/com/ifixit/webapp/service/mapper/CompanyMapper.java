package com.ifixit.webapp.service.mapper;

import com.ifixit.webapp.domain.*;
import com.ifixit.webapp.service.dto.CompanyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Company and its DTO CompanyDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CompanyMapper extends EntityMapper <CompanyDTO, Company> {

    @Mapping(source = "parent.id", target = "parentId")
    CompanyDTO toDto(Company company); 

    @Mapping(source = "parentId", target = "parent")
    Company toEntity(CompanyDTO companyDTO); 
    default Company fromId(Long id) {
        if (id == null) {
            return null;
        }
        Company company = new Company();
        company.setId(id);
        return company;
    }
}
