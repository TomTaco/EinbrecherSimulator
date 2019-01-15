package model.Gameshit;

import com.mysql.cj.xdevapi.SqlDataResult;
import control.framework.UIController;
import model.framework.GraphicalObject;
import view.framework.DrawTool;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Inventory extends GraphicalObject {

    private int x, y;
    private boolean open;
    private boolean clicked;

    private Connection con;
    private Item[] items;
    private Player player;

    public Inventory(Connection con, Player player) {
        this.con = con;
        this.player = player;
        player.setInv(this);
        x = 820;
        y = 0;
        setItemData();

    }

    public void setItemData() {
        try{
            Statement stmt = con.createStatement();
            Statement stmt2 = con.createStatement();
            ResultSet results = stmt2.executeQuery("SELECT COUNT(lootID) " +
                    "FROM DD_owns_Loot ;");
            results.next();
            items = new Item[results.getInt(1)];

             results = stmt.executeQuery("SELECT lootID, amount " +
                    "FROM DD_owns_Loot;" );
            int i = 0;
            while(results.next()){
                Item item = new Item(results.getInt("lootID"),stmt2);
                item.setAmount(results.getInt("amount"));
                items[i] = item;
                i++;

            }

        }catch(SQLException e){
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public void setItemPositions(){
        for (int i = 0; i < items.length; i++) {
            if(i < 11) items[i].setPosition(x + 50,y + 110 + i * 58);
            else items[i].setPosition(x + 50+125,y + 110 + (i-11) * 58);
        }
    }



    @Override
    public void draw(DrawTool drawTool) {
        super.draw(drawTool);
        drawTool.drawImage(createNewImage("images/inventory.png"), x, y);
        if(items != null) {
            int dx;
            int dy;
            for (int i = 0; i < items.length; i++) {
                if(i < 11) {
                     dx = x ;
                     dy = y + i * 58;
                }else {
                     dx = x + 125;
                     dy = y + (i - 11) * 58;
                }
                    drawTool.drawImage(items[i].getMyImage(), dx + 50, dy + 110, 40, 40);
                    drawTool.setFont("Impact", 24, false);
                    drawTool.drawText(dx + 95, dy + 145 , "x " + items[i].getAmount());
                    drawTool.setFont("Arial", 12, false);
                    drawTool.drawText(dx + 30, dy + 165 , items[i].getName());
                    if(items[i].isSelling()) drawTool.drawImage(createNewImage("Images/sell.png"),dx + 65, dy + 115 , 35,18);

            }
        }
    }

    @Override
    public void update(double dt) {
        super.update(dt);
        if (open && x > 550) {
            x -= 700 * dt;
        }else if(open){
            x = 520;
            setItemPositions();
        }
        else if (!open && x < 820) {
            x += 1000 * dt;
        }
    }

    @Override
    public void keyReleased(int key) {
        super.keyReleased(key);
        if(key == KeyEvent.VK_I && !open){
            open = true;
        }else if(open && key == KeyEvent.VK_I){
            open = false;
        }
        if(key >= 49 && key <= 57){
            for (int i = 0; i < items.length; i++) {
                if(items[i].isSelling()){
                    sell(items[i],Integer.parseInt(KeyEvent.getKeyText(key)));
                }

            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        boolean sold = false;
        if(!clicked) {
            for (int i = 0; i < items.length; i++) {
                if(items[i].isSelling() && items[i].isSelling() && e.getX() > items[i].getX()+15 && e.getX() < items[i].getX()+15 + 35 && e.getY() > items[i].getY()+5 && e.getY() < items[i].getY()+5 + 18){
                    items[i].setSelling(false);
                    sell(items[i]);
                    sold = true;
                }
                if (!sold && e.getX() > items[i].getX() && e.getX() < items[i].getX() + 40 && e.getY() > items[i].getY() && e.getY() < items[i].getY() + 40) {
                    items[i].setSelling(true);
                } else if(!sold) {
                    items[i].setSelling(false);
                }

            }
        }
        clicked = !clicked;
    }



    public void sell(Item item){

        try {
            Statement stmt = con.createStatement();
            player.addMoney(item.getPrice());
            if(item.getAmount() > 1){
                stmt.execute("UPDATE DD_owns_Loot " +
                        "SET amount = "+ (item.getAmount()-1)+" " +
                        "WHERE lootID = "+ item.getID()+";");
                System.out.println("done");
            }else{
                stmt.execute("DELETE FROM DD_owns_Loot " +
                        "WHERE lootiD = "+item.getID()+";");
            }

        }catch (SQLException e){
            System.out.println(e);
        }
        setItemData();
    }

    public void sell(Item item, int sAmount){
        try {
            Statement stmt = con.createStatement();
            if(item.getAmount() >= sAmount) {
                player.addMoney(item.getPrice());
                if ((item.getAmount() - sAmount) >= 1) {
                    stmt.execute("UPDATE DD_owns_Loot " +
                            "SET amount = " + (item.getAmount() - sAmount) + " " +
                            "WHERE lootID = " + item.getID() + ";");

                    System.out.println("done");
                }else if((item.getAmount() - sAmount) == 0){
                    stmt.execute("DELETE FROM DD_owns_Loot " +
                            "WHERE lootiD = "+item.getID()+";");
                }
            }
        }catch (SQLException e){
            System.out.println(e);
        }
        setItemData();
    }
}