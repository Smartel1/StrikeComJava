package ru.smartel.strike.configuration.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@ConfigurationProperties(prefix = "publications.ok")
public class OkProperties {
    public static final Logger logger = LoggerFactory.getLogger(OkProperties.class);
    /**
     * Application eternal access token
     */
    private String accessToken;
    /**
     * Application secret
     */
    private String appSecret;
    /**
     * Application public key
     */
    private String appKey;
    /**
     * Id of OK group to publish to
     */
    private String gid;
    private boolean specified = false;

    @PostConstruct
    public void setSpecified() {
        specified = !gid.isBlank() && !appSecret.isBlank() && !appKey.isBlank() && !accessToken.isBlank();
        if (specified) {
            logger.debug("OK properties: gid: {}, secret: {}, appKey: {}, accessToken: {}", gid, appSecret, appKey, accessToken);
        } else {
            logger.debug("OK properties not specified");
        }
    }

    public boolean isSpecified() {
        return specified;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }
}
