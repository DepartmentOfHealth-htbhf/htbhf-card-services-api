package uk.gov.dhsc.htbhf.card.jose;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.crypto.AESDecrypter;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.text.ParseException;

public class JoseDecrypt {

    public static final String RESPONSE = "eyJhbGciOiJBMjU2S1ciLCJlbmMiOiJBMjU2Q0JDLUhTNTEyIn0.gDYvUc50H3wqHhCe5VMVuUd5WHKLGtXZNJUXCtp2_eA2ll97NiLiRsrzn2i5gLT6QVdjgELJ5_jXB-M97NVGEJBCrwnMCvxG.D8VRR264cNpLxUWH7dJKMw.j966POkdNUjJUkCEjgxi179sK7LbeXHbEpxwA9AA2W7MY9RkEg_TlIBIzrrXOjVOLGVh0PFUZDfpYRiBB0BDbndkdqP40hipzKd8Li9-L6eqWr5GFK7BGqP6mnUlRl7JJ5DNqrpcU8b3_Tj3iNrXBWerKwetGxKasxbjtAoy5CtY0BMKzNzuG5Arp4vg6SnUENS8oHtTxHAovXWL_jvLmM9GGVbS2PEUO3zKei4UWZO7qYDn2guRn9LbN-D-vEc5iu3E_J7qffF2TrZ4KpSFI98AQPKHIILEg8ZHOl0IId4.UA3v_EBFKc0U6gnaVCBLu_QZDQJRAPvxJO5T6cxeKJU";

    public static void main(String[] args) throws JOSEException, DecoderException, ParseException {
        JWEObject jweObject = JWEObject.parse(RESPONSE);
        String secret = "secret-key";
        byte[] secretKey = Hex.decodeHex(secret);
        jweObject.decrypt(new AESDecrypter(secretKey));

        System.out.println(jweObject.getPayload());
    }
}
