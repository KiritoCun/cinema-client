package vn.udn.dut.cinema.common.mail.utils;

import cn.hutool.core.builder.Builder;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.activation.FileTypeMap;
import jakarta.mail.*;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.internet.MimeUtility;
import jakarta.mail.util.ByteArrayDataSource;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Date;

/**
 * mail sending client
 *
 * @author HoaLD
 * @since 3.2.0
 */
public class Mail implements Builder<MimeMessage> {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Email account information and some client configuration information
     */
    private final MailAccount mailAccount;
    /**
     * recipient list
     */
    private String[] tos;
    /**
     * Cc list (carbon copy)
     */
    private String[] ccs;
    /**
     * blind carbon copy
     */
    private String[] bccs;
    /**
     * reply address (reply-to)
     */
    private String[] reply;
    /**
     * title
     */
    private String title;
    /**
     * content
     */
    private String content;
    /**
     * Is it HTML
     */
    private boolean isHtml;
    /**
     * Mixed section of body text, attachments, and images
     */
    private final Multipart multipart = new MimeMultipart();
    /**
     * Whether to use the global session, the default is false
     */
    private boolean useGlobalSession = false;

    /**
     * Debug output location, you can customize the debug log
     */
    private PrintStream debugOutput;

    /**
     * Create a mail client
     *
     * @param mailAccount email account
     * @return Mail
     */
    public static Mail create(MailAccount mailAccount) {
        return new Mail(mailAccount);
    }

    /**
     * Create a mail client, using a global mail account
     *
     * @return Mail
     */
    public static Mail create() {
        return new Mail();
    }

    // --------------------------------------------------------------- Constructor start

    /**
     * Construct, using global mail account
     */
    public Mail() {
        this(GlobalMailAccount.INSTANCE.getAccount());
    }

    /**
     * structure
     *
     * @param mailAccount The mail account, if null uses the global mail configuration of the default profile
     */
    public Mail(MailAccount mailAccount) {
        mailAccount = (null != mailAccount) ? mailAccount : GlobalMailAccount.INSTANCE.getAccount();
        this.mailAccount = mailAccount.defaultIfEmpty();
    }
    // --------------------------------------------------------------- Constructor end

    // --------------------------------------------------------------- Getters and Setters start

    /**
     * set recipient
     *
     * @param tos recipient list
     * @return this
     * @see #setTos(String...)
     */
    public Mail to(String... tos) {
        return setTos(tos);
    }

    /**
     * Set up multiple recipients
     *
     * @param tos recipient list
     * @return this
     */
    public Mail setTos(String... tos) {
        this.tos = tos;
        return this;
    }

    /**
     * Set multiple carbon copies (carbon copy)
     *
     * @param ccs CC list
     * @return this
     * @since 4.0.3
     */
    public Mail setCcs(String... ccs) {
        this.ccs = ccs;
        return this;
    }

    /**
     * Set up multiple blind carbon copies
     *
     * @param bccs bcc list
     * @return this
     * @since 4.0.3
     */
    public Mail setBccs(String... bccs) {
        this.bccs = bccs;
        return this;
    }

    /**
     * Set multiple reply addresses (reply-to)
     *
     * @param reply Reply-to list
     * @return this
     * @since 4.6.0
     */
    public Mail setReply(String... reply) {
        this.reply = reply;
        return this;
    }

