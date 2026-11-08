package com.neo.payments.hub.business.processor.sepa;

import com.neo.payments.hub.model.payment.DirectDebitPayment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Locale;
import java.util.Objects;

@Component
public class Pain008Generator {

    private static final String NAMESPACE =
            "urn:iso:std:iso:20022:tech:xsd:pain.008.001.02";

    private final String scheme;

    public Pain008Generator(
            @Value("${sepa.direct-debit.scheme:CORE}") String scheme) {
        this.scheme = scheme.toUpperCase(Locale.ROOT);

        if (!this.scheme.equals("CORE") && !this.scheme.equals("B2B")) {
            throw new IllegalArgumentException(
                    "Unsupported SEPA direct debit scheme: " + scheme);
        }
    }

    public String generate(DirectDebitPayment payment) {

        Objects.requireNonNull(payment, "Payment must not be null");

        validatePayment(payment);

        try {
            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();

            factory.setNamespaceAware(true);

            Document document =
                    factory.newDocumentBuilder().newDocument();

            // Root element
            Element root = document.createElementNS(
                    NAMESPACE, "Document");

            document.appendChild(root);

            Element initiation = addElement(
                    document, root, "CstmrDrctDbtInitn");

            // 1. Group Header
            createGroupHeader(document, initiation, payment);

            // 2. Payment Information
            createPaymentInformation(document, initiation, payment);

            // Convert DOM document to XML String
            return convertToXml(document);

        } catch (Exception exception) {
            throw new IllegalStateException(
                    "Failed to generate SEPA pain.008 XML for payment: "
                            + payment.getPaymentId(),
                    exception);
        }
    }

    private void createGroupHeader(
            Document document,
            Element initiation,
            DirectDebitPayment payment) {

        Element groupHeader = addElement(
                document, initiation, "GrpHdr");

        addText(document, groupHeader, "MsgId",
                payment.getSubmissionId());

        addText(document, groupHeader, "CreDtTm",
                Instant.now().toString());

        addText(document, groupHeader, "NbOfTxs", "1");

        addText(document, groupHeader, "CtrlSum",
                formatAmount(payment.getAmountRemit()));

        Element initiatingParty = addElement(
                document, groupHeader, "InitgPty");

        addText(document, initiatingParty, "Nm",
                payment.getCreditorName());
    }

    private void createPaymentInformation(
            Document document,
            Element initiation,
            DirectDebitPayment payment) {

        Element paymentInfo = addElement(
                document, initiation, "PmtInf");

        addText(document, paymentInfo, "PmtInfId",
                payment.getSubmissionId());

        addText(document, paymentInfo, "PmtMtd", "DD");

        addText(document, paymentInfo, "NbOfTxs", "1");

        addText(document, paymentInfo, "CtrlSum",
                formatAmount(payment.getAmountRemit()));

        // Payment Type Information
        Element paymentTypeInfo = addElement(
                document, paymentInfo, "PmtTpInf");

        Element serviceLevel = addElement(
                document, paymentTypeInfo, "SvcLvl");

        addText(document, serviceLevel, "Cd", "SEPA");

        Element localInstrument = addElement(
                document, paymentTypeInfo, "LclInstrm");

        addText(document, localInstrument, "Cd", scheme);

        addText(document, paymentTypeInfo, "SeqTp",
                normalizeSequenceType(payment.getSequenceType()));

        // Requested collection date
        addText(document, paymentInfo, "ReqdColltnDt",
                payment.getPaymentDate().toString());

        // Creditor details
        Element creditor = addElement(
                document, paymentInfo, "Cdtr");

        addText(document, creditor, "Nm",
                payment.getCreditorName());

        // Creditor account
        Element creditorAccount = addElement(
                document, paymentInfo, "CdtrAcct");

        Element creditorAccountId = addElement(
                document, creditorAccount, "Id");

        addText(document, creditorAccountId, "IBAN",
                payment.getCreditorIban());

        // Creditor bank
        createAgent(
                document,
                paymentInfo,
                "CdtrAgt",
                payment.getCreditorBic());

        addText(document, paymentInfo, "ChrgBr", "SLEV");

        // Creditor SEPA Identifier
        createCreditorSchemeId(document, paymentInfo, payment);

        // Individual Direct Debit Transaction
        createDirectDebitTransaction(document, paymentInfo, payment);
    }

    private void createDirectDebitTransaction(
            Document document,
            Element paymentInfo,
            DirectDebitPayment payment) {

        Element transaction = addElement(
                document, paymentInfo, "DrctDbtTxInf");

        // Payment identification
        Element paymentId = addElement(
                document, transaction, "PmtId");

        addText(document, paymentId, "EndToEndId",
                payment.getPaymentId());

        // Amount
        Element instructedAmount = addElement(
                document, transaction, "InstdAmt");

        instructedAmount.setAttribute(
                "Ccy", payment.getCurrency());

        instructedAmount.setTextContent(
                formatAmount(payment.getAmountRemit()));

        // Mandate information
        Element directDebitTransaction = addElement(
                document, transaction, "DrctDbtTx");

        Element mandateInfo = addElement(
                document, directDebitTransaction, "MndtRltdInf");

        addText(document, mandateInfo, "MndtId",
                payment.getIdUmr());

        addText(document, mandateInfo, "DtOfSgntr",
                payment.getMandateSignatureDate().toString());

        addText(document, mandateInfo, "AmdmntInd", "false");

        // Debtor bank
        createAgent(
                document,
                transaction,
                "DbtrAgt",
                payment.getDebtorBic());

        // Debtor
        Element debtor = addElement(
                document, transaction, "Dbtr");

        addText(document, debtor, "Nm",
                payment.getDebtorName());

        // Debtor account
        Element debtorAccount = addElement(
                document, transaction, "DbtrAcct");

        Element debtorAccountId = addElement(
                document, debtorAccount, "Id");

        addText(document, debtorAccountId, "IBAN",
                payment.getDebtorIban());

        // Remittance information
        Element remittanceInfo = addElement(
                document, transaction, "RmtInf");

        addText(document, remittanceInfo, "Ustrd",
                payment.getPaymentId());
    }

