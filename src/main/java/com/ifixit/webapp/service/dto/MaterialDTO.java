package com.ifixit.webapp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Material entity.
 */
public class MaterialDTO implements Serializable {

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

    @NotNull
    private Float cost;

    @NotNull
    private String unit;

    @NotNull
    private Integer quantity;

    private String location;

    private String imgUrl;

    private String imgPath;

    private String specification;

    private Long itemTypeId;

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

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public Long getItemTypeId() {
        return itemTypeId;
    }

    public void setItemTypeId(Long itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MaterialDTO materialDTO = (MaterialDTO) o;
        if (materialDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), materialDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MaterialDTO{"
                + "id=" + getId()
                + ", code='" + getCode() + "'"
                + ", completeCode='" + getCompleteCode() + "'"
                + ", name='" + getName() + "'"
                + ", description='" + getDescription() + "'"
                + ", cost='" + getCost() + "'"
                + ", unit='" + getUnit() + "'"
                + ", quantity='" + getQuantity() + "'"
                + ", location='" + getLocation() + "'"
                + ", imgUrl='" + getImgUrl() + "'"
                + ", imgPath='" + getImgPath() + "'"
                + ", specification='" + getSpecification() + "'"
                + "}";
    }

    private String itemTypeName;
    private String itemTypeCode;
    private String itemTypeSpe;

    public String getItemTypeName() {
        return itemTypeName;
    }

    public void setItemTypeName(String itemTypeName) {
        this.itemTypeName = itemTypeName;
    }

    public String getItemTypeCode() {
        return itemTypeCode;
    }

    public void setItemTypeCode(String itemTypeCode) {
        this.itemTypeCode = itemTypeCode;
    }

    public String getItemTypeSpe() {
        return itemTypeSpe;
    }

    public void setItemTypeSpe(String itemTypeSpe) {
        this.itemTypeSpe = itemTypeSpe;
    }

    public MaterialDTO(Long id, String code, String completeCode, String name, String description, Float cost, String unit, Integer quantity, String location, String imgUrl, String imgPath, String specification, Long itemTypeId, String itemTypeName, String itemTypeCode, String itemTypeSpe) {
        this.id = id;
        this.code = code;
        this.completeCode = completeCode;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.unit = unit;
        this.quantity = quantity;
        this.location = location;
        this.imgUrl = imgUrl;
        this.imgPath = imgPath;
        this.specification = specification;
        this.itemTypeId = itemTypeId;
        this.itemTypeName = itemTypeName;
        this.itemTypeCode = itemTypeCode;
        this.itemTypeSpe = itemTypeSpe;
    }

    public MaterialDTO() {
    }

}