    /**
     * set title
     *
     * @param title title
     * @return this
     */
    public Mail setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * set text<br>
     * The body can be normal text or HTML (default normal text), you can set whether it is HTML by calling {@link #setHtml(boolean)}
     *
     * @param content text
     * @return this
     */
    public Mail setContent(String content) {
        this.content = content;
        return this;
    }

    /**
     * Whether the setting is HTML
     *
     * @param isHtml Is it HTML
     * @return this
     */
    public Mail setHtml(boolean isHtml) {
        this.isHtml = isHtml;
        return this;
    }

    /**
     * set text
     *
     * @param content Text content
     * @param isHtml  Is it HTML
     * @return this
     */
    public Mail setContent(String content, boolean isHtml) {
        setContent(content);
        return setHtml(isHtml);
    }

    /**
     * Set the file type attachment, the file can be a picture file, at this time the cid is automatically set (the picture is quoted in the text), and the default cid is the file name
     *
     * @param files Attachment file list
     * @return this
     */
    public Mail setFiles(File... files) {
        if (ArrayUtil.isEmpty(files)) {
            return this;
        }

        final DataSource[] attachments = new DataSource[files.length];
        for (int i = 0; i < files.length; i++) {
            attachments[i] = new FileDataSource(files[i]);
        }
        return setAttachments(attachments);
    }

    /**
     * Add attachments or pictures. Attachments are expressed in the form of {@link DataSource}, and {@link FileDataSource} wrapper files can be used to indicate file attachments
     *
     * @param attachments Attachment list
     * @return this
     * @since 4.0.9
     */
    public Mail setAttachments(DataSource... attachments) {
        if (ArrayUtil.isNotEmpty(attachments)) {
            final Charset charset = this.mailAccount.getCharset();
            MimeBodyPart bodyPart;
            String nameEncoded;
            try {
                for (DataSource attachment : attachments) {
                    bodyPart = new MimeBodyPart();
                    bodyPart.setDataHandler(new DataHandler(attachment));
                    nameEncoded = attachment.getName();
                    if (this.mailAccount.isEncodefilename()) {
                        nameEncoded = InternalMailUtil.encodeText(nameEncoded, charset);
                    }
                    // Generic attachment filename
                    bodyPart.setFileName(nameEncoded);
                    if (StrUtil.startWith(attachment.getContentType(), "image/")) {
                        // Image attachments for citing images in text
                        bodyPart.setContentID(nameEncoded);
                    }
                    this.multipart.addBodyPart(bodyPart);
                }
            } catch (MessagingException e) {
                throw new MailException(e);
            }
        }
        return this;
    }

    /**
     * Add a picture, the key of the picture corresponds to the placeholder string in the email template, and the default picture type is "image/jpeg"
     *
     * @param cid         Image and placeholder, the placeholder format is cid:${cid}
     * @param imageStream picture file
     * @return this
     * @since 4.6.3
     */
    public Mail addImage(String cid, InputStream imageStream) {
        return addImage(cid, imageStream, null);
    }

    /**
     * Add a picture, the key of the picture corresponds to the placeholder string in the email template
     *
     * @param cid         Image and placeholder, the placeholder format is cid:${cid}
     * @param imageStream picture stream, do not close
     * @param contentType Image type, null assigns the default "image/jpeg"
     * @return this
     * @since 4.6.3
     */
    public Mail addImage(String cid, InputStream imageStream, String contentType) {
        ByteArrayDataSource imgSource;
        try {
            imgSource = new ByteArrayDataSource(imageStream, ObjectUtil.defaultIfNull(contentType, "image/jpeg"));
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
        imgSource.setName(cid);
        return setAttachments(imgSource);
    }

    /**
     * Add a picture, the key of the picture corresponds to the placeholder string in the email template
     *
     * @param cid       Image and placeholder, the placeholder format is cid:${cid}
     * @param imageFile picture file
     * @return this
     * @since 4.6.3
     */
    public Mail addImage(String cid, File imageFile) {
        InputStream in = null;
        try {
            in = FileUtil.getInputStream(imageFile);
            return addImage(cid, in, FileTypeMap.getDefaultFileTypeMap().getContentType(imageFile));
        } finally {
            IoUtil.close(in);
        }
    }

    /**
     * Set character set encoding
     *
     * @param charset character set encoding
     * @return this
     * @see MailAccount#setCharset(Charset)
     */
    public Mail setCharset(Charset charset) {
        this.mailAccount.setCharset(charset);
        return this;
    }

    /**
     * Set whether to use the global session, the default is true
     *
     * @param isUseGlobalSession Whether to use the global session, the default is true
     * @return this
     * @since 4.0.2
     */
    public Mail setUseGlobalSession(boolean isUseGlobalSession) {
        this.useGlobalSession = isUseGlobalSession;
        return this;
    }

    /**
     * Set the debug output location, you can customize the debug log
     *
     * @param debugOutput debug output location
     * @return this
     * @since 5.5.6
     */
    public Mail setDebugOutput(PrintStream debugOutput) {
        this.debugOutput = debugOutput;
        return this;
    }
    // --------------------------------------------------------------- Getters and Setters end

    @Override
    public MimeMessage build() {
        try {
            return buildMsg();
        } catch (MessagingException e) {
            throw new MailException(e);
        }
    }

    /**
     * send
     *
     * @return message-id
     * @throws MailException Email sending exception
     */
    public String send() throws MailException {
        try {
            return doSend();
        } catch (MessagingException e) {
            if (e instanceof SendFailedException) {
                // When the address is invalid, display more detailed invalid address information
                final Address[] invalidAddresses = ((SendFailedException) e).getInvalidAddresses();
                final String msg = StrUtil.format("Invalid Addresses: {}", ArrayUtil.toString(invalidAddresses));
                throw new MailException(msg, e);
            }
            throw new MailException(e);
        }
    }

    // --------------------------------------------------------------- Private method start

    /**
     * execute send
     *
     * @return message-id
     * @throws MessagingException send exception
     */
    private String doSend() throws MessagingException {
        final MimeMessage mimeMessage = buildMsg();
        Transport.send(mimeMessage);
        return mimeMessage.getMessageID();
    }

    /**
     * build message
     *
     * @return {@link MimeMessage} message
     * @throws MessagingException abnormal message
     */
    private MimeMessage buildMsg() throws MessagingException {
        final Charset charset = this.mailAccount.getCharset();
        final MimeMessage msg = new MimeMessage(getSession());
        // sender
        final String from = this.mailAccount.getFrom();
        if (StrUtil.isEmpty(from)) {
            // If the user does not provide the sender, it will be automatically obtained from the Session
            msg.setFrom();
        } else {
            msg.setFrom(InternalMailUtil.parseFirstAddress(from, charset));
        }
        // title
        msg.setSubject(this.title, (null == charset) ? null : charset.name());
        // sending time
        msg.setSentDate(new Date());
        // Content and Attachments
        msg.setContent(buildContent(charset));
        // recipient
        msg.setRecipients(MimeMessage.RecipientType.TO, InternalMailUtil.parseAddressFromStrs(this.tos, charset));
        // cc
        if (ArrayUtil.isNotEmpty(this.ccs)) {
            msg.setRecipients(MimeMessage.RecipientType.CC, InternalMailUtil.parseAddressFromStrs(this.ccs, charset));
        }
        // CC
        if (ArrayUtil.isNotEmpty(this.bccs)) {
            msg.setRecipients(MimeMessage.RecipientType.BCC, InternalMailUtil.parseAddressFromStrs(this.bccs, charset));
        }
        // reply address (reply-to)
        if (ArrayUtil.isNotEmpty(this.reply)) {
            msg.setReplyTo(InternalMailUtil.parseAddressFromStrs(this.reply, charset));
        }

        return msg;
    }

    /**
     * Build the body of the email message
     *
     * @param charset encoding, {@code null} uses {@link MimeUtility#getDefaultJavaCharset()}
     * @return Email message body
     * @throws MessagingException abnormal message
     */
    private Multipart buildContent(Charset charset) throws MessagingException {
        final String charsetStr = null != charset ? charset.name() : MimeUtility.getDefaultJavaCharset();
        // 正文
        final MimeBodyPart body = new MimeBodyPart();
        body.setContent(content, StrUtil.format("text/{}; charset={}", isHtml ? "html" : "plain", charsetStr));
        this.multipart.addBodyPart(body);

        return this.multipart;
    }

    /**
     * Get Default Mail Session<br>
     * If it is a global singleton session, only one mail account is allowed globally, otherwise a new session will be created every time an email is sent
     *
     * @return Mail Session {@link Session}
     */
    private Session getSession() {
        final Session session = MailUtils.getSession(this.mailAccount, this.useGlobalSession);

        if (null != this.debugOutput) {
            session.setDebugOut(debugOutput);
        }

        return session;
    }
    // --------------------------------------------------------------- Private method end
}
