package com.inventory;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ProductFactory class.
 * TODO: Write comprehensive tests for Factory pattern implementation.
 */
class ProductFactoryTest {

    /**
     * TODO: Test creating a valid book product
     */
    @Test
    void testCreateValidBook() {
        // TODO: Create a book with valid parameters
        // Assert that product is created successfully
        // Verify all properties are set correctly

        Product book = ProductFactory.createProduct("B001", "Java Book", "BOOK", 15.99, 5);
        assertNotNull(book);
        assertEquals("B001", book.getId());
        assertEquals("BOOK", book.getType());
        assertEquals(15.99, book.getPrice());
    }

    /**
     * TODO: Test creating a valid electronics product
     */
    @Test
    void testCreateValidElectronics() {
        // TODO: Create electronics with valid parameters
        // Assert that product is created successfully
        // Verify all properties are set correctly
    }

    /**
     * TODO: Test book minimum price validation
     */
    @Test
    void testBookMinimumPriceValidation() {
        // TODO: Try creating book with price below $5
        // Assert that IllegalArgumentException is thrown
        // Verify error message is appropriate

        assertThrows(IllegalArgumentException.class, () -> {
            ProductFactory.createProduct("B001", "Cheap Book", "BOOK", 3.99, 5);
        });
    }

    /**
     * TODO: Test electronics minimum price validation
     */
    @Test
    void testElectronicsMinimumPriceValidation() {
        // TODO: Try creating electronics with price below $10
        // Assert that IllegalArgumentException is thrown
        // Verify error message is appropriate
    }

    /**
     * TODO: Test invalid product type
     */
    @Test
    void testInvalidProductType() {
        // TODO: Try creating product with invalid type
        // Assert that IllegalArgumentException is thrown
    }

    /**
     * TODO: Test boundary conditions
     */
    @Test
    void testBoundaryConditions() {
        // TODO: Test book at exactly $5.00
        // TODO: Test electronics at exactly $10.00
        // Both should succeed
    }

    // TODO: Add more tests as needed
    // - Test with null parameters
    // - Test with empty strings
    // - Test with negative quantities
}