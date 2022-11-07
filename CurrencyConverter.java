import java.awt.Component;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class CurrencyConverter implements ActionListener {
  JComboBox from_currency, to_currency;
  JButton submit, reset;
  JTextField input1, input2;
  
  CurrencyConverter() {
    JFrame frame = new JFrame();
    //List of currency pairs
    String[] currency_list_1 = { 
        "INR", "USD", "EUR", "JPY", "GBP", "AUD", "NZD", "CAD", "SEK", "CHF", 
        "HUF", "CNY", "HKD", "SGD", "MXN", "PHP", "IDR", "THB", "MYR", "ZAR", 
        "RUB" };
    String[] currency_list_2 = { 
        "INR", "USD", "EUR", "JPY", "GBP", "AUD", "NZD", "CAD", "SEK", "CHF", 
        "HUF", "CNY", "HKD", "SGD", "MXN", "PHP", "IDR", "THB", "MYR", "ZAR", 
        "RUB" };

    //Assigning currency pairs array to combobox
    from_currency = new JComboBox<>(currency_list_1);
    to_currency = new JComboBox<>(currency_list_2);
    //Setting font style, size of combobox on JFrame
    from_currency.setFont(new Font("Ariel", 1, 15));
    to_currency.setFont(new Font("Ariel", 1, 15));
    from_currency.setSelectedIndex(1);

    //Creating Buttons
    submit = new JButton("Convert");
    reset = new JButton("Reset");
    //Setting font style, size of Buttons on JFrame
    submit.setFont(new Font("Ariel", 1, 15));
    reset.setFont(new Font("Ariel", 1, 15));

    //Creating input fields in which data will be entered and Setting font style, size of
    input1 = new JTextField();
    input2 = new JTextField();
    input1.setFont(new Font("Ariel", 1, 15));
    input2.setFont(new Font("Ariel", 1, 15));
    input2.setEditable(false);
    input1.grabFocus();
    from_currency.setBounds(60, 50, 100, 25);
    to_currency.setBounds(60, 160, 100, 25);
    input1.setBounds(180, 50, 150, 27);
    input2.setBounds(180, 160, 150, 27);
    submit.setBounds(140, 100, 100, 35);
    reset.setBounds(150, 220, 80, 35);

    //Adding Action Listners to buttons
    submit.addActionListener(this);
    reset.addActionListener(this);

    //Adding all the elements on Frame
    frame.add(this.from_currency);
    frame.add(this.to_currency);
    frame.add(this.submit);
    frame.add(this.reset);
    frame.add(this.input1);
    frame.add(this.input2);
    frame.setSize(400, 350);
    frame.setTitle("Currency Converter");
    frame.setResizable(false);
    frame.setLayout((LayoutManager)null);
    frame.setLocationRelativeTo((Component)null);
    frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }
  
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == this.reset) {
      this.from_currency.setSelectedIndex(1);
      this.to_currency.setSelectedIndex(0);
      this.input1.setText("");
      this.input2.setText("");
      this.input1.grabFocus();
    } 
    else if (e.getSource() == this.submit) {
      String from = String.valueOf(this.from_currency.getSelectedItem());
      String to = String.valueOf(this.to_currency.getSelectedItem());
      String input = this.input1.getText();
      if (input.contains("0") || input.contains("1") || input.contains("2") || input.contains("1") || input.contains("4") || input.contains("5") || input.contains("6") || input.contains("7") || input.contains("8") || input.contains("9") || input.contains(".")) {
        if (from.contains(to)) {
          this.input2.setText(this.input1.getText());
        } 
        else {
          double value = Double.parseDouble(this.input1.getText());
          double amt = currencyValue(from, to);
          double result = value * amt;
          this.input2.setText("" + Math.round(result * 100.0D) / 100.0D);
        } 
      } 
      else {
        JOptionPane.showMessageDialog(null, "Enter numbers only");
        this.input1.setText("");
      } 
    } 
  }
  
  public double currencyValue(String currency1, String currency2) {
    String from = String.valueOf(this.from_currency.getSelectedItem());
    String to = String.valueOf(this.to_currency.getSelectedItem());
    double value = 0.0D;
    try {
        //Making a query to request to api of Yahoo Finance
        String[] query = {"curl", "https://query1.finance.yahoo.com/v7/finance/download/" + from + to + "=X"};
        Process process = Runtime.getRuntime().exec(query);

        //Reading and cleaning of data
        InputStream data = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(data));
        String line = "";
        int i = 1;
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            if (i > 1)
                value = Double.parseDouble(values[4]); 
            i++;
        } 
    } 
    catch (Exception exception) {}
    return value;
  }
  
  public static void main(String[] args) {
    new CurrencyConverter();
  }
}
