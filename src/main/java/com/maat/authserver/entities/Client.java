package com.maat.authserver.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Data
public class Client {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "client_id")
  private String clientId;

  private String secret;

  private String scope;

  @Column(name = "redirect_uri")
  private String redirectURI;

  @OneToMany(mappedBy = "client", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
  private List<ClientGrantType> grantTypes;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Client client = (Client) o;
    return id == client.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
