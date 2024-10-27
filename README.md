# Vaultify

**Vaultify** is a secure password-storing application built in Java that leverages PBKDF2 hashing with salt generation for strong password security. The application features a sleek and modern Swing-based GUI, making it easy to securely store and verify passwords. Vaultify serves as both a practical solution for password management and a learning tool for secure password-handling techniques in Java.

![Vaultify Screenshot](screenshot.png) *(Add a screenshot of your UI here)*

## Features

- **Secure Password Hashing**: Uses PBKDF2 with SHA-256 for robust password hashing, combined with a unique salt for each password.
- **Password Verification**: Easily verify stored passwords against user input with a high degree of security.
- **User-Friendly UI**: Modern, dark-themed interface built with Java Swing for an intuitive user experience.
- **Enhanced Security Practices**: Follows best practices in password management, protecting against brute-force and rainbow table attacks.

## Getting Started

Follow these instructions to set up Vaultify on your local machine.

### Prerequisites

Ensure you have Java installed. You can check with:

```bash
java -version
```

If Java isn’t installed, download it from [Oracle's Java SE Downloads](https://www.oracle.com/java/technologies/javase-downloads.html).

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/yourusername/vaultify.git
   cd vaultify
   ```

2. Compile the application:

   ```bash
   javac PasswordStoreApp.java
   ```

3. Run the application:

   ```bash
   java PasswordStoreApp
   ```

## Usage

1. **Store a Password**: Enter a password in the "Enter Password" field and click **Store Password**. Vaultify will generate a secure hash and salt, storing them in memory.
2. **Verify a Password**: Enter the password in the "Verify Password" field and click **Verify Password**. If the input matches the stored hash, you’ll see a confirmation message.

## Project Structure

```plaintext
vaultify/
├── PasswordStoreApp.java   # Main application file with Swing UI
├── SecurePasswordStore.java # Password hashing and verification logic
└── README.md               # Project documentation
```

## How It Works

Vaultify utilizes Java's `PBKDF2WithHmacSHA256` algorithm to hash passwords, along with a unique salt for each entry, following these steps:

1. **Salt Generation**: A unique, random salt is generated for each password.
2. **Hashing**: The password and salt are hashed together using PBKDF2.
3. **Verification**: When verifying, Vaultify hashes the input with the stored salt and checks it against the stored hash.

## Contributing

Contributions are welcome! To contribute:

1. Fork the repository.
2. Create a new branch: `git checkout -b feature-name`.
3. Commit your changes: `git commit -m "Add feature-name"`.
4. Push to the branch: `git push origin feature-name`.
5. Open a pull request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

## Contact

For questions or support, please contact [haafizshamnad@gmail.com](mailto:haafizshamnad@gmail.com).
