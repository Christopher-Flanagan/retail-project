package retail.jwt.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("jwt")
public class JwtProperties {

    private String key;
    private String duration;

    public String getKey() {
        return this.key;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
