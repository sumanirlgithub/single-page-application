package com.neo.api.order.config;

import com.neo.api.order.exception.OrderServiceException;
import feign.Client;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

@Configuration
@Slf4j
public class FeignClientConfig {
    //@Value("${server.ssl.key-store:test}")
    //private String keyStore;

    //@Value("${server.ssl.key-store-password:test}")
    //private String keyStorePass;

    @Value("${payment.ssl.trust-store:test}")
    private String trustStore;
    @Value("${payment.ssl.trust-store-password:test}")
    private String trustStorePass;

    @Value("${payment.service.url:test}")
    private String paymentUrl;

    @Bean
    public Client feignClient() {
        log.info("Retrieve paymentUrl from environment : " + paymentUrl);
        Client trustSslSockets =  new Client.Default(
                sslContextFactory(),
                new NoopHostnameVerifier());
        return trustSslSockets;
    }

    private SSLSocketFactory sslContextFactory() {
        try (FileInputStream is =
                     new FileInputStream(ResourceUtils.getFile(trustStore));) {
            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            keystore.load(is, trustStorePass.toCharArray());

            SSLContext contextBuilder  = SSLContextBuilder
                    .create()
                    //.loadKeyMaterial(ResourceUtils.getFile(keyStore), keyStorePass.toCharArray(),
                    //        keyStorePass.toCharArray())
                    .loadTrustMaterial(ResourceUtils.getFile(trustStore), trustStorePass.toCharArray())
                    .build();
            return contextBuilder.getSocketFactory();
        } catch (NoSuchAlgorithmException | KeyStoreException | CertificateException  | IOException | KeyManagementException ex) {
            ex.printStackTrace();
            throw new OrderServiceException("failed to initialize:" + ex.getMessage());
        } //catch (UnrecoverableKeyException e) {
          //  throw new RuntimeException(e);
        //}
    }
}
