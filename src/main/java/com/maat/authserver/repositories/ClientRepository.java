package com.maat.authserver.repositories;

import com.maat.authserver.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Integer> {

  Optional<Client> findClientByClientId(String clientId);
}
