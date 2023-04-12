package org.obiz.export;

import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

public class RowProcessor {

    private final int columnCount;
    private final Worksheet ws;
    private Workbook wb;
    private final ArrayList<String> columnNames;
    private final ArrayList<Integer> columnTypes;
    int currrentRow = 0;
    private int batch;

    public RowProcessor(ResultSetMetaData metaData, Workbook wb, int batch) throws SQLException {
        columnCount = metaData.getColumnCount();
        this.wb = wb;
        this.batch = batch;
        columnNames = new ArrayList<>();
        columnTypes = new ArrayList<>();
        ws = wb.newWorksheet("Export data");
        for (int i = 0; i < columnCount; i++) {
            ws.value(currrentRow, i, metaData.getColumnName(i+1));
            columnNames.add(metaData.getColumnName(i+1));
            columnTypes.add(metaData.getColumnType(i+1));
        }
        currrentRow++;

    }

    public void consumeRow(ResultSet resultSet) throws SQLException, IOException {
        for (int i = 0; i < columnCount; i++) {
            fillCell(i, resultSet);
        }
        currrentRow++;
        if(currrentRow%batch==0) {
            System.out.print("|");
            if(currrentRow%(batch * 100)==0) {
                System.out.println(" " + currrentRow);
            }
            ws.flush();
        }
    }

    private void fillCell(int i, ResultSet resultSet) throws SQLException {
        final int dbColumnIndex = i + 1;
        switch (columnTypes.get(i)) {
            case Types.NUMERIC, Types.DECIMAL, Types.INTEGER, Types.TINYINT -> ws.value(currrentRow,i, resultSet.getLong(dbColumnIndex));
            case Types.REAL, Types.FLOAT, Types.DOUBLE ->  ws.value(currrentRow,i, resultSet.getDouble(dbColumnIndex));
            case Types.DATE, Types.TIME, Types.TIME_WITH_TIMEZONE, Types.TIMESTAMP, Types.TIMESTAMP_WITH_TIMEZONE ->  ws.value(currrentRow,i, resultSet.getDate(dbColumnIndex));
            default -> ws.value(currrentRow,i, resultSet.getString(dbColumnIndex));
        }
    }

}
