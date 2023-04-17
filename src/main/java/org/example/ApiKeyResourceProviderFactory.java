package org.example;

import org.keycloak.Config.Scope;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.services.resource.RealmResourceProvider;
import org.keycloak.services.resource.RealmResourceProviderFactory;

public class ApiKeyResourceProviderFactory implements RealmResourceProviderFactory {

  @Override
  public RealmResourceProvider create(KeycloakSession keycloakSession) {
    return new ApiKeyResourceProvider(keycloakSession);
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
    return "check";
  }
}
