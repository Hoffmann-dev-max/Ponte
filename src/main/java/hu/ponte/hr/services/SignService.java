package hu.ponte.hr.services;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Service
public class SignService {
    File keyFile;

    @PostConstruct
    public void postConstructInit(){
        try {
            this.keyFile = new ClassPathResource("config/keys/key.private_pk8key.pem").getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String signSHA256RSA(byte[] input) throws Exception {
        String privateKey = new String(Files.readAllBytes(keyFile.toPath()));

        // Remove markers and new line characters in private key
        String realPK = privateKey.replaceAll("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll("-----END PRIVATE KEY-----", "")
                .replaceAll("\n", "")
                .replaceAll("\r","");

        byte[] b1 = Base64.getDecoder().decode(realPK);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(b1);

        KeyFactory kf = KeyFactory.getInstance("RSA");

        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(kf.generatePrivate(spec));
        privateSignature.update(input);
        byte[] s = privateSignature.sign();
        return Base64.getEncoder().encodeToString(s);
    }

}
