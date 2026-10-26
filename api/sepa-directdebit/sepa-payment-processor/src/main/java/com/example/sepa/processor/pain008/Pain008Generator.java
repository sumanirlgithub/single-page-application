package com.example.sepa.processor.pain008;

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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class Pain008Generator {

    private static final String PAIN_NAMESPACE =
            "urn:iso:std:iso:20022:tech:xsd:pain.008.001.08";

    private static final DateTimeFormatter DATE_TIME_FORMAT =
            DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public String generate(Pain008Request request) {

        try {

            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();

            factory.setNamespaceAware(true);

            Document document =
                    factory.newDocumentBuilder()
                            .newDocument();

            Element documentElement =
                    document.createElementNS(
                            PAIN_NAMESPACE,
                            "Document");

            document.appendChild(documentElement);

            Element root =
                    append(documentElement, "CstmrDrctDbtInitn");

            // ------------------------------------------------
            // Group Header
            // ------------------------------------------------

            Element groupHeader =
                    append(root, "GrpHdr");

            appendText(
                    document,
                    groupHeader,
                    "MsgId",
                    request.messageId());

            appendText(
                    document,
                    groupHeader,
                    "CreDtTm",
                    LocalDateTime.now()
                            .format(DATE_TIME_FORMAT));

            appendText(
                    document,
                    groupHeader,
                    "NbOfTxs",
                    "1");

            appendText(
                    document,
                    groupHeader,
                    "CtrlSum",
                    request.amount()
                            .toPlainString());

            Element initiatingParty =
                    append(
                            groupHeader,
                            "InitgPty");

            appendText(
                    document,
                    initiatingParty,
                    "Nm",
                    request.creditorName());

            // ------------------------------------------------
            // Payment Information
            // ------------------------------------------------

            Element paymentInfo =
                    append(root, "PmtInf");

            appendText(
                    document,
                    paymentInfo,
                    "PmtInfId",
                    request.collectionId());

            appendText(
                    document,
                    paymentInfo,
                    "PmtMtd",
                    "DD");

            appendText(
                    document,
                    paymentInfo,
                    "NbOfTxs",
                    "1");

            appendText(
                    document,
                    paymentInfo,
                    "CtrlSum",
                    request.amount()
                            .toPlainString());

            // ------------------------------------------------
            // Payment Type Information
            // ------------------------------------------------

            Element paymentType =
                    append(
                            paymentInfo,
                            "PmtTpInf");

            Element serviceLevel =
                    append(
                            paymentType,
                            "SvcLvl");

            appendText(
                    document,
                    serviceLevel,
                    "Cd",
                    "SEPA");

            Element localInstrument =
                    append(
                            paymentType,
                            "LclInstrm");

            appendText(
                    document,
                    localInstrument,
                    "Cd",
                    "CORE");

            appendText(
                    document,
                    paymentType,
                    "SeqTp",
                    request.sequenceType());

            // ------------------------------------------------
            // Requested Collection Date
            // ------------------------------------------------

            appendText(
                    document,
                    paymentInfo,
                    "ReqdColltnDt",
                    request.requestedCollectionDate()
                            .toString());

            // ------------------------------------------------
            // Creditor
            // ------------------------------------------------

            Element creditor =
                    append(
                            paymentInfo,
                            "Cdtr");

            appendText(
                    document,
                    creditor,
                    "Nm",
                    request.creditorName());

            Element creditorAccount =
                    append(
                            paymentInfo,
                            "CdtrAcct");

            Element creditorAccountId =
                    append(
                            creditorAccount,
                            "Id");

            appendText(
                    document,
                    creditorAccountId,
                    "IBAN",
                    request.creditorIban());

            Element creditorAgent =
                    append(
                            paymentInfo,
                            "CdtrAgt");

            Element creditorFinInstnId =
                    append(
                            creditorAgent,
                            "FinInstnId");

            appendText(
                    document,
                    creditorFinInstnId,
                    "BIC",
                    request.creditorBic());

            // ------------------------------------------------
            // Creditor Scheme Identification
            // ------------------------------------------------

            Element creditorScheme =
                    append(
                            paymentInfo,
                            "CdtrSchmeId");

            Element creditorSchemeId =
                    append(
                            creditorScheme,
                            "Id");

            Element privateId =
                    append(
                            creditorSchemeId,
                            "PrvtId");

            Element other =
                    append(
                            privateId,
                            "Othr");

            appendText(
                    document,
                    other,
                    "Id",
                    request.creditorId());

            // ------------------------------------------------
            // Direct Debit Transaction
            // ------------------------------------------------

            Element transaction =
                    append(
                            paymentInfo,
                            "DrctDbtTxInf");

            // Amount
            Element amount =
                    document.createElement("InstdAmt");

            amount.setAttribute(
                    "Ccy",
                    request.currency());

            amount.setTextContent(
                    request.amount()
                            .toPlainString());

            transaction.appendChild(amount);

            // ------------------------------------------------
            // Mandate Information
            // ------------------------------------------------

            Element directDebitTx =
                    append(
                            transaction,
                            "DrctDbtTx");

            Element mandateInfo =
                    append(
                            directDebitTx,
                            "MndtRltdInf");

            appendText(
                    document,
                    mandateInfo,
                    "MndtId",
                    request.mandateId());

            appendText(
                    document,
                    mandateInfo,
                    "DtOfSgntr",
                    request.mandateSignatureDate()
                            .toString());

            // ------------------------------------------------
            // Debtor Agent
            // ------------------------------------------------

            Element debtorAgent =
                    append(
                            transaction,
                            "DbtrAgt");

            Element debtorFinInstnId =
                    append(
                            debtorAgent,
                            "FinInstnId");

            appendText(
                    document,
                    debtorFinInstnId,
                    "BIC",
                    request.debtorBic());

            // ------------------------------------------------
            // Debtor
            // ------------------------------------------------

            Element debtor =
                    append(
                            transaction,
                            "Dbtr");

            appendText(
                    document,
                    debtor,
                    "Nm",
                    request.debtorName());

            // ------------------------------------------------
            // Debtor Account
            // ------------------------------------------------

            Element debtorAccount =
                    append(
                            transaction,
                            "DbtrAcct");

            Element debtorAccountId =
                    append(
                            debtorAccount,
                            "Id");

            appendText(
                    document,
                    debtorAccountId,
                    "IBAN",
                    request.debtorIban());

            // ------------------------------------------------
            // Remittance Information
            // ------------------------------------------------

            Element remittance =
                    append(
                            transaction,
                            "RmtInf");

            appendText(
                    document,
                    remittance,
                    "Ustrd",
                    "Direct Debit "
                            + request.collectionId());

            return convertToXml(document);

        } catch (Exception e) {

            throw new IllegalStateException(
                    "Unable to generate pain.008 XML",
                    e);
        }
    }

    private Element append(
            Element parent,
            String name) {

        Document document =
                parent.getOwnerDocument();

        Element element =
                document.createElementNS(
                        PAIN_NAMESPACE,
                        name);

        parent.appendChild(element);

        return element;
    }

    private void appendText(
            Document document,
            Element parent,
            String name,
            String value) {

        Element element =
                document.createElementNS(
                        PAIN_NAMESPACE,
                        name);

        element.setTextContent(value);

        parent.appendChild(element);
    }

    private String convertToXml(
            Document document) throws Exception {

        TransformerFactory factory =
                TransformerFactory.newInstance();

        Transformer transformer =
                factory.newTransformer();

        transformer.setOutputProperty(
                OutputKeys.INDENT,
                "yes");

        transformer.setOutputProperty(
                OutputKeys.ENCODING,
                "UTF-8");

        StringWriter writer =
                new StringWriter();

        transformer.transform(
                new DOMSource(document),
                new StreamResult(writer));

        return writer.toString();
    }
}