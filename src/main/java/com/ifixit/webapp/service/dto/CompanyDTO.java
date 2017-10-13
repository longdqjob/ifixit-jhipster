package com.ifixit.webapp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

/**
 * A DTO for the Company entity.
 */
public class CompanyDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 20)
    private String code;

    @NotNull
    @Size(max = 20)
    private String completeCode;

    @NotNull
    @Size(max = 128)
    private String name;

    @Size(max = 255)
    private String description;

    private Integer state;

    private Long parentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCompleteCode() {
        return completeCode;
    }

    public void setCompleteCode(String completeCode) {
        this.completeCode = completeCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long companyId) {
        this.parentId = companyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompanyDTO companyDTO = (CompanyDTO) o;
        if (companyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), companyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompanyDTO{"
                + "id=" + getId()
                + ", code='" + getCode() + "'"
                + ", completeCode='" + getCompleteCode() + "'"
                + ", name='" + getName() + "'"
                + ", description='" + getDescription() + "'"
                + ", state='" + getState() + "'"
                + "}";
    }

    //Thuyetlv
    private String parentName;
    private String parentCode;
    private String child;

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
    
    boolean hasChildren = true;

    public CompanyDTO(Long id, String code, String completeCode, String name, String description, Integer state, Long parentId, String parentName, String parentCode, String child) {
        this.id = id;
        this.code = code;
        this.completeCode = completeCode;
        this.name = name;
        this.description = description;
        this.state = state;
        this.parentId = parentId;
        this.parentName = parentName;
        this.parentCode = parentCode;
        this.child = child;
        if(StringUtils.isBlank(child)){
            this.hasChildren = false;
        }else{
            this.hasChildren = true;
        }
    }

    public CompanyDTO() {
    }

}
