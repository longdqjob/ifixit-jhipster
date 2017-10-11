package com.ifixit.webapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ItemType.
 */
@Entity
@Table(name = "item_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ItemType implements Serializable {

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

    @Column(name = "jhi_specification")
    private String specification;

    @OneToMany(mappedBy = "itemType")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Material> itemTypes = new HashSet<>();

    @ManyToOne
    private ItemType parent;

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

    public ItemType code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCompleteCode() {
        return completeCode;
    }

    public ItemType completeCode(String completeCode) {
        this.completeCode = completeCode;
        return this;
    }

    public void setCompleteCode(String completeCode) {
        this.completeCode = completeCode;
    }

    public String getName() {
        return name;
    }

    public ItemType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecification() {
        return specification;
    }

    public ItemType specification(String specification) {
        this.specification = specification;
        return this;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public Set<Material> getItemTypes() {
        return itemTypes;
    }

    public ItemType itemTypes(Set<Material> materials) {
        this.itemTypes = materials;
        return this;
    }

    public ItemType addItemType(Material material) {
        this.itemTypes.add(material);
        material.setItemType(this);
        return this;
    }

    public ItemType removeItemType(Material material) {
        this.itemTypes.remove(material);
        material.setItemType(null);
        return this;
    }

    public void setItemTypes(Set<Material> materials) {
        this.itemTypes = materials;
    }

    public ItemType getParent() {
        return parent;
    }

    public ItemType parent(ItemType itemType) {
        this.parent = itemType;
        return this;
    }

    public void setParent(ItemType itemType) {
        this.parent = itemType;
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
        ItemType itemType = (ItemType) o;
        if (itemType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), itemType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ItemType{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", completeCode='" + getCompleteCode() + "'" +
            ", name='" + getName() + "'" +
            ", specification='" + getSpecification() + "'" +
            "}";
    }
}
