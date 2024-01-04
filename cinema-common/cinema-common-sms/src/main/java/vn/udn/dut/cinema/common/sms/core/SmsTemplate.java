package vn.udn.dut.cinema.common.sms.core;

import java.util.Map;

import vn.udn.dut.cinema.common.sms.entity.SmsResult;

/**
 * SMS template
 *
 * @author HoaLD
 * @version 4.2.0
 */
public interface SmsTemplate {

    /**
     * send messages
     *
     * @param phones     Phone number (separated by multiple commas)
     * @param templateId template id
     * @param param      Template corresponding parameters
     *                   Ali needs to use the corresponding content of the template variable name, for example: code=1234
     *                   Tencent needs to use the corresponding content of the template variable order For example: 1=1234, 1 is the first parameter in the template
     */
    SmsResult send(String phones, String templateId, Map<String, String> param);

}
