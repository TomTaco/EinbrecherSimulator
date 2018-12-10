package model.framework;

import java.sql.*;

/**
 * Zur Benutzung dieser Klasse muss ein JDBC-Connector als Bibliothek in das Projekt eingebunden sein.
 */
public class SQL_Demo {

    public SQL_Demo(){
        //runDemo();
        makeTable();

    }

    /*public void runDemo(){

        try {
            // Erstelle eine Verbindung zu unserer SQL-Datenbank
            Connection con = DriverManager.getConnection("jdbc:mysql://mysql.webhosting24.1blu.de/db85565x2810214?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "s85565_2810214", "kkgbeste");
            Statement stmt = con.createStatement();

            // lösche die Tabelle, falls sie schon existiert
            try {

            } catch (Exception e){stmt.execute("DROP TABLE test_person;");
                System.out.println("Tabelle nicht gelöscht.");
            }

            // Lege eine neue Tabelle (wirft Exception, falls Tabelle schon vorhanden)
            try {
                stmt.execute("CREATE TABLE test_person (" +
                        "pID int NOT NULL AUTO_INCREMENT," +
                        "firstname varchar(255) NOT NULL," +
                        "lastname varchar(255) NOT NULL," +
                        "currentage int NOT NULL," +
                        "PRIMARY KEY (pID)" +
                        ");");
                } catch (Exception e){
                    System.out.println("Keine neue Tabelle angelegt.");
            }

            // Lege ein paar Datensätze in der Tabelle an (primary key wird ausgelassen wg. auto-inkrement => heißt aber man kann Leute auch doppelt anlegen)
            stmt.execute("INSERT INTO test_person (firstname, lastname, currentage) " +
                    "VALUES ('Peter', 'Pan', 14);");
            stmt.execute("INSERT INTO test_person (firstname, lastname, currentage) " +
                    "VALUES ('Jack', 'Ripperlein', 32);");
            stmt.execute("INSERT INTO test_person (firstname, lastname, currentage) " +
                    "VALUES ('Olaf', 'Steiner', 52);");
            stmt.execute("INSERT INTO test_person (firstname, lastname, currentage) " +
                    "VALUES ('Klaus', 'Kloppmann', 41);");
            stmt.execute("INSERT INTO test_person (firstname, lastname, currentage) " +
                    "VALUES ('Bese', 'Flor', 16);");

            // Gib die gesamte Tabelle test_person aus
            ResultSet results = stmt.executeQuery("SELECT * FROM test_person;");

            System.out.println("ID(primary key) - Vorname - Nachname - Alter");
            while(results.next()){
                System.out.println(results.getString(1) + " - " +results.getString(2) + " - " + results.getString(3) + " - " + results.getString(4));
            }

        } catch(Exception e){
            e.printStackTrace();
        }*/

        public void makeTable(){
            try {
            // Erstelle eine Verbindung zu unserer SQL-Datenbank
            Connection con = DriverManager.getConnection("jdbc:mysql://mysql.webhosting24.1blu.de/db85565x2810214?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "s85565_2810214", "kkgbeste");
            Statement stmt = con.createStatement();

            try {

                stmt.execute("CREATE TABLE District IF NOT EXISTS (" +
                        "districtID NOT NULL AUTO_INCREMENT PRIMARY KEY ," +
                        "name VARCHAR(255) NOT NULL,");

                stmt.execute("CREATE TABLE House IF NOT EXISTS(" +
                        "houseID NOT NULL AUTO_INCREMENT PRIMARY KEY " +
                        "security INT NOT NULL" +
                        " ");

                stmt.execute("CREATE TABLE Resident IF NOT EXISTS(" +
                        "residentID NOT NULL AUTO_INCREMENT PRIMARY KEY " +
                        "name VARCHAR(255)) " +
                        "comeHome TIME NOT NULL " +
                        "goesAway TIME NOT NULL");

                stmt.execute("CREATE TABLE Loot IF NOT EXISTS(" +
                        "lootID NOT NULL AUTO_INCREMENT PRIMARY KEY " +
                        "term VARCHAR(255)) NOT NULL ");

                stmt.execute("CREATE TABLE Tool IF NOT EXISTS(" +
                        "toolID NOT NULL AUTO_INCREMENT PRIMARY KEY " +
                        "name VARCHAR (255))" +
                        " ");

                stmt.execute("CREATE TABLE Car IF NOT EXISTS ("+
                        "carID NOT NULL AUTO_INCREMENT PRIMARY KEY "+
                        "carModel VARCHAR (255))");

                stmt.execute("CREATE TABLE Valuables IF NOT EXISTS ("+ "" +
                        "valuablesID NOT NULL AUTO_INCREMENT PRIMARY KEY " +
                        "term VARCHAR (255))");

                stmt.execute("CREATE TABLE Trader IF NOT EXISTS(" +
                        "traderID NOT NULL AUTO_INCREMENT PRIMARY KEY " +
                        "name VARCHART (255))");

                stmt.execute("CREATE TABLE Quest IF NOT EXISTS(" +
                        "questID NOT NULL AUTO_INCREMENT PRIMARY KEY ");

                stmt.execute("CREATE TABLE Conatct IF NOT EXIST (" +
                        "conatctID NOT NULL AUTO_INCREMENT PRIMARY KEY "+
                        "information VARCHAR(255))");

                System.out.println("hello!");





            } catch (Exception e){
                System.out.println("Keine neue Tabelle angelegt.");
            }
        } catch(Exception e){
        e.printStackTrace();

        }




    }

}
