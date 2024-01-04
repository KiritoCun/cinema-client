package vn.udn.dut.cinema.common.mail.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * JavaMail configuration properties
 *
 * @author HoaLD
 */
@Data
@ConfigurationProperties(prefix = "mail")
public class MailProperties {

    /**
     * filter switch
     */
    private Boolean enabled;

    /**
     * SMTP server domain name
     */
    private String host;

    /**
     * SMTP service port
     */
    private Integer port;

    /**
     * Do you need username and password authentication?
     */
    private Boolean auth;

    /**
     * username
     */
    private String user;

    /**
     * password
     */
    private String pass;

    /**
     * Sender, following the RFC-822 standard
     */
    private String from;

    /**
     * Secure connections using STARTTLS, an extension to the plain text communication protocol. It upgrades the plain text connection to an encrypted connection (TLS or SSL) instead of using a separate port for encrypted communication.
     */
    private Boolean starttlsEnable;

    /**
     * Use SSL secure connection
     */
    private Boolean sslEnable;

    /**
     * SMTP timeout, in milliseconds, the default value is no timeout
     */
    private Long timeout;

    /**
     * Socket connection timeout value, in milliseconds, the default value is no timeout
     */
    private Long connectionTimeout;
}
