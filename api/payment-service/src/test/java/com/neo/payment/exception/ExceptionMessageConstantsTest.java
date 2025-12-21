package com.neo.payment.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeanUtils;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ExceptionMessageConstantsTest {

    @Test
    void testConstructor() {
        assertThrows(BeanInstantiationException.class,
                () -> BeanUtils.instantiateClass(ExceptionMessageConstants.class));
    }
}
