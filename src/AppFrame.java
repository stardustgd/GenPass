import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.security.SecureRandom;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class AppFrame implements ActionListener, ChangeListener {

    private char capitalLetters[] = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    private char lowercaseLetters[] = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    private char numbers[] = {'0','1','2','3','4','5','6','7','8','9'};
    private char symbols[] = {'!','@','#','$','%','^','&','*'};

    private final int PASS_LENGTH_MIN = 1;
    private final int PASS_LENGTH_MAX = 50;
    private final int PASS_LENGTH_INIT = 16;

    private String password = "";
    private int passLength = PASS_LENGTH_INIT;

    private JLabel passwordLengthLabel;
    private JTextField passwordLengthField; 
    private JSlider passwordLength;
    private JLabel passwordLabel;
    private JTextField passwordTextField;

    private JButton generateButton;
    private JButton copyButton;

    private JFrame frame;
    private JPanel panel;

    SecureRandom rand = new SecureRandom();

    public AppFrame() {

        frame = new JFrame();

        passwordLengthLabel = new JLabel("Password length");

        passwordLengthField = new JTextField("16");
        passwordLengthField.setEditable(false);

        passwordLength = new JSlider(JSlider.HORIZONTAL, PASS_LENGTH_MIN, PASS_LENGTH_MAX, PASS_LENGTH_INIT);
        passwordLength.addChangeListener(this);

        generateButton = new JButton("Generate");
        generateButton.addActionListener(this);
        generateButton.setBackground(new Color(0, 130, 216));
        generateButton.setForeground(Color.WHITE);
        generateButton.setFocusPainted(false);
        generateButton.setFont(new Font("Arial", Font.BOLD, 12));

        passwordLabel = new JLabel("Password: ");

        passwordTextField = new JTextField("Click button to generate password");
        passwordTextField.setEditable(false);

        copyButton = new JButton("Copy to clipboard");
        copyButton.addActionListener(this);
        copyButton.setBackground(new Color(122, 122, 122));
        copyButton.setForeground(Color.WHITE);
        copyButton.setFocusPainted(false);

            
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(passwordLengthLabel);
        panel.add(passwordLengthField);
        panel.add(passwordLength);
        panel.add(generateButton);
        panel.add(passwordLabel);
        panel.add(passwordTextField);
        panel.add(copyButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("GenPass");
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        }

    private String generatePassword() {
        password = "";
        int charChooser = 0;

        for (int i = 0; i < passLength; i++) {
            charChooser = rand.nextInt(4) + 1;

            switch (charChooser) {
                case 1:
                   password += capitalLetters[rand.nextInt(capitalLetters.length)];
                   break;
                case 2:
                   password += lowercaseLetters[rand.nextInt(lowercaseLetters.length)];
                   break;
                case 3:
                   password += numbers[rand.nextInt(numbers.length)];
                   break;
                case 4:
                   password += symbols[rand.nextInt(symbols.length)];
                   break;
            }
        }

        return password;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == generateButton) {
            passwordTextField.setText(generatePassword());
        }
        else if (e.getSource() == copyButton) {
            if (passwordTextField.getText().equals("Click button to generate password")) {
                return;
            }
            else {
                StringSelection stringSelection = new StringSelection(passwordTextField.getText());
                Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
                clpbrd.setContents(stringSelection, null);
            }
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        passLength = passwordLength.getValue();
        passwordLengthField.setText(String.valueOf(passLength));
        
    }
}
