package com.example.oop_8;

import android.widget.TextView;

import java.util.ArrayList;

class BottleDispenser {
    private int bottles;
    private ArrayList<Bottle> bottleList;
    private double money;
    private String moneyStr;
    private static BottleDispenser bd = new BottleDispenser();

    private BottleDispenser() {
        bottles = 13;
        money = 0;
        bottleList = new ArrayList<Bottle>();

        bottleList.add(new Bottle());
        bottleList.add(new Bottle("Pepsi Max","Pepsi",0.9,1.5,2.2));
        bottleList.add(new Bottle("Coca-Cola Zero","Coca-Cola",0.3,0.5,2.0));
        bottleList.add(new Bottle("Coca-Cola Zero","Coca-Cola",0.9,1.5,2.5));
        bottleList.add(new Bottle("Fanta Zero","Coca-Cola",0.4,0.5,1.95));
        bottleList.add(new Bottle("Fanta Zero","Coca-Cola",0.4,0.5,1.95));
        bottleList.add(new Bottle("Fanta Zero","Coca-Cola",0.4,0.5,1.95));
        bottleList.add(new Bottle("Fanta Zero","Coca-Cola",0.4,0.5,1.95));
        bottleList.add(new Bottle("Fanta Zero","Coca-Cola",0.4,0.5,1.95));
        bottleList.add(new Bottle("Fanta Zero","Coca-Cola",0.4,0.5,1.95));
        bottleList.add(new Bottle("Fanta Zero","Coca-Cola",0.4,0.5,1.95));
        bottleList.add(new Bottle("Fanta Zero","Coca-Cola",0.4,0.5,1.95));
        bottleList.add(new Bottle("Fanta Zero","Coca-Cola",0.4,0.5,1.95));
    }

    private void removeBottle(int idx) {
        bottleList.remove(idx);
        bottles -= 1;
    }

    static BottleDispenser getInstance() {
        return bd;
    }

    void addMoney(float amount, TextView t, TextView m) {
        money += amount;
        t.setText("Klink! Added more money!");
        moneyStr = String.format("%.2f", money);
        m.setText("Money: " + moneyStr);
    }

    String buyBottle(int i, TextView t, TextView m) {
        Bottle bottle = bottleList.get(i);
        String info;

        if (money < bottle.getPrice()) {
            t.setText("Add money first!");
            return null;
        }

        money -= bottle.getPrice();
        moneyStr = String.format("%.2f", money);
        m.setText("Money: " + moneyStr);
        t.setText("KACHUNK! " + bottle.getName() + " came out of the dispenser!");
        info = bottle.getName() + " " + bottle.getSize() + " l, " + bottle.getPrice() + "€";
        this.removeBottle(i);
        return info;
    }

    void returnMoney(TextView t, TextView m) {
        moneyStr = String.format("%.2f", money);
        t.setText("Klink klink. Money came out! You got " + moneyStr + "€ back");
        money = 0;
        moneyStr = String.format("%.2f", money);
        m.setText("Money: " + moneyStr);
    }

    void listBottles(TextView t) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < bottleList.size(); i++) {
            sb.append(i + 1 + ". Name: " + bottleList.get(i).getName() + "\n Size: " +
                    bottleList.get(i).getSize() + "\n Price: " +
                    bottleList.get(i).getPrice());
            sb.append("\n");
        }

        t.setText(sb.toString());
    }

    int getBottleIdx(String name, float size) {
        for (int i = 0; i < bottleList.size(); i++) {
            if (bottleList.get(i).getName().equals(name) && bottleList.get(i).getSize() == size) {
                return i;
            }
        }

        return -1;
    }
}
