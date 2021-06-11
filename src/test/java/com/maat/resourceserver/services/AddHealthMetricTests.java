package com.maat.resourceserver.services;

import com.maat.resourceserver.entities.HealthMetric;
import com.maat.resourceserver.entities.HealthProfile;
import com.maat.resourceserver.exceptions.NonExistentHealthProfileException;
import com.maat.resourceserver.repositories.HealthMetricRepository;
import com.maat.resourceserver.repositories.HealthProfileRepository;
import com.maat.resourceserver.services.context.TestUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class AddHealthMetricTests {

  @Autowired
  private HealthMetricService healthMetricService;

  @MockBean
  private HealthMetricRepository healthMetricRepository;

  @MockBean
  private HealthProfileRepository healthProfileRepository;

  @Test
  @TestUser(username = "john")
  @DisplayName("Considering a request is done to add a new metric record for the authenticated user," +
          " and the user profile exists " +
          " assert that the record is added to the database.")
  void addHealthMetricValidUserAuthenticatedTest() {
    HealthProfile healthProfile = new HealthProfile();
    healthProfile.setUsername("john");

    HealthMetric healthMetric = new HealthMetric();
    healthMetric.setProfile(healthProfile);

    when(healthProfileRepository.findHealthProfileByUsername(healthProfile.getUsername()))
            .thenReturn(Optional.of(healthProfile));

    healthMetricService.addHealthMetric(healthMetric);

    verify(healthMetricRepository).save(healthMetric);
  }

  @Test
  @TestUser(username = "john")
  @DisplayName("Considering a request is done to add a new metric record for the authenticated user," +
          " and the user profile deesn't exist assert that the record is not added to the database " +
          " and the app throws an exception.")
  void addHealthMetricValidUserAuthenticatedProfileDoesntExistTest() {
    HealthProfile healthProfile = new HealthProfile();
    healthProfile.setUsername("john");

    HealthMetric healthMetric = new HealthMetric();
    healthMetric.setProfile(healthProfile);

    when(healthProfileRepository.findHealthProfileByUsername(healthProfile.getUsername()))
            .thenReturn(Optional.empty());

    assertThrows(NonExistentHealthProfileException.class,
            () -> healthMetricService.addHealthMetric(healthMetric));

    verify(healthMetricRepository, never()).save(healthMetric);
  }

}
