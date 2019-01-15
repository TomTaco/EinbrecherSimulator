package model.Gameshit;

import com.mysql.cj.xdevapi.SqlDataResult;
import control.framework.UIController;
import model.Overlay;
import model.framework.GraphicalObject;
import view.framework.DrawTool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Player extends GraphicalObject {

    //Attribute
    private int skillLvl;

    private int money, hours, minutes, exp;

    //Referenzen
    private Connection con;
    private UIController ui;
    private Overlay overlay;
    private LootScreen loot;
    private Inventory inv;


    public Player(Connection con, UIController ui){
        this.con = con;
        this.ui = ui;
        skillLvl = 40;
        money = 100;
        setPlayerData();
    }

    @Override
    public void draw(DrawTool drawTool) {
        super.draw(drawTool);
        drawTool.drawImage(createNewImage("Images/creditcard.png"),34,105,128,64);
        drawTool.setCurrentColor(255,255,255,255);
        drawTool.setFont("Arial",12,false);
        drawTool.drawText(84,154,money+"$");
    }

    private void setPlayerData() {
        try{
            Statement stmt = con.createStatement();
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
            Statement stmt = con.createStatement();
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

            Statement stmt = con.createStatement();
            ResultSet results = stmt.executeQuery("SELECT COUNT(DD_Loot.lootID) " +
                    "FROM DD_Loot " +
                    "INNER JOIN DD_has_Loot " +
                    "ON DD_Loot.lootID = DD_has_Loot.lootID " +
                    "WHERE DD_has_Loot.houseID = "+hID+";");

            results.next();
            int[][] lootInfo = new int[results.getInt(1)][2];
            System.out.println("So viele Dinge gibts zu stehlen: "+results.getInt(1));

            Statement stmt2 = con.createStatement();
            results = stmt2.executeQuery("SELECT DD_Loot.lootID, DD_Loot.difficulty " +
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

        try{
            Statement stmt = con.createStatement();
            loot.addItem(itemID, stmt);
            Statement stmt2 = con.createStatement();
            ResultSet results = stmt2.executeQuery("SELECT lootID, amount " +
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
            e.printStackTrace();
        }

    }


    private void fail() {
        System.out.println("Wie schlecht kann man nur sein !??!");
        addMoney(-money/10);
        System.out.println("Dein Geld!: "+money);
    }

    public void setOverlay(Overlay ov){
        this.overlay = ov;
    }

    public void closeLootScreen(Item[] items) {
        ui.removeObject(loot);
        loot= null;
        if(inv != null)inv.setItemData();
    }

    public void setInv(Inventory inv){
        this.inv = inv;
    }

    public void addMoney(int amount){
        this.money += amount;
        try {
            con.createStatement().execute("UPDATE DD_PlayerData " +
                    "SET money = "+ money+";" );
        }catch(SQLException e){
            System.out.println(e);
        }
    }

    public int getMoney(){
        return money;
    }
}