package com.neo.bank.simulator.api.service;

import com.neo.bank.simulator.api.contract.BankSubmissionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
@Slf4j
public class SimulatedBankService {

    private static final String PAIN008_NAMESPACE =
            "urn:iso:std:iso:20022:tech:xsd:pain.008.001.02";

    private final ConcurrentMap<String, BankSubmissionResponse>
            idempotencyStore = new ConcurrentHashMap<>();

    public BankSubmissionResponse submit(
            String idempotencyKey,
            String pain008Xml) {

        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            throw new IllegalArgumentException(
                    "Idempotency key is required"
            );
        }

        if (pain008Xml == null || pain008Xml.isBlank()) {
            throw new IllegalArgumentException(
                    "pain.008 XML is required"
            );
        }

        // Return the original response for duplicate requests.
        return idempotencyStore.computeIfAbsent(
                idempotencyKey,
                key -> processSubmission(pain008Xml)
        );
    }

    private BankSubmissionResponse processSubmission(
            String pain008Xml) {

        try {
            validatePain008Xml(pain008Xml);

            String providerReference =
                    "BANK-" + UUID.randomUUID()
                            .toString()
                            .replace("-", "")
                            .substring(0, 20)
                            .toUpperCase();

            return new BankSubmissionResponse(
                    true,
                    providerReference,
                    "Direct debit submission accepted successfully"
            );

        } catch (Exception ex) {

            return new BankSubmissionResponse(
                    false,
                    null,
                    "Direct debit submission rejected: "
                            + ex.getMessage()
            );
        }
    }

    private void validatePain008Xml(String pain008Xml)
            throws Exception {

        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();

        factory.setNamespaceAware(true);

        // Protect against XML external entity attacks.
        factory.setFeature(
                XMLConstants.FEATURE_SECURE_PROCESSING,
                true
        );

        factory.setFeature(
                "http://apache.org/xml/features/disallow-doctype-decl",
                true
        );

        factory.setFeature(
                "http://xml.org/sax/features/external-general-entities",
                false
        );

        factory.setFeature(
                "http://xml.org/sax/features/external-parameter-entities",
                false
        );

        factory.setAttribute(
                XMLConstants.ACCESS_EXTERNAL_DTD, ""
        );

        factory.setAttribute(
                XMLConstants.ACCESS_EXTERNAL_SCHEMA, ""
        );

        Document document = factory
                .newDocumentBuilder()
                .parse(new InputSource(new StringReader(pain008Xml)));

        if (!"Document".equals(document.getDocumentElement()
                .getLocalName())) {

            throw new IllegalArgumentException(
                    "Invalid pain.008 root element"
            );
        }

        if (!PAIN008_NAMESPACE.equals(
                document.getDocumentElement().getNamespaceURI())) {

            throw new IllegalArgumentException(
                    "Unsupported pain.008 namespace"
            );
        }
    }
}