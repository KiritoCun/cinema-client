package vn.udn.dut.cinema.common.core.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.AntPathMatcher;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * string tools
 *
 * @author HoaLD
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static final String SEPARATOR = ",";

    /**
     * get parameter is not null
     *
     * @param str defaultValue The value to be judged
     * @return value return value
     */
    public static String blankToDefault(String str, String defaultValue) {
        return StrUtil.blankToDefault(str, defaultValue);
    }

    /**
     * * Check if a string is empty
     *
     * @param str String
     * @return true: empty false: not empty
     */
    public static boolean isEmpty(String str) {
        return StrUtil.isEmpty(str);
    }

    /**
     * * Check if a string is non-empty
     *
     * @param str String
     * @return true: non-empty string false: empty string
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * remove spaces
     */
    public static String trim(String str) {
        return StrUtil.trim(str);
    }

    /**
     * intercept string
     *
     * @param str   string
     * @param start start
     * @return result
     */
    public static String substring(final String str, int start) {
        return substring(str, start, str.length());
    }

    /**
     * intercept string
     *
     * @param str   string
     * @param start start
     * @param end   Finish
     * @return result
     */
    public static String substring(final String str, int start, int end) {
        return StrUtil.sub(str, start, end);
    }

    /**
     * Formatted text, {} means placeholder<br>
     * This method simply replaces the placeholders {} with parameters in order<br>
     * If you want to output {} use \\escape {, if you want to output \ before {} use double escape character \\\\<br>
     * Example: <br>
     * Usually use: format("this is {} for {}", "a", "b") -> this is a for b<br>
     * Escaping {}: format("this is \\{} for {}", "a", "b") -> this is {} for a<br>
     * Escape \: format("this is \\\\{} for {}", "a", "b") -> this is \a for b<br>
     *
     * @param template Text template, the replaced part is represented by {}
     * @param params   parameter value
     * @return formatted text
     */
    public static String format(String template, Object... params) {
        return StrUtil.format(template, params);
    }

    /**
     * Whether it starts with http(s)://
     *
     * @param link Link
     * @return result
     */
    public static boolean ishttp(String link) {
        return Validator.isUrl(link);
    }

    /**
     * String to set
     *
     * @param str string
     * @param sep delimiter
     * @return set collection
     */
    public static Set<String> str2Set(String str, String sep) {
        return new HashSet<>(str2List(str, sep, true, false));
    }

    /**
     * string to list
     *
     * @param str         string
     * @param sep         delimiter
     * @param filterBlank filter pure blank
     * @param trim        remove leading and trailing whitespace
     * @return list collection
     */
    public static List<String> str2List(String str, String sep, boolean filterBlank, boolean trim) {
        List<String> list = new ArrayList<>();
        if (isEmpty(str)) {
            return list;
        }

        // filter blank string
        if (filterBlank && isBlank(str)) {
            return list;
        }
        String[] split = str.split(sep);
        for (String string : split) {
            if (filterBlank && isBlank(string)) {
                continue;
            }
            if (trim) {
                string = trim(string);
            }
            list.add(string);
        }

        return list;
    }

    /**
     * Finds whether the specified string contains any string in the specified string list while ignoring case
     *
     * @param cs                  specified string
     * @param searchCharSequences array of strings to check
     * @return Does it contain any string
     */
    public static boolean containsAnyIgnoreCase(CharSequence cs, CharSequence... searchCharSequences) {
        return StrUtil.containsAnyIgnoreCase(cs, searchCharSequences);
    }

    /**
     * CamelCase to underscore naming
     */
    public static String toUnderScoreCase(String str) {
        return StrUtil.toUnderlineCase(str);
    }

    /**
     * whether to contain a string
     *
     * @param str  authentication string
     * @param strs group of strings
     * @return contains returns true
     */
    public static boolean inStringIgnoreCase(String str, String... strs) {
        return StrUtil.equalsAnyIgnoreCase(str, strs);
    }

    /**
     * Convert underscore-capitalized strings to camel case. Returns an empty string if the underscore-capitalized string before conversion is empty. For example: HELLO_WORLD->HelloWorld
     *
     * @param name A string named with an underscore and capitalized before conversion
     * @return Converted camel-cased string
     */
    public static String convertToCamelCase(String name) {
        return StrUtil.upperFirst(StrUtil.toCamelCase(name));
    }

    /**
     * Camel case naming method For example: user_name->userName
     */
    public static String toCamelCase(String s) {
        return StrUtil.toCamelCase(s);
    }

    /**
     * Finds whether the specified string matches any string in the specified string list
     *
     * @param str  specified string
     * @param strs array of strings to check
     * @return Does it match
     */
    public static boolean matches(String str, List<String> strs) {
        if (isEmpty(str) || CollUtil.isEmpty(strs)) {
            return false;
        }
        for (String pattern : strs) {
            if (isMatch(pattern, str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determine whether the url matches the rule configuration:
     * ? represents a single character;
     * * Indicates any character string within a layer path, and cannot cross layers;
     * ** Indicates any layer path;
     *
     * @param pattern matching rules
     * @param url     url to match
     */
    public static boolean isMatch(String pattern, String url) {
        AntPathMatcher matcher = new AntPathMatcher();
        return matcher.match(pattern, url);
    }

    /**
     * The number is padded with 0 on the left to make it reach the specified length. Note that if the length is greater than size after the number is converted to a string, only the last size characters will be kept.
     *
     * @param num  digital object
     * @param size string specified length
     * @return Returns the string format of the number, the string is the specified length.
     */
    public static String padl(final Number num, final int size) {
        return padl(num.toString(), size, '0');
    }

    /**
     * String left padding. If the length of the original string s is greater than size, only the last size characters are kept.
     *
     * @param s    raw string
     * @param size string specified length
     * @param c    characters for padding
     * @return Returns a string of the specified length, which is obtained by left padding or intercepting the original string.
     */
    public static String padl(final String s, final int size, final char c) {
        final StringBuilder sb = new StringBuilder(size);
        if (s != null) {
            final int len = s.length();
            if (s.length() <= size) {
                sb.append(String.valueOf(c).repeat(size - len));
                sb.append(s);
            } else {
                return s.substring(len - size, len);
            }
        } else {
            sb.append(String.valueOf(c).repeat(Math.max(0, size)));
        }
        return sb.toString();
    }

    /**
     * Split the string (the delimiter defaults to a comma)
     *
     * @param str the split string
     * @return Split data list
     */
    public static List<String> splitList(String str) {
        return splitTo(str, Convert::toStr);
    }

    /**
     * split string
     *
     * @param str       the split string
     * @param separator delimiter
     * @return Split data list
     */
    public static List<String> splitList(String str, String separator) {
        return splitTo(str, separator, Convert::toStr);
    }

    /**
     * Segment string custom conversion (delimiter defaults to comma)
     *
     * @param str    the split string
     * @param mapper custom conversion
     * @return Split data list
     */
    public static <T> List<T> splitTo(String str, Function<? super Object, T> mapper) {
        return splitTo(str, SEPARATOR, mapper);
    }

    /**
     * Segment string custom conversion
     *
     * @param str       the split string
     * @param separator delimiter
     * @param mapper    custom conversion
     * @return Split data list
     */
    public static <T> List<T> splitTo(String str, String separator, Function<? super Object, T> mapper) {
        if (isBlank(str)) {
            return new ArrayList<>(0);
        }
        return StrUtil.split(str, separator)
            .stream()
            .filter(Objects::nonNull)
            .map(mapper)
            .collect(Collectors.toList());
    }

}