    private void createCreditorSchemeId(
            Document document,
            Element paymentInfo,
            DirectDebitPayment payment) {

        Element creditorSchemeId = addElement(
                document, paymentInfo, "CdtrSchmeId");

        Element id = addElement(
                document, creditorSchemeId, "Id");

        Element privateId = addElement(
                document, id, "PrvtId");

        Element other = addElement(
                document, privateId, "Othr");

        addText(document, other, "Id",
                payment.getCreditorId());

        Element schemeName = addElement(
                document, other, "SchmeNm");

        addText(document, schemeName, "Prtry", "SEPA");
    }

    private void createAgent(
            Document document,
            Element parent,
            String agentName,
            String bic) {

        Element agent = addElement(
                document, parent, agentName);

        Element financialInstitution = addElement(
                document, agent, "FinInstnId");

        if (bic != null && !bic.isBlank()) {

            addText(document, financialInstitution, "BIC", bic);

        } else {

            Element other = addElement(
                    document, financialInstitution, "Othr");

            addText(document, other, "Id", "NOTPROVIDED");
        }
    }

    private Element addElement(
            Document document,
            Element parent,
            String elementName) {

        Element element = document.createElementNS(
                NAMESPACE, elementName);

        parent.appendChild(element);

        return element;
    }

    private void addText(
            Document document,
            Element parent,
            String elementName,
            String value) {

        Element element = addElement(
                document, parent, elementName);

        element.setTextContent(value);
    }

    private String convertToXml(Document document) throws Exception {

        TransformerFactory transformerFactory =
                TransformerFactory.newInstance();

        transformerFactory.setFeature(
                javax.xml.XMLConstants.FEATURE_SECURE_PROCESSING,
                true);

        Transformer transformer =
                transformerFactory.newTransformer();

        transformer.setOutputProperty(
                OutputKeys.ENCODING, "UTF-8");

        transformer.setOutputProperty(
                OutputKeys.INDENT, "yes");

        transformer.setOutputProperty(
                OutputKeys.OMIT_XML_DECLARATION, "no");

        StringWriter writer = new StringWriter();

        transformer.transform(
                new DOMSource(document),
                new StreamResult(writer));

        return writer.toString();
    }

    private String formatAmount(BigDecimal amount) {

        return amount.setScale(2, RoundingMode.UNNECESSARY)
                .toPlainString();
    }

    private String normalizeSequenceType(String sequenceType) {

        String normalized = sequenceType.toUpperCase(Locale.ROOT);

        return switch (normalized) {
            case "FRST", "RCUR", "OOFF", "FNAL" -> normalized;
            default -> throw new IllegalArgumentException(
                    "Invalid SEPA sequence type: " + sequenceType);
        };
    }

    private void validatePayment(DirectDebitPayment payment) {

        requireText(payment.getPaymentId(), "Payment ID");
        requireText(payment.getSubmissionId(), "Submission ID");
        requireText(payment.getDebtorName(), "Debtor name");
        requireText(payment.getDebtorIban(), "Debtor IBAN");
        requireText(payment.getCreditorName(), "Creditor name");
        requireText(payment.getCreditorId(), "Creditor identifier");
        requireText(payment.getCreditorIban(), "Creditor IBAN");
        requireText(payment.getIdUmr(), "Mandate reference");
        requireText(payment.getSequenceType(), "Sequence type");

        Objects.requireNonNull(
                payment.getPaymentDate(), "Payment date is required");

        Objects.requireNonNull(
                payment.getMandateSignatureDate(),
                "Mandate signature date is required");

        BigDecimal amount = Objects.requireNonNull(
                payment.getAmountRemit(), "Payment amount is required");

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Payment amount must be greater than zero");
        }

        if (!"EUR".equalsIgnoreCase(payment.getCurrency())) {
            throw new IllegalArgumentException(
                    "SEPA Direct Debit requires EUR currency");
        }

        // ISO 20022 pain.008.001.02 identifier length limits
        requireMaxLength(payment.getPaymentId(), 35, "Payment ID");
        requireMaxLength(payment.getSubmissionId(), 35, "Submission ID");
        requireMaxLength(payment.getIdUmr(), 35, "Mandate reference");
        requireMaxLength(payment.getCreditorId(), 35, "Creditor identifier");

        formatAmount(amount);
    }

    private void requireText(String value, String fieldName) {

        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    fieldName + " is required");
        }
    }

    private void requireMaxLength(
            String value,
            int maxLength,
            String fieldName) {

        if (value.length() > maxLength) {
            throw new IllegalArgumentException(
                    fieldName + " cannot exceed "
                            + maxLength + " characters");
        }
    }
}
