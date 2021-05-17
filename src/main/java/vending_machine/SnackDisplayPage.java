package vending_machine;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.*;

import java.util.*;
import vending_machine.snacks.*;


public class SnackDisplayPage extends JFrame {

    private JFrame frame;
    private JPanel panel;
    private Model model;
    private Map<String, java.util.List<Snack>> snackInfo = new HashMap<>();
    private java.util.List<Snack> recent = new ArrayList<>();
    private Map<Snack, JSpinner> spinners = new HashMap<>();
    private JLabel message;

    SnackDisplayPage(Model model)
    {
        this.model = model;
        frame = new JFrame();
        panel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(panel);
        frame.setVisible(true);
        panel.setLayout(null);
        System.out.println(model.user.type);

        message = new JLabel();
        panel.add(message);

        // Test getting snack data
        snackInfo = (Map<String, java.util.List<Snack>>) model.getSnackInfo().data;
        recent = (java.util.List<Snack>) model.getRecentSnacks(model.user.getId()).data;

        createTitle();
        // Move these to a customer GUI
        createDrinksSection();
        createChocolatesSection();
        createChipsSection();
        createCandySection();
        createPayButton();
        createRecentSection();

    }

    public void createTitle()
    {
        JLabel title = new JLabel();
        title.setBounds(250, 30, 300, 45);
        title.setFont(new Font("Verdana", Font.PLAIN, 30));
        title.setText("Vending Machine");
        panel.add(title);

    }

    //Drinks section on the default page
    private void createDrinksSection()
    {
        JLabel drinks = new JLabel();
        drinks.setBounds(10, 90, 150, 500);
        drinks.setVerticalAlignment(JLabel.TOP);
        panel.add(drinks);

        java.util.List<Snack> drinkInfo = snackInfo.get("drink");
        String information = getInfo(drinkInfo, 110);

        drinks.setText("<html>Drinks<br>" + information + "</html>");
    }

    //Chocolates section on default page
    private void createChocolatesSection()
    {
        JLabel chocolates = new JLabel();
        chocolates.setBounds(160, 90, 150, 500);
        chocolates.setVerticalAlignment(JLabel.TOP);
        panel.add(chocolates);

        java.util.List<Snack> chocInfo = snackInfo.get("chocolate");
        String information = getInfo(chocInfo, 260);

        chocolates.setText("<html>Chocolates<br>" + information + "</html>");
    }

    //Chips section on default page
    private void createChipsSection()
    {
        JLabel chips = new JLabel();
        chips.setBounds(310, 90, 150, 500);
        chips.setVerticalAlignment(JLabel.TOP);
        panel.add(chips);

        java.util.List<Snack> chipInfo = snackInfo.get("chip");
        String information = getInfo(chipInfo, 410);

        chips.setText("<html>Chips<br>" + information + "</html>");
    }

    private void createCandySection()
    {
        JLabel candies = new JLabel();
        candies.setBounds(460, 90, 150, 500);
        candies.setVerticalAlignment(JLabel.TOP);
        panel.add(candies);

        java.util.List<Snack> candyInfo = snackInfo.get("candy");
        String information = getInfo(candyInfo, 560);

        candies.setText("<html>Candy<br>" + information + "</html>");
    }

    private void createRecentSection() 
    {
        JLabel recent = new JLabel();
        recent.setBounds(610, 90, 150, 500);
        recent.setVerticalAlignment(JLabel.TOP);
        panel.add(recent);

        String information = getRecent(this.recent);

        recent.setText("<html>Recently Purchased<br>" + information + "</html>");
    }

    private String getInfo(java.util.List<Snack> snacks, int xPos) {
        String information = "";
        int y = 130;
        for (Snack snack : snacks) {
            String price = String.format("%.2f", snack.getPrice());
            String quantity = String.format("%d", snack.getQuantity());
            information += String.format(snack.getName() + "<blockquote>Price: " + price + "<br>Quantity: " + quantity + "</blockquote> <br>");
            SpinnerModel spinnerModel = new SpinnerNumberModel(0, 0, snack.getQuantity(), 1);
            JSpinner spinner = new JSpinner(spinnerModel);
            spinner.setBounds(xPos, y, 50, 25);
            ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setEditable(false);
            panel.add(spinner);
            y += 75;
            spinners.put(snack, spinner);
            
        }
        return information;
    }

    private String getRecent(java.util.List<Snack> snacks) {
        String information = "";
        for (Snack snack : snacks) {
            information += String.format("<br>" + snack.getName());
        }
        return information;
    }

    private void createPayButton() {
        JButton payWithCard = new JButton("Pay");
        payWithCard.setBounds(300, 480, 170, 25);
        payWithCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<Snack, Integer> order = new HashMap<>();
                for(Map.Entry<Snack, JSpinner> entry: spinners.entrySet()) {
                    order.put(entry.getKey(), (Integer) entry.getValue().getValue());
                }
                if(model.getCost(order) == 0.00) {
                    message.setBounds(300, 460, 200, 25);
                    message.setText("Please select some items!");
                    message.setForeground(Color.red);
                }
                else {
                    TransactionPage transactionPage = new TransactionPage(model, order);
                    frame.setVisible(false);
                }
            }
        });
        panel.add(payWithCard);
    }

}