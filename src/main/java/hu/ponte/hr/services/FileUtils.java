package hu.ponte.hr.services;

import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileUtils {

  public static byte[] getResourceAsByteArray(String path) {
    byte[] bytes = null;
    try {
      File file = new ClassPathResource(path).getFile();
      bytes = Files.readAllBytes(file.toPath());
    } catch (IOException e) {
      e.printStackTrace();
    }

    return bytes;
}
  }
