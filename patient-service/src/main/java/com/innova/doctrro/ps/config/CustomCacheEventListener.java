package com.innova.doctrro.ps.config;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomCacheEventListener implements CacheEventListener<Object, Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomCacheEventListener.class);

    @Override
    public void onEvent(CacheEvent<?, ?> cacheEvent) {
        switch (cacheEvent.getType()) {
            case EXPIRED:
            case REMOVED:
            case EVICTED:
                LOGGER.info("Cache event = {}, Key = {}", cacheEvent.getType(), cacheEvent.getKey());
                break;
            default:
                LOGGER.debug("Cache event = {}, Key = {},  Old value = {}, New value = {}", cacheEvent.getType(),
                        cacheEvent.getKey(), cacheEvent.getOldValue(), cacheEvent.getNewValue());
                break;
        }
    }
}
