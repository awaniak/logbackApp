package io.github.awaniak.logbackApp.commons.logging;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class LeefLoggingService {

    private static final String APP_VERSION_NAME = "appVersion";
    private static final String APP_NAME = "appName";
    private static final String EVENT_NAME = "event";
    private static final String CLIENT_IP_NAME = "clientIp";
    private static final String TRANSACTION_ID_NAME = "transactionId";
    private static final String SOURCE_NAME = "source";
    private static final String USER_AGENT_NAME = "userAgent";
    private static final String FINGERPRINT_NAME = "fingerprint";
    private static final String CHECKSUM_NAME = "checksum";
    @Value("${app.version}")
    private String appVersionValue;
    @Value("${app.name}")
    private String appNameValue;

    Logger log = LoggerFactory.getLogger("leef-logger");

    public void log(HttpServletRequest request, String eventName, String source) {
        MDC.put(APP_VERSION_NAME, appVersionValue);
        MDC.put(APP_NAME, appNameValue);
        MDC.put(EVENT_NAME, eventName);
        MDC.put(CLIENT_IP_NAME, request.getRemoteAddr());
        MDC.put(TRANSACTION_ID_NAME, getTransactionIdFromRequest(request));
        MDC.put(SOURCE_NAME, source);
        MDC.put(USER_AGENT_NAME, request.getHeader(HttpHeaders.USER_AGENT));
        MDC.put(FINGERPRINT_NAME, request.getHeader("fingerprint"));
        String checksum = DigestUtils.md5Hex(MDC.getCopyOfContextMap().toString());
        MDC.put(CHECKSUM_NAME, checksum);
        log.info("leefLog");
    }

    private String getTransactionIdFromRequest(HttpServletRequest request) {
        return "transactionId";
    }

}
