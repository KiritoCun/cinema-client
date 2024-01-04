package vn.udn.dut.cinema.common.mail.utils;

import cn.hutool.core.util.ArrayUtil;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeUtility;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Mail Internal Utilities
 *
 * @author HoaLD
 * @since 3.2.3
 */
public class InternalMailUtil {

    /**
     * Convert multiple string email addresses to a list of {@link InternetAddress}<br>
     * A single string address can be a string combining multiple addresses
     *
     * @param addrStrs address array
     * @param charset  Encoding (mainly used for encoding Chinese usernames)
     * @return address array
     * @since 4.0.3
     */
    public static InternetAddress[] parseAddressFromStrs(String[] addrStrs, Charset charset) {
        final List<InternetAddress> resultList = new ArrayList<>(addrStrs.length);
        InternetAddress[] addrs;
        for (String addrStr : addrStrs) {
            addrs = parseAddress(addrStr, charset);
            if (ArrayUtil.isNotEmpty(addrs)) {
                Collections.addAll(resultList, addrs);
            }
        }
        return resultList.toArray(new InternetAddress[0]);
    }

    /**
     * resolve the first address
     *
     * @param address address string
     * @param charset encoding, {@code null} indicates the encoding defined using the system property or the system encoding
     * @return address list
     */
    public static InternetAddress parseFirstAddress(String address, Charset charset) {
        final InternetAddress[] internetAddresses = parseAddress(address, charset);
        if (ArrayUtil.isEmpty(internetAddresses)) {
            try {
                return new InternetAddress(address);
            } catch (AddressException e) {
                throw new MailException(e);
            }
        }
        return internetAddresses[0];
    }

    /**
     * Parse an address string into multiple addresses<br>
     * Use " ", ",", ";" to separate addresses
     *
     * @param address address string
     * @param charset encoding, {@code null} indicates the encoding defined using the system property or the system encoding
     * @return address list
     */
    public static InternetAddress[] parseAddress(String address, Charset charset) {
        InternetAddress[] addresses;
        try {
            addresses = InternetAddress.parse(address);
        } catch (AddressException e) {
            throw new MailException(e);
        }
        // Encode username
        if (ArrayUtil.isNotEmpty(addresses)) {
            final String charsetStr = null == charset ? null : charset.name();
            for (InternetAddress internetAddress : addresses) {
                try {
                    internetAddress.setPersonal(internetAddress.getPersonal(), charsetStr);
                } catch (UnsupportedEncodingException e) {
                    throw new MailException(e);
                }
            }
        }

        return addresses;
    }

    /**
     * Encode Chinese characters<br>
     * If the encoding fails, return the original string
     *
     * @param text    encoded text
     * @param charset coding
     * @return encoded result
     */
    public static String encodeText(String text, Charset charset) {
        try {
            return MimeUtility.encodeText(text, charset.name(), null);
        } catch (UnsupportedEncodingException e) {
            // ignore
        }
        return text;
    }
}
