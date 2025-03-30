package com.lrasata.telecomproviderapp.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A ApplicationUser.
 */
@Entity
@Table(name = "application_user")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApplicationUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "phone_number", nullable = false)
    private Integer phoneNumber;

    @NotNull
    @Column(name = "wallet", nullable = false)
    private Integer wallet;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private User internalUser;

    @ManyToOne(fetch = FetchType.LAZY)
    private MobilePlan chosenMobilePlan;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ApplicationUser id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPhoneNumber() {
        return this.phoneNumber;
    }

    public ApplicationUser phoneNumber(Integer phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getWallet() {
        return this.wallet;
    }

    public ApplicationUser wallet(Integer wallet) {
        this.setWallet(wallet);
        return this;
    }

    public void setWallet(Integer wallet) {
        this.wallet = wallet;
    }

    public User getInternalUser() {
        return this.internalUser;
    }

    public void setInternalUser(User user) {
        this.internalUser = user;
    }

    public ApplicationUser internalUser(User user) {
        this.setInternalUser(user);
        return this;
    }

    public MobilePlan getChosenMobilePlan() {
        return this.chosenMobilePlan;
    }

    public void setChosenMobilePlan(MobilePlan mobilePlan) {
        this.chosenMobilePlan = mobilePlan;
    }

    public ApplicationUser chosenMobilePlan(MobilePlan mobilePlan) {
        this.setChosenMobilePlan(mobilePlan);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicationUser)) {
            return false;
        }
        return getId() != null && getId().equals(((ApplicationUser) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicationUser{" +
            "id=" + getId() +
            ", phoneNumber=" + getPhoneNumber() +
            ", wallet=" + getWallet() +
            "}";
    }
}
