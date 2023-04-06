package com.marpe.cht.export.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import com.marpe.cht.entities.User;

public class CSVHelper {

  public static ByteArrayInputStream usersToCSV(List<User> users) {
    final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
      for (User user : users) {
        List<String> data = Arrays.asList(
              String.valueOf(user.getId()),
              user.getNome(),
              user.getEmail(),
              user.getTelefone()
            );

        csvPrinter.printRecord(data);
      }

      csvPrinter.flush();
      return new ByteArrayInputStream(out.toByteArray());
    } catch (IOException e) {
      throw new RuntimeException("Failed to import data to CSV file: " + e.getMessage());
    }
  }
}