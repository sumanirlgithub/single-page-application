package com.neo.payment.exception;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(MockitoExtension.class)
class ErrorDetailTest {

    @Test
    void test_equals() {
        ErrorDetail errorDetail1 = new ErrorDetail("error");
        assertEquals(errorDetail1, errorDetail1);
        assertNotEquals(errorDetail1, new Object());

        ErrorDetail errorDetail2 = new ErrorDetail("error");
        assertEquals(errorDetail1, errorDetail2);
    }

    @Test
    void test_hashCode() {
        assertNotEquals(0, new ErrorDetail("error").hashCode());
    }

    @Test
    void test_toString() {
        assertNotEquals(0, new ErrorDetail("error").toString().length());
    }

}
