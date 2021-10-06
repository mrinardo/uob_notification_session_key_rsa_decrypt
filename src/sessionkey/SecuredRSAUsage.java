package sessionkey;

import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher ;

import java.io.File;
import java.lang.Exception ;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.Key ;
import java.security.KeyFactory;

public class SecuredRSAUsage {

        //static int RSA_KEY_LENGTH = 4096;
        static String ALGORITHM_NAME = "RSA" ;
        static String PADDING_SCHEME = "PKCS1Padding"; //"OAEPWITHSHA-512ANDMGF1PADDING" ;
        static String MODE_OF_OPERATION = "ECB" ; // This essentially means none behind the scene

        public static PrivateKey readPrivateKey(File file) throws Exception {
            String key = new String(Files.readAllBytes(file.toPath()), Charset.defaultCharset());

            String privateKeyPEM = key
              .replace("-----BEGIN PRIVATE KEY-----", "")
              .replaceAll(System.lineSeparator(), "")
              .replace("-----END PRIVATE KEY-----", "");

            byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
            return keyFactory.generatePrivate(keySpec);
            

        }

        public static String decryptPayload (String sPayload, String sPrivateKeyPEM) throws Exception {
        	String decryptedText="";

        	File fPrivatePEM=new File(sPrivateKeyPEM);
            PrivateKey PrivateKey = readPrivateKey(fPrivatePEM);

            try {
            	decryptedText = rsaDecrypt(Base64.getDecoder().decode(sPayload), PrivateKey);

            } catch(Exception e) {System.out.println("Exception while encryption/decryption") ;e.printStackTrace() ; }

            return decryptedText;
        }

        public static String rsaDecrypt(byte[] encryptedMessage, Key privateKey) throws Exception {
                Cipher c = Cipher.getInstance(ALGORITHM_NAME + "/" + MODE_OF_OPERATION + "/" + PADDING_SCHEME) ;
                c.init(Cipher.DECRYPT_MODE, privateKey);
                byte[] plainText = c.doFinal(encryptedMessage);

                return Base64.getEncoder().encodeToString(plainText)  ;

        }
}
