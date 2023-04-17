package org.example;

import java.util.Objects;
import javax.persistence.EntityManager;
import org.keycloak.common.util.SecretGenerator;
import org.keycloak.connections.jpa.JpaConnectionProvider;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.events.admin.OperationType;
import org.keycloak.events.admin.ResourceType;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RealmProvider;
import org.keycloak.models.jpa.entities.UserAttributeEntity;
import org.keycloak.models.jpa.entities.UserEntity;

public class RegisterUserListenerProvider implements EventListenerProvider {

  private KeycloakSession session;

  private RealmProvider model;

  private EntityManager entityManager;

  private SecretGenerator secretGenerator;


  public RegisterUserListenerProvider(KeycloakSession session) {
    this.session = session;
    this.model = session.realms();
    this.entityManager = session.getProvider(JpaConnectionProvider.class).getEntityManager();
    this.secretGenerator = SecretGenerator.getInstance();
  }
  @Override
  public void onEvent(Event event) {
    if (event.getType().equals(EventType.REGISTER)) {
      RealmModel realm = model.getRealm(event.getRealmId());
      String userId = event.getUserId();
      addApiKeyAttribute(userId);
    }
  }

  @Override
  public void onEvent(AdminEvent adminEvent, boolean b) {
    if (Objects.equals(adminEvent.getResourceType(), ResourceType.USER)
        && Objects.equals(adminEvent.getOperationType(), OperationType.CREATE)) {
      String userId = adminEvent.getResourcePath().split("/")[1];
      if (Objects.nonNull(userId)) {
        addApiKeyAttribute(userId);
      }
    }
  }

  private void addApiKeyAttribute(String userId) {

    String apiKey = secretGenerator.randomString(50);
    UserEntity userEntity = entityManager.find(UserEntity.class, userId);

    UserAttributeEntity attributeEntity = new UserAttributeEntity();
    attributeEntity.setName("api-key");
    attributeEntity.setUser(userEntity);
    attributeEntity.setId(userId);
    attributeEntity.setValue(apiKey);

    entityManager.persist(attributeEntity);

  }

  @Override
  public void close() {
    //do something when destroy instance
  }
}
