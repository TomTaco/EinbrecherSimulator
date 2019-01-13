package model.Gameshit;

import control.framework.UIController;
import model.framework.GraphicalObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Player extends GraphicalObject {

    //Attribute
    private int skillLvl;
    private int money;

    //Referenzen
    private Statement stmt;
    private UIController ui;

    public Player(Statement stmt, UIController ui){
        this.stmt = stmt;
        this.ui = ui;
        skillLvl = 40;
        money = 100;
    }


    public void breakIn(int houseID) {
        double successChance = 0.7;
        try{
            ResultSet results = stmt.executeQuery("SELECT DD_House.security " +
                    "FROM DD_House " +
                    "WHERE DD_House.houseID = "+houseID+"" +
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
                success();
            }

        }catch(SQLException e){
            System.out.println(e);
        }
    }

    private void success() {
        System.out.println("Alda du bist der geborene Dieb!!");


    }

    private void fail() {
        System.out.println("Wie schlecht kann man nur sein !??!");
        money -= money /10;
        System.out.println("Dein Geld!: "+money);
    }
}