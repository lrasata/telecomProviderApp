package com.lrasata.telecomproviderapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.lrasata.telecomproviderapp.domain.MobilePlan} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MobilePlanDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Integer internetDataInGB;

    @NotNull
    private Boolean unlimitedSmsAndCalls;

    @NotNull
    private Float price;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getInternetDataInGB() {
        return internetDataInGB;
    }

    public void setInternetDataInGB(Integer internetDataInGB) {
        this.internetDataInGB = internetDataInGB;
    }

    public Boolean getUnlimitedSmsAndCalls() {
        return unlimitedSmsAndCalls;
    }

    public void setUnlimitedSmsAndCalls(Boolean unlimitedSmsAndCalls) {
        this.unlimitedSmsAndCalls = unlimitedSmsAndCalls;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MobilePlanDTO)) {
            return false;
        }

        MobilePlanDTO mobilePlanDTO = (MobilePlanDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, mobilePlanDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MobilePlanDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", internetDataInGB=" + getInternetDataInGB() +
            ", unlimitedSmsAndCalls='" + getUnlimitedSmsAndCalls() + "'" +
            ", price=" + getPrice() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
