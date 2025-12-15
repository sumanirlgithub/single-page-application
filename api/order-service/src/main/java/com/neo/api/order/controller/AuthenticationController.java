package com.neo.api.order.controller;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;

import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HttpURLConnectionFactory;
import com.sun.jersey.client.urlconnection.URLConnectionClientHandler;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.ws.rs.core.MediaType;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@RequestMapping(value = "/ccapi/auth")
public class AuthenticationController {

    @Value("${auth.sslcert-truststore}")
    private String sslCertTruststoreFile;
    @Autowired
    ResourceLoader resourceLoader;

    /**
     *
     * Endpoint get auth token from authentication service.
     */
    @GetMapping("/v3")
    public ResponseEntity<String> getAuthToken() {

        String oAuthPayload = "{ \"oAuthToken\": { \"grantType\": \"client_credentials\", \"scope\": \"/authenticationservices/v1\" }}";
        String encryptedResponse = null;
        try {

            Resource resource = resourceLoader.getResource(sslCertTruststoreFile);
            // Client's SSL location
            //String SSL_CERT_PATH = "C:\\projects\\ssl_certificate\\client_sslo_alias.p12";
            String SSL_PWD = "Password1"; // SSL cert password
            String endPointUrl = "https://localhost:8443/shared-services/api/authenticationservices/v3.oauth/token";
            String clientId = "49c2ef66-965c";
            String secretKey = "J8jE6mkI6";

            encryptedResponse = callAuthenticationAPI(oAuthPayload, resource, SSL_PWD, endPointUrl, clientId, secretKey);
            log.info("Encrypted response: "+ encryptedResponse);

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private String callAuthenticationAPI(String oAuthPayload, Resource resource, String certPwd,
                                         String oAuthUrl, String clientId, String secretKey) {
        String response = "";
        try {
            //KeyStore clientStore = KeyStore.getInstance("PKCS12");
            KeyStore clientStore = KeyStore.getInstance("JKS");
            InputStream inputStream = resource.getInputStream();
            clientStore.load(inputStream, certPwd.toCharArray());
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(clientStore, certPwd.toCharArray());

            SSLContext sslContext =  SSLContext.getInstance("TLSv1.2");
            sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());

            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            Client client = new Client(new URLConnectionClientHandler(new HttpURLConnectionFactory() {
                Proxy proxy = null;

                @Override
                public HttpURLConnection getHttpURLConnection(URL url) throws IOException {
                    proxy = Proxy.NO_PROXY;
                    return (HttpsURLConnection) url.openConnection(proxy);
                }
            }), new DefaultClientConfig());
            WebResource webResource = client.resource(oAuthPayload);
            Builder builder = webResource.type(MediaType.APPLICATION_JSON);
            builder.header(HttpHeaders.AUTHORIZATION, "Basic "
                    + Base64.encodeBase64String((clientId + ":" + secretKey).getBytes()).replaceAll("(\\r|\\n)", ""));
            ClientResponse clientResponse = builder.post(ClientResponse.class, oAuthPayload);
            log.info("getHeaders()--> " +clientResponse.getHeaders());
            response = clientResponse.getEntity(String.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

}
