package vn.udn.dut.cinema.common.web.utils;

import cn.hutool.captcha.generator.CodeGenerator;
import cn.hutool.core.math.Calculator;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.RandomUtil;
import vn.udn.dut.cinema.common.core.utils.StringUtils;

import java.io.Serial;

/**
 * unsigned computation generator
 *
 * @author HoaLD
 */
public class UnsignedMathGenerator implements CodeGenerator {

    @Serial
    private static final long serialVersionUID = -5514819971774091076L;

    private static final String OPERATORS = "+-*";

    /**
     * Participate in calculating the maximum length of numbers
     */
    private final int numberLength;

    /**
     * structure
     */
    public UnsignedMathGenerator() {
        this(2);
    }

    /**
     * structure
     *
     * @param numberLength Participate in the calculation of the maximum number of digits
     */
    public UnsignedMathGenerator(int numberLength) {
        this.numberLength = numberLength;
    }

    @Override
    public String generate() {
        final int limit = getLimit();
        int a = RandomUtil.randomInt(limit);
        int b = RandomUtil.randomInt(limit);
        String max = Integer.toString(Math.max(a,b));
        String min = Integer.toString(Math.min(a,b));
        max = StringUtils.rightPad(max, this.numberLength, CharUtil.SPACE);
        min = StringUtils.rightPad(min, this.numberLength, CharUtil.SPACE);

        return max + RandomUtil.randomChar(OPERATORS) + min + '=';
    }

    @Override
    public boolean verify(String code, String userInputCode) {
        int result;
        try {
            result = Integer.parseInt(userInputCode);
        } catch (NumberFormatException e) {
            // User enters non-numeric
            return false;
        }

        final int calculateResult = (int) Calculator.conversion(code);
        return result == calculateResult;
    }

    /**
     * Get the verification code length
     *
     * @return Verification code length
     */
    public int getLength() {
        return this.numberLength * 2 + 2;
    }

    /**
     * According to the length, get the maximum value of the number involved in the calculation
     *
     * @return maximum value
     */
    private int getLimit() {
        return Integer.parseInt("1" + StringUtils.repeat('0', this.numberLength));
    }
}
