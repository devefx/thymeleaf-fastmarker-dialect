package org.devefx.thymeleaf.expression;

import org.thymeleaf.Configuration;
import org.thymeleaf.cache.ICache;
import org.thymeleaf.cache.ICacheManager;

final class ExpressionCache {
    
    private static final String PREFRAMENT_SELECTION_CACHE_PREFIX = "{prefragment_selection}";

    private ExpressionCache() {
        super();
    }
    
    private static Object getFromCache(final Configuration configuration, final String input, final String prefix) {
        final ICacheManager cacheManager = configuration.getCacheManager();
        if (cacheManager != null) {
            final ICache<String,Object> cache = cacheManager.getExpressionCache();
            if (cache != null) {
                return cache.get(prefix + input);
            }
        }
        return null;
    }
    
    private static <V> void putIntoCache(final Configuration configuration, final String input, final V value, final String prefix) {
        final ICacheManager cacheManager = configuration.getCacheManager();
        if (cacheManager != null) {
            final ICache<String,Object> cache = cacheManager.getExpressionCache();
            if (cache != null) {
                cache.put(prefix + input, value);
            }
        }
    }

    public static PreFragmentSelection getPreFragmentSelectionFromCache(final Configuration configuration, final String input) {
        return (PreFragmentSelection) getFromCache(configuration, input, PREFRAMENT_SELECTION_CACHE_PREFIX);
    }

    public static void putPreFragmentSelectionIntoCache(final Configuration configuration, final String input, final PreFragmentSelection value) {
        putIntoCache(configuration, input, value, PREFRAMENT_SELECTION_CACHE_PREFIX);
    }
    
}
