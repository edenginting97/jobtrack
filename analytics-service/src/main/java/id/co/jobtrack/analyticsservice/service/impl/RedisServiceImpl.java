package id.co.jobtrack.analyticsservice.service.impl;

import id.co.jobtrack.analyticsservice.service.RedisService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
public class RedisServiceImpl implements RedisService {

    @CacheEvict(
            cacheNames = {
                    "application-summary",
                    "application-status-summary"
            },
            allEntries = true
    )
    @Override
    public void evictAnalyticsCaches() {
    }
}
