package com.lrasata.telecomproviderapp.repository;

import com.lrasata.telecomproviderapp.domain.MobilePlan;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MobilePlan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MobilePlanRepository extends JpaRepository<MobilePlan, Long> {}
