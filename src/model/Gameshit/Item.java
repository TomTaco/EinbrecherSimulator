package model.Gameshit;

import model.framework.GraphicalObject;
import view.framework.DrawTool;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Item extends GraphicalObject {
    //Attribute
    private int x,y;
    private int price;
    private boolean selling;
    private String name;
    private int iD;
    private int amount;

    private Statement stmt;

    public Item(int iD, Statement stmt){
        this.iD = iD;
        this.stmt = stmt;
        setData();
        amount = 1;
        System.out.println("du hast geklaut: "+ name);
        createAndSetNewImage("images/Items/no.png");
        createAndSetNewImage("images/Items/"+name+".png");
    }

    private void setData() {
        try{
            ResultSet results = stmt.executeQuery("SELECT term, price " +
                    "FROM DD_Loot " +
                    "WHERE lootID = "+iD+";");
            results.next();
            name = results.getString("term");
            price = results.getInt("price");
        }catch(SQLException e){
            System.out.println(e);
        }
    }

    @Override
    public void draw(DrawTool drawTool) {
        super.draw(drawTool);
    }

    public void addOne(){
        amount += 1;
    }
    public int getAmount(){
        return amount;
    }

    public int getID(){
        return iD;
    }

    public String getName(){
        return name;
    }

    public void setAmount(int amount){
        this.amount = amount;
    }

    public boolean isSelling(){
        return selling;
    }

    public void setSelling(boolean selling){
        this.selling = selling;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getPrice(){
        return  price;
    }



}