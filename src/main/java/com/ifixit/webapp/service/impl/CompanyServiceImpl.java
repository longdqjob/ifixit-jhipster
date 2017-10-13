package com.ifixit.webapp.service.impl;

import com.ifixit.webapp.service.CompanyService;
import com.ifixit.webapp.domain.Company;
import com.ifixit.webapp.repository.CompanyRepository;
import com.ifixit.webapp.service.dto.CompanyDTO;
import com.ifixit.webapp.service.mapper.CompanyMapper;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.swing.StringUIClientPropertyKey;

/**
 * Service Implementation for managing Company.
 */
@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    private final Logger log = LoggerFactory.getLogger(CompanyServiceImpl.class);

    private final CompanyRepository companyRepository;

    private final CompanyMapper companyMapper;

    public CompanyServiceImpl(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    /**
     * Save a company.
     *
     * @param companyDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CompanyDTO save(CompanyDTO companyDTO) {
        log.debug("Request to save Company : {}", companyDTO);
        Company company = companyMapper.toEntity(companyDTO);
        company = companyRepository.save(company);
        return companyMapper.toDto(company);
    }

    /**
     * Get all the companies.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CompanyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Companies");
        return companyRepository.findAll(pageable)
                .map(companyMapper::toDto);
    }

    /**
     * Get one company by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CompanyDTO findOne(Long id) {
        log.debug("Request to get Company : {}", id);
        Company company = companyRepository.findOne(id);
        return companyMapper.toDto(company);
    }

    /**
     * Delete the company by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Company : {}", id);
        companyRepository.delete(id);
    }

    //ThuyetLV Add
    @Override
    public List<Long> getAllId() {
        return companyRepository.getAllId();
    }
    
    @Override
    public String getChild(Long id) {
        log.debug("Request to get getChild");
        return companyRepository.getChild(id);
    }

    @Override
    public List<Company> getListChild(List<Long> lstChild) {
        log.debug("Request to get getListChild: " + StringUtils.join(lstChild, ","));
        return companyRepository.getListChild(lstChild);
    }

    @Override
    public List<Company> getTree(Long id) {
        log.debug("Request to get getTree: " + id);
        return companyRepository.getTree(id);
    }

    @Override
    public List<CompanyDTO> findTopProducts(Long id) {
        log.debug("Request to get findTopProducts: ");
        return companyRepository.getGroupDetails(id);
    }
}
