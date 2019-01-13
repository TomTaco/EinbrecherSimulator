package model.Gameshit;

import com.mysql.cj.xdevapi.SqlDataResult;
import control.framework.UIController;
import model.Overlay;
import model.framework.GraphicalObject;
import view.framework.DrawTool;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Player extends GraphicalObject {

    //Attribute
    private int skillLvl;

    private int money, hours, minutes, exp;

    //Referenzen
    private Statement stmt;
    private UIController ui;
    private Overlay overlay;
    private LootScreen loot;
    private Inventory inv;

    public Player(Statement stmt, UIController ui, Inventory inv){
        this.stmt = stmt;
        this.ui = ui;
        this.inv = inv;
        skillLvl = 40;
        money = 100;
        setPlayerData();
    }

    private void setPlayerData() {
        try{
            ResultSet results = stmt.executeQuery("SELECT * FROM DD_PlayerData");
            results.next();
            money = results.getInt("money");
            hours = (int)(results.getInt("cTime")/100);
            minutes = results.getInt("cTime")-hours;
            exp = results.getInt("Exp");
        }catch (SQLException e){
            System.out.println(e);
        }
    }



    public void breakIn(int houseID, int breakInTime) {
        //Stunden in Minuten umwandeln
        int actualTime = breakInTime *60;
        double successChance = 0.7;
        try{
            ResultSet results = stmt.executeQuery("SELECT security " +
                    "FROM DD_House " +
                    "WHERE houseID = "+houseID+"" +
                    ";");
            results.next();
            int secLvl  = results.getInt(1);
            if(secLvl > skillLvl){
                successChance  = successChance/ Math.pow(secLvl - skillLvl + 1,2);
                System.out.println(successChance);
            }else if(secLvl < skillLvl){
                successChance = successChance + (1 - successChance)* (((double)skillLvl-(double)secLvl+1)/10);

            }
            if(successChance < Math.random()){
                fail();
            }else{
                success(houseID, actualTime);
            }

        }catch(SQLException e){
            System.out.println(e);
        }
    }

    private void success(int hID, int leftTime) {
        System.out.println("Alda du bist der geborene Dieb!!");
        try{
            loot = new LootScreen(200,200,ui, this);


            ResultSet results = stmt.executeQuery("SELECT COUNT(DD_Loot.lootID) " +
                    "FROM DD_Loot " +
                    "INNER JOIN DD_has_Loot " +
                    "ON DD_Loot.lootID = DD_has_Loot.lootID " +
                    "WHERE DD_has_Loot.houseID = "+hID+";");

            results.next();
            int[][] lootInfo = new int[results.getInt(1)][2];
            System.out.println("So viele Dinge gibts zu stehlen: "+results.getInt(1));

            results = stmt.executeQuery("SELECT DD_Loot.lootID, DD_Loot.difficulty " +
                    "FROM DD_Loot " +
                    "INNER JOIN DD_has_Loot " +
                    "ON DD_Loot.lootID = DD_has_Loot.lootID " +
                    "WHERE DD_has_Loot.houseID = "+hID+";");
            System.out.println("Hier gibts zu stehlen: ");
            for (int i = 0; results.next(); i++) {
                lootInfo[i][0] = results.getInt(1);
                lootInfo[i][1] = results.getInt(2);
                System.out.println(results.getInt(1));
            }
            steal(lootInfo, leftTime);



        }catch(SQLException e){
            System.out.println(e);
        }

    }

    private void steal(int[][] lInfo, double leftTime) {
        //wählt per Mathrandom einen zufälligen Index aus
        int chosen = (int) (Math.random()* lInfo.length);

        addItem(lInfo[chosen][0]);

        leftTime -= lInfo[chosen][1] * 10;

        if(leftTime > 10)steal(lInfo, leftTime);
        else ui.drawObject(loot);

    }

    private void addItem(int itemID) {
        loot.addItem(itemID, stmt);
        try{
            ResultSet results = stmt.executeQuery("SELECT lootID, amount " +
                    "FROM DD_owns_Loot");
            boolean alreadyIn = false;
            while(results.next() && !alreadyIn){
                if(itemID == results.getInt("lootID")){
                    alreadyIn = true;
                    stmt.execute("UPDATE DD_owns_Loot " +
                            "SET amount = "+(results.getInt("amount")+1)+" " +
                            "WHERE lootID = "+ itemID + ";");
                }
            }
            if(!alreadyIn) {
                stmt.execute("INSERT INTO DD_owns_Loot (playerID, lootID, amount) " +
                        "VALUES ('1','" + itemID + "','1');");
            }

        }catch(SQLException e){
            System.out.println(e);
            //e.printStackTrace();
        }

    }


    private void fail() {
        System.out.println("Wie schlecht kann man nur sein !??!");
        money -= money /10;
        System.out.println("Dein Geld!: "+money);
    }

    public void setOverlay(Overlay ov){
        this.overlay = ov;
    }

    public void closeLootScreen(Item[] items) {
        ui.removeObject(loot);
        loot= null;
    }
}