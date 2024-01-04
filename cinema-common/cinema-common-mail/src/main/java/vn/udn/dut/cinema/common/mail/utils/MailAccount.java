package vn.udn.dut.cinema.common.mail.utils;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.Setting;

import java.io.Serial;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * mail account object
 *
 * @author HoaLD
 */
public class MailAccount implements Serializable {
    @Serial
    private static final long serialVersionUID = -6937313421815719204L;

    private static final String MAIL_PROTOCOL = "mail.transport.protocol";
    private static final String SMTP_HOST = "mail.smtp.host";
    private static final String SMTP_PORT = "mail.smtp.port";
    private static final String SMTP_AUTH = "mail.smtp.auth";
    private static final String SMTP_TIMEOUT = "mail.smtp.timeout";
    private static final String SMTP_CONNECTION_TIMEOUT = "mail.smtp.connectiontimeout";
    private static final String SMTP_WRITE_TIMEOUT = "mail.smtp.writetimeout";

    // SSL
    private static final String STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    private static final String SSL_ENABLE = "mail.smtp.ssl.enable";
    private static final String SSL_PROTOCOLS = "mail.smtp.ssl.protocols";
    private static final String SOCKET_FACTORY = "mail.smtp.socketFactory.class";
    private static final String SOCKET_FACTORY_FALLBACK = "mail.smtp.socketFactory.fallback";
    private static final String SOCKET_FACTORY_PORT = "smtp.socketFactory.port";

    // System Properties
    private static final String SPLIT_LONG_PARAMS = "mail.mime.splitlongparameters";
    //private static final String ENCODE_FILE_NAME = "mail.mime.encodefilename";
    //private static final String CHARSET = "mail.mime.charset";

    // other
    private static final String MAIL_DEBUG = "mail.debug";

    public static final String[] MAIL_SETTING_PATHS = new String[]{"config/mail.setting", "config/mailAccount.setting", "mail.setting"};

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
     * Whether to enable the debug mode, the debug mode will display the communication process with the mail server, it is not enabled by default
     */
    private boolean debug;
    /**
     * Encoding is used to encode the text of the email and the Chinese characters of the sender and recipient
     */
    private Charset charset = CharsetUtil.CHARSET_UTF_8;
    /**
     * For whether the extra-long parameter is divided into multiple parts, the default is false (the domestic mailbox attachment does not support the divided attachment name)
     */
    private boolean splitlongparameters = false;
    /**
     * Whether to use {@link #charset} encoding for filenames, the default is {@code true}
     */
    private boolean encodefilename = true;

    /**
     * Secure connections using STARTTLS, an extension to the plain text communication protocol. It upgrades the plain text connection to an encrypted connection (TLS or SSL) instead of using a separate port for encrypted communication.
     */
    private boolean starttlsEnable = false;
    /**
     * Use SSL secure connection
     */
    private Boolean sslEnable;

    /**
     * SSL protocol, multiple protocols are separated by spaces
     */
    private String sslProtocols;

    /**
     * Specify the name of the class that implements the javax.net.SocketFactory interface. This class will be used to create SMTP sockets
     */
    private String socketFactoryClass = "javax.net.ssl.SSLSocketFactory";
    /**
     * If set to true, failure to create a socket using the specified socket factory class will result in the socket class being created using java.net.Socket, the default is true
     */
    private boolean socketFactoryFallback;
    /**
     * The specified port is connected to using the specified socket factory. If not set, the default port will be used
     */
    private int socketFactoryPort = 465;

    /**
     * SMTP timeout, in milliseconds, the default value is no timeout
     */
    private long timeout;
    /**
     * Socket connection timeout value, in milliseconds, the default value is no timeout
     */
    private long connectionTimeout;
    /**
     * Socket write timeout value, in milliseconds, the default value is no timeout
     */
    private long writeTimeout;

    /**
     * Other custom properties, this custom property will override the default property
     */
    private final Map<String, Object> customProperty = new HashMap<>();

    // -------------------------------------------------------------- Constructor start

    /**
     * Construction, all parameters need to be defined by themselves or keep the default values
     */
    public MailAccount() {
    }

    /**
     * structure
     *
     * @param settingPath configuration file path
     */
    public MailAccount(String settingPath) {
        this(new Setting(settingPath));
    }

    /**
     * structure
     *
     * @param setting configuration file
     */
    public MailAccount(Setting setting) {
        setting.toBean(this);
    }

    // -------------------------------------------------------------- Constructor end

    /**
     * Obtain SMTP server domain name
     *
     * @return SMTP server domain name
     */
    public String getHost() {
        return host;
    }

    /**
     * Set SMTP server domain name
     *
     * @param host SMTP server domain name
     * @return this
     */
    public MailAccount setHost(String host) {
        this.host = host;
        return this;
    }

    /**
     * Get SMTP service port
     *
     * @return SMTP service port
     */
    public Integer getPort() {
        return port;
    }

    /**
     * Set SMTP service port
     *
     * @param port SMTP service port
     * @return this
     */
    public MailAccount setPort(Integer port) {
        this.port = port;
        return this;
    }

    /**
     * Do you need username and password authentication?
     *
     * @return Do you need username and password authentication?
     */
    public Boolean isAuth() {
        return auth;
    }

    /**
     * Set whether to require username and password authentication
     *
     * @param isAuth Do you need username and password authentication?
     * @return this
     */
    public MailAccount setAuth(boolean isAuth) {
        this.auth = isAuth;
        return this;
    }

    /**
     * get username
     *
     * @return username
     */
    public String getUser() {
        return user;
    }

    /**
     * set username
     *
     * @param user username
     * @return this
     */
    public MailAccount setUser(String user) {
        this.user = user;
        return this;
    }

    /**
     * get password
     *
     * @return password
     */
    public String getPass() {
        return pass;
    }

    /**
     * set password
     *
     * @param pass password
     * @return this
     */
    public MailAccount setPass(String pass) {
        this.pass = pass;
        return this;
    }

    /**
     * Get the sender, follow the RFC-822 standard
     *
     * @return Sender, following the RFC-822 standard
     */
    public String getFrom() {
        return from;
    }

    /**
     * Set the sender, follow the RFC-822 standard<br>
     * The sender can be of the form:
     *
     * <pre>
     * 1. user@xxx.xx
     * 2.  name &lt;user@xxx.xx&gt;
     * </pre>
     *
     * @param from Sender, following the RFC-822 standard
     * @return this
     */
    public MailAccount setFrom(String from) {
        this.from = from;
        return this;
    }

    /**
     * Whether to enable the debug mode, the debug mode will display the communication process with the mail server, it is not enabled by default
     *
     * @return Whether to enable the debug mode, the debug mode will display the communication process with the mail server, it is not enabled by default
     * @since 4.0.2
     */
    public boolean isDebug() {
        return debug;
    }

    /**
     * Set whether to enable the debug mode. The debug mode will display the communication process with the mail server. It is not enabled by default.
     *
     * @param debug Whether to enable the debug mode, the debug mode will display the communication process with the mail server, it is not enabled by default
     * @return this
     * @since 4.0.2
     */
    public MailAccount setDebug(boolean debug) {
        this.debug = debug;
        return this;
    }

    /**
     * Get character set encoding
     *
     * @return encoding, which may be {@code null}
     */
    public Charset getCharset() {
        return charset;
    }

    /**
     * Set the character set encoding. This option will not modify the global configuration. If you want to modify the global configuration, please set this to {@code null} and set:
     * <pre>
     * 	System.setProperty("mail.mime.charset", charset);
     * </pre>
     *
     * @param charset Character set encoding, {@code null} means to use the default encoding set globally, and the global encoding is the mail.mime.charset system property
     * @return this
     */
    public MailAccount setCharset(Charset charset) {
        this.charset = charset;
        return this;
    }

    /**
     * For whether the extra-long parameter is divided into multiple parts, the default is false (the domestic mailbox attachment does not support the divided attachment name)
     *
     * @return Whether to divide the ultra-long parameter into multiple parts
     */
    public boolean isSplitlongparameters() {
        return splitlongparameters;
    }

    /**
     * Set whether to split into multiple parts for super long parameters, the default is false (Domestic mailbox attachments do not support split attachment names)<br>
     * Note that this is a global setting, this will call
     * <pre>
     * System.setProperty("mail.mime.splitlongparameters", true)
     * </pre>
     *
     * @param splitlongparameters Whether to divide the ultra-long parameter into multiple parts
     */
    public void setSplitlongparameters(boolean splitlongparameters) {
        this.splitlongparameters = splitlongparameters;
    }

    /**
     * Whether to use {@link #charset} encoding for filenames, the default is {@code true}
     *
     * @return Whether to use {@link #charset} encoding for filenames, the default is {@code true}
     * @since 5.7.16
     */
    public boolean isEncodefilename() {

        return encodefilename;
    }

    /**
     * Set whether to use {@link #charset} encoding for the file name, this option will not modify the global configuration<br>
     * If this option is set to {@code false}, encoding depends on two system properties:
     * <ul>
     *     <li>mail.mime.encodefilename  Whether to encode the attachment filename</li>
     *     <li>mail.mime.charset         Encoding for encoding filenames</li>
     * </ul>
     *
     * @param encodefilename Whether to use {@link #charset} encoding for filenames
     * @since 5.7.16
     */
    public void setEncodefilename(boolean encodefilename) {
        this.encodefilename = encodefilename;
    }

    /**
     * Whether to use STARTTLS secure connection, STARTTLS is an extension to the plain text communication protocol. It upgrades the plain text connection to an encrypted connection (TLS or SSL) instead of using a separate port for encrypted communication.
     *
     * @return Whether to use STARTTLS secure connection
     */
    public boolean isStarttlsEnable() {
        return this.starttlsEnable;
    }

    /**
     * Set whether to use STARTTLS secure connection, STARTTLS is an extension of the plain text communication protocol. It upgrades the plain text connection to an encrypted connection (TLS or SSL) instead of using a separate port for encrypted communication.
     *
     * @param startttlsEnable Whether to use STARTTLS secure connection
     * @return this
     */
    public MailAccount setStarttlsEnable(boolean startttlsEnable) {
        this.starttlsEnable = startttlsEnable;
        return this;
    }

    /**
     * Whether to use SSL secure connection
     *
     * @return Whether to use SSL secure connection
     */
    public Boolean isSslEnable() {
        return this.sslEnable;
    }

    /**
     * Set whether to use SSL secure connection
     *
     * @param sslEnable Whether to use SSL secure connection
     * @return this
     */
    public MailAccount setSslEnable(Boolean sslEnable) {
        this.sslEnable = sslEnable;
        return this;
    }

    /**
     * Obtain the SSL protocol, multiple protocols are separated by spaces
     *
     * @return SSL protocol, multiple protocols are separated by spaces
     * @since 5.5.7
     */
    public String getSslProtocols() {
        return sslProtocols;
    }

    /**
     * Set the SSL protocol, multiple protocols are separated by spaces
     *
     * @param sslProtocols SSL protocol, multiple protocols are separated by spaces
     * @since 5.5.7
     */
    public void setSslProtocols(String sslProtocols) {
        this.sslProtocols = sslProtocols;
    }

    /**
     * Get the name of the specified class that implements the javax.net.SocketFactory interface, which will be used to create SMTP sockets
     *
     * @return Specify the name of the class that implements the javax.net.SocketFactory interface, this class will be used to create SMTP sockets
     */
    public String getSocketFactoryClass() {
        return socketFactoryClass;
    }

    /**
     * Set the name of the class that implements the javax.net.SocketFactory interface. This class will be used to create SMTP sockets
     *
     * @param socketFactoryClass Specify the name of the class that implements the javax.net.SocketFactory interface. This class will be used to create SMTP sockets
     * @return this
     */
    public MailAccount setSocketFactoryClass(String socketFactoryClass) {
        this.socketFactoryClass = socketFactoryClass;
        return this;
    }

    /**
     * If set to true, failure to create a socket using the specified socket factory class will result in the socket class being created using java.net.Socket, the default is true
     *
     * @return If set to true, failure to create a socket using the specified socket factory class will result in the socket class being created using java.net.Socket, the default is true
     */
    public boolean isSocketFactoryFallback() {
        return socketFactoryFallback;
    }

    /**
     * If set to true, failure to create a socket using the specified socket factory class will result in the socket class being created using java.net.Socket, the default is true
     *
     * @param socketFactoryFallback If set to true, failure to create a socket using the specified socket factory class will result in the socket class being created using java.net.Socket, the default is true
     * @return this
     */
    public MailAccount setSocketFactoryFallback(boolean socketFactoryFallback) {
        this.socketFactoryFallback = socketFactoryFallback;
        return this;
    }

    /**
     * Gets a connection to the specified port using the specified socket factory. If not set, the default port will be used
     *
     * @return The specified port is connected to using the specified socket factory. If not set, the default port will be used
     */
    public int getSocketFactoryPort() {
        return socketFactoryPort;
    }

    /**
     * The specified port is connected to using the specified socket factory. If not set, the default port will be used
     *
     * @param socketFactoryPort The specified port is connected to using the specified socket factory. If not set, the default port will be used
     * @return this
     */
    public MailAccount setSocketFactoryPort(int socketFactoryPort) {
        this.socketFactoryPort = socketFactoryPort;
        return this;
    }

    /**
     * Set the SMTP timeout period, in milliseconds, the default value is no timeout
     *
     * @param timeout SMTP timeout, in milliseconds, the default value is no timeout
     * @return this
     * @since 4.1.17
     */
    public MailAccount setTimeout(long timeout) {
        this.timeout = timeout;
        return this;
    }

    /**
     * Set the Socket connection timeout value in milliseconds, the default value is no timeout
     *
     * @param connectionTimeout Socket connection timeout value, in milliseconds, the default value is no timeout
     * @return this
     * @since 4.1.17
     */
    public MailAccount setConnectionTimeout(long connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
        return this;
    }

    /**
     * Set the Socket write timeout value in milliseconds, the default value is no timeout
     *
     * @param writeTimeout Socket write timeout value, in milliseconds, the default value is no timeout
     * @return this
     * @since 5.8.3
     */
    public MailAccount setWriteTimeout(long writeTimeout) {
        this.writeTimeout = writeTimeout;
        return this;
    }

    /**
     * Get a list of custom properties
     *
     * @return Custom parameter list
     * @since 5.6.4
     */
    public Map<String, Object> getCustomProperty() {
        return customProperty;
    }

    /**
     * Set custom properties like mail.smtp.ssl.socketFactory
     *
     * @param key   attribute name, blanks are ignored
     * @param value the property value, null is ignored
     * @return this
     * @since 5.6.4
     */
    public MailAccount setCustomProperty(String key, Object value) {
        if (StrUtil.isNotBlank(key) && ObjectUtil.isNotNull(value)) {
            this.customProperty.put(key, value);
        }
        return this;
    }

    /**
     * Get SMTP related information
     *
     * @return {@link Properties}
     */
    public Properties getSmtpProps() {
        // Global System Parameters
        System.setProperty(SPLIT_LONG_PARAMS, String.valueOf(this.splitlongparameters));

        final Properties p = new Properties();
        p.put(MAIL_PROTOCOL, "smtp");
        p.put(SMTP_HOST, this.host);
        p.put(SMTP_PORT, String.valueOf(this.port));
        p.put(SMTP_AUTH, String.valueOf(this.auth));
        if (this.timeout > 0) {
            p.put(SMTP_TIMEOUT, String.valueOf(this.timeout));
        }
        if (this.connectionTimeout > 0) {
            p.put(SMTP_CONNECTION_TIMEOUT, String.valueOf(this.connectionTimeout));
        }
        // issue#2355
        if (this.writeTimeout > 0) {
            p.put(SMTP_WRITE_TIMEOUT, String.valueOf(this.writeTimeout));
        }

        p.put(MAIL_DEBUG, String.valueOf(this.debug));

        if (this.starttlsEnable) {
            // STARTTLS is an extension to the plain text communication protocol. It upgrades the plain text connection to an encrypted connection (TLS or SSL) instead of using a separate port for encrypted communication.
            p.put(STARTTLS_ENABLE, "true");

            if (null == this.sslEnable) {
                // In order to be compatible with the old version, when the user does not have this configuration, it will be treated as when starttlsEnable is enabled
                this.sslEnable = true;
            }
        }

        // SSL
        if (null != this.sslEnable && this.sslEnable) {
            p.put(SSL_ENABLE, "true");
            p.put(SOCKET_FACTORY, socketFactoryClass);
            p.put(SOCKET_FACTORY_FALLBACK, String.valueOf(this.socketFactoryFallback));
            p.put(SOCKET_FACTORY_PORT, String.valueOf(this.socketFactoryPort));
            // issue#IZN95@Gitee, you need to customize the SSL protocol version under Linux
            if (StrUtil.isNotBlank(this.sslProtocols)) {
                p.put(SSL_PROTOCOLS, this.sslProtocols);
            }
        }

        // Supplementary custom attributes, allowing custom attributes to override already set values
        p.putAll(this.customProperty);

        return p;
    }

    /**
     * If some value is null, use default value
     *
     * @return this
     */
    public MailAccount defaultIfEmpty() {
        // Strip sender's name part
        final String fromAddress = InternalMailUtil.parseFirstAddress(this.from, this.charset).getAddress();

        if (StrUtil.isBlank(this.host)) {
            // If the SMTP address is empty, smtp is used by default. <Sender email suffix>
            this.host = StrUtil.format("smtp.{}", StrUtil.subSuf(fromAddress, fromAddress.indexOf('@') + 1));
        }
        if (StrUtil.isBlank(user)) {
            // Defaults to sender if username is empty (issue#I4FYVY@Gitee)
            // this.user = StrUtil.subPre(fromAddress, fromAddress.indexOf('@'));
            this.user = fromAddress;
        }
        if (null == this.auth) {
            // If password is non-blank, use authentication mode
            this.auth = (false == StrUtil.isBlank(this.pass));
        }
        if (null == this.port) {
            // The default port is the same as socketFactoryPort in the SSL state, and the default is 25 in the non-SSL state
            this.port = (null != this.sslEnable && this.sslEnable) ? this.socketFactoryPort : 25;
        }
        if (null == this.charset) {
            // Default UTF-8 encoding
            this.charset = CharsetUtil.CHARSET_UTF_8;
        }

        return this;
    }

    @Override
    public String toString() {
        return "MailAccount [host=" + host + ", port=" + port + ", auth=" + auth + ", user=" + user + ", pass=" + (StrUtil.isEmpty(this.pass) ? "" : "******") + ", from=" + from + ", startttlsEnable="
            + starttlsEnable + ", socketFactoryClass=" + socketFactoryClass + ", socketFactoryFallback=" + socketFactoryFallback + ", socketFactoryPort=" + socketFactoryPort + "]";
    }
}
