package vending_machine;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vending_machine.snacks.*;

public class SellerPage {
    private JPanel panel;
    private JFrame frame;
    private JLabel message;
    private Model model;

    private JButton logout;
    private JButton backAdmin;

    private JLabel getSnackLabel;
    private JTextField getSnackText;

    private JLabel displaySnackName;
    private JLabel displaySnackPrice;
    private JLabel displaySnackStock;
    private JTextField newPrice;
    private JButton updatePrice;
    private JTextField newStock;
    private JButton updateStock;

    private JLabel newSnackName;
    private JLabel categoryLabel;
    private JButton addSnack;
    private String[] categories = new String[] {"candy", "chip", "chocolate", "drink"};
    private JComboBox<String> categoriesDropDown = new JComboBox<String>(categories);

    private JButton availableRecord;
    private JButton soldRecord;

    private String currentSnack;

    public SellerPage(Model model, boolean isAdmin)
    {
        this.model = model;
        panel = new JPanel();
        frame = new JFrame();
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        frame.add(panel);
        panel.setLayout(null);
        frame.setVisible(true);
        createTitle();

        // Message
        message = new JLabel("default message");
        message.setBounds(80, 80, 500, 25);
        message.setForeground(Color.RED);
        panel.add(message);


        // Logout button and behaviour
        JButton logout = new JButton("Logout");
        logout.setBounds(560, 40, 150, 25);
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI gui = new GUI(model);
                frame.setVisible(false);
            }
        });
        panel.add(logout);
        
        // Go back to admin page if admin 
        if (isAdmin) {
            backAdmin = new JButton("Back to Admin");
            backAdmin.setBounds(560, 70, 150, 25);
            backAdmin.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    AdminPage admin = new AdminPage(model);
                    frame.setVisible(false);
                }
            });
            panel.add(backAdmin);
        }

        // Report buttons
        availableRecord = new JButton("Get Available Report");
        availableRecord.setBounds(80, 400, 150, 25);
        availableRecord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.writeAvailable();
                message.setText("Report generated!");
            }
        });
        panel.add(availableRecord);

        soldRecord = new JButton("Get Sold Report");
        soldRecord.setBounds(80, 440, 150, 25);
        soldRecord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.writeSold();
                message.setText("Report generated!");
            }
        });
        panel.add(soldRecord);

        // Initialise snack modificaton interface
        initialiseSnackModification();
        // Init add snack
        initialiseSnackAdd();

        // Get snack
        createSnackNameInput();

        
    }

    public void createTitle()
    {
        JLabel title = new JLabel();
        title.setBounds(250, 30, 300, 45);
        title.setFont(new Font("Verdana", Font.PLAIN, 30));
        title.setText("Seller Page");
        panel.add(title);

    }

    public void createSnackNameInput() 
    {
        // Label
        getSnackLabel =  new JLabel("Snack Name: ");
        getSnackLabel.setBounds(80, 120, 80, 25);
        panel.add(getSnackLabel);

        // Field
        getSnackText = new JTextField(20);
        getSnackText.setBounds(160, 120, 165, 25);
        panel.add(getSnackText);

        // Button
        JButton getSnack = new JButton("Search");
        getSnack.setBounds(340, 120, 120, 25);
        getSnack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentSnack = getSnackText.getText();
                drawSnack(currentSnack);
            }
        });
        panel.add(getSnack);

        frame.setVisible(true);

    }

    public void setModifyVisible(boolean b) {
        displaySnackName.setVisible(b);
        displaySnackPrice.setVisible(b);
        displaySnackStock.setVisible(b);
        newPrice.setVisible(b);
        updatePrice.setVisible(b);
        newStock.setVisible(b);
        updateStock.setVisible(b);
    }

    public void setAddVisible(boolean b) {
        categoryLabel.setVisible(b);
        addSnack.setVisible(b);
        categoriesDropDown.setVisible(b);
    }

    public void drawSnack(String snackName) 
    {
        if (model.getSingleSnackInfo(snackName).success) {
            // Display info and modification buttons
            message.setText("Snack found!");

            setModifyVisible(true);
            setAddVisible(false);

            updateInfo();
        } else {
            // Offer to create a new snack
            message.setText("Snack not found, create new snack?");
            setModifyVisible(false);
            setAddVisible(true);
        }
        return;
    }

    public void initialiseSnackAdd() {
        categoryLabel = new JLabel("Category: ");
        categoryLabel.setBounds(80, 160, 80, 25);
        panel.add(categoryLabel);

        addSnack = new JButton("Add new snack");
        addSnack.setBounds(340, 160, 150, 25);
        addSnack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModelResponse ret = model.addSnack(getSnackText.getText(), (String) categoriesDropDown.getSelectedItem());

                if (ret.success) {
                    message.setText("Snack added!");
                } else {
                    message.setText("Snack add error: " + ret.reason);
                }
            }
        });
        panel.add(addSnack);

        categoriesDropDown.setBounds(160, 160, 165, 25);
        panel.add(categoriesDropDown);

        setAddVisible(false);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
    
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public void updateInfo() {
        setModifyVisible(true);

        Snack s = (Snack) model.getSingleSnackInfo(currentSnack).data;

        displaySnackName.setText("Snack: " + currentSnack);
        displaySnackPrice.setText("Price: $" + String.valueOf(round(s.getPrice(), 2)));
        displaySnackStock.setText("Stock: " + String.valueOf(s.getQuantity()));
    }

    public void initialiseSnackModification() {
        // private JLabel displaySnackName;
        // private JLabel displaySnackPrice;
        // private JLabel displaySnackStock;
        // private JTextField newPrice;
        // private JButton updatePrice;
        // private JTextField newStock;
        // private JButton updateStock;

        displaySnackName = new JLabel("Snack: ");
        displaySnackName.setBounds(80, 160, 500, 25);
        panel.add(displaySnackName);

        displaySnackPrice = new JLabel("Price: ");
        displaySnackPrice.setBounds(80, 190, 100, 25);
        panel.add(displaySnackPrice);

        displaySnackStock = new JLabel("Stock: ");
        displaySnackStock.setBounds(80, 220, 100, 25);
        panel.add(displaySnackStock);

        newPrice = new JTextField(20);
        newPrice.setBounds(300, 190, 70, 25);
        panel.add(newPrice);

        updatePrice = new JButton("Update price");
        updatePrice.setBounds(380, 190, 150, 25);
        updatePrice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double price;
                try {
                    price = Double.valueOf(newPrice.getText());
                    ModelResponse ret = model.updatePrice(currentSnack, price);
                    if (ret.success) {
                        message.setText("Price updated successfully!");
                        updateInfo();
                    } else {
                        message.setText("Price update error: " + ret.reason);
                    }
                } catch (java.lang.NumberFormatException numException) {
                    message.setText("Price update error: cannot format new price");
                }               
            }
        });
        panel.add(updatePrice);

        newStock = new JTextField(20);
        newStock.setBounds(300, 220, 120, 25);
        panel.add(newStock);

        updateStock = new JButton("Update stock");
        updateStock.setBounds(430, 220, 120, 25);
        updateStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int stock = Integer.valueOf(newStock.getText());

                if (stock > 15) {
                    message.setText("Stock update error: stock too high");
                    return;
                }

                if (stock < 0) {
                    message.setText("Stock update error: stock too low");
                    return;
                }

                ModelResponse ret = model.updateStock(currentSnack, stock);
                if (ret.success) {
                    message.setText("Stock updated!");
                    updateInfo();
                } else {
                    message.setText("Stock update error: " + ret.reason);
                }
            }
        });
        panel.add(updateStock);


        setModifyVisible(false);
    }

}