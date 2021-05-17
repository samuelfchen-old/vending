package vending_machine;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import vending_machine.snacks.*;

public class TransactionPage {
    private JFrame frame;
    private JPanel panel;
    private JLabel message;
    private Model model;
    private JTextField cardField;
    private JTextField nameField;
    private int uid;
    private double total;
    private Map<Snack, Integer> order;
    // store amounts in cents
    private Map<Integer, JSpinner> cashInput;

    TransactionPage(Model model, Map<Snack, Integer> order)
    {
        this.model = model;
        this.order = order;
        this.uid = uid;
        this.total = getTotalPrice();
        cashInput = new HashMap<>();

        frame = new JFrame();
        panel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(panel);
        frame.setVisible(true);
        panel.setLayout(null);

        message = new JLabel();
        panel.add(message);

        createTitle();
        createTimer();
        createCashPaySection();

        //$50
        createCashLabel("$50", 70, 150);
        createCashField(5000, 100, 150);

        //$20
        createCashLabel("$20", 190, 150);
        createCashField(2000, 220, 150);

        //$10
        createCashLabel("$10",310, 150 );
        createCashField(1000, 340, 150);

        //$5
        createCashLabel("$5",430, 150 );
        createCashField(500, 470, 150);

        //$2
        createCashLabel("$2",550, 150 );
        createCashField(200, 590, 150);

        //$1
        createCashLabel("$1",670, 150 );
        createCashField(100, 700, 150);

        //50c
        createCashLabel("50c",70, 190 );
        createCashField(50, 100, 190);

        //20c
        createCashLabel("20c",190, 190 );
        createCashField(20, 220, 190);

        //10c
        createCashLabel("10c",310, 190 );
        createCashField(10, 340, 190);

        //5c
        createCashLabel("5c",430, 190 );
        createCashField(5, 470, 190);

        //Pay with cash
        createPayWithCashButton();

        //Card pay section
        createCardPaySection();

        //Credit card label
        createCredCardDetLabel();

        //Credit card field
        createCredCardField();

        //Create name label
        createNameLabel();

        //Create name field
        createNameField();

        //Pay with card
        createPayWithCardButton();

        //Cancel transaction
        createCancelButton();


    }

    public double getTotalPrice() {
        double total = 0.0;
        for (Map.Entry<Snack, Integer> entry : order.entrySet()) {
            total += (entry.getKey().getPrice() * entry.getValue());
        }
        return total;
    }

    public void createTitle()
    {
        JLabel title = new JLabel();
        title.setBounds(300, 30, 300, 45);
        title.setFont(new Font("Verdana", Font.PLAIN, 30));
        title.setText(String.format("Pay Now: $%.2f", model.getCost(order)));
        panel.add(title);

    }

