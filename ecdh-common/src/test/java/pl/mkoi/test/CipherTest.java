package pl.mkoi.test;

import org.apache.log4j.Logger;
import org.junit.Test;
import pl.mkoi.ecdh.crypto.model.Point;
import pl.mkoi.ecdh.crypto.model.Polynomial;
import pl.mkoi.ecdh.crypto.util.AesCipher;

import java.util.Random;


public class CipherTest {
    private final static Logger log = Logger.getLogger(CipherTest.class);

    @Test
    public void cipherTest() {
        for (int i = 0; i < 10000; i++) {
            Random random = new Random();
            int x = random.nextInt(753) + 100;
            int y = random.nextInt(753) + 100;

            Point generatedPoint = new Point(Polynomial.createFromLong(x), Polynomial.createFromLong(y));

            String plainText = "to jest kod do zaszyfasdasdasdasdsadsadśąśćąśćąśćąśćąśćąścsrowania, taki w sumie nie za krótki, ani nie za długi. (*&^%$#!^&^@#$@#$@#$ Taki w sumie spoko. Dziwne znaki też są.";

            String encryptedText = AesCipher.encrypt(generatedPoint, plainText);
            assert plainText.equals(AesCipher.decrypt(generatedPoint, encryptedText));

            log.info("\n" + plainText + "\n" + encryptedText);
            log.info("Strings are same " + i);

        }
    }

}
