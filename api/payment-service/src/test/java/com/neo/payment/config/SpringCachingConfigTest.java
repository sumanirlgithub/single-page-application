package com.neo.payment.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = SpringCachingConfig.class)
@ExtendWith(SpringExtension.class)
class SpringCachingConfigTest {

    @Autowired
    private SpringCachingConfig springCachingConfig;

    @Test
    @DisplayName("Test cacheManager()")
    void test_CacheManager() {
        // Arrange and Act
        CacheManager actualCacheManagerResult = springCachingConfig.cacheManager();

        // Assert
        Collection<String> cacheNames = actualCacheManagerResult.getCacheNames();
        assertEquals(1, cacheNames.size());
        assertTrue(cacheNames.contains("authTokenDetails"));
        assertTrue(cacheNames instanceof Set);
        assertTrue(actualCacheManagerResult instanceof ConcurrentMapCacheManager);
        assertFalse(((ConcurrentMapCacheManager) actualCacheManagerResult).isStoreByValue());
        assertTrue(((ConcurrentMapCacheManager) actualCacheManagerResult).isAllowNullValues());
    }
}
