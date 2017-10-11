package com.ifixit.webapp.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ItemType entity.
 */
public class ItemTypeDTO implements Serializable {

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

    private String specification;

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

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long itemTypeId) {
        this.parentId = itemTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ItemTypeDTO itemTypeDTO = (ItemTypeDTO) o;
        if(itemTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), itemTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ItemTypeDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", completeCode='" + getCompleteCode() + "'" +
            ", name='" + getName() + "'" +
            ", specification='" + getSpecification() + "'" +
            "}";
    }
}
