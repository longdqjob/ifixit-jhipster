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
                + ", iInterval='" + getiInterval() + "'"
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

    //Add
    private String workTypeCode;
    private String workTypeName;
    private String groupEngineerCode;
    private String groupEngineerName;
    private String mechanicCode;
    private String mechanicName;

    public String getWorkTypeCode() {
        return workTypeCode;
    }

    public void setWorkTypeCode(String workTypeCode) {
        this.workTypeCode = workTypeCode;
    }

    public String getWorkTypeName() {
        return workTypeName;
    }

    public void setWorkTypeName(String workTypeName) {
        this.workTypeName = workTypeName;
    }

    public String getGroupEngineerCode() {
        return groupEngineerCode;
    }

    public void setGroupEngineerCode(String groupEngineerCode) {
        this.groupEngineerCode = groupEngineerCode;
    }

    public String getGroupEngineerName() {
        return groupEngineerName;
    }

    public void setGroupEngineerName(String groupEngineerName) {
        this.groupEngineerName = groupEngineerName;
    }

    public String getMechanicCode() {
        return mechanicCode;
    }

    public void setMechanicCode(String mechanicCode) {
        this.mechanicCode = mechanicCode;
    }

    public String getMechanicName() {
        return mechanicName;
    }

    public void setMechanicName(String mechanicName) {
        this.mechanicName = mechanicName;
    }

    public WorkOrderDTO(Long id, String code, String name, LocalDate startTime, LocalDate endTime, Integer iInterval, Integer isRepeat, String task, String reason, String note, Float mhTotal, Float mhTotalCost, Float stockTotalCost, LocalDate lastUpdate, WOStatus status, Long workTypeId, Long groupEngineerId, Long mechanicId, String workTypeCode, String workTypeName, String groupEngineerCode, String groupEngineerName, String mechanicCode, String mechanicName) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.iInterval = iInterval;
        this.isRepeat = isRepeat;
        this.task = task;
        this.reason = reason;
        this.note = note;
        this.mhTotal = mhTotal;
        this.mhTotalCost = mhTotalCost;
        this.stockTotalCost = stockTotalCost;
        this.lastUpdate = lastUpdate;
        this.status = status;
        this.workTypeId = workTypeId;
        this.groupEngineerId = groupEngineerId;
        this.mechanicId = mechanicId;
        this.workTypeCode = workTypeCode;
        this.workTypeName = workTypeName;
        this.groupEngineerCode = groupEngineerCode;
        this.groupEngineerName = groupEngineerName;
        this.mechanicCode = mechanicCode;
        this.mechanicName = mechanicName;
    }

    public WorkOrderDTO() {
    }

}
