package ru.mentee.power.io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тесты корректности копирования (PerformanceComparison)")
class PerformanceComparisonCorrectnessTest {

  @TempDir
  Path tempDir;

  private Path sourceFile;
  private Path destBufferedFile;
  private Path destUnbufferedFile;
  private PerformanceComparison performanceComparison;

  @BeforeEach
  void setUp(@TempDir Path tempDir) throws IOException {
    this.tempDir = tempDir;
    sourceFile = tempDir.resolve("source.bin");
    destBufferedFile = tempDir.resolve("dest_buffered.bin");
    destUnbufferedFile = tempDir.resolve("dest_unbuffered.bin");
    performanceComparison = new PerformanceComparison();

    // Создадим небольшой тестовый файл
    byte[] testData = new byte[1024 * 5]; // 5 KB
    for (int i = 0; i < testData.length; i++) testData[i] = (byte) i;
    Files.write(sourceFile, testData);
  }

  @Test
  @DisplayName("copyBuffered должен корректно копировать файл")
  void copyBufferedShouldCopyFileCorrectly() throws IOException {
    performanceComparison.copyBuffered(sourceFile, destBufferedFile);
    assertThat(destBufferedFile).exists();
    assertThat(Files.mismatch(sourceFile, destBufferedFile)).isEqualTo(-1L);
  }

  @Test
  @DisplayName("copyUnbuffered должен корректно копировать файл")
  void copyUnbufferedShouldCopyFileCorrectly() throws IOException {
    performanceComparison.copyUnbuffered(sourceFile, destUnbufferedFile);
    assertThat(destUnbufferedFile).exists();
    assertThat(Files.mismatch(sourceFile, destUnbufferedFile)).isEqualTo(-1L);
  }

  @Test
  @DisplayName("createLargeBinaryFile должен создавать файл нужного размера")
  void createLargeBinaryFileShouldCreateFileOfCorrectSize() throws IOException {
    Path largeFile = tempDir.resolve("test_large.bin");
    long expectedSize = 1024 * 10; // 10 KB для теста
    performanceComparison.createLargeBinaryFile(largeFile, expectedSize);
    assertThat(largeFile).exists();
    assertThat(Files.size(largeFile)).isEqualTo(expectedSize);
  }
}

// Sample PerformanceComparison class for testing purposes
class PerformanceComparison {

  public void copyBuffered(Path source, Path destination) throws IOException {
    try (InputStream in = new BufferedInputStream(Files.newInputStream(source));
         OutputStream out = new BufferedOutputStream(Files.newOutputStream(destination))) {
      byte[] buffer = new byte[1024];
      int bytesRead;
      while ((bytesRead = in.read(buffer)) != -1) {
        out.write(buffer, 0, bytesRead);
      }
    }
  }

  public void copyUnbuffered(Path source, Path destination) throws IOException {
    try (InputStream in = Files.newInputStream(source);
         OutputStream out = Files.newOutputStream(destination)) {
      int byteRead;
      while ((byteRead = in.read()) != -1) {
        out.write(byteRead);
      }
    }
  }

  public void createLargeBinaryFile(Path file, long size) throws IOException {
    try (OutputStream out = Files.newOutputStream(file)) {
      byte[] buffer = new byte[1024];
      long bytesWritten = 0;
      while (bytesWritten < size) {
        int bytesToWrite = (int) Math.min(1024, size - bytesWritten);
        out.write(buffer, 0, bytesToWrite);
        bytesWritten += bytesToWrite;
      }
    }
  }
}