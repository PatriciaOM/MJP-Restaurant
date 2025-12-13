package com.mjprestaurant.controller;

import javax.net.ssl.*;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;

/**
 * Classe que controla la comprovació del truststore per la connexió HTTPS
 * @author Patricia Oliva
 */
public class SSLConfig {

    public static void init() throws Exception {

        KeyStore trustStore = KeyStore.getInstance("PKCS12");

        try (InputStream is = SSLConfig.class
                .getClassLoader()
                .getResourceAsStream("truststore.p12")) {

            if (is == null) {
                throw new RuntimeException("No es troba truststore.p12 a resources");
            }

            trustStore.load(is, "changeit".toCharArray());
        }

        TrustManagerFactory tmf =
                TrustManagerFactory.getInstance(
                        TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(trustStore);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), new SecureRandom());

        HttpsURLConnection.setDefaultSSLSocketFactory(
                sslContext.getSocketFactory());
    }
}

