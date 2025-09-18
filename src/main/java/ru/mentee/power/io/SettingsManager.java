package ru.mentee.power.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SettingsManager {
  /**
   * Сохраняет список Serializable объектов в файл.
   *
   * @param settings Список объектов (может содержать ServerConfiguration, WindowConfiguration и др. Serializable).
   * @param filename Имя файла.
   */
  public static void saveSettings(List<Serializable> settings, String filename) {
    try (FileOutputStream fos = new FileOutputStream(filename);
         BufferedOutputStream bos = new BufferedOutputStream(fos);
         ObjectOutputStream oos = new ObjectOutputStream(bos);
    ) {
      oos.writeObject(settings);
      System.out.println("Список настроек сохранен в " + filename);

    } catch (IOException e) {
      System.err.println("Ошибка при сохранении настроек: " + e.getMessage());
    }
  }

  /**
   * Загружает список Serializable объектов из файла.
   *
   * @param filename Имя файла.
   * @return Загруженный список Serializable объектов или пустой список в случае ошибки.
   */
  @SuppressWarnings("unchecked")
  public static List<Serializable> loadSettings(String filename) {
    File file = new File(filename);
    if (!file.exists()) {
      System.out.println("Файл настроек " + filename + " не найден.");
      return new ArrayList<>();
    }

    try (FileInputStream fis = new FileInputStream(file);
         BufferedInputStream bis = new BufferedInputStream(fis);
         ObjectInputStream ois = new ObjectInputStream(bis)) {

      List<Serializable> loadedSettings = (List<Serializable>) ois.readObject();
      System.out.println("Список настроек загружен из " + filename);
      return loadedSettings;

    } catch (IOException | ClassNotFoundException | ClassCastException e) {
      System.err.println("Ошибка при загрузке настроек: " + e.getMessage());
      return new ArrayList<>();
    }
  }

  public static void main(String[] args) {
    String filename = "settings.ser";

    // 1. Создать список с разными типами конфигураций
    List<Serializable> currentSettings = new ArrayList<>();
    currentSettings.add(new ServerConfiguration("prod.server.com", 443, true));
    currentSettings.add(new ServerConfiguration.WindowConfiguration("Основное Окно Приложения", 1280, 720));

    // Демонстрация transient поля
    if (!currentSettings.isEmpty() && currentSettings.get(0) instanceof ServerConfiguration) {
      ((ServerConfiguration) currentSettings.get(0)).setLastStatus("Перед сохранением");
    }
    System.out.println("Сохраняем список: " + currentSettings);

    // 2. Сохранить список
    saveSettings(currentSettings, filename);

    // 3. Загрузить список
    System.out.println("\nЗагружаем список...");
    List<Serializable> loadedSettings = loadSettings(filename);

    // 4. Вывести результат и проверить типы/данные
    if (loadedSettings.isEmpty()) {
      System.out.println("Не удалось загрузить настройки.");
    } else {
      System.out.println("Загруженный список: " + loadedSettings);
      System.out.println("--- Проверка объектов в списке ---");
      for (Serializable setting : loadedSettings) {
        if (setting instanceof ServerConfiguration configClass) { // Pattern matching for instanceof (Java 16+)
          System.out.println("  Загружен Server Config: server=" + configClass.getServerAddress() +
              ", status=" + configClass.getLastStatus()); // Статус должен быть null или Idle
        } else if (setting instanceof ServerConfiguration.WindowConfiguration configRecord) {
          System.out.println("  Загружен Window Config: title=" + configRecord.windowTitle() +
              ", size=" + configRecord.width() + "x" + configRecord.height());
        } else {
          System.out.println("  Загружен неизвестный тип: " + setting.getClass());
        }
      }
    }

    // Очистка файла после демонстрации
    new File(filename).delete();
  }
}
