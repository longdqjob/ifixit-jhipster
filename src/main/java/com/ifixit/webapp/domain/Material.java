package com.ifixit.webapp.domain;

import com.ifixit.webapp.service.dto.MaterialDTO;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Material.
 */
@Entity
@Table(name = "material")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@SqlResultSetMapping(
        name = "materialDTOResult",
        classes = {
            @ConstructorResult(
                    targetClass = com.ifixit.webapp.service.dto.MaterialDTO.class, 
                    columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "code", type = String.class),
                        @ColumnResult(name = "completeCode", type = String.class),
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "description", type = String.class),
                        @ColumnResult(name = "cost", type = Float.class),
                        @ColumnResult(name = "unit", type = String.class),
                        @ColumnResult(name = "quantity", type = Integer.class),
                        @ColumnResult(name = "location", type = String.class),
                        @ColumnResult(name = "imgUrl", type = String.class),
                        @ColumnResult(name = "imgPath", type = String.class),
                        @ColumnResult(name = "specification", type = String.class),
                        @ColumnResult(name = "itemTypeId", type = Long.class),
                        @ColumnResult(name = "itemTypeName", type = String.class),
                        @ColumnResult(name = "itemTypeCode", type = String.class),
                        @ColumnResult(name = "itemTypeSpe", type = String.class)
                    }
            )
        }
)
//@NamedNativeQuery(name = "Material.getData", query = "SELECT c.id as id,c.code as code,c.complete_code as completeCode,"
//        + "c.name as name,c.description as description,c.jhi_cost as cost,c.unit as unit, c.quantity as quantity, "
//        + "c.location as location,c.img_url as imgUrl,c.img_path as imgPath,c.jhi_specification as specification,  "
//        + "c.item_type_id as itemTypeId,i.name as itemTypeName,i.complete_code as itemTypeCode,i.jhi_specification as itemTypeSpe "
//        + "FROM material c LEFT JOIN item_type i ON c.item_type_id=i.id WHERE c.id=:id", resultSetMapping = "materialDTOResult")
//@NamedNativeQuery(name = "Material.getAll", query = "SELECT c.id as id,c.code as code,c.complete_code as completeCode,"
//        + "c.name as name,c.description as description,c.jhi_cost as cost,c.unit as unit, c.quantity as quantity, "
//        + "c.location as location,c.img_url as imgUrl,c.img_path as imgPath,c.jhi_specification as specification,  "
//        + "c.item_type_id as itemTypeId,i.name as itemTypeName,i.complete_code as itemTypeCode,i.jhi_specification as itemTypeSpe "
//        + "FROM material c LEFT JOIN item_type i ON c.item_type_id=i.id WHERE c.id=:id", resultSetMapping = "materialDTOResult")
//
//@Entity(name="EmployeeEntity")
//@Table (name="employee")

@NamedNativeQueries({
    @NamedNativeQuery(
            name = "Material.getData",
            query = "SELECT c.id as id,c.code as code,c.complete_code as completeCode,"
            + "c.name as name,c.description as description,c.jhi_cost as cost,c.unit as unit, c.quantity as quantity, "
            + "c.location as location,c.img_url as imgUrl,c.img_path as imgPath,c.jhi_specification as specification,  "
            + "c.item_type_id as itemTypeId,i.name as itemTypeName,i.complete_code as itemTypeCode,i.jhi_specification as itemTypeSpe "
            + "FROM material c LEFT JOIN item_type i ON c.item_type_id=i.id WHERE c.id=:id",
            resultSetMapping = "materialDTOResult"
    ),
    @NamedNativeQuery(
            name = "Material.getMaterials",
            query = "SELECT c.id as id,c.code as code,c.complete_code as completeCode,"
            + "c.name as name,c.description as description,c.jhi_cost as cost,c.unit as unit, c.quantity as quantity, "
            + "c.location as location,c.img_url as imgUrl,c.img_path as imgPath,c.jhi_specification as specification,  "
            + "c.item_type_id as itemTypeId,i.name as itemTypeName,i.complete_code as itemTypeCode,i.jhi_specification as itemTypeSpe "
            + "FROM material c LEFT JOIN item_type i ON c.item_type_id=i.id",
            resultSetMapping = "materialDTOResult"
    )
})

public class Material implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "code", length = 20, nullable = false)
    private String code;

    @NotNull
    @Size(max = 20)
    @Column(name = "complete_code", length = 20, nullable = false)
    private String completeCode;

    @NotNull
    @Size(max = 128)
    @Column(name = "name", length = 128, nullable = false)
    private String name;

    @Size(max = 255)
    @Column(name = "description", length = 255)
    private String description;

    @NotNull
    @Column(name = "jhi_cost", nullable = false)
    private Float cost;

    @NotNull
    @Column(name = "unit", nullable = false)
    private String unit;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "location")
    private String location;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "img_path")
    private String imgPath;

    @Column(name = "jhi_specification")
    private String specification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_type_id")
    private ItemType itemType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Material code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCompleteCode() {
        return completeCode;
    }

    public Material completeCode(String completeCode) {
        this.completeCode = completeCode;
        return this;
    }

    public void setCompleteCode(String completeCode) {
        this.completeCode = completeCode;
    }

    public String getName() {
        return name;
    }

    public Material name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Material description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getCost() {
        return cost;
    }

    public Material cost(Float cost) {
        this.cost = cost;
        return this;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public String getUnit() {
        return unit;
    }

    public Material unit(String unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Material quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getLocation() {
        return location;
    }

    public Material location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Material imgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgPath() {
        return imgPath;
    }

    public Material imgPath(String imgPath) {
        this.imgPath = imgPath;
        return this;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getSpecification() {
        return specification;
    }

    public Material specification(String specification) {
        this.specification = specification;
        return this;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public Material itemType(ItemType itemType) {
        this.itemType = itemType;
        return this;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Material material = (Material) o;
        if (material.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), material.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Material{"
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
}
