package model.Gameshit;

import control.framework.UIController;
import model.framework.GraphicalObject;
import view.framework.DrawTool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Player extends GraphicalObject {

    //Attribute
    private int skillLvl;
    private boolean caught;
    private double textCD;
    private boolean failed;

    private int money, hours, minutes, exp;
    private Tool selectedTool;

    //Referenzen
    private Connection con;
    private UIController ui;
    private Overlay overlay;
    private LootScreen loot;
    private Inventory inv;



    public Player(Connection con, UIController ui){
        this.con = con;
        this.ui = ui;
        skillLvl = 1;
        setPlayerData();
    }

    @Override
    public void draw(DrawTool drawTool) {
        super.draw(drawTool);
        drawTool.drawImage(createNewImage("Images/creditcard.png"),34,105,128,64);
        drawTool.setCurrentColor(255,255,255,255);
        drawTool.setFont("Arial",12,false);
        drawTool.drawText(84,154,money+"$");
        drawTool.setFont("Arial",12,true);
        drawTool.drawText(60,124,"SkillLvl: "+skillLvl);
        if(caught) {
            drawTool.setFont("Impact", 42, true);
            drawTool.setCurrentColor(240, 60, 60, 255);
            drawTool.drawText(195, 160, "Du wurdest gepackt!!");
        }
        if(failed){
            drawTool.setFont("Impact", 26, true);
            drawTool.setCurrentColor(240, 60, 60, 255);
            drawTool.drawText(175, 160, "Du bist gescheitert! Hol dir besseres Werkzeug!");
        }
    }

    private void setPlayerData() {
        try{
            Statement stmt = con.createStatement();
            ResultSet results = stmt.executeQuery("SELECT selectedTool FROM DD_PlayerData ");
            results.next();
            if(results.getInt("selectedTool") == 0) {
                selectedTool = null;
            }else{
                results = con.createStatement().executeQuery("SELECT DD_PlayerData.money, DD_PlayerData.EXP, DD_Tool.toolLevel FROM DD_PlayerData INNER JOIN DD_Tool ON DD_PlayerData.selectedTool = DD_Tool.toolID;");
                results.next();
                money = results.getInt(1);
                exp = results.getInt(2);
                skillLvl = 1+ results.getInt(3);
            }

        }catch (SQLException e){
            System.out.println(e);
            e.printStackTrace();
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
            }else if(secLvl < skillLvl){
                successChance = successChance + (1 - successChance)* (((double)skillLvl-(double)secLvl+1)/10);

            }
            if(successChance < Math.random()){
                fail();
            }else{

                results = con.createStatement().executeQuery("SELECT COUNT(residentID) FROM DD_Resident WHERE houseID = "+houseID+";");
                results.next();
                int[] gMin = new int[results.getInt(1)];
                int[] hMin = new int[results.getInt(1)];

                results = con.createStatement().executeQuery("SELECT DD_Resident.goesAway, DD_Resident.comeHome FROM DD_Resident INNER JOIN DD_House WHERE DD_Resident.houseID = "+houseID+" ;");
                int i = 0;
                while(results.next() && i < gMin.length) {
                    String[] goesAway = results.getString(1).split(":");
                    gMin[i] = Integer.parseInt(goesAway[0]) * 60 + Integer.parseInt(goesAway[1]);
                    String[] comesHome = results.getString(2).split(":");
                    hMin[i] = Integer.parseInt(comesHome[0]) * 60 + Integer.parseInt(comesHome[1]);
                    i++;
                }
                success(houseID, actualTime, gMin, hMin);
            }

        }catch(SQLException e){
            System.out.println(e);
        }
    }

    private void success(int hID, int leftTime, int[] gMin, int[]  hMin) {
        try{
            loot = new LootScreen(150,200,ui, this);

            Statement stmt = con.createStatement();
            ResultSet results = stmt.executeQuery("SELECT COUNT(DD_Loot.lootID) " +
                    "FROM DD_Loot " +
                    "INNER JOIN DD_has_Loot " +
                    "ON DD_Loot.lootID = DD_has_Loot.lootID " +
                    "WHERE DD_has_Loot.houseID = "+hID+";");

            results.next();
            int[][] lootInfo = new int[results.getInt(1)][2];

            Statement stmt2 = con.createStatement();
            results = stmt2.executeQuery("SELECT DD_Loot.lootID, DD_Loot.difficulty " +
                    "FROM DD_Loot " +
                    "INNER JOIN DD_has_Loot " +
                    "ON DD_Loot.lootID = DD_has_Loot.lootID " +
                    "WHERE DD_has_Loot.houseID = "+hID+";");
            for (int i = 0; results.next(); i++) {
                lootInfo[i][0] = results.getInt(1);
                lootInfo[i][1] = results.getInt(2);
            }

            double pastTime = 0;
            caught = false;
            double cTgC = Math.random();
            double[] awayFrom = new double[gMin.length];
            double[] awayTo = new double[hMin.length];
            for (int i = 0; i < awayFrom.length; i++) {
                awayFrom[i] = gMin[i];
                if(hMin[i] >= gMin[i]){
                    awayTo[i] = hMin[i];
                }else{
                    awayTo[i] = gMin[i] + 24*60 -gMin[i] + hMin[i];
                }
            }


            steal(lootInfo, leftTime, pastTime,cTgC, awayFrom,awayTo);



        }catch(SQLException e){
            System.out.println(e);
        }

    }

    private void steal(int[][] lInfo, double leftTime, double pastTime, double cTgC, double[] awayFrom, double[] awayTo) {
        //wählt per Mathrandom einen zufälligen Index aus
        int chosen = (int) (Math.random()* lInfo.length);

        addItem(lInfo[chosen][0]);

        leftTime -= lInfo[chosen][1] * 10;
        pastTime += lInfo[chosen][1] * 10;

        minutes += lInfo[chosen][1] * 10;
        int a = 1;
        double risk = -1*Math.pow(0.94,pastTime/60)+1;
        for (int i = 0; i < awayFrom.length; i++) {
            if(minutes > awayFrom[i] && minutes < awayTo[i]) a = a;
                else a += 1;
        }
        risk = risk *a;
        if(risk > cTgC){
            caught();

        }

        if(leftTime > 10&& !caught)steal(lInfo, leftTime,pastTime,cTgC,awayFrom,awayTo);
        else {
            ui.drawObject(loot);
            overlay.setHours(overlay.getHours()+(int)(pastTime/60));
        }
    }

    private void caught() {
        caught = true;
        addMoney(-(int)(money*0.6));
        textCD = 5;
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
        addMoney(-money/10);
        failed = true;
        textCD = 5;
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

    public void setSelectedTool(Tool tool){
        this.selectedTool = tool;
        try {
            con.createStatement().execute("UPDATE DD_PlayerData " +
                    "SET selectedTool = "+tool.getId()+";");
            skillLvl = tool.getLvl();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }


    @Override
    public void update(double dt) {
        super.update(dt);
        if((caught || failed )&& textCD > 0){
            textCD -=dt;
        }else if(textCD < 0){
            caught = false;
            failed = false;
        }
        minutes = overlay.getMinutes() + overlay.getHours()*60;
    }

}