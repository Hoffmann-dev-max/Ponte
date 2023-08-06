package hu.ponte.hr.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.KeyFactory;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

/**
 * Service for signing input data with a private key
 *
 * @author  Zoltán Hoffmann
 * @version 1.0
 * @since   2023-08-07
 */
@Service
public class SignService {

    Logger logger = LoggerFactory.getLogger(SignService.class);

    String keyFileData;

    @PostConstruct
    public void postConstructInit() {
        String rawKeyFileData = new String(FileUtils.getResourceAsByteArray("config/keys/key.private_pk8key.pem"));
        keyFileData = rawKeyFileData.replaceAll("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll("-----END PRIVATE KEY-----", "")
                .replaceAll("\n", "")
                .replaceAll("\r", "");
    }

    public String signSHA256RSA(byte[] input) {
        logger.info("SignService.signSHA256RSA() with byte array of this length:{}", input.length);

        byte[] signatureBytes = null;
        try {

            byte[] b1 = Base64.getDecoder().decode(keyFileData);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(b1);

            KeyFactory kf = KeyFactory.getInstance("RSA");

            Signature privateSignature = Signature.getInstance("SHA256withRSA");
            privateSignature.initSign(kf.generatePrivate(spec));
            privateSignature.update(input);
            signatureBytes = privateSignature.sign();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return Base64.getEncoder().encodeToString(signatureBytes);
    }

}
