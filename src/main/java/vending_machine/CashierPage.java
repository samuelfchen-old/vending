package vending_machine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CashierPage {
    private JPanel panel;
    private JFrame frame;
    private JLabel message;
    private Model model;
    private Map<Integer, JSpinner> cashInput;

    /*
        For this page the cashier is presented with a few spinners and he is able to adjust
        the amount of each currency in the system by turning them and setting it. If he clicks
        generate report a report is generated on your local system
     */


    public CashierPage(Model model)
    {
        this.model = model;
        panel = new JPanel();
        frame = new JFrame();
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cashInput = new HashMap<>();



        frame.add(panel);
        panel.setLayout(null);
        frame.setVisible(true);
        createTitle();

        //$50
        createCashLabel("$50", 70, 150);
        createCashField(5000, 100, 150, model.db.getCurrency().get(5000));

        //$20
        createCashLabel("$20", 190, 150);
        createCashField(2000, 220, 150, model.db.getCurrency().get(2000));

        //$10
        createCashLabel("$10",310, 150 );
        createCashField(1000, 340, 150, model.db.getCurrency().get(1000));

        //$5
        createCashLabel("$5",430, 150 );
        createCashField(500, 470, 150, model.db.getCurrency().get(500));

        //$2
        createCashLabel("$2",550, 150 );
        createCashField(200, 590, 150, model.db.getCurrency().get(200));

        //$1
        createCashLabel("$1",670, 150 );
        createCashField(100, 700, 150, model.db.getCurrency().get(100));

        //50c
        createCashLabel("50c",70, 190 );
        createCashField(50, 100, 190, model.db.getCurrency().get(50));

        //20c
        createCashLabel("20c",190, 190 );
        createCashField(20, 220, 190, model.db.getCurrency().get(20));

        //10c
        createCashLabel("10c",310, 190 );
        createCashField(10, 340, 190, model.db.getCurrency().get(10));

        //5c
        createCashLabel("5c",430, 190 );
        createCashField(5, 470, 190, model.db.getCurrency().get(5));
        createSetButton();
        generateReportButton();
    }

    public void createTitle()
    {
        JLabel title = new JLabel();
        title.setBounds(250, 30, 300, 45);
        title.setFont(new Font("Verdana", Font.PLAIN, 30));
        title.setText("Cashier Page");
        panel.add(title);

    }


    private void createCashLabel(String text, int xCoord, int yCoord)
    {
        JLabel cashDenom =  new JLabel(text);
        cashDenom.setBounds(xCoord, yCoord, 80, 25);
        panel.add(cashDenom);
    }

    private void createSetButton()
    {
        JButton set = new JButton("Set");
        set.setBounds(300, 250, 170, 25);
        set.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(Map.Entry<Integer, JSpinner> input: cashInput.entrySet())
                {

                    model.db.sellerUpdateCurrency(input.getKey(), (Integer)input.getValue().getValue());
                }

            }
        });
        panel.add(set);
    }

    private void generateReportButton()
    {
        JButton report = new JButton("Generate Report");
        report.setBounds(300, 290, 170, 25);
        panel.add(report);
        report.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.writeTransactions();

            }
        });
    }


    private void createCashField(int amount, int xCoord, int yCoord, int currentVal)
    {
        SpinnerModel spinnerModel = new SpinnerNumberModel(currentVal, 0, 15, 1);
        JSpinner spinner = new JSpinner(spinnerModel);
        spinner.setBounds(xCoord, yCoord, 50, 25);
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setEditable(false);
        panel.add(spinner);
        cashInput.put(amount, spinner);

    }




}