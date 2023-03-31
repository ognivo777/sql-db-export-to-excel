# sql-db-export-to-excel
Simple but strong tool for export huge amount of data from database.

# Usage

Grab jar from [releases](https://github.com/ognivo777/sql-db-export-to-excel/releases) an run like this:
```sh
java -Xms32m -jar db_export_sql_to_excel-1.0-SNAPSHOT.jar -j jdbc:oracle:thin:@localhost:1521/mydb -p ora_passwd -u ora_oser -q myQuery.sql
```

After few seconds you'll found result in **exportResult.xlsx**.
