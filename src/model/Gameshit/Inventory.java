package model.Gameshit;

import control.framework.UIController;
import model.framework.GraphicalObject;
import view.framework.DrawTool;

import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Inventory extends GraphicalObject {

    private int x, y;
    private boolean open;

    private Statement stmt;
    private Item[] items;
    private UIController ui;

    public Inventory(Statement stmt, UIController ui) {
        this.stmt = stmt;
        this.ui = ui;
        x = 820;
        y = 0;


    }

    private void setItemData() {
        try{

            ResultSet results = stmt.executeQuery("SELECT COUNT(lootID) " +
                    "FROM DD_owns_Loot ;");
            results.next();
            items = new Item[results.getInt(1)];

             results = stmt.executeQuery("SELECT lootID, amount " +
                    "FROM DD_owns_Loot;" );
            int i = 0;
            while(results.next()){
                Item item = new Item(results.getInt("lootID"),stmt);
                item.setAmount(results.getInt("amount"));
                items[i] = item;
                i++;

            }
        }catch(SQLException e){
            System.out.println(e);
        }
    }



    @Override
    public void draw(DrawTool drawTool) {
        super.draw(drawTool);
        drawTool.drawImage(createNewImage("images/inventory.png"), x, y);
        if(items != null) {
            for (int i = 0; i < items.length; i++) {
                drawTool.drawImage(items[i].getMyImage(), x + 20, y + 25 + i * 55, 40, 40);
                drawTool.setFont("Impact", 24, false);
                drawTool.drawText(x + 65, y + 55 + i * 55, "x " + items[i].getAmount());
                drawTool.setFont("Arial", 12, false);
                drawTool.drawText(x, y + 75 + i * 55, items[i].getName());
            }
        }
    }

    @Override
    public void update(double dt) {
        super.update(dt);
        if (open && x > 550) {
            x -= 700 * dt;
        } else if (!open && x < 820) {
            x += 1000 * dt;
        }
    }

    @Override
    public void keyReleased(int key) {
        super.keyReleased(key);
        if(key == KeyEvent.VK_I && !open){
            open = true;
            setItemData();
        }else if(open){
            open = false;
        }
    }
}