package vn.udn.dut.cinema.common.sms.core;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;

import lombok.SneakyThrows;
import vn.udn.dut.cinema.common.core.utils.StringUtils;
import vn.udn.dut.cinema.common.json.utils.JsonUtils;
import vn.udn.dut.cinema.common.sms.config.properties.SmsProperties;
import vn.udn.dut.cinema.common.sms.entity.SmsResult;
import vn.udn.dut.cinema.common.sms.exception.SmsException;

import java.util.Map;

/**
 * Aliyun SMS template
 *
 * @author HoaLD
 * @version 4.2.0
 */
public class AliyunSmsTemplate implements SmsTemplate {

    private SmsProperties properties;

    private Client client;

    @SneakyThrows(Exception.class)
    public AliyunSmsTemplate(SmsProperties smsProperties) {
        this.properties = smsProperties;
        Config config = new Config()
            // Your AccessKey ID
            .setAccessKeyId(smsProperties.getAccessKeyId())
            // Your AccessKey Secret
            .setAccessKeySecret(smsProperties.getAccessKeySecret())
            // domain name visited
            .setEndpoint(smsProperties.getEndpoint());
        this.client = new Client(config);
    }

    @Override
    public SmsResult send(String phones, String templateId, Map<String, String> param) {
        if (StringUtils.isBlank(phones)) {
            throw new SmsException("Mobile phone number cannot be empty");
        }
        if (StringUtils.isBlank(templateId)) {
            throw new SmsException("Template ID cannot be empty");
        }
        SendSmsRequest req = new SendSmsRequest()
            .setPhoneNumbers(phones)
            .setSignName(properties.getSignName())
            .setTemplateCode(templateId)
            .setTemplateParam(JsonUtils.toJsonString(param));
        try {
            SendSmsResponse resp = client.sendSms(req);
            return SmsResult.builder()
                .isSuccess("OK".equals(resp.getBody().getCode()))
                .message(resp.getBody().getMessage())
                .response(JsonUtils.toJsonString(resp))
                .build();
        } catch (Exception e) {
            throw new SmsException(e.getMessage());
        }
    }

}
