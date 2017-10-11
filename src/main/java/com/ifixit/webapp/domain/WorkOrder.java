package com.ifixit.webapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.ifixit.webapp.domain.enumeration.WOStatus;

/**
 * A WorkOrder.
 */
@Entity
@Table(name = "work_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WorkOrder implements Serializable {

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

    @NotNull
    @Column(name = "start_time", nullable = false)
    private LocalDate startTime;

    @NotNull
    @Column(name = "end_time", nullable = false)
    private LocalDate endTime;

    @Column(name = "i_interval")
    private Integer iInterval;

    @NotNull
    @Column(name = "is_repeat", nullable = false)
    private Integer isRepeat;

    @Column(name = "task")
    private String task;

    @Column(name = "reason")
    private String reason;

    @Column(name = "note")
    private String note;

    @Column(name = "mh_total")
    private Float mhTotal;

    @Column(name = "mh_total_cost")
    private Float mhTotalCost;

    @Column(name = "stock_total_cost")
    private Float stockTotalCost;

    @Column(name = "last_update")
    private LocalDate lastUpdate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private WOStatus status;

    @ManyToOne
    private WorkType workType;

    @ManyToOne
    private GroupEngineer groupEngineer;

    @ManyToOne
    private Mechanic mechanic;

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

    public WorkOrder code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public WorkOrder name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public WorkOrder startTime(LocalDate startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public WorkOrder endTime(LocalDate endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public Integer getiInterval() {
        return iInterval;
    }

    public WorkOrder iInterval(Integer iInterval) {
        this.iInterval = iInterval;
        return this;
    }

    public void setiInterval(Integer iInterval) {
        this.iInterval = iInterval;
    }

    public Integer getIsRepeat() {
        return isRepeat;
    }

    public WorkOrder isRepeat(Integer isRepeat) {
        this.isRepeat = isRepeat;
        return this;
    }

    public void setIsRepeat(Integer isRepeat) {
        this.isRepeat = isRepeat;
    }

    public String getTask() {
        return task;
    }

    public WorkOrder task(String task) {
        this.task = task;
        return this;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getReason() {
        return reason;
    }

    public WorkOrder reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getNote() {
        return note;
    }

    public WorkOrder note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Float getMhTotal() {
        return mhTotal;
    }

    public WorkOrder mhTotal(Float mhTotal) {
        this.mhTotal = mhTotal;
        return this;
    }

    public void setMhTotal(Float mhTotal) {
        this.mhTotal = mhTotal;
    }

    public Float getMhTotalCost() {
        return mhTotalCost;
    }

    public WorkOrder mhTotalCost(Float mhTotalCost) {
        this.mhTotalCost = mhTotalCost;
        return this;
    }

    public void setMhTotalCost(Float mhTotalCost) {
        this.mhTotalCost = mhTotalCost;
    }

    public Float getStockTotalCost() {
        return stockTotalCost;
    }

    public WorkOrder stockTotalCost(Float stockTotalCost) {
        this.stockTotalCost = stockTotalCost;
        return this;
    }

    public void setStockTotalCost(Float stockTotalCost) {
        this.stockTotalCost = stockTotalCost;
    }

    public LocalDate getLastUpdate() {
        return lastUpdate;
    }

    public WorkOrder lastUpdate(LocalDate lastUpdate) {
        this.lastUpdate = lastUpdate;
        return this;
    }

    public void setLastUpdate(LocalDate lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public WOStatus getStatus() {
        return status;
    }

    public WorkOrder status(WOStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(WOStatus status) {
        this.status = status;
    }

    public WorkType getWorkType() {
        return workType;
    }

    public WorkOrder workType(WorkType workType) {
        this.workType = workType;
        return this;
    }

    public void setWorkType(WorkType workType) {
        this.workType = workType;
    }

    public GroupEngineer getGroupEngineer() {
        return groupEngineer;
    }

    public WorkOrder groupEngineer(GroupEngineer groupEngineer) {
        this.groupEngineer = groupEngineer;
        return this;
    }

    public void setGroupEngineer(GroupEngineer groupEngineer) {
        this.groupEngineer = groupEngineer;
    }

    public Mechanic getMechanic() {
        return mechanic;
    }

    public WorkOrder mechanic(Mechanic mechanic) {
        this.mechanic = mechanic;
        return this;
    }

    public void setMechanic(Mechanic mechanic) {
        this.mechanic = mechanic;
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
        WorkOrder workOrder = (WorkOrder) o;
        if (workOrder.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), workOrder.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WorkOrder{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", iInterval='" + getiInterval() + "'" +
            ", isRepeat='" + getIsRepeat() + "'" +
            ", task='" + getTask() + "'" +
            ", reason='" + getReason() + "'" +
            ", note='" + getNote() + "'" +
            ", mhTotal='" + getMhTotal() + "'" +
            ", mhTotalCost='" + getMhTotalCost() + "'" +
            ", stockTotalCost='" + getStockTotalCost() + "'" +
            ", lastUpdate='" + getLastUpdate() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
