package com.example.eventmanagement.utils;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimiter {
    private static final long RATE_LIMIT_WINDOW_MS = 60 * 1000; // 1 minute window
    private static final int MAX_REQUESTS_PER_WINDOW = 100;
    private ConcurrentHashMap<String, UserRequestInfo> userRequestMap = new ConcurrentHashMap<>();

    public synchronized boolean isRateLimited(String userEmail) {
        UserRequestInfo userInfo = userRequestMap.getOrDefault(userEmail, new UserRequestInfo(0, Instant.now()));
        Instant now = Instant.now();

        if (now.isAfter(userInfo.getWindowStart().plusMillis(RATE_LIMIT_WINDOW_MS))) {
            userInfo.setWindowStart(now);
            userInfo.setRequestCount(0);
        }

        if (userInfo.getRequestCount() < MAX_REQUESTS_PER_WINDOW) {
            userInfo.incrementRequestCount();
            userRequestMap.put(userEmail, userInfo);
            return false;
        } else {
            return true;
        }
    }

    private static class UserRequestInfo {
        private int requestCount;
        private Instant windowStart;

        public UserRequestInfo(int requestCount, Instant windowStart) {
            this.requestCount = requestCount;
            this.windowStart = windowStart;
        }

        public int getRequestCount() {
            return requestCount;
        }

        public Instant getWindowStart() {
            return windowStart;
        }

        public void incrementRequestCount() {
            this.requestCount++;
        }

        public void setWindowStart(Instant windowStart) {
            this.windowStart = windowStart;
        }

        public void setRequestCount(int requestCount) {
            this.requestCount = requestCount;
        }
    }
}
