package com.ifixit.webapp.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Mechanic entity.
 */
public class MechanicDTO implements Serializable {

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

    private String note;

    @NotNull
    private LocalDate since;

    private String specification;

    private String location;

    private String imgUrl;

    private String imgPath;

    private Long itemTypeId;

    private Long mechanicTypeId;

    private Long companyId;

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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDate getSince() {
        return since;
    }

    public void setSince(LocalDate since) {
        this.since = since;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Long getItemTypeId() {
        return itemTypeId;
    }

    public void setItemTypeId(Long itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    public Long getMechanicTypeId() {
        return mechanicTypeId;
    }

    public void setMechanicTypeId(Long mechanicTypeId) {
        this.mechanicTypeId = mechanicTypeId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MechanicDTO mechanicDTO = (MechanicDTO) o;
        if (mechanicDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mechanicDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MechanicDTO{"
                + "id=" + getId()
                + ", code='" + getCode() + "'"
                + ", completeCode='" + getCompleteCode() + "'"
                + ", name='" + getName() + "'"
                + ", description='" + getDescription() + "'"
                + ", note='" + getNote() + "'"
                + ", since='" + getSince() + "'"
                + ", specification='" + getSpecification() + "'"
                + ", location='" + getLocation() + "'"
                + ", imgUrl='" + getImgUrl() + "'"
                + ", imgPath='" + getImgPath() + "'"
                + "}";
    }

    //ThuyetLV add
    private String itemTypeCode;
    private String itemTypeName;
    private String itemTypeSpe;
    private String mechanicTypeCode;
    private String mechanicTypeName;
    private String companyCode;
    private String companyName;

    public String getItemTypeCode() {
        return itemTypeCode;
    }

    public void setItemTypeCode(String itemTypeCode) {
        this.itemTypeCode = itemTypeCode;
    }

    public String getItemTypeName() {
        return itemTypeName;
    }

    public void setItemTypeName(String itemTypeName) {
        this.itemTypeName = itemTypeName;
    }

    public String getItemTypeSpe() {
        return itemTypeSpe;
    }

    public void setItemTypeSpe(String itemTypeSpe) {
        this.itemTypeSpe = itemTypeSpe;
    }

    public String getMechanicTypeCode() {
        return mechanicTypeCode;
    }

    public void setMechanicTypeCode(String mechanicTypeCode) {
        this.mechanicTypeCode = mechanicTypeCode;
    }

    public String getMechanicTypeName() {
        return mechanicTypeName;
    }

    public void setMechanicTypeName(String mechanicTypeName) {
        this.mechanicTypeName = mechanicTypeName;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public MechanicDTO() {
    }

    public MechanicDTO(Long id, String code, String completeCode, String name, String description, String note, LocalDate since, String specification, String location, String imgUrl, String imgPath, Long itemTypeId, Long mechanicTypeId, Long companyId, String itemTypeCode, String itemTypeName, String itemTypeSpe, String mechanicTypeCode, String mechanicTypeName, String companyCode, String companyName) {
        this.id = id;
        this.code = code;
        this.completeCode = completeCode;
        this.name = name;
        this.description = description;
        this.note = note;
        this.since = since;
        this.specification = specification;
        this.location = location;
        this.imgUrl = imgUrl;
        this.imgPath = imgPath;
        this.itemTypeId = itemTypeId;
        this.mechanicTypeId = mechanicTypeId;
        this.companyId = companyId;
        this.itemTypeCode = itemTypeCode;
        this.itemTypeName = itemTypeName;
        this.itemTypeSpe = itemTypeSpe;
        this.mechanicTypeCode = mechanicTypeCode;
        this.mechanicTypeName = mechanicTypeName;
        this.companyCode = companyCode;
        this.companyName = companyName;
    }

}
