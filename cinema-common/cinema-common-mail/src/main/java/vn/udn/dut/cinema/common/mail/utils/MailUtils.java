package vn.udn.dut.cinema.common.mail.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.mail.Authenticator;
import jakarta.mail.Session;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import vn.udn.dut.cinema.common.core.utils.SpringUtils;
import vn.udn.dut.cinema.common.core.utils.StringUtils;

import java.io.File;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * mail tools
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MailUtils {

    private static final MailAccount ACCOUNT = SpringUtils.getBean(MailAccount.class);

    /**
     * Get an example of sending an email
     */
    public static MailAccount getMailAccount() {
        return ACCOUNT;
    }

    /**
     * Get email sending instance (custom sender and authorization code)
     *
     * @param user sender
     * @param pass Authorization code
     */
    public static MailAccount getMailAccount(String from, String user, String pass) {
        ACCOUNT.setFrom(StringUtils.blankToDefault(from, ACCOUNT.getFrom()));
        ACCOUNT.setUser(StringUtils.blankToDefault(user, ACCOUNT.getUser()));
        ACCOUNT.setPass(StringUtils.blankToDefault(pass, ACCOUNT.getPass()));
        return ACCOUNT;
    }

    /**
     * Send text mail to single or multiple recipients using the account set up in the configuration file<br>
     * Multiple recipients can be separated by a comma "," or a semicolon ";"
     *
     * @param to      recipient
     * @param subject title
     * @param content text
     * @param files   Attachment list
     * @return message-id
     * @since 3.2.0
     */
    public static String sendText(String to, String subject, String content, File... files) {
        return send(to, subject, content, false, files);
    }

    /**
     * Send HTML emails to single or multiple recipients using the account set in the configuration file<br>
     * Multiple recipients can be separated by a comma "," or a semicolon ";"
     *
     * @param to      recipient
     * @param subject title
     * @param content text
     * @param files   Attachment list
     * @return message-id
     * @since 3.2.0
     */
    public static String sendHtml(String to, String subject, String content, File... files) {
        return send(to, subject, content, true, files);
    }
    
    /**
     * Send HTML emails to single or multiple recipients using the account set in the configuration file<br>
     * Multiple recipients can be separated by a comma "," or a semicolon ";"
     *
     * @param to      recipient
     * @param subject title
     * @param content text
     * @param files   Attachment list
     * @return message-id
     * @since 3.2.0
     */
    public static String sendHtml(String to, String cc, String bcc, String subject, String content, File... files) {
    	return send(splitAddress(to), splitAddress(cc), splitAddress(bcc), subject, content, true, files);
    }

    /**
     * Send mail using the account set in the configuration file, to single or multiple recipients<br>
     * Multiple recipients can be separated by a comma "," or a semicolon ";"
     *
     * @param to      recipient
     * @param subject title
     * @param content text
     * @param isHtml  Is it HTML
     * @param files   Attachment list
     * @return message-id
     */
    public static String send(String to, String subject, String content, boolean isHtml, File... files) {
        return send(splitAddress(to), subject, content, isHtml, files);
    }

    /**
     * Send mail using the account set in the configuration file, to single or multiple recipients<br>
     * Multiple recipients, cc, bcc can be separated by a comma "," or a semicolon ";"
     *
     * @param to      Recipients can be separated by a comma "," or a semicolon ";"
     * @param cc      Cc, can be separated by a comma "," or a semicolon ";"
     * @param bcc     Bcc can be separated by comma "," or semicolon ";"
     * @param subject title
     * @param content text
     * @param isHtml  Is it HTML
     * @param files   Attachment list
     * @return message-id
     * @since 4.0.3
     */
    public static String send(String to, String cc, String bcc, String subject, String content, boolean isHtml, File... files) {
        return send(splitAddress(to), splitAddress(cc), splitAddress(bcc), subject, content, isHtml, files);
    }

    /**
     * Send text mail using the account set up in the configuration file, send to multiple people
     *
     * @param tos     recipient list
     * @param subject title
     * @param content text
     * @param files   Attachment list
     * @return message-id
     */
    public static String sendText(Collection<String> tos, String subject, String content, File... files) {
        return send(tos, subject, content, false, files);
    }

    /**
     * Use the account set in the configuration file to send HTML mail to multiple people
     *
     * @param tos     recipient list
     * @param subject title
     * @param content text
     * @param files   Attachment list
     * @return message-id
     * @since 3.2.0
     */
    public static String sendHtml(Collection<String> tos, String subject, String content, File... files) {
        return send(tos, subject, content, true, files);
    }

    /**
     * Use the account set in the configuration file to send emails to multiple people
     *
     * @param tos     recipient list
     * @param subject title
     * @param content text
     * @param isHtml  Is it HTML
     * @param files   Attachment list
     * @return message-id
     */
    public static String send(Collection<String> tos, String subject, String content, boolean isHtml, File... files) {
        return send(tos, null, null, subject, content, isHtml, files);
    }

    /**
     * Use the account set in the configuration file to send emails to multiple people
     *
     * @param tos     recipient list
     * @param ccs     CC list, can be null or empty
     * @param bccs    BCC list, can be null or empty
     * @param subject title
     * @param content text
     * @param isHtml  Is it HTML
     * @param files   Attachment list
     * @return message-id
     * @since 4.0.3
     */
    public static String send(Collection<String> tos, Collection<String> ccs, Collection<String> bccs, String subject, String content, boolean isHtml, File... files) {
        return send(getMailAccount(), true, tos, ccs, bccs, subject, content, null, isHtml, files);
    }

    // ------------------------------------------------------------------------------------------------------------------------------- Custom MailAccount

    /**
     * send mail to multiple people
     *
     * @param mailAccount Email Authentication Object
     * @param to          Recipients, multiple recipients separated by commas or semicolons
     * @param subject     title
     * @param content     text
     * @param isHtml      Is it in HTML format
     * @param files       Attachment list
     * @return message-id
     * @since 3.2.0
     */
    public static String send(MailAccount mailAccount, String to, String subject, String content, boolean isHtml, File... files) {
        return send(mailAccount, splitAddress(to), subject, content, isHtml, files);
    }

    /**
     * send mail to multiple people
     *
     * @param mailAccount mail account information
     * @param tos         recipient list
     * @param subject     title
     * @param content     text
     * @param isHtml      Is it in HTML format
     * @param files       Attachment list
     * @return message-id
     */
    public static String send(MailAccount mailAccount, Collection<String> tos, String subject, String content, boolean isHtml, File... files) {
        return send(mailAccount, tos, null, null, subject, content, isHtml, files);
    }

    /**
     * send mail to multiple people
     *
     * @param mailAccount mail account information
     * @param tos         recipient list
     * @param ccs         CC list, can be null or empty
     * @param bccs        BCC list, can be null or empty
     * @param subject     title
     * @param content     text
     * @param isHtml      Is it in HTML format
     * @param files       Attachment list
     * @return message-id
     * @since 4.0.3
     */
    public static String send(MailAccount mailAccount, Collection<String> tos, Collection<String> ccs, Collection<String> bccs, String subject, String content, boolean isHtml, File... files) {
        return send(mailAccount, false, tos, ccs, bccs, subject, content, null, isHtml, files);
    }

    /**
     * Send HTML emails to single or multiple recipients using the account set in the configuration file<br>
     * Multiple recipients can be separated by a comma "," or a semicolon ";"
     *
     * @param to       recipient
     * @param subject  title
     * @param content  text
     * @param imageMap Image and placeholder, the placeholder format is cid:$IMAGE_PLACEHOLDER
     * @param files    Attachment list
     * @return message-id
     * @since 3.2.0
     */
    public static String sendHtml(String to, String subject, String content, Map<String, InputStream> imageMap, File... files) {
        return send(to, subject, content, imageMap, true, files);
    }

    /**
     * Send mail using the account set in the configuration file, to single or multiple recipients<br>
     * Multiple recipients can be separated by a comma "," or a semicolon ";"
     *
     * @param to       recipient
     * @param subject  title
     * @param content  text
     * @param imageMap Image and placeholder, the placeholder format is cid:$IMAGE_PLACEHOLDER
     * @param isHtml   Is it HTML
     * @param files    Attachment list
     * @return message-id
     */
    public static String send(String to, String subject, String content, Map<String, InputStream> imageMap, boolean isHtml, File... files) {
        return send(splitAddress(to), subject, content, imageMap, isHtml, files);
    }

    /**
     * Send mail using the account set in the configuration file, to single or multiple recipients<br>
     * Multiple recipients, cc, bcc can be separated by a comma "," or a semicolon ";"
     *
     * @param to       Recipients can be separated by a comma "," or a semicolon ";"
     * @param cc       Cc, can be separated by a comma "," or a semicolon ";"
     * @param bcc      Bcc can be separated by comma "," or semicolon ";"
     * @param subject  title
     * @param content  text
     * @param imageMap Image and placeholder, the placeholder format is cid:$IMAGE_PLACEHOLDER
     * @param isHtml   Is it HTML
     * @param files    Attachment list
     * @return message-id
     * @since 4.0.3
     */
    public static String send(String to, String cc, String bcc, String subject, String content, Map<String, InputStream> imageMap, boolean isHtml, File... files) {
        return send(splitAddress(to), splitAddress(cc), splitAddress(bcc), subject, content, imageMap, isHtml, files);
    }

    /**
     * Use the account set in the configuration file to send HTML mail to multiple people
     *
     * @param tos      recipient list
     * @param subject  title
     * @param content  text
     * @param imageMap Image and placeholder, the placeholder format is cid:$IMAGE_PLACEHOLDER
     * @param files    Attachment list
     * @return message-id
     * @since 3.2.0
     */
    public static String sendHtml(Collection<String> tos, String subject, String content, Map<String, InputStream> imageMap, File... files) {
        return send(tos, subject, content, imageMap, true, files);
    }

    /**
     * Use the account set in the configuration file to send emails to multiple people
     *
     * @param tos      recipient list
     * @param subject  title
     * @param content  text
     * @param imageMap Image and placeholder, the placeholder format is cid:$IMAGE_PLACEHOLDER
     * @param isHtml   Is it HTML
     * @param files    Attachment list
     * @return message-id
     */
    public static String send(Collection<String> tos, String subject, String content, Map<String, InputStream> imageMap, boolean isHtml, File... files) {
        return send(tos, null, null, subject, content, imageMap, isHtml, files);
    }

    /**
     * Use the account set in the configuration file to send emails to multiple people
     *
     * @param tos      recipient list
     * @param ccs      CC list, can be null or empty
     * @param bccs     BCC list, can be null or empty
     * @param subject  title
     * @param content  text
     * @param imageMap Image and placeholder, the placeholder format is cid:$IMAGE_PLACEHOLDER
     * @param isHtml   Is it HTML
     * @param files    Attachment list
     * @return message-id
     * @since 4.0.3
     */
    public static String send(Collection<String> tos, Collection<String> ccs, Collection<String> bccs, String subject, String content, Map<String, InputStream> imageMap, boolean isHtml, File... files) {
        return send(getMailAccount(), true, tos, ccs, bccs, subject, content, imageMap, isHtml, files);
    }

    // ------------------------------------------------------------------------------------------------------------------------------- Custom MailAccount

    /**
     * send mail to multiple people
     *
     * @param mailAccount Email Authentication Object
     * @param to          Recipients, multiple recipients separated by commas or semicolons
     * @param subject     title
     * @param content     text
     * @param imageMap    Image and placeholder, the placeholder format is cid:$IMAGE_PLACEHOLDER
     * @param isHtml      Is it in HTML format
     * @param files       Attachment list
     * @return message-id
     * @since 3.2.0
     */
    public static String send(MailAccount mailAccount, String to, String subject, String content, Map<String, InputStream> imageMap, boolean isHtml, File... files) {
        return send(mailAccount, splitAddress(to), subject, content, imageMap, isHtml, files);
    }

    /**
     * send mail to multiple people
     *
     * @param mailAccount mail account information
     * @param tos         recipient list
     * @param subject     title
     * @param content     text
     * @param imageMap    Image and placeholder, the placeholder format is cid:$IMAGE_PLACEHOLDER
     * @param isHtml      Is it in HTML format
     * @param files       Attachment list
     * @return message-id
     * @since 4.6.3
     */
    public static String send(MailAccount mailAccount, Collection<String> tos, String subject, String content, Map<String, InputStream> imageMap, boolean isHtml, File... files) {
        return send(mailAccount, tos, null, null, subject, content, imageMap, isHtml, files);
    }

    /**
     * send mail to multiple people
     *
     * @param mailAccount mail account information
     * @param tos         recipient list
     * @param ccs         CC list, can be null or empty
     * @param bccs        BCC list, can be null or empty
     * @param subject     title
     * @param content     text
     * @param imageMap    Image and placeholder, the placeholder format is cid:$IMAGE_PLACEHOLDER
     * @param isHtml      Is it in HTML format
     * @param files       Attachment list
     * @return message-id
     * @since 4.6.3
     */
    public static String send(MailAccount mailAccount, Collection<String> tos, Collection<String> ccs, Collection<String> bccs, String subject, String content, Map<String, InputStream> imageMap,
                              boolean isHtml, File... files) {
        return send(mailAccount, false, tos, ccs, bccs, subject, content, imageMap, isHtml, files);
    }

    /**
     * According to the configuration file, get the mail client session
     *
     * @param mailAccount Mail account configuration
     * @param isSingleton Is it a singleton (globally shared session)
     * @return {@link Session}
     * @since 5.5.7
     */
    public static Session getSession(MailAccount mailAccount, boolean isSingleton) {
        Authenticator authenticator = null;
        if (mailAccount.isAuth()) {
            authenticator = new UserPassAuthenticator(mailAccount.getUser(), mailAccount.getPass());
        }

        return isSingleton ? Session.getDefaultInstance(mailAccount.getSmtpProps(), authenticator) //
            : Session.getInstance(mailAccount.getSmtpProps(), authenticator);
    }

    // ------------------------------------------------------------------------------------------------------------------------ Private method start

    /**
     * send mail to multiple people
     *
     * @param mailAccount      mail account information
     * @param useGlobalSession Whether to share Session globally
     * @param tos              recipient list
     * @param ccs              CC list, can be null or empty
     * @param bccs             BCC list, can be null or empty
     * @param subject          title
     * @param content          text
     * @param imageMap         Image and placeholder, the placeholder format is cid:${cid}
     * @param isHtml           Is it in HTML format
     * @param files            Attachment list
     * @return message-id
     * @since 4.6.3
     */
    private static String send(MailAccount mailAccount, boolean useGlobalSession, Collection<String> tos, Collection<String> ccs, Collection<String> bccs, String subject, String content,
                               Map<String, InputStream> imageMap, boolean isHtml, File... files) {
        final Mail mail = Mail.create(mailAccount).setUseGlobalSession(useGlobalSession);

        // optional cc
        if (CollUtil.isNotEmpty(ccs)) {
            mail.setCcs(ccs.toArray(new String[0]));
        }
        // optional blind sender
        if (CollUtil.isNotEmpty(bccs)) {
            mail.setBccs(bccs.toArray(new String[0]));
        }

        mail.setTos(tos.toArray(new String[0]));
        mail.setTitle(subject);
        mail.setContent(content);
        mail.setHtml(isHtml);
        mail.setFiles(files);

        // picture
        if (MapUtil.isNotEmpty(imageMap)) {
            for (Map.Entry<String, InputStream> entry : imageMap.entrySet()) {
                mail.addImage(entry.getKey(), entry.getValue());
                // close stream
                IoUtil.close(entry.getValue());
            }
        }

        return mail.send();
    }

    /**
     * Convert multiple contacts into a list, separated by comma or semicolon
     *
     * @param addresses Multiple contacts, if empty return null
     * @return contact list
     */
    private static List<String> splitAddress(String addresses) {
        if (StrUtil.isBlank(addresses)) {
            return null;
        }

        List<String> result;
        if (StrUtil.contains(addresses, CharUtil.COMMA)) {
            result = StrUtil.splitTrim(addresses, CharUtil.COMMA);
        } else if (StrUtil.contains(addresses, ';')) {
            result = StrUtil.splitTrim(addresses, ';');
        } else {
            result = CollUtil.newArrayList(addresses);
        }
        return result;
    }
    // ------------------------------------------------------------------------------------------------------------------------ Private method end

}
