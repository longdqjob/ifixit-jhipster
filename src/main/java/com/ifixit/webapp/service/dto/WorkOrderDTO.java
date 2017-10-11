package com.ifixit.webapp.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.ifixit.webapp.domain.enumeration.WOStatus;

/**
 * A DTO for the WorkOrder entity.
 */
public class WorkOrderDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 20)
    private String code;

    @NotNull
    @Size(max = 128)
    private String name;

    @NotNull
    private LocalDate startTime;

    @NotNull
    private LocalDate endTime;

    private Integer iInterval;

    @NotNull
    private Integer isRepeat;

    private String task;

    private String reason;

    private String note;

    private Float mhTotal;

    private Float mhTotalCost;

    private Float stockTotalCost;

    private LocalDate lastUpdate;

    private WOStatus status;

    private Long workTypeId;

    private Long groupEngineerId;

    private Long mechanicId;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public Integer getiInterval() {
        return iInterval;
    }

    public void setiInterval(Integer iInterval) {
        this.iInterval = iInterval;
    }

    public Integer getIsRepeat() {
        return isRepeat;
    }

    public void setIsRepeat(Integer isRepeat) {
        this.isRepeat = isRepeat;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Float getMhTotal() {
        return mhTotal;
    }

    public void setMhTotal(Float mhTotal) {
        this.mhTotal = mhTotal;
    }

    public Float getMhTotalCost() {
        return mhTotalCost;
    }

    public void setMhTotalCost(Float mhTotalCost) {
        this.mhTotalCost = mhTotalCost;
    }

    public Float getStockTotalCost() {
        return stockTotalCost;
    }

    public void setStockTotalCost(Float stockTotalCost) {
        this.stockTotalCost = stockTotalCost;
    }

    public LocalDate getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDate lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public WOStatus getStatus() {
        return status;
    }

    public void setStatus(WOStatus status) {
        this.status = status;
    }

    public Long getWorkTypeId() {
        return workTypeId;
    }

    public void setWorkTypeId(Long workTypeId) {
        this.workTypeId = workTypeId;
    }

    public Long getGroupEngineerId() {
        return groupEngineerId;
    }

    public void setGroupEngineerId(Long groupEngineerId) {
        this.groupEngineerId = groupEngineerId;
    }

    public Long getMechanicId() {
        return mechanicId;
    }

    public void setMechanicId(Long mechanicId) {
        this.mechanicId = mechanicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WorkOrderDTO workOrderDTO = (WorkOrderDTO) o;
        if (workOrderDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), workOrderDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WorkOrderDTO{"
                + "id=" + getId()
                + ", code='" + getCode() + "'"
                + ", name='" + getName() + "'"
                + ", startTime='" + getStartTime() + "'"
                + ", endTime='" + getEndTime() + "'"
                + ", iInterval='" + getiInterval()+ "'"
                + ", isRepeat='" + getIsRepeat() + "'"
                + ", task='" + getTask() + "'"
                + ", reason='" + getReason() + "'"
                + ", note='" + getNote() + "'"
                + ", mhTotal='" + getMhTotal() + "'"
                + ", mhTotalCost='" + getMhTotalCost() + "'"
                + ", stockTotalCost='" + getStockTotalCost() + "'"
                + ", lastUpdate='" + getLastUpdate() + "'"
                + ", status='" + getStatus() + "'"
                + "}";
    }
}
