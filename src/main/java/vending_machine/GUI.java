package vending_machine;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.*;



public class GUI extends JFrame implements ActionListener {

    private String username;
    private String password;
    private JTextField userText;
    private JPasswordField passwordField;
    private JPanel panel;
    private JFrame frame;
    private JLabel message;
    private Model model;


    GUI(Model model) {


        this.model = model;
        panel = new JPanel();
        frame = new JFrame();
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(panel);

        message = new JLabel();
        panel.add(message);

        panel.setLayout(null);

        //User name label
        createUsernameLabel();

        //User name field
        createUsernameField();

        //Password label
        createPasswordLabel();

        //Password field
        createPasswordFields();

        //Login button
        createLoginButton();

        //Don't have an account button?
        createNoAccountButton();

        // Back button
        createBackButton();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.username = userText.getText();
        this.password = passwordField.getText();
        ModelResponse mr = this.model.checkLogin(this.username, this.password);
        if(mr.success) {
            // message.setBounds(320, 250, 200, 25);
            // message.setText("SUCCESSFUL LOGIN BRO NICE");
            // message.setForeground(Color.red);


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
                    break;


            }
            frame.setVisible(false);
        }
        else {
            message.setBounds(320, 250, 200, 25);
            message.setText(mr.reason);
            message.setForeground(Color.red);
        }

    }

    public String getUsername()
    {
        return this.username;

    }

    public String getPassword()
    {
        return this.password;
    }

    private void createUsernameLabel()
    {
        JLabel user =  new JLabel("User name: ");
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
        JLabel password = new JLabel("Password");
        password.setBounds(260, 130, 80, 25);
        panel.add(password);
    }

    private void createPasswordFields()
    {
        passwordField = new JPasswordField(20);
        passwordField.setBounds(350, 130, 165, 25);
        panel.add(passwordField);

    }

    private void createLoginButton()
    {
        JButton login = new JButton("Login");
        login.setBounds(350, 180, 80, 25 );
        login.addActionListener(this);
        panel.add(login);
    }

    private void createNoAccountButton()
    {
        JButton makeAccount = new JButton("Don't have an account?");
        makeAccount.setBounds(308, 230, 170, 25);
        makeAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateAccountPage newpage = new CreateAccountPage(model);

                frame.setVisible(false);
            }
        });
        panel.add(makeAccount);

        frame.setVisible(true);

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
