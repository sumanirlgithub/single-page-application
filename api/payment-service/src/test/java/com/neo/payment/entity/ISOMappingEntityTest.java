package com.neo.payment.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(MockitoExtension.class)
class ISOMappingEntityTest {

    @Test
    void test_fields() {
        ISOMappingEntity entity = new ISOMappingEntity();
        entity.setId(0);
        entity.setStatusCode("statusCode");

        assertEquals(0, entity.getId());
        assertEquals("statusCode", entity.getStatusCode());
    }

    @Test
    void test_equals() {
        ISOMappingEntity entity1 = new ISOMappingEntity(
                0, "statusCode", "reasonCode", "additionalInfo",
                "status", "statusReason");

        assertEquals(entity1, entity1);
        assertNotEquals(entity1, new Object());

        ISOMappingEntity entity2 = ISOMappingEntity.builder()
                .id(0)
                .statusCode("statusCode")
                .reasonCode("reasonCode")
                .additionalInfo("additionalInfo")
                .status("status")
                .statusReason("statusReason")
                .build();

        assertEquals(entity1, entity2);

        entity2.setId(1);
        assertNotEquals(entity1, entity2);
        entity2.setId(null);
        assertNotEquals(entity2, entity1);
        entity1.setId(null);
        assertEquals(entity2, entity1);

        //ToDo continue for all fields in similar manner

    }

    @Test
    void test_hashCode() {
        assertNotEquals(0, new ISOMappingEntity().hashCode());
    }

    @Test
    void test_toString() {
        assertNotEquals(0, new ISOMappingEntity().toString().length());
    }
}
