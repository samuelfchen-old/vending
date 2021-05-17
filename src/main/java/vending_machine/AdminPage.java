package vending_machine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class AdminPage {
    private JPanel panel;
    private JFrame frame;
    private JLabel message;
    private JLabel userDisplay;
    private Model model;
    private JTextField username;

    public AdminPage(Model model)
    {
        this.model = model;
        panel = new JPanel();
        frame = new JFrame();
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        panel.setLayout(null);
        frame.setVisible(true);

        message = new JLabel();
        panel.add(message);

        createTitle();
        createAddAcount();
        createRemoveUser();
        displayUsers();
        removeUser();
        getTransactions();
        getItemDetails();
    }

    public void createTitle()
    {
        JLabel title = new JLabel();
        title.setBounds(250, 30, 300, 45);
        title.setFont(new Font("Verdana", Font.PLAIN, 30));
        title.setText("Admin Page");
        panel.add(title);

    }

    private void createAddAcount()
    {
        JButton createAccount = new JButton("Add Account");
        createAccount.setBounds(300, 100, 170, 25);
        createAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateAccountPage page = new CreateAccountPage(model);
                frame.setVisible(false);
            }
        });
        panel.add(createAccount);
    }

    private void createRemoveUser() {
        JButton remove = new JButton("Remove");
        remove.setBounds(500, 200, 150, 25);
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = username.getText();
                ModelResponse mr = model.checkUsername(user);
                message.setBounds(500, 230, 300, 25);
                message.setText(mr.reason);
                if (mr.success) {
                    message.setForeground(Color.green);
                } else {
                    message.setForeground(Color.red);
                }
                List<User> users = model.db.getUsers();
                String info = getInfo(users);
                userDisplay.setText("<html>Users:<br>" + info + "</html>");
            }
        });
        panel.add(remove);
    }

    private void displayUsers() {
        List<User> users = model.db.getUsers();
        String info = getInfo(users);

        userDisplay = new JLabel();
        userDisplay.setBounds(10, 140, 500, 500);
        userDisplay.setVerticalAlignment(JLabel.TOP);
        panel.add(userDisplay);

        userDisplay.setText("<html>Users:<br>" + info + "</html>");

        JButton saveUsers = new JButton("Save user records");
        saveUsers.setBounds(10, 500, 200, 25);
        saveUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.writeUsers();
                JLabel response = new JLabel();
                response.setBounds(10, 530, 300, 25);
                response.setVerticalAlignment(JLabel.TOP);
                panel.add(response);
                response.setText("Users saved");
                response.setForeground(Color.green);
            }
        });
        panel.add(saveUsers);
    }

    private String getInfo(List<User> users) {
        String information = "";
        for (User u : users) {
            String name = u.getName();
            String type = u.getType();
            information += String.format("<pre class=\"tab\">Name: %-30s" + "Type: " + type + "</pre> <br>", name);
        }
        return information;
    }

    private void removeUser() {
        JLabel removeDisplay = new JLabel();
        removeDisplay.setBounds(500, 140, 200, 25);
        removeDisplay.setVerticalAlignment(JLabel.TOP);
        panel.add(removeDisplay);
        removeDisplay.setText("<html>Remove Users: </html>");

        username = new JTextField(20);
        username.setBounds(500, 170, 200, 25);
        panel.add(username);
    }

    private void getTransactions() {
        JButton transactions = new JButton("Get Transaction History");
        transactions.setBounds(500, 260, 210, 25);
        transactions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.writeTransactions();
                model.writeCancelledTransactions();
                JLabel response = new JLabel();
                response.setBounds(500, 290, 300, 25);
                response.setVerticalAlignment(JLabel.TOP);
                panel.add(response);
                response.setText("Transaction history saved");
                response.setForeground(Color.green);
            }
        });
        panel.add(transactions);
    }

    private void getItemDetails() {
        JButton items = new JButton("Access item details");
        items.setBounds(500, 500, 210, 25);
        items.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SellerPage sp = new SellerPage(model, true);
                frame.setVisible(false);
            }
        });
        panel.add(items);
    }
}
