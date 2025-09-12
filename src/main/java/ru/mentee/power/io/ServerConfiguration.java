package ru.mentee.power.io;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class ServerConfiguration implements Serializable {
  @Serial
  private String serverAddress;
  private int serverPort;
  private boolean loggingEnabled;
  private transient String lastStatus = "Idle";
  private static final long serialVersionUID = 301L;



  public ServerConfiguration(String serverAddress, int serverPort, boolean loggingEnabled){
    this.serverAddress = serverAddress;
    this.serverPort = serverPort;
    this. loggingEnabled = loggingEnabled;
  }
  // Геттеры
  public String getServerAddress() {
    return serverAddress;
  }

  public int getServerPort() {
    return serverPort;
  }

  public boolean isLoggingEnabled() {
    return loggingEnabled;
  }

  public String getLastStatus() {
    return lastStatus;
  }

  public void setServerAddress(String serverAddress) {
    this.serverAddress = serverAddress;
  }

  public void setServerPort(int serverPort) {
    this.serverPort = serverPort;
  }

  public void setLoggingEnabled(boolean loggingEnabled) {
    this.loggingEnabled = loggingEnabled;
  }

  public void setLastStatus(String lastStatus) {
    this.lastStatus = lastStatus;
  }

  @Override
  public String toString() {
    return "ServerConfiguration{" +
        "serverAddress='" + serverAddress + '\'' +
        ", serverPort=" + serverPort +
        ", loggingEnabled=" + loggingEnabled +
        ", lastStatus='" + lastStatus + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    ServerConfiguration that = (ServerConfiguration) o;
    return serverPort == that.serverPort && loggingEnabled == that.loggingEnabled && Objects.equals(serverAddress, that.serverAddress) && Objects.equals(lastStatus, that.lastStatus);
  }

  @Override
  public int hashCode() {
    return Objects.hash(serverAddress, serverPort, loggingEnabled, lastStatus);
  }

  // Record конфигурации окна (автоматически Serializable)
  record WindowConfiguration(String windowTitle, int width, int height) implements Serializable {
    @Serial
    private static final long serialVersionUID = 302L; // Пример UID
    // Конструктор, геттеры, equals, hashCode, toString - сгенерированы
  }
}
