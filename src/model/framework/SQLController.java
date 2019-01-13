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
            //loadAllTables();
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

                stmt.execute("DROP TABLE DD_PlayerData");
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
                        "carID int ," +
                        "PRIMARY KEY (houseID)," +
                        "FOREIGN KEY (districtID) REFERENCES DD_District(districtID) ON DELETE CASCADE ," +
                        "FOREIGN KEY (tenantID) REFERENCES DD_Resident(residentID) ON DELETE CASCADE ," +
                        "FOREIGN KEY (carID) REFERENCES DD_Car(carID) ON DELETE CASCADE" +
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
                        "difficulty int NOT NULL," +
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
                        "price int NOT NULL, " +
                        "difficulty int NOT NULL," +
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

                stmt.execute("CREATE TABLE DD_PlayerData(" +
                        "playerID int NOT NULL AUTO_INCREMENT," +
                        "money int NOT NULL," +
                        "cTime int NOT NULL," +
                        "Exp int NOT NULL," +
                        "PRIMARY KEY (playerID)" +
                        ");");

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
                        "VALUES ('Janet','Arm', 220000, 180000, 1);");
                stmt.execute("INSERT INTO DD_Resident (firstname, lastname, comeHome, goesAway, houseID) " +
                        "VALUES ('Jan','Reich', 80000, 200000, 1);");
                stmt.execute("INSERT INTO DD_Resident (firstname, lastname, comeHome, goesAway, houseID) " +
                        "VALUES ('Justin','Scholz', 170000, 80000, 2);");
                stmt.execute("INSERT INTO DD_Resident (firstname, lastname, comeHome, goesAway, houseID) " +
                        "VALUES ('Dang','Mich', 80000, 40000, 3);");
                stmt.execute("INSERT INTO DD_Resident (firstname, lastname, comeHome, goesAway, houseID) " +
                        "VALUES ('Pika','Chu', 50000, 000000, 3);");
                stmt.execute("INSERT INTO DD_Resident (firstname, lastname, comeHome, goesAway, houseID) " +
                        "VALUES ('Knebi','Knebellord', 80000, 70000, 12);");
                stmt.execute("INSERT INTO DD_Resident (firstname, lastname, comeHome, goesAway, houseID) " +
                        "VALUES ('Konrad','Zuse', 150000, 70000, 12);");
                stmt.execute("INSERT INTO DD_Resident (firstname, lastname, comeHome, goesAway, houseID) " +
                        "VALUES ('Timer.','Berners-Lee', 120000, 40000, 12);");
                stmt.execute("INSERT INTO DD_Resident (firstname, lastname, comeHome, goesAway, houseID) " +
                        "VALUES ('Ambro','Ambrolord', 160000, 80000, 11);");
                stmt.execute("INSERT INTO DD_Resident (firstname, lastname, comeHome, goesAway, houseID) " +
                        "VALUES ('Frau','Ambrolord', 160000, 80000, 11);");
                stmt.execute("INSERT INTO DD_Resident (firstname, lastname, comeHome, goesAway, houseID) " +
                        "VALUES ('Tom','Taco', 240000, 233000, 9);");
                stmt.execute("INSERT INTO DD_Resident (firstname, lastname, comeHome, goesAway, houseID) " +
                        "VALUES ('Mister','KaputteKI', 160000, 80000, 7);");
                stmt.execute("INSERT INTO DD_Resident (firstname, lastname, comeHome, goesAway, houseID) " +
                        "VALUES ('Misses','FunktionierendeKI', 80000, 160000, 7);");
                stmt.execute("INSERT INTO DD_Resident (firstname, lastname, comeHome, goesAway, houseID) " +
                        "VALUES ('Mister','Highman', 233000, 180000, 4);");
                stmt.execute("INSERT INTO DD_Resident (firstname, lastname, comeHome, goesAway, houseID) " +
                        "VALUES ('Marcel','Broooaaaun', 200000, 123000,5);");
                stmt.execute("INSERT INTO DD_Resident (firstname, lastname, comeHome, goesAway, houseID) " +
                        "VALUES ('Bese','Flor', 160000, 80000,6);");
                stmt.execute("INSERT INTO DD_Resident (firstname, lastname, comeHome, goesAway, houseID) " +
                        "VALUES ('Misses','Lowman', 200000, 123000, 4);");
                stmt.execute("INSERT INTO DD_Resident (firstname, lastname, comeHome, goesAway, houseID) " +
                        "VALUES ('Dufte','Dave', 00000, 00000, 10);");
                stmt.execute("INSERT INTO DD_Resident (firstname, lastname, comeHome, goesAway, houseID) " +
                        "VALUES ('Valentina','Kerman', 100000, 80000, 8);");
                stmt.execute("INSERT INTO DD_Resident (firstname, lastname, comeHome, goesAway, houseID) " +
                        "VALUES ('Jebediah','Kerman', 120000, 80000, 8);");
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
                stmt.execute("INSERT INTO DD_House (security, districtID, tenantID, carID) " +
                        "VALUES ('1', '1', '2',null);");
                stmt.execute("INSERT INTO DD_House (security, districtID, tenantID, carID) " +
                        "VALUES ('2', '1', '3',null);");
                stmt.execute("INSERT INTO DD_House (security, districtID, tenantID, carID) " +
                        "VALUES ('3', '1', '4',null);");
                stmt.execute("INSERT INTO DD_House (security, districtID, tenantID, carID) " +
                        "VALUES ('4', '2', '14',null);");
                stmt.execute("INSERT INTO DD_House (security, districtID, tenantID, carID) " +
                        "VALUES ('5', '2', '15',null);");
                stmt.execute("INSERT INTO DD_House (security, districtID, tenantID, carID) " +
                        "VALUES ('6', '2', '16',null);");
                stmt.execute("INSERT INTO DD_House (security, districtID, tenantID, carID) " +
                        "VALUES ('10', '3', '12','1');");
                stmt.execute("INSERT INTO DD_House (security, districtID, tenantID, carID) " +
                        "VALUES ('15', '3', '19','2');");
                stmt.execute("INSERT INTO DD_House (security, districtID, tenantID, carID) " +
                        "VALUES ('20', '3', '11','3');");
                stmt.execute("INSERT INTO DD_House (security, districtID, tenantID, carID) " +
                        "VALUES ('30', '4', '18','4');");
                stmt.execute("INSERT INTO DD_House (security, districtID, tenantID, carID) " +
                        "VALUES ('40', '4', '9','5');");
                stmt.execute("INSERT INTO DD_House (security, districtID, tenantID, carID) " +
                        "VALUES ('50', '4', '6','6');");
                //Autos
                stmt.execute("INSERT INTO DD_Car (carModel, price, difficulty) " +
                        "VALUES ('Golf GTI','1000','12');");
                stmt.execute("INSERT INTO DD_Car (carModel, price, difficulty) " +
                        "VALUES ('Shirocco','2000','21');");
                stmt.execute("INSERT INTO DD_Car (carModel, price, difficulty) " +
                        "VALUES ('Prosche 911','3000','37');");
                stmt.execute("INSERT INTO DD_Car (carModel, price, difficulty) " +
                        "VALUES ('Ferrari 458','5000','44');");
                stmt.execute("INSERT INTO DD_Car (carModel, price, difficulty) " +
                        "VALUES ('Audi R8','10000','64');");
                stmt.execute("INSERT INTO DD_Car (carModel, price, difficulty) " +
                        "VALUES ('Lamborgini Huracan','15000','73');");

                //Diebesware
                stmt.execute("INSERT INTO DD_Loot (term, price, difficulty) " +
                        "VALUES ('Tasse', '7', '1');"); //1
                stmt.execute("INSERT INTO DD_Loot (term, price, difficulty) " +
                        "VALUES ('Teller', '8', '1');"); //2
                stmt.execute("INSERT INTO DD_Loot(term,price, difficulty)" +
                        "VALUES ('Lieblingskuscheltier', '10', '2');"); //3
                stmt.execute("INSERT INTO DD_Loot (term, price, difficulty) " +
                        "VALUES ('Besteck', '15', '2');"); //4
                stmt.execute("INSERT INTO DD_Loot(term,price, difficulty)" +
                        "VALUES ('Pfandflaschentüte', '17', '2');"); //5
                stmt.execute("INSERT INTO DD_Loot(term,price, difficulty)" +
                        "VALUES ('Waage', '20', '2');"); //6
                stmt.execute("INSERT INTO DD_Loot (term, price, difficulty) " +
                        "VALUES ('Horn Pub Abonnement', '25', '3');"); //7
                stmt.execute("INSERT INTO DD_Loot(term,price, difficulty)" +
                        "VALUES ('Kupferkabel', '30', '3');"); //8
                stmt.execute("INSERT INTO DD_Loot (term, price, difficulty) " +
                        "VALUES ('Abibas Shirt', '30', '3');"); //8
                stmt.execute("INSERT INTO DD_Loot (term, price, difficulty) " +
                        "VALUES ('Blaue Vase', '35', '3');");//10
                stmt.execute("INSERT INTO DD_Loot (term, price, difficulty) " +
                        "VALUES ('Rote Vase', '40', '3');"); //11
                stmt.execute("INSERT INTO DD_Loot (term, price, difficulty) " +
                        "VALUES ('Wens Schuhe', '50', '3');"); //12
                stmt.execute("INSERT INTO DD_Loot(term,price, difficulty)" +
                        "VALUES ('Röhrenfernseher', '65', '4');"); //13
                stmt.execute("INSERT INTO DD_Loot (term, price, difficulty) " +
                        "VALUES ('Silberkette', '80', '4');"); //14
                stmt.execute("INSERT INTO DD_Loot (term, price, difficulty) " +
                        "VALUES ('Mokia 3310', '100', '4');"); //15
                stmt.execute("INSERT INTO DD_Loot(term,price, difficulty)" +
                        "VALUES ('150$', '150', '4');"); //16
                stmt.execute("INSERT INTO DD_Loot(term,price, difficulty)" +
                        "VALUES ('Michelle Mors Handtasche', '210', '5');"); //17
                stmt.execute("INSERT INTO DD_Loot (term, price, difficulty) " +
                        "VALUES ('Samschnung Smartwatch', '250', '5');"); //18
                stmt.execute("INSERT INTO DD_Loot (term, price, difficulty) " +
                        "VALUES ('Play Box 4 Pro', '300', '5');");//19
                stmt.execute("INSERT INTO DD_Loot (term, price, difficulty) " +
                        "VALUES ('X-Station One S', '380', '5');");//20
                stmt.execute("INSERT INTO DD_Loot (term, price, difficulty) " +
                        "VALUES ('Laptop', '500', '6');"); //21
                stmt.execute("INSERT INTO DD_Loot (term, price, difficulty) " +
                        "VALUES ('Voodoo Puppe', '666', '7');");//22
                stmt.execute("INSERT INTO DD_Loot (term, price, difficulty) " +
                        "VALUES ('IG SmartTv', '800', '8');");//23
                stmt.execute("INSERT INTO DD_Loot (term, price, difficulty) " +
                        "VALUES ('Goldkette', '1100', '10');"); //24
                stmt.execute("INSERT INTO DD_Loot (term, price, difficulty) " +
                        "VALUES ('MePhone', '1200', '10');");//25
                stmt.execute("INSERT INTO DD_Loot (term, price, difficulty) " +
                        "VALUES ('Gaming PC', '1200', '11');"); //26
                stmt.execute("INSERT INTO DD_Loot (term, price, difficulty) " +
                        "VALUES ('Diamantring', '2000', '12');"); //27
                stmt.execute("INSERT INTO DD_Loot (term, price, difficulty) " +
                        "VALUES ('Waschmaschine', '2500', '13');"); //28
                stmt.execute("INSERT INTO DD_Loot (term, price, difficulty) " +
                        "VALUES ('Abstraktes Gemälde', '3600', '15');"); //29
                stmt.execute("INSERT INTO DD_Loot (term, price, difficulty) " +
                        "VALUES ('Half Life 3 ', '5000', '20');"); //30
                stmt.execute("INSERT INTO DD_Loot(term,price, difficulty)" +
                        "VALUES ('Rolecks', '11000', '25');"); //31
                stmt.execute("INSERT INTO DD_Loot (term, price, difficulty) " +
                        "VALUES ('Quanten PC', '25000', '30');");//32
                stmt.execute("INSERT INTO DD_Loot (term, price, difficulty) " +
                        "VALUES ('Zeitmaschine', '45000','45');");//33

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
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('1', '5');");
                //Haus02
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('2', '1');");
                //
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('2', '2');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('2', '3');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('2', '4');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('2', '5');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('2', '6');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('2', '7');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('2', '8');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('2', '15');");
                //Haus03
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('3', '1');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('3', '3');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('3', '5');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('3', '6');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('3', '8');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('3', '9');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('3', '10');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('3', '13');");
                //Haus04

                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('4', '2');");

                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('4', '6');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('4', '8');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('4', '9');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('4', '10');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('4', '11');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('4', '12');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('4', '14');");
                //Haus05

                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('5', '3');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('5', '5');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('5', '8');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('5', '9');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('5', '10');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('5', '12');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('5', '13');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('5', '15');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('5', '16');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('5', '17');");
                //Haus06
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('6', '3');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('6', '7');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('6', '11');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('6', '13');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('6', '14');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('6', '15');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('6', '16');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('6', '17');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('6', '19');");


                //Haus07
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('7', '9');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('7', '10');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('7', '11');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('7', '12');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('7', '14');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('7', '15');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('7', '17');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('7', '19');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('7', '20');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('7', '21');");


                //Haus08
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('8', '8');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('8', '11');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('8', '12');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('8', '151');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('8', '16');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('8', '17');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('8', '18');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('8', '19');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('8', '21');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('8', '22');");
                //Haus09
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('9', '15');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('9', '16');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('9', '18');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('9', '19');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('9', '20');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('9', '22');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('9', '23');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('9', '24');");
                //Haus10
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('10', '7');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('10', '15');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('10', '20');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('10', '21');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('10', '22');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('10', '23');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('10', '26');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('10', '30');");
                //Haus11


                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('11', '17');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('11', '19');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('11', '21');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('11', '23');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('11', '24');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('11', '25');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('11', '27');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('11', '28');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('11', '29');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('11', '31');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('11', '32');");
                //Haus12
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('12', '16');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('12', '21');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('12', '23');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('12', '24');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('12', '26');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('12', '28');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('12', '29');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('12', '30');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('12', '32');");
                stmt.execute("INSERT INTO DD_has_Loot (houseID, lootID) " +
                        "VALUES ('12', '33');");


                //Player

                stmt.execute("INSERT INTO DD_PlayerData (money, cTime, Exp) " +
                        "VALUES ('100', '1200', '0');");























            } catch (SQLException e){
                System.out.println("Keine neue Tabelle gefüllt.");
                System.out.println(e);
            }

        }

        public Statement getStatement (){
            return stmt;
        }


}
