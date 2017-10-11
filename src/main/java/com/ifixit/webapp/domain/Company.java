package com.ifixit.webapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Company.
 */
@Entity
@Table(name = "company")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@SqlResultSetMapping(
        name = "companyDTOResult",
        classes = {
            @ConstructorResult(
                    targetClass = com.ifixit.webapp.service.dto.CompanyDTO.class,
                    columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "code", type = String.class),
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "completeCode", type = String.class),
                        @ColumnResult(name = "description", type = String.class),
                        @ColumnResult(name = "state", type = Integer.class),
                        @ColumnResult(name = "parentId", type = Long.class),
                        @ColumnResult(name = "parentName", type = String.class),
                        @ColumnResult(name = "parentCode", type = String.class),
                        @ColumnResult(name = "child", type = String.class)
                    }
            )
        }
)
@NamedNativeQuery(name = "Company.getGroupDetails", query = "SELECT c.id as id,c.code as code,c.complete_code as completeCode,c.name as name,c.description as description,c.state as state,c.parent_id as parentId, p.name as parentName, p.complete_code as parentCode, GetCompanyTree(c.id,7) as child "
        + "FROM company c LEFT JOIN company p ON c.parent_id=p.id WHERE c.parent_id=:id", resultSetMapping = "companyDTOResult")
public class Company implements Serializable {

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

    @Column(name = "state")
    private Integer state;

    @ManyToOne
    private Company parent;

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

    public Company code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCompleteCode() {
        return completeCode;
    }

    public Company completeCode(String completeCode) {
        this.completeCode = completeCode;
        return this;
    }

    public void setCompleteCode(String completeCode) {
        this.completeCode = completeCode;
    }

    public String getName() {
        return name;
    }

    public Company name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Company description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getState() {
        return state;
    }

    public Company state(Integer state) {
        this.state = state;
        return this;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Company getParent() {
        return parent;
    }

    public Company parent(Company company) {
        this.parent = company;
        return this;
    }

    public void setParent(Company company) {
        this.parent = company;
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
        Company company = (Company) o;
        if (company.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), company.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Company{"
                + "id=" + getId()
                + ", code='" + getCode() + "'"
                + ", completeCode='" + getCompleteCode() + "'"
                + ", name='" + getName() + "'"
                + ", description='" + getDescription() + "'"
                + ", state='" + getState() + "'"
                + "}";
    }
}
