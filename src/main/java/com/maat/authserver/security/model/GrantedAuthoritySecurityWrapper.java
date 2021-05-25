package com.maat.authserver.security.model;

import com.maat.authserver.entities.Authority;
import org.springframework.security.core.GrantedAuthority;

public class GrantedAuthoritySecurityWrapper
        implements GrantedAuthority {

  private final Authority authority;

  public GrantedAuthoritySecurityWrapper(Authority authority) {
    this.authority = authority;
  }

  @Override
  public String getAuthority() {
    return authority.getName();
  }
}
