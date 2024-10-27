import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class PasswordStoreApp extends JFrame {
    private final JTextField passwordField;
    private final JTextField verifyField;
    private final JLabel statusLabel;

    private String storedSalt;
    private String storedHash;

    public PasswordStoreApp() {
        setTitle("Secure Password Vault");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set layout and panel
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(34, 45, 65));
        panel.setLayout(new GridLayout(6, 1, 10, 10));

        // Custom font
        Font font = new Font("SansSerif", Font.BOLD, 14);

        // Title Label
        JLabel titleLabel = new JLabel("Welcome to Secure Password Vault", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setForeground(new Color(255, 223, 186));
        panel.add(titleLabel);

        // Password Entry Field
        passwordField = new JPasswordField();
        styleTextField(passwordField, "Enter Password", font);
        panel.add(passwordField);

        // Verify Password Field
        verifyField = new JPasswordField();
        styleTextField(verifyField, "Verify Password", font);
        panel.add(verifyField);

        // Status Label
        statusLabel = new JLabel(" ", JLabel.CENTER);
        statusLabel.setFont(font);
        statusLabel.setForeground(new Color(255, 223, 186));
        panel.add(statusLabel);

        // Store Password Button
        JButton storeButton = createStyledButton("Store Password");
        storeButton.addActionListener(new StorePasswordAction());
        panel.add(storeButton);

        // Verify Password Button
        JButton verifyButton = createStyledButton("Verify Password");
        verifyButton.addActionListener(new VerifyPasswordAction());
        panel.add(verifyButton);

        // Add panel to frame
        add(panel);
    }

    private void styleTextField(JTextField textField, String placeholder, Font font) {
        textField.setFont(font);
        textField.setForeground(new Color(230, 230, 230));
        textField.setBackground(new Color(44, 54, 79));
        textField.setCaretColor(Color.WHITE);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(89, 126, 247)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        textField.setToolTipText(placeholder);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(89, 126, 247));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }

    private class StorePasswordAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                char[] password = passwordField.getText().toCharArray();
                String[] storedData = SecurePasswordStore.storePassword(password);
                storedSalt = storedData[0];
                storedHash = storedData[1];
                statusLabel.setText("Password stored successfully!");
            } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
                statusLabel.setText("Error storing password.");
                ex.printStackTrace();
            }
        }
    }

    private class VerifyPasswordAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                char[] password = verifyField.getText().toCharArray();
                boolean isPasswordCorrect = SecurePasswordStore.verifyPassword(password, storedSalt, storedHash);
                statusLabel.setText(isPasswordCorrect ? "Password verified!" : "Incorrect password!");
            } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
                statusLabel.setText("Error verifying password.");
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PasswordStoreApp app = new PasswordStoreApp();
            app.setVisible(true);
        });
    }
}

class SecurePasswordStore {
    private static final int SALT_LENGTH = 16;
    private static final int HASH_LENGTH = 256;
    private static final int ITERATIONS = 65536;

    // Generate a random salt
    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    // Hash password with salt using PBKDF2
    public static String hashPassword(char[] password, byte[] salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, HASH_LENGTH);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hash = keyFactory.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(hash);
    }

    // Store password securely
    public static String[] storePassword(char[] password)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = generateSalt();
        String hashedPassword = hashPassword(password, salt);
        return new String[]{Base64.getEncoder().encodeToString(salt), hashedPassword};
    }

    // Verify password
    public static boolean verifyPassword(char[] password, String storedSalt, String storedHash)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = Base64.getDecoder().decode(storedSalt);
        String hash = hashPassword(password, salt);
        return hash.equals(storedHash);
    }
}
