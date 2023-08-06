package hu.ponte.hr.services;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.KeyFactory;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Service
public class SignService {
    byte[] keyFileData;

    @PostConstruct
    public void postConstructInit(){
        this.keyFileData = FileUtils.getResourceAsByteArray("config/keys/key.private_pk8key.pem");
    }

    public String signSHA256RSA(byte[] input) {
        byte[] s = null;
        try {
            String privateKey = new String(keyFileData);
            // Remove markers and new line characters in private key
            String realPK = privateKey.replaceAll("-----BEGIN PRIVATE KEY-----", "")
                    .replaceAll("-----END PRIVATE KEY-----", "")
                    .replaceAll("\n", "")
                    .replaceAll("\r", "");

            byte[] b1 = Base64.getDecoder().decode(realPK);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(b1);

            KeyFactory kf = KeyFactory.getInstance("RSA");

            Signature privateSignature = Signature.getInstance("SHA256withRSA");
            privateSignature.initSign(kf.generatePrivate(spec));
            privateSignature.update(input);
            s = privateSignature.sign();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Base64.getEncoder().encodeToString(s);
    }

}
