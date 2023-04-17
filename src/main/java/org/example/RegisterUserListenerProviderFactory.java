package org.example;

import org.keycloak.Config.Scope;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class RegisterUserListenerProviderFactory implements EventListenerProviderFactory {

  @Override
  public EventListenerProvider create(KeycloakSession keycloakSession) {
    return new RegisterUserListenerProvider(keycloakSession);
  }

  @Override
  public void init(Scope scope) {

  }

  @Override
  public void postInit(KeycloakSessionFactory keycloakSessionFactory) {

  }

  @Override
  public void close() {

  }

  @Override
  public String getId() {
    return "custom-user-registration";
  }
}
