package org.obiz.export;

import org.dhatim.fastexcel.Workbook;

import java.io.*;
import java.sql.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class Loader {
    private Connection connection;
    private String query;

    public Loader(Connection connection, String query) {
        this.connection = connection;
        this.query = query;
    }

    public File doExport(int batch, String outputFile) {
        try (OutputStream os =  new FileOutputStream(outputFile)) {
            Workbook wb = new Workbook(os, "DbExporter", "1.0");

            Thread.sleep(1);
//            Thread.sleep(1456);

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setFetchSize(batch);
            Instant start = Instant.now();
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("DB execution time: " + start.until(Instant.now(), ChronoUnit.MILLIS)/1000f);
            System.out.println("Batch size = " + batch);
            RowProcessor processor = new RowProcessor(resultSet.getMetaData(), wb, batch);

            while(resultSet.next()) {
                processor.consumeRow(resultSet);
            }
            wb.finish();

        } catch (InterruptedException | SQLException | IOException e) {
            e.printStackTrace();
        }
        //TODO
        return new File(outputFile);
    }
}
