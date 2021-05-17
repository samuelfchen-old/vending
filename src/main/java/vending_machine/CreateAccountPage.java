package vending_machine;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.*;
/*
Going to refactor this code to make it neater later
*/
public class CreateAccountPage extends JFrame {

    private String username;
    private String newPassword;
    private JTextField userText;
    private JPasswordField passwordField;
    private JPasswordField passwordTwoField;
    private JPanel panel;
    private JFrame frame;
    private JLabel message;
    private Model model;
    String accountType = "customer";



    CreateAccountPage(Model model)
    {

        this.model = model;
        frame = new JFrame();
        panel = new JPanel();

        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setVisible(true);

        panel.setLayout(null);

        message = new JLabel();
        panel.add(message);


        //Username Label
        createUsernameLabel();

        //User name field
        createUsernameField();

        //Password Label
        createPasswordLabel();
        //Password Field
        createPasswordField();

        //Re-enter Password Label
        createReEnterPassLabel();

        //Re-enter Password Field
        createReEnterPassField();

        //Back To Main Screen Option top left corner to go back to login page
        createBackButton();

        //Add an "ok" button
        createOkButton();

    }


    private void createUsernameLabel()
    {
        JLabel user = new JLabel("User name: ");
        user.setBounds(260, 80, 80, 25);
        panel.add(user);
    }

    private void createUsernameField()
    {
        userText = new JTextField(20);
        userText.setBounds(350, 80, 165, 25);
        panel.add(userText);

    }

    private void createPasswordLabel()
    {
        JLabel password = new JLabel("Password:");
        password.setBounds(260, 130, 80, 25);
        panel.add(password);

    }

    private void createPasswordField()
    {
        passwordField = new JPasswordField(20);
        passwordField.setBounds(350, 130, 165, 25);
        panel.add(passwordField);
    }

    private void createReEnterPassLabel()
    {
        JLabel passwordTwo = new JLabel("Re-enter password: ");
        passwordTwo.setBounds(210, 180, 180, 25);
        panel.add(passwordTwo);
    }

    private void createReEnterPassField()
    {
        passwordTwoField = new JPasswordField(20);
        passwordTwoField.setBounds(350, 180, 165, 25);
        panel.add(passwordTwoField);
    }

    private void createBackButton()
    {
        JLabel backButton = new JLabel();
        backButton.setText("<-- Back");
        backButton.setForeground(Color.blue);
        backButton.setBounds(10, 0, 110, 50);
        panel.add(backButton);
        backButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                GUI homeLogin = new GUI(model);
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

    private void createOkButton()
    {
        JButton next = new JButton("Next");
        next.setBounds(347, 230, 80, 25 );
        if(model.user != null) {
            if (model.user.type.equals("owner")) {

                ButtonGroup buttonGroup = new ButtonGroup();
                JRadioButton cashier = new JRadioButton("Cashier");
                JRadioButton seller = new JRadioButton("Seller");
                buttonGroup.add(cashier);
                buttonGroup.add(seller);
                cashier.setBounds(380, 350, 200, 25);
                seller.setBounds(380, 380, 200, 25);
                cashier.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        accountType = "cashier";
                    }
                });

                seller.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        accountType = "seller";
                    }
                });
                panel.add(cashier);
                panel.add(seller);
            }
        }
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                ModelResponse mr = model.createAccount(username, passwordField.getText(), passwordTwoField.getText(), accountType);
                if(mr.success) {
                    // log in and redirect here 
                    message.setBounds(320, 250, 200, 25);
                    message.setText("Account made!");
                    message.setForeground(Color.red);
                    model.checkLogin(userText.getText(), passwordField.getText());
                    switch(model.user.type)
                    {
                        case "owner":
                            System.out.println(model.user.type);
                            AdminPage admin = new AdminPage(model);
                            frame.setVisible(false);
                            break;

                        case "customer":
                            System.out.println(model.user.type);
                            SnackDisplayPage homepage = new SnackDisplayPage(model);
                            frame.setVisible(false);
                            break;
                        case "cashier":
                            CashierPage page = new CashierPage(model);
                            frame.setVisible(false);
                            break;
                        case "seller":
                            SellerPage sellerPage = new SellerPage(model, false);
                            frame.setVisible(false);


                    }


                    frame.setVisible(false);
                    
                }
                else {
                    message.setBounds(320, 250, 200, 25);
                    message.setText(mr.reason);
                    message.setForeground(Color.red);
                }   
            }
        });
        panel.add(next);

    }


}
