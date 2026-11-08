package com.neo.payments.hub.business.processor.sepa;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.InputStream;
import java.io.StringReader;

@Component
public class Pain008Validator {

    private static final String XSD_PATH =
            "xsd/pain.008.001.02.xsd";

    private final Schema schema;

    public Pain008Validator() {
        this.schema = loadSchema();
    }

    public void validate(String pain008Xml) {

        if (pain008Xml == null || pain008Xml.isBlank()) {
            throw new Pain008ValidationException(
                    "pain.008 XML must not be null or empty");
        }

        try {
            Validator validator = schema.newValidator();

            // Prevent external DTD and schema access
            validator.setProperty(
                    XMLConstants.ACCESS_EXTERNAL_DTD, "");

            validator.setProperty(
                    XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

            // Configure a secure XML parser
            SAXParserFactory parserFactory =
                    SAXParserFactory.newInstance();

            parserFactory.setNamespaceAware(true);

            parserFactory.setFeature(
                    XMLConstants.FEATURE_SECURE_PROCESSING, true);

            parserFactory.setFeature(
                    "http://apache.org/xml/features/disallow-doctype-decl",
                    true);

            parserFactory.setFeature(
                    "http://xml.org/sax/features/external-general-entities",
                    false);

            parserFactory.setFeature(
                    "http://xml.org/sax/features/external-parameter-entities",
                    false);

            parserFactory.setFeature(
                    "http://apache.org/xml/features/nonvalidating/load-external-dtd",
                    false);

            XMLReader xmlReader =
                    parserFactory.newSAXParser().getXMLReader();

            InputSource inputSource =
                    new InputSource(new StringReader(pain008Xml));

            SAXSource saxSource =
                    new SAXSource(xmlReader, inputSource);

            // Validate XML against the pain.008 XSD
            validator.validate(saxSource);

        } catch (Exception exception) {

            throw new Pain008ValidationException(
                    "Invalid SEPA pain.008 XML: "
                            + exception.getMessage(),
                    exception);
        }
    }

    private Schema loadSchema() {

        try {
            ClassPathResource resource =
                    new ClassPathResource(XSD_PATH);

            if (!resource.exists()) {
                throw new IllegalStateException(
                        "SEPA pain.008 XSD not found: " + XSD_PATH);
            }

            SchemaFactory schemaFactory =
                    SchemaFactory.newInstance(
                            XMLConstants.W3C_XML_SCHEMA_NS_URI);

            schemaFactory.setFeature(
                    XMLConstants.FEATURE_SECURE_PROCESSING, true);

            schemaFactory.setProperty(
                    XMLConstants.ACCESS_EXTERNAL_DTD, "");

            schemaFactory.setProperty(
                    XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

            try (InputStream inputStream =
                         resource.getInputStream()) {

                return schemaFactory.newSchema(
                        new javax.xml.transform.stream.StreamSource(
                                inputStream));
            }

        } catch (Exception exception) {

            throw new IllegalStateException(
                    "Failed to load SEPA pain.008 XSD schema",
                    exception);
        }
    }
}
