package com.neo.payment.config;


import com.neo.payment.exception.ApiServiceException;
import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.util.ResourceUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.security.KeyStore;

@Configuration
public class WebClientConfig {

    //@Value("${server.ssl.key-store:test}")
    //private String keyStore;

    //@Value("${server.ssl.key-store-password:test}")
    //private String keyStorePass;

    @Value("${payment-gateway.ssl.trust-store:test}")
    private String trustStore;

    @Value("${payment-gateway.ssl.trust-store-password:test}")
    private String trustStorePass;

    @Value("${payment-gateway.service.connection-timeout}")
    private int httpConnectionTimeout;

    /**
     * Configuration for webclient
     *
     * @return WebClient
     */
    @Bean
    public WebClient webClient() {
        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory();
        uriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, httpConnectionTimeout)
                //.secure(sslSpec -> sslSpec.sslContext(createSslContext(keyStore, keyStorePass, trustStore, trustStorePass)))
                //.secure(sslSpec -> sslSpec.sslContext(createSslContext(trustStore, trustStorePass)))
                ;

        ClientHttpConnector clientHttpConnector = new ReactorClientHttpConnector(httpClient);
        return WebClient.builder()
                .uriBuilderFactory(uriBuilderFactory)
                .clientConnector(clientHttpConnector)
                .build();
    }

    /**
     * Create SslContext for handshake between client and server
     *
     * @return SslContext sslcontext
     * @throws Exception exception
     */
    private SslContext createSslContext(String trustStorePath, String trustStorePassword) {
        try {
            KeyStore trustStoreInst = KeyStore.getInstance(KeyStore.getDefaultType());
            try (FileInputStream trustStoreStream = new FileInputStream(ResourceUtils.getFile(
                    trustStorePath))) {
                trustStoreInst.load(trustStoreStream, trustStorePassword.toCharArray());
            }
//            KeyStore keyStoreInst = KeyStore.getInstance(KeyStore.getDefaultType());
//            try (FileInputStream keyStoreStream = new FileInputStream(ResourceUtils.getFile(
//                    keyStorePath))) {
//                keyStoreInst.load(keyStoreStream, keyStorePassword.toCharArray());
//            }

            //KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
            //        KeyManagerFactory.getDefaultAlgorithm());
            //keyManagerFactory.init(keyStoreInst, keyStorePassword.toCharArray());
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(trustStoreInst);

            return SslContextBuilder.forClient()
                    //.keyManager(keyManagerFactory)
                    .trustManager(trustManagerFactory)
                    .build();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new ApiServiceException("Failed tp load ssl context, description: " + e.getMessage());
        }
    }


    /**
     * Create SslContext for handshake between client and server
     *
     * @return SslContext sslcontext
     * @throws Exception exception
     */
    private SslContext createSslContext(String keyStorePath, String keyStorePassword,
                                        String trustStorePath, String trustStorePassword) {
        try {
            KeyStore trustStoreInst = KeyStore.getInstance(KeyStore.getDefaultType());
            try (FileInputStream trustStoreStream = new FileInputStream(ResourceUtils.getFile(
                    trustStorePath))) {
                trustStoreInst.load(trustStoreStream, trustStorePassword.toCharArray());
            }
            KeyStore keyStoreInst = KeyStore.getInstance(KeyStore.getDefaultType());
            try (FileInputStream keyStoreStream = new FileInputStream(ResourceUtils.getFile(
                    keyStorePath))) {
                keyStoreInst.load(keyStoreStream, keyStorePassword.toCharArray());
            }

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
                    KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStoreInst, keyStorePassword.toCharArray());
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(trustStoreInst);

            return SslContextBuilder.forClient()
                    .keyManager(keyManagerFactory)
                    .trustManager(trustManagerFactory)
                    .build();
        }
        catch (Exception e) {
            throw new ApiServiceException("Failed tp load ssl context, description: " + e.getMessage());
        }
    }

}