    public void createTimer()
    {
        Timer timer = new Timer(120000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.db.addTransaction(model.user.getId(), 0.00, 0.00, "timeout");
                SnackDisplayPage page2 = new SnackDisplayPage(model);
                frame.setVisible(false);
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void createCashPaySection()
    {
        JLabel cash = new JLabel();
        cash.setBounds(70, 100, 300, 45);
        cash.setFont(new Font("Verdana", Font.PLAIN, 20));
        cash.setText("Cash");
        panel.add(cash);
    }

    private void createCashLabel(String text, int xCoord, int yCoord)
    {
        JLabel cashDenom =  new JLabel(text);
        cashDenom.setBounds(xCoord, yCoord, 80, 25);
        panel.add(cashDenom);
    }

    private void createCashField(int amount, int xCoord, int yCoord)
    {
        SpinnerModel spinnerModel = new SpinnerNumberModel(0, 0, 50, 1);
        JSpinner spinner = new JSpinner(spinnerModel);
        spinner.setBounds(xCoord, yCoord, 50, 25);
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setEditable(false);
        panel.add(spinner);
        cashInput.put(amount, spinner);
    }

    private void createPayWithCashButton()
    {
        JButton payWithCash = new JButton("Pay");
        payWithCash.setBounds(300, 250, 170, 25);
        payWithCash.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map <Integer, Integer> payment = new HashMap<>();
                int totalCents = 0;
                int rounded = 0;
                for (Map.Entry<Integer, JSpinner> entry : cashInput.entrySet()) {
                    int num = (Integer) entry.getValue().getValue();
                    totalCents += (entry.getKey() * num);
                    if (num != 0) {
                        payment.put(entry.getKey(), num);
                    }
                }
                int totalInCents = (int) (total * 100);
                int temp = totalInCents % 5;
                if (temp < 3) {
                    rounded = totalInCents - temp;
                } else {
                    rounded = totalInCents - temp + 5;
                }
                if (totalCents < rounded) {
                    message.setVisible(true);
                    message.setBounds(345, 210, 300, 45);
                    message.setFont(new Font("Verdana", Font.PLAIN, 12));
                    message.setText("Not Enough!");
                    message.setForeground(Color.red);
                } else {
                    message.setVisible(false);
                    ModelResponse mr = model.cashTransaction(order, payment, totalCents - rounded);
                    if(mr.success) {
                        message.setVisible(true);
                        message.setBounds(345, 210, 300, 45);
                        message.setFont(new Font("Verdana", Font.PLAIN, 12));

                        String change = "";
                        if (mr.reason == "no change provided") {
                            change = "No change provided.";
                        } else {
                            TreeMap<Integer, Integer> ret = (TreeMap) mr.data;
                            for (Map.Entry<Integer, Integer> entry: ret.entrySet()) {
                                double denomination = (double) entry.getKey() / 100;
                                Integer amount = entry.getValue();
                                change += "$" + String.valueOf(denomination) + "*" + String.valueOf(amount) + " ";
                            }
                        }
                    
                        System.out.println("Change provided: " + change);

                        message.setText("Change provided: " + change);
                        message.setForeground(Color.red);

                        Timer timer = new Timer(2000, new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent arg0) {            
                                LandingPage landing = new LandingPage(model);
                                frame.setVisible(false);
                            }
                        });
                        timer.setRepeats(false);
                        timer.start();
                    } 
                    else {
                        message.setVisible(true); 
                        message.setBounds(345, 210, 300, 45);
                        message.setFont(new Font("Verdana", Font.PLAIN, 12));
                        message.setText("Error: " + mr.reason);
                        message.setForeground(Color.red);
                    }
                }
            }
        });
        panel.add(payWithCash);
    }

    private void createCardPaySection()
    {
        JLabel card = new JLabel();
        card.setBounds(70, 300, 300, 45);
        card.setFont(new Font("Verdana", Font.PLAIN, 20));
        card.setText("Card");
        panel.add(card);
    }

    private void createNameLabel()
    {
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(430, 360, 120, 25);
        panel.add(nameLabel);
    }

    private void createNameField()
    {
        nameField = new JTextField();
        nameField.setBounds(480, 360, 165, 25);
        List<String> cardInfo = model.getCard();
        if(cardInfo != null && cardInfo.size() == 2) {
            nameField.setText(cardInfo.get(0));
        }
        panel.add(nameField);
    }

    private void createCredCardDetLabel()
    {
        JLabel cardLabel =  new JLabel("Credit card number:");
        cardLabel.setBounds(110, 360, 120, 25);
        panel.add(cardLabel);
    }

    private void createCredCardField()
    {
        cardField = new JTextField();
        cardField.setBounds(240, 360, 165, 25);
        List<String> cardInfo = model.getCard();
        if(cardInfo != null && cardInfo.size() == 2) {
            cardField.setText(cardInfo.get(1));
        }
        
        panel.add(cardField);
    }

    private void createPayWithCardButton()
    {
        JButton payWithCard = new JButton("Pay");
        payWithCard.setBounds(300, 420, 170, 25);
        payWithCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                // Don't offer to save card if anon
                int n = 1;
                if(model.user.getId() != 1) {
                    n = JOptionPane.showConfirmDialog(frame, "Do you want to save your details?", "confirm", JOptionPane.YES_NO_OPTION);
                }
                int cardNo = -1;
                try {
                    cardNo = Integer.parseInt(cardField.getText());
                } catch(NumberFormatException ex) {
                    cardNo = -1;
                }
                if(cardNo == -1) {
                    message.setBounds(300, 400, 200, 25);
                    message.setText("That's not a number!");
                    message.setForeground(Color.red);
                }
                else {
                    ModelResponse mr = model.cardTransaction(order, nameField.getText(), cardNo, n);
                    if(mr.success) {
                        LandingPage landing = new LandingPage(model);
                        frame.setVisible(false);
                    }
                    else {
                        message.setBounds(300, 400, 200, 25);
                        message.setText(mr.reason);
                        message.setForeground(Color.red);
                    }
                }
            }
        });
        panel.add(payWithCard);


    }

    private void createCancelButton()
    {
        JLabel backButton = new JLabel();
        backButton.setText("<-- Cancel");
        backButton.setForeground(Color.blue);
        backButton.setBounds(10, 0, 110, 50);
        panel.add(backButton);
        backButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String reason = (String)JOptionPane.showInputDialog(frame, "Why did you cancel transaction", "Why?",
                        JOptionPane.PLAIN_MESSAGE, null, null, null);
                System.out.println(reason);
                model.db.addTransaction(model.user.getId(), 0.00, 0.00, reason);
                // this writes to file
                model.writeCancelledTransactions();
                LandingPage landing = new LandingPage(model);
                frame.setVisible(false);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                backButton.setForeground(Color.red);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                backButton.setForeground(Color.blue);
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

    }
}
