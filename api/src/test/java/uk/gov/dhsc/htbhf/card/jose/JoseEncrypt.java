package uk.gov.dhsc.htbhf.card.jose;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.AESEncrypter;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class JoseEncrypt {

    public static final String CARD_REQUEST_PAYLOAD = "{\n"
            + "    \"transactionId\": \"65ded5ca-f463-46bc-8ea6-d2ccf09471d4\",\n"
            + "  \"programmeCode\": \"program-code\",\n"
            + "  \"organisationId\": \"org-id\",\n"
            + "  \"person\": {\n"
            + "    \"firstName\": \"Matt\",\n"
            + "    \"lastName\": \"Smith\",\n"
            + "    \"familyStatus\": \"not-applicable\",\n"
            + "    \"gender\": \"not-applicable\",\n"
            + "    \"title\": \"not-applicable\",\n"
            + "    \"dateOfBirth\": \"1991-01-01\",\n"
            + "    \"nationality\": \"GB\"\n"
            + "  },\n"
            + "  \"emailAddress\": \"test@email.com\",\n"
            + "  \"primaryPhoneNumber\": {\n"
            + "    \"countryCode\": \"44\",\n"
            + "    \"phoneNumber\": \"7123456789\"\n"
            + "  },\n"
            + "  \"address\": {\n"
            + "    \"addressLine1\": \"Desklodge House\",\n"
            + "    \"addressLine2\": \"Redcliffe Way\",\n"
            + "    \"town\": \"Bristol\",\n"
            + "    \"region\": \"Bristol\",\n"
            + "    \"postcode\": \"BS1 6NL\",\n"
            + "    \"country\": \"GB\"\n"
            + "  },\n"
            + "  \"deliveryRecipient\": \"organisation\",\n"
            + "  \"deliveryAddress\": {\n"
            + "    \"addressLine1\": \"Desklodge House\",\n"
            + "    \"addressLine2\": \"Redcliffe Way\",\n"
            + "    \"addressLine3\": \"Address Line 3\",\n"
            + "    \"addressLine4\": \"Address Line 4\",\n"
            + "    \"town\": \"Bristol\",\n"
            + "    \"region\": \"Bristol\",\n"
            + "    \"postcode\": \"BS1 6NL\",\n"
            + "    \"country\": \"GB\"\n"
            + "  },\n"
            + "  \"plasticCode\": \"\",\n"
            + "  \"virtualFlag\": false,\n"
            + "  \"timestamp\": \"2019-11-19T10:18:58Z\"}\n";

    public static void main(String[] args) throws JOSEException, DecoderException {
        JWEHeader header = new JWEHeader(JWEAlgorithm.A256KW, EncryptionMethod.A256CBC_HS512);
        Payload payload = new Payload(CARD_REQUEST_PAYLOAD);
        JWEObject object = new JWEObject(header, payload);

        String secret = "secret-key";
        byte[] secretKey = Hex.decodeHex(secret);
        AESEncrypter encrypter = new AESEncrypter(secretKey);

        object.encrypt(encrypter);
        String token = object.serialize();
        System.out.println(token);
    }
}
