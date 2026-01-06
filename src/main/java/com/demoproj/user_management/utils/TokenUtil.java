package com.demoproj.user_management.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "token")
@Getter
@Setter
public class TokenUtil {
    private access access;
    private refresh refresh;

    @Getter
    @Setter
    public static class access{
        private long expire_in_ms;
    }

    @Getter
    @Setter
    public static class refresh{
        private long expire_in_ms;
    }
}
