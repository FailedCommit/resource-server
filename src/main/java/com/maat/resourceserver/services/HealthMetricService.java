package com.maat.resourceserver.services;

import com.maat.resourceserver.entities.HealthMetric;
import com.maat.resourceserver.entities.HealthProfile;
import com.maat.resourceserver.exceptions.NonExistentHealthProfileException;
import com.maat.resourceserver.repositories.HealthMetricRepository;
import com.maat.resourceserver.repositories.HealthProfileRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HealthMetricService {

  private final HealthMetricRepository healthMetricRepository;
  private final HealthProfileRepository healthProfileRepository;

  public HealthMetricService(HealthMetricRepository healthMetricRepository, HealthProfileRepository healthProfileRepository) {
    this.healthMetricRepository = healthMetricRepository;
    this.healthProfileRepository = healthProfileRepository;
  }

  public void addHealthMetric(HealthMetric healthMetric) {
    Optional<HealthProfile> profile = healthProfileRepository
        .findHealthProfileByUsername(healthMetric.getProfile().getUsername());

    profile.ifPresentOrElse(
            p -> {
              healthMetric.setProfile(p);
              healthMetricRepository.save(healthMetric);
            },
            () -> {
              throw new NonExistentHealthProfileException("The profile doesn't exist");
            });

    ;
  }

  public List<HealthMetric> findHealthMetricHistory(String username) {
    return healthMetricRepository.findHealthMetricHistory(username);
  }

  @PreAuthorize("hasAuthority('admin')")
  public void deleteHealthMetricForUser(String username) {
    Optional<HealthProfile> profile = healthProfileRepository.findHealthProfileByUsername(username);

    profile.ifPresentOrElse(healthMetricRepository::deleteAllForUser,
            () -> {
              throw new NonExistentHealthProfileException("The profile doesn't exist");
            }
    );
  }
}
