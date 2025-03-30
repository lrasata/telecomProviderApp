package com.lrasata.telecomproviderapp.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A MobilePlan.
 */
@Entity
@Table(name = "mobile_plan")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MobilePlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "internet_data_in_gb", nullable = false)
    private Integer internetDataInGB;

    @NotNull
    @Column(name = "unlimited_sms_and_calls", nullable = false)
    private Boolean unlimitedSmsAndCalls;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MobilePlan id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public MobilePlan name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getInternetDataInGB() {
        return this.internetDataInGB;
    }

    public MobilePlan internetDataInGB(Integer internetDataInGB) {
        this.setInternetDataInGB(internetDataInGB);
        return this;
    }

    public void setInternetDataInGB(Integer internetDataInGB) {
        this.internetDataInGB = internetDataInGB;
    }

    public Boolean getUnlimitedSmsAndCalls() {
        return this.unlimitedSmsAndCalls;
    }

    public MobilePlan unlimitedSmsAndCalls(Boolean unlimitedSmsAndCalls) {
        this.setUnlimitedSmsAndCalls(unlimitedSmsAndCalls);
        return this;
    }

    public void setUnlimitedSmsAndCalls(Boolean unlimitedSmsAndCalls) {
        this.unlimitedSmsAndCalls = unlimitedSmsAndCalls;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MobilePlan)) {
            return false;
        }
        return getId() != null && getId().equals(((MobilePlan) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MobilePlan{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", internetDataInGB=" + getInternetDataInGB() +
            ", unlimitedSmsAndCalls='" + getUnlimitedSmsAndCalls() + "'" +
            "}";
    }
}
