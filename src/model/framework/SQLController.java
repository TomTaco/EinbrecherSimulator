package model.framework;

import java.sql.*;

/**
 * Zur Benutzung dieser Klasse muss ein JDBC-Connector als Bibliothek in das Projekt eingebunden sein.
 */
public class SQLController {

    private Statement stmt;


    public SQLController() {
        //runDemo();


        try {
            // Erstelle eine Verbindung zu unserer SQL-Datenbank
            Connection con = DriverManager.getConnection("jdbc:mysql://mysql.webhosting24.1blu.de/db85565x2810214?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "s85565_2810214", "kkgbeste");
            stmt = con.createStatement();
            loadAllTables();
        } catch (Exception e) {
            e.printStackTrace();


        }
    }

    public void loadAllTables(){
        makeTable();
        fillTable();
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

                stmt.execute("SET foreign_key_checks = 0;");

                stmt.execute("DROP TABLE DD_has_Loot;");
                stmt.execute("DROP TABLE DD_District;");
                stmt.execute("DROP TABLE DD_House;");
                stmt.execute("DROP TABLE DD_Resident;");
                stmt.execute("DROP TABLE DD_Tool;");
                stmt.execute("DROP TABLE DD_Loot;");
                stmt.execute("DROP TABLE DD_Car;");
                stmt.execute("DROP TABLE DD_Valuables;");
                stmt.execute("DROP TABLE DD_Trader;");
                stmt.execute("DROP TABLE DD_Quest;");
                stmt.execute("DROP TABLE DD_Contact;");

            } catch (SQLException e){
                System.out.println(e);
                System.out.println("Tabelle nicht gelöscht.");
            }

            try {

                stmt.execute("CREATE TABLE DD_District(" +
                        "districtID int NOT NULL AUTO_INCREMENT," +
                        "name VARCHAR(255) NOT NULL,"+
                        "PRIMARY KEY (districtID)" +
                        ");");

                stmt.execute("CREATE TABLE DD_Resident(" +
                        "residentID int NOT NULL AUTO_INCREMENT, " +
                        "firstName VARCHAR(255), " +
                        "lastName VARCHAR(255)," +
                        "comeHome TIME NOT NULL, " +
                        "goesAway TIME NOT NULL," +
                        "PRIMARY KEY (residentID)" +
                        "); ");

                stmt.execute("CREATE TABLE DD_House(" +
                        "houseID  int NOT NULL AUTO_INCREMENT, " +
                        "security int NOT NULL," +
                        "districtID int NOT NULL," +
                        "tenantID int NOT NULL," +
                        "PRIMARY KEY (houseID)," +
                        "FOREIGN KEY (districtID) REFERENCES DD_District(districtID) ON DELETE CASCADE ," +
                        "FOREIGN KEY (tenantID) REFERENCES DD_Resident(residentID) ON DELETE CASCADE" +
                        "); ");

                stmt.execute("ALTER TABLE DD_Resident " +
                        "ADD houseID int NOT NULL," +
                        "ADD FOREIGN KEY(houseID) REFERENCES DD_House(houseID) " +
                        "ON DELETE CASCADE" +
                        ";");


                stmt.execute("CREATE TABLE DD_Loot(" +
                        "lootID int NOT NULL AUTO_INCREMENT," +
                        "price int NOT NULL, "  +
                        "term VARCHAR(255) NOT NULL, " +
                        "PRIMARY KEY (lootID)" +
                        "); ");

                stmt.execute("CREATE TABLE DD_Tool(" +
                        "toolID int NOT NULL AUTO_INCREMENT," +
                        "toollevel int NOT NULL, " +
                        "price int NOT NULL, " +
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


                stmt.execute("CREATE TABLE DD_Contact(" +
                        "contactID int NOT NULL AUTO_INCREMENT, "+
                        "information VARCHAR(255)," +
                        "PRIMARY KEY (contactID)" +
                        "); ");

                stmt.execute("CREATE TABLE DD_has_Loot(" +
                        "houseID int NOT NULL , "+
                        "lootID int NOT NULL , " +
                        "PRIMARY KEY (houseID, lootID), " +
                        "FOREIGN KEY (houseID) REFERENCES DD_House(houseID), "+
                        "FOREIGN KEY (lootID) REFERENCES DD_Loot(lootID) "+
                        "); ");


            } catch (SQLException e){
                System.out.println(e);
                System.out.println("Keine Tabellen erstellt");
            }
        }

        public void fillTable(){

            try {

                //Bewohner
                stmt.execute("INSERT INTO DD_Resident (firstname, lastname, comeHome, goesAway, houseID) " +
                        "VALUES ('Janet','Arm', 160000, 80000, 1);");
                stmt.execute("INSERT INTO DD_Resident (firstname, lastname, comeHome, goesAway, houseID) " +
                        "VALUES ('Jan','Reich', 160000, 80000, 1);");
                stmt.execute("INSERT INTO DD_Resident (firstname, lastname, comeHome, goesAway, houseID) " +
                        "VALUES ('Justin','Scholz', 160000, 80000, 2);");
                stmt.execute("INSERT INTO DD_Resident (firstname, lastname, comeHome, goesAway, houseID) " +
                        "VALUES ('Dang','Mich', 160000, 80000, 3);");
                stmt.execute("INSERT INTO DD_Resident (firstname, lastname, comeHome, goesAway, houseID) " +
                        "VALUES ('Kenbi','Knebellord', 160000, 80000, 12);");
                stmt.execute("INSERT INTO DD_Resident (firstname, lastname, comeHome, goesAway, houseID) " +
                        "VALUES ('Ambro','Ambrolord', 160000, 80000, 11);");
                stmt.execute("INSERT INTO DD_Resident (firstname, lastname, comeHome, goesAway, houseID) " +
                        "VALUES ('Tom','Taco', 160000, 80000, 9);");
                stmt.execute("INSERT INTO DD_Resident (firstname, lastname, comeHome, goesAway, houseID) " +
                        "VALUES ('Mister','KaputteKI', 160000, 80000, 7);");
                stmt.execute("INSERT INTO DD_Resident (firstname, lastname, comeHome, goesAway, houseID) " +
                        "VALUES ('Mister','Highman', 160000, 80000, 4);");
                stmt.execute("INSERT INTO DD_Resident (firstname, lastname, comeHome, goesAway, houseID) " +
                        "VALUES ('Marcel','Broooaaaun', 160000, 80000,5);");
                stmt.execute("INSERT INTO DD_Resident (firstname, lastname, comeHome, goesAway, houseID) " +
                        "VALUES ('Besl','Flor', 160000, 80000,6);");
                stmt.execute("INSERT INTO DD_Resident (firstname, lastname, comeHome, goesAway, houseID) " +
                        "VALUES ('Misses','Lowman', 160000, 80000, 4);");
                stmt.execute("INSERT INTO DD_Resident (firstname, lastname, comeHome, goesAway, houseID) " +
                        "VALUES ('Dufte','Dave', 160000, 80000, 10);");
                stmt.execute("INSERT INTO DD_Resident (firstname, lastname, comeHome, goesAway, houseID) " +
                        "VALUES ('Valentina','Kerman', 160000, 80000, 8);");
                stmt.execute("INSERT INTO DD_Resident (firstname, lastname, comeHome, goesAway, houseID) " +
                        "VALUES ('Jebediah','Kerman', 160000, 80000, 8);");
                //Viertel
                stmt.execute("INSERT INTO DD_District (name) " +
                        "VALUES ('Ghetto');");
                stmt.execute("INSERT INTO DD_District (name) " +
                        "VALUES ('Dorf');");
                stmt.execute("INSERT INTO DD_District (name) " +
                        "VALUES ('City');");
                stmt.execute("INSERT INTO DD_District (name) " +
                        "VALUES ('Bonzenviertel');");
                //Häuser
                stmt.execute("INSERT INTO DD_House (security, districtID, tenantID) " +
                        "VALUES ('1', '1', '2');");
                stmt.execute("INSERT INTO DD_House (security, districtID, tenantID) " +
                        "VALUES ('2', '1', '3');");
                stmt.execute("INSERT INTO DD_House (security, districtID, tenantID) " +
                        "VALUES ('3', '1', '4');");
                stmt.execute("INSERT INTO DD_House (security, districtID, tenantID) " +
                        "VALUES ('4', '2', '9');");
                stmt.execute("INSERT INTO DD_House (security, districtID, tenantID) " +
                        "VALUES ('5', '2', '10');");
                stmt.execute("INSERT INTO DD_House (security, districtID, tenantID) " +
                        "VALUES ('6', '2', '11');");
                stmt.execute("INSERT INTO DD_House (security, districtID, tenantID) " +
                        "VALUES ('10', '3', '8');");
                stmt.execute("INSERT INTO DD_House (security, districtID, tenantID) " +
                        "VALUES ('15', '3', '14');");
                stmt.execute("INSERT INTO DD_House (security, districtID, tenantID) " +
                        "VALUES ('20', '3', '7');");
                stmt.execute("INSERT INTO DD_House (security, districtID, tenantID) " +
                        "VALUES ('30', '4', '13');");
                stmt.execute("INSERT INTO DD_House (security, districtID, tenantID) " +
                        "VALUES ('40', '4', '5');");
                stmt.execute("INSERT INTO DD_House (security, districtID, tenantID) " +
                        "VALUES ('50', '4', '6');");
                //Autos
                stmt.execute("INSERT INTO DD_Car (carModel) " +
                        "VALUES ('Golf GTI');");
                stmt.execute("INSERT INTO DD_Car (carModel) " +
                        "VALUES ('Shirocco');");
                stmt.execute("INSERT INTO DD_Car (carModel) " +
                        "VALUES ('Prosche 911');");
                stmt.execute("INSERT INTO DD_Car (carModel) " +
                        "VALUES ('Ferrari 458');");
                stmt.execute("INSERT INTO DD_Car (carModel) " +
                        "VALUES ('Audi R8');");
                stmt.execute("INSERT INTO DD_Car (carModel) " +
                        "VALUES ('Lamborgini Huracan');");
                //Diebesware
                stmt.execute("INSERT INTO DD_Loot (term, price) " +
                        "VALUES ('Vase', '5');"); //1
                stmt.execute("INSERT INTO DD_Loot (term, price) " +
                        "VALUES ('Tasse', '10');"); //2
                stmt.execute("INSERT INTO DD_Loot (term, price) " +
                        "VALUES ('Teller', '15');"); //3
                stmt.execute("INSERT INTO DD_Loot (term, price) " +
                        "VALUES ('Besteck', '20');"); //4
                stmt.execute("INSERT INTO DD_Loot (term, price) " +
                        "VALUES ('Kleidung', '30');"); //5
                stmt.execute("INSERT INTO DD_Loot (term, price) " +
                        "VALUES ('Schuhe', '50');"); //6
                stmt.execute("INSERT INTO DD_Loot (term, price) " +
                        "VALUES ('Handy', '100');"); //7
                stmt.execute("INSERT INTO DD_Loot (term, price) " +
                        "VALUES ('Schmuck', '150');"); //8
                stmt.execute("INSERT INTO DD_Loot (term, price) " +
                        "VALUES ('Uhr', '200');"); //9
                stmt.execute("INSERT INTO DD_Loot (term, price) " +
                        "VALUES ('Konsole', '250');"); //10
                stmt.execute("INSERT INTO DD_Loot (term, price) " +
                        "VALUES ('Laptop', '300');"); //11
                stmt.execute("INSERT INTO DD_Loot (term, price) " +
                        "VALUES ('SamrtTv', '500');"); //12
                stmt.execute("INSERT INTO DD_Loot (term, price) " +
                        "VALUES ('Gaming PC', '1000');"); //13
                stmt.execute("INSERT INTO DD_Loot (term, price) " +
                        "VALUES ('Gold', '1500');"); //14
                stmt.execute("INSERT INTO DD_Loot (term, price) " +
                        "VALUES ('Diamanten', '2000');"); //15
                stmt.execute("INSERT INTO DD_Loot (term, price) " +
                        "VALUES ('Quanten PC', '2500');"); //16
                stmt.execute("INSERT INTO DD_Loot (term, price) " +
                        "VALUES ('Half Life 3 ', '5000');"); //17
                //Werkzeuge
                stmt.execute("INSERT INTO DD_Tool (term, toollevel, price) " +
                        "VALUES ('Ziegelstein', '1', '10');");
                stmt.execute("INSERT INTO DD_Tool (term, toollevel, price) " +
                        "VALUES ('Büroklammer', '2', '25');");
                stmt.execute("INSERT INTO DD_Tool (term, toollevel, price) " +
                        "VALUES ('Dietrich', '3', '50');");
                stmt.execute("INSERT INTO DD_Tool (term, toollevel, price) " +
                        "VALUES ('Bolzenschneider', '4', '100');");
                stmt.execute("INSERT INTO DD_Tool (term, toollevel, price) " +
                        "VALUES ('Brechstange', '5', '200');");
                stmt.execute("INSERT INTO DD_Tool (term, toollevel, price) " +
                        "VALUES ('Hammer', '6', '500');");
                stmt.execute("INSERT INTO DD_Tool (term, toollevel, price) " +
                        "VALUES ('Bohrer', '10', '1000');");
                stmt.execute("INSERT INTO DD_Tool (term, toollevel, price) " +
                        "VALUES ('Elektrischer Dietrich', '15', '2500');");
                stmt.execute("INSERT INTO DD_Tool (term, toollevel, price) " +
                        "VALUES ('Fensterschneider', '20', '5000');");
                stmt.execute("INSERT INTO DD_Tool (term, toollevel, price) " +
                        "VALUES ('C4', '30', '10000');");
                stmt.execute("INSERT INTO DD_Tool (term, toollevel, price) " +
                        "VALUES ('Laser', '40', '25000');");
                stmt.execute("INSERT INTO DD_Tool (term, toollevel, price) " +
                        "VALUES ('Polnischer Schlüssel', '50', '50000');");
                //Loot in den Häusern
                //Haus01
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('1', '1');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('1', '2');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('1', '3');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('1', '4');");
                //Haus02
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('2', '1');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('2', '2');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('2', '3');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('2', '4');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('2', '5');");
                //Haus03
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('3', '1');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('3', '2');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('3', '3');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('3', '4');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('3', '5');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('3', '6');");
                //Haus04
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('4', '1');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('4', '2');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('4', '3');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('4', '4');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('4', '5');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('4', '6');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('4', '7');");
                //Haus05
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('5', '1');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('5', '2');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('5', '3');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('5', '4');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('5', '5');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('5', '6');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('5', '7');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('5', '8');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('5', '9');");
                //Haus06
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('6', '1');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('6', '2');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('6', '3');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('6', '4');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('6', '5');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('6', '6');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('6', '7');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('6', '8');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('6', '9');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('6', '10');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('6', '11');");
                //Haus07
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('7', '7');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('7', '8');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('7', '9');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('7', '10');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('7', '11');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('7', '12');");
                //Haus08
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('8', '7');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('8', '8');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('8', '9');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('8', '10');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('8', '11');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('8', '12');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('8', '13');");
                //Haus09
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('9', '7');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('9', '8');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('9', '9');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('9', '10');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('9', '11');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('9', '12');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('9', '13');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('9', '14');");
                //Haus10
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('10', '12');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('10', '13');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('10', '14');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('10', '15');");
                //Haus11
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('11', '12');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('11', '13');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('11', '14');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('11', '15');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('11', '16');");
                //Haus12
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('12', '12');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('12', '13');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('12', '14');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('12', '15');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('12', '16');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('12', '17');");

























            } catch (SQLException e){
                System.out.println("Keine neue Tabelle gefüllt.");
                System.out.println(e);
            }

        }

        public Statement getStatement (){
            return stmt;
        }


}
