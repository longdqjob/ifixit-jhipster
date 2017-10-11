package com.ifixit.webapp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the WorkType entity.
 */
public class WorkTypeDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 20)
    private String code;

    @NotNull
    @Size(max = 128)
    private String name;

    private Integer iInterval;

    @NotNull
    private Integer isRepeat;

    private String task;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WorkTypeDTO workTypeDTO = (WorkTypeDTO) o;
        if (workTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), workTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WorkTypeDTO{"
                + "id=" + getId()
                + ", code='" + getCode() + "'"
                + ", name='" + getName() + "'"
                + ", iInterval='" + getiInterval() + "'"
                + ", isRepeat='" + getIsRepeat() + "'"
                + ", task='" + getTask() + "'"
                + "}";
    }
}
