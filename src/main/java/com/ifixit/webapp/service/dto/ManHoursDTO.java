package com.ifixit.webapp.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ManHours entity.
 */
public class ManHoursDTO implements Serializable {

    private Long id;

    @NotNull
    private Float mh;

    private Long workOrderId;

    private Long groupEngineerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getMh() {
        return mh;
    }

    public void setMh(Float mh) {
        this.mh = mh;
    }

    public Long getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
    }

    public Long getGroupEngineerId() {
        return groupEngineerId;
    }

    public void setGroupEngineerId(Long groupEngineerId) {
        this.groupEngineerId = groupEngineerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ManHoursDTO manHoursDTO = (ManHoursDTO) o;
        if(manHoursDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), manHoursDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ManHoursDTO{" +
            "id=" + getId() +
            ", mh='" + getMh() + "'" +
            "}";
    }
}
