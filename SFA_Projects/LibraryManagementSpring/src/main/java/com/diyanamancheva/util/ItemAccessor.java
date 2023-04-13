package com.diyanamancheva.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ItemAccessor {

    //private static final String ITEMS_FILE_PATH = "src/main/java/com/diyanamancheva/Clients.txt";

    private static final String FILE_NOT_FOUND_MESSAGE = "File not found with path ";

    public void readItem(String string, String ITEMS_FILE_PATH) {
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(ITEMS_FILE_PATH, true))) {
        writer.write(string + "\n");
      } catch (IOException e) {
        throw new RuntimeException(FILE_NOT_FOUND_MESSAGE, e);
      }
    }

    public List<String> readAllItems(String ITEMS_FILE_PATH) {
      try (BufferedReader reader = new BufferedReader(new FileReader(ITEMS_FILE_PATH))) {
        return reader.lines().collect(Collectors.toList());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    public void overwriteFile(String items, String ITEMS_FILE_PATH) {
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(ITEMS_FILE_PATH))) {
        writer.write(items);
      } catch (IOException e) {
        throw new RuntimeException(FILE_NOT_FOUND_MESSAGE, e);
      }
    }

}
