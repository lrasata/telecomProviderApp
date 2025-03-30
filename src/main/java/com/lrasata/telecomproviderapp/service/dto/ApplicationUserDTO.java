package com.lrasata.telecomproviderapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.lrasata.telecomproviderapp.domain.ApplicationUser} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApplicationUserDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer phoneNumber;

    @NotNull
    private Integer wallet;

    private UserDTO internalUser;

    private MobilePlanDTO chosenMobilePlan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getWallet() {
        return wallet;
    }

    public void setWallet(Integer wallet) {
        this.wallet = wallet;
    }

    public UserDTO getInternalUser() {
        return internalUser;
    }

    public void setInternalUser(UserDTO internalUser) {
        this.internalUser = internalUser;
    }

    public MobilePlanDTO getChosenMobilePlan() {
        return chosenMobilePlan;
    }

    public void setChosenMobilePlan(MobilePlanDTO chosenMobilePlan) {
        this.chosenMobilePlan = chosenMobilePlan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicationUserDTO)) {
            return false;
        }

        ApplicationUserDTO applicationUserDTO = (ApplicationUserDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, applicationUserDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicationUserDTO{" +
            "id=" + getId() +
            ", phoneNumber=" + getPhoneNumber() +
            ", wallet=" + getWallet() +
            ", internalUser=" + getInternalUser() +
            ", chosenMobilePlan=" + getChosenMobilePlan() +
            "}";
    }
}
