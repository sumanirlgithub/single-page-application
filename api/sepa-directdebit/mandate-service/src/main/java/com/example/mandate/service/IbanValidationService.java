package com.example.mandate.service;

import org.springframework.stereotype.Service;

@Service
public class IbanValidationService {

    public void validate(String iban) {

        String normalized = iban
                .replace(" ", "")
                .toUpperCase();

        if (!normalized.matches("[A-Z]{2}[0-9]{2}[A-Z0-9]{11,30}")) {
            throw new IllegalArgumentException("Invalid IBAN format");
        }

        if (!isValidChecksum(normalized)) {
            throw new IllegalArgumentException("Invalid IBAN checksum");
        }
    }

    private boolean isValidChecksum(String iban) {

        String rearranged =
                iban.substring(4) + iban.substring(0, 4);

        StringBuilder numeric = new StringBuilder();

        for (char c : rearranged.toCharArray()) {

            if (Character.isLetter(c)) {
                numeric.append(c - 'A' + 10);
            } else {
                numeric.append(c);
            }
        }

        return mod97(numeric.toString()) == 1;
    }

    private int mod97(String value) {

        int remainder = 0;

        for (char c : value.toCharArray()) {
            remainder =
                    (remainder * 10 + (c - '0')) % 97;
        }

        return remainder;
    }
}