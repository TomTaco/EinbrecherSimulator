package model.framework;

import java.sql.*;

/**
 * Zur Benutzung dieser Klasse muss ein JDBC-Connector als Bibliothek in das Projekt eingebunden sein.
 */
public class SQL_Demo {

    private Statement stmt;


    public SQL_Demo() {
        //runDemo();


        try {
            // Erstelle eine Verbindung zu unserer SQL-Datenbank
            Connection con = DriverManager.getConnection("jdbc:mysql://mysql.webhosting24.1blu.de/db85565x2810214?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "s85565_2810214", "kkgbeste");
            stmt = con.createStatement();
            makeTable();
            fillTable();
        } catch (Exception e) {
            e.printStackTrace();


        }
    }

    /*public void runDemo(){

        try {
            // Erstelle eine Verbindung zu unserer SQL-Datenbank
            Connection con = DriverManager.getConnection("jdbc:mysql://mysql.webhosting24.1blu.de/db85565x2810214?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "s85565_2810214", "kkgbeste");
            Statement stmt = con.createStatement();

            // lösche die Tabelle, falls sie schon existiert
            try {
                stmt.execute("DROP TABLE test_person;");
            } catch (Exception e){
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
                stmt.execute("DROP TABLE DD_District;");
                stmt.execute("DROP TABLE DD_House;");
                stmt.execute("DROP TABLE DD_Resident;");
                stmt.execute("DROP TABLE DD_Loot;");
                stmt.execute("DROP TABLE DD_Car;");
                stmt.execute("DROP TABLE DD_Valuables;");
                stmt.execute("DROP TABLE DD_Trader;");
                stmt.execute("DROP TABLE DD_Quest;");
                stmt.execute("DROP TABLE DD_Conatct;");

            } catch (Exception e){
                System.out.println("Tabelle nicht gelöscht.");
            }

            try {

                stmt.execute("CREATE TABLE DD_District(" +
                        "districtID int NOT NULL AUTO_INCREMENT," +
                        "name VARCHAR(255) NOT NULL,"+
                        "PRIMARY KEY (districtID)" +
                        ");");

                stmt.execute("CREATE TABLE DD_House(" +
                        "houseID  int NOT NULL AUTO_INCREMENT, " +
                        "security INT NOT NULL," +
                        "PRIMARY KEY (houseID)" +
                        "); ");

                stmt.execute("CREATE TABLE DD_Resident(" +
                        "residentID int NOT NULL AUTO_INCREMENT, " +
                        "firstName VARCHAR(255), " +
                        "lastName VARCHAR(255)," +
                        "comeHome TIME NOT NULL, " +
                        "goesAway TIME NOT NULL," +
                        "PRIMARY KEY (residentID)" +
                        "); ");


                stmt.execute("CREATE TABLE DD_Loot(" +
                        "lootID int NOT NULL AUTO_INCREMENT," +
                        "term VARCHAR(255) NOT NULL, " +
                        "PRIMARY KEY (lootID)" +
                        "); ");

                stmt.execute("CREATE TABLE DD_Tool(" +
                        "toolID int NOT NULL AUTO_INCREMENT," +
                        "term VARCHAR(255) NOT NULL, " +
                        "PRIMARY KEY (toolID)" +
                        "); ");

                stmt.execute("CREATE TABLE DD_Car("+
                        "carID int NOT NULL AUTO_INCREMENT,"+
                        "carModel VARCHAR (255)," +
                        "PRIMARY KEY (carID)" +
                        "); ");

                stmt.execute("CREATE TABLE DD_Valuables("+
                        "valuablesID int NOT NULL AUTO_INCREMENT, " +
                        "term VARCHAR (255),"+
                        "PRIMARY KEY (valuablesID)" +
                        "); ");

                stmt.execute("CREATE TABLE DD_Trader(" +
                        "traderID int NOT NULL AUTO_INCREMENT, " +
                        "name VARCHAR (255)," +
                        "PRIMARY KEY (traderID)" +
                        "); ");

                stmt.execute("CREATE TABLE DD_Quest(" +
                        "questID int NOT NULL AUTO_INCREMENT, " +
                        "PRIMARY KEY (questID)" +
                        "); ");


                stmt.execute("CREATE TABLE DD_Conatct(" +
                        "conatctID int NOT NULL AUTO_INCREMENT, "+
                        "information VARCHAR(255)," +
                        "PRIMARY KEY (contactID)" +
                        "); ");






            } catch (SQLException e){
                System.out.println(e);
            }
        }

        public void fillTable(){

            try {

            stmt.execute("INSERT INTO DD_Resident (firstname, lastname, comeHome, goesAway) " +
                    "VALUES ('Janet', 'Arm', 16, 8);" +
                    "VALUES ('Jan', 'Reich', 0, 23 );" +
                    "VALUES ('Justin', 'Scholz', 8 ,16)" +
                    "VALUES ('Michael','Dang',16 ,8 );" +
                    "VALUES ('Alex','Heighman', 16 , 8);" +
                    "VALUES ('Marcel','Braun', 16, 8);" +
                    "VALUES ('Haydar','Genc', 16, 8);" +
                    "VALUES ('Artur','Lyadsky', 16, 8);" +
                    "VALUES ('Joshy','Eulberg', 16, 8);" +
                    "VALUES ('Luis','Böhme', , );" +
                    "VALUES ('David','Junowitsch', 16, 8);" +
                    "VALUES ('David','Kopyra', 16, 8);" +
                    "VALUES ('Ambro','Ambro', 16, 8);" +
                    "VALUES ('Knebi','Knebi', 16, 8);");

            } catch (SQLException e){
                System.out.println("Keine neue Tabelle angelegt.");
            }

        }


}
