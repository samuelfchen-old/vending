package vending_machine;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.*;


public class LandingPage extends JFrame {

    private JFrame frame;
    private JPanel panel;
    private Model model;


    LandingPage(Model model)
    {
        this.model = model;
        frame = new JFrame();
        panel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(panel);
        frame.setVisible(true);
        panel.setLayout(null);

        createTitle();
        // Move these to a customer GUI
        // createDrinksSection();
        // createChocolatesSection();
        // createChipsSection();
        // createCandySection();
        createLoginButton();
        createNewAccount();
        createGuestButton();

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
        drinks.setBounds(10, 90, 40, 40);
        drinks.setText("Drinks");
        panel.add(drinks);
    }

    //Chocolates section on default page
    private void createChocolatesSection()
    {
        JLabel chocolates = new JLabel();
        chocolates.setBounds(95, 90, 70, 40);
        chocolates.setText("Chocolates");
        panel.add(chocolates);
    }

    //Chips section on default page
    private void createChipsSection()
    {
        JLabel chips = new JLabel();
        chips.setBounds(210, 90, 40, 40);
        chips.setText("Chips");
        panel.add(chips);
    }

    private void createCandySection()
    {
        JLabel candy = new JLabel();
        candy.setBounds(300, 90, 40, 40);
        candy.setText("Candy");
        panel.add(candy);
    }

    private void createLoginButton()
    {
        JButton loginbutton = new JButton("Login");
        loginbutton.setForeground(Color.blue);
        loginbutton.setBounds(360, 200, 80, 25);
        panel.add(loginbutton);
        loginbutton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                GUI homeLogin = new GUI(model);
                frame.setVisible(false);


            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {


            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

    }

    private void createNewAccount()
    {
        /*
        JUST A NOTE: When user clicks back we want to be able to go to last visited page,
        so our model class will have to deal with all of that
        */
        JButton newAccountButton = new JButton("New Account");
        newAccountButton.setForeground(Color.blue);
        newAccountButton.setBounds(340, 300, 120, 25);
        panel.add(newAccountButton);
        newAccountButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
                CreateAccountPage page = new CreateAccountPage(model);
                frame.setVisible(false);


            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {


            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

    }

    private void createGuestButton()
    {
        JButton loginbutton = new JButton("Continue as Guest");
        loginbutton.setForeground(Color.blue);
        loginbutton.setBounds(325, 400, 150, 25);
        panel.add(loginbutton);
        loginbutton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ModelResponse mr = model.checkLogin("anonymous", "anonymous");
                if(mr.success) {
                    // Change to user page once created
                    System.out.println("logged in anonymously");

                    // Switch to snack display page (testing only)
                    SnackDisplayPage snackDisplay = new SnackDisplayPage(model);
                    frame.setVisible(false);

                }
                // This should never happen
                else {
                    System.out.println("uhoh stinky");
                }
                
                frame.setVisible(false);


            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {


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
