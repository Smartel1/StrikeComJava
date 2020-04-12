package ru.smartel.strike.configuration.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@ConfigurationProperties(prefix = "publications.vk")
public class VkProperties {
    public static final Logger logger = LoggerFactory.getLogger(VkProperties.class);
    private String groupId;
    private String accessToken;
    private boolean specified = false;

    @PostConstruct
    public void setSpecified() {
        specified = !groupId.isBlank() && !accessToken.isBlank();
        if (specified) {
            logger.debug("Vk properties: groupId: {}, accessToken: {}", groupId, accessToken);
        } else {
            logger.debug("Vk properties not specified");
        }
    }

    public boolean isSpecified() {
        return specified;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
