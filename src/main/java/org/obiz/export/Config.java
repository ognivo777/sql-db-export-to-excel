package org.obiz.export;

import org.apache.commons.cli.*;

public class Config {

    private final Options options;
    private String connectString;
    private String user;
    private String passwd;
    private String queryFile;
    //    private String schema;

    public Config() {
        options = new Options();
        Option help = new Option("help", "print this message");
        options.addOption(help);

        Option url = Option.builder()
                .longOpt("jdbcUrl").option("j").hasArg()
                .argName("url").required().desc("JDBC connection url.").build();
        options.addOption(url);

        Option dbUser = Option.builder()
                .longOpt("user").option("u").hasArg()
                .argName("user").required().desc("Database user login.").build();
        options.addOption(dbUser);

        Option dbPassword = Option.builder().longOpt("password").option("p").hasArg()
                .argName("password").required().desc("Database user password.").build();
        options.addOption(dbPassword);

        Option sql = Option.builder().longOpt("sql").option("q").hasArg()
                .argName("query.sql").required().desc("File name with query.").build();
        options.addOption(sql);

//        Option dbSchema = Option.builder().longOpt("schema").option("s").hasArg()
//                .argName("schema").desc("Database schema. \"rules\" is used by default.").build();
//        options.addOption(dbSchema);

    }

    public Config load(String[] args) {
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            connectString = cmd.getOptionValue('j');
            user = cmd.getOptionValue('u');
            passwd = cmd.getOptionValue('p');
            queryFile = cmd.getOptionValue('q');
        } catch (Exception e) {
            System.err.println("Can not start.  Reason: " + e.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("db2xlsx", options);
            System.exit(0);
        }
        return this;
    }

    public String getConnectString() {
        return connectString;
    }

    public String getUser() {
        return user;
    }

    public String getPasswd() {
        return passwd;
    }

    public String getQueryFile() {
        return queryFile;
    }
}
