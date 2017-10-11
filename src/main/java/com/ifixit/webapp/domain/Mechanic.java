package com.ifixit.webapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Mechanic.
 */
@Entity
@Table(name = "mechanic")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Mechanic implements Serializable {

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

    @Column(name = "note")
    private String note;

    @NotNull
    @Column(name = "since", nullable = false)
    private LocalDate since;

    @Column(name = "jhi_specification")
    private String specification;

    @Column(name = "location")
    private String location;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "img_path")
    private String imgPath;

    @ManyToOne
    private ItemType itemType;

    @ManyToOne
    private MechanicType mechanicType;

    @ManyToOne
    private Company company;

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

    public Mechanic code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCompleteCode() {
        return completeCode;
    }

    public Mechanic completeCode(String completeCode) {
        this.completeCode = completeCode;
        return this;
    }

    public void setCompleteCode(String completeCode) {
        this.completeCode = completeCode;
    }

    public String getName() {
        return name;
    }

    public Mechanic name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Mechanic description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNote() {
        return note;
    }

    public Mechanic note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDate getSince() {
        return since;
    }

    public Mechanic since(LocalDate since) {
        this.since = since;
        return this;
    }

    public void setSince(LocalDate since) {
        this.since = since;
    }

    public String getSpecification() {
        return specification;
    }

    public Mechanic specification(String specification) {
        this.specification = specification;
        return this;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getLocation() {
        return location;
    }

    public Mechanic location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Mechanic imgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgPath() {
        return imgPath;
    }

    public Mechanic imgPath(String imgPath) {
        this.imgPath = imgPath;
        return this;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public Mechanic itemType(ItemType itemType) {
        this.itemType = itemType;
        return this;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public MechanicType getMechanicType() {
        return mechanicType;
    }

    public Mechanic mechanicType(MechanicType mechanicType) {
        this.mechanicType = mechanicType;
        return this;
    }

    public void setMechanicType(MechanicType mechanicType) {
        this.mechanicType = mechanicType;
    }

    public Company getCompany() {
        return company;
    }

    public Mechanic company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
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
        Mechanic mechanic = (Mechanic) o;
        if (mechanic.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mechanic.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Mechanic{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", completeCode='" + getCompleteCode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", note='" + getNote() + "'" +
            ", since='" + getSince() + "'" +
            ", specification='" + getSpecification() + "'" +
            ", location='" + getLocation() + "'" +
            ", imgUrl='" + getImgUrl() + "'" +
            ", imgPath='" + getImgPath() + "'" +
            "}";
    }
}
