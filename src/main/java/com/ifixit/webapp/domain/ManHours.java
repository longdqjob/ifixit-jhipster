package com.ifixit.webapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ManHours.
 */
@Entity
@Table(name = "man_hours")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ManHours implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "mh", nullable = false)
    private Float mh;

    @ManyToOne
    private WorkOrder workOrder;

    @ManyToOne
    private GroupEngineer groupEngineer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getMh() {
        return mh;
    }

    public ManHours mh(Float mh) {
        this.mh = mh;
        return this;
    }

    public void setMh(Float mh) {
        this.mh = mh;
    }

    public WorkOrder getWorkOrder() {
        return workOrder;
    }

    public ManHours workOrder(WorkOrder workOrder) {
        this.workOrder = workOrder;
        return this;
    }

    public void setWorkOrder(WorkOrder workOrder) {
        this.workOrder = workOrder;
    }

    public GroupEngineer getGroupEngineer() {
        return groupEngineer;
    }

    public ManHours groupEngineer(GroupEngineer groupEngineer) {
        this.groupEngineer = groupEngineer;
        return this;
    }

    public void setGroupEngineer(GroupEngineer groupEngineer) {
        this.groupEngineer = groupEngineer;
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
        ManHours manHours = (ManHours) o;
        if (manHours.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), manHours.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ManHours{" +
            "id=" + getId() +
            ", mh='" + getMh() + "'" +
            "}";
    }
}
