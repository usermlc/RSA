import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Scanner;

public class Main {
    private BigInteger publicKey;
    private BigInteger privateKey;
    private BigInteger modulus;

    public Main(int bitLength) {
        SecureRandom random = new SecureRandom();

        BigInteger p = BigInteger.probablePrime(bitLength / 2, random);
        BigInteger q = BigInteger.probablePrime(bitLength / 2, random);
        modulus = p.multiply(q);

        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        publicKey = BigInteger.valueOf(65537);
        while (phi.gcd(publicKey).compareTo(BigInteger.ONE) > 0 && publicKey.compareTo(phi) < 0) {
            publicKey = publicKey.add(BigInteger.TWO);
        }

        privateKey = publicKey.modInverse(phi);
    }

    public BigInteger encrypt(BigInteger message) {
        return message.modPow(publicKey, modulus);
    }

    public BigInteger decrypt(BigInteger encrypted) {
        return encrypted.modPow(privateKey, modulus);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the message (as a number):");
        BigInteger message = new BigInteger(scanner.nextLine());

        int bitLength = 4096;
        Main rsa = new Main(bitLength);

        BigInteger encryptedMessage = rsa.encrypt(message);
        BigInteger decryptedMessage = rsa.decrypt(encryptedMessage);

        System.out.println("Original Message: " + message);
        System.out.println("Encrypted Message: " + encryptedMessage);
        System.out.println("Decrypted Message: " + decryptedMessage);
    }
}
