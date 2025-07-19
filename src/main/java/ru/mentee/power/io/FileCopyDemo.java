package ru.mentee.power.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

public class FileCopyDemo {

  public static void main(String[] args) {
    String sourceFileName = "source.txt";
    String destinationFileName = "copy_of_source.txt";
    Path sourcePath = Paths.get(sourceFileName);

    try {
      if (!Files.exists(sourcePath)){
        Files.writeString(sourcePath,  "Текст по умолчанию...", StandardCharsets.UTF_8 );
      }
    }catch (IOException e){
      System.err.println("Ошибка при создании " + e.getMessage());
    }

    int bufferSize = 4096; // Размер буфера (например, 4KB)
    byte[] buffer = new byte[bufferSize];

    try (FileInputStream fis = new FileInputStream(sourceFileName);
          FileOutputStream fos = new FileOutputStream(destinationFileName)) {
      int bytesRead;
       while ((bytesRead = fis.read(buffer)) != -1) {
         System.out.println(bytesRead + " ");
         fos.write(buffer, 0 , bytesRead);
       }

      System.out.println("Файл успешно скопирован из " + sourceFileName + " в " + destinationFileName);

    } catch (IOException e) {
      System.err.println("Ошибка при копировании файла: " + e.getMessage());
      e.printStackTrace(); // Для отладки
    }
  }
}