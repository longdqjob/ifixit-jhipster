package com.ifixit.webapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A WorkType.
 */
@Entity
@Table(name = "work_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WorkType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "code", length = 20, nullable = false)
    private String code;

    @NotNull
    @Size(max = 128)
    @Column(name = "name", length = 128, nullable = false)
    private String name;

    @Column(name = "i_interval")
    private Integer iInterval;

    @NotNull
    @Column(name = "is_repeat", nullable = false)
    private Integer isRepeat;

    @Column(name = "task")
    private String task;

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

    public WorkType code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public WorkType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getiInterval() {
        return iInterval;
    }

    public WorkType iInterval(Integer iInterval) {
        this.iInterval = iInterval;
        return this;
    }

    public void setiInterval(Integer iInterval) {
        this.iInterval = iInterval;
    }

    public Integer getIsRepeat() {
        return isRepeat;
    }

    public WorkType isRepeat(Integer isRepeat) {
        this.isRepeat = isRepeat;
        return this;
    }

    public void setIsRepeat(Integer isRepeat) {
        this.isRepeat = isRepeat;
    }

    public String getTask() {
        return task;
    }

    public WorkType task(String task) {
        this.task = task;
        return this;
    }

    public void setTask(String task) {
        this.task = task;
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
        WorkType workType = (WorkType) o;
        if (workType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), workType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WorkType{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", iInterval='" + getiInterval() + "'" +
            ", isRepeat='" + getIsRepeat() + "'" +
            ", task='" + getTask() + "'" +
            "}";
    }
}
