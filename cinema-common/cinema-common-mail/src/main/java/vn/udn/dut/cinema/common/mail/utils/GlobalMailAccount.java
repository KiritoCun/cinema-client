package vn.udn.dut.cinema.common.mail.utils;

import cn.hutool.core.io.IORuntimeException;

/**
 * Global mail account, depends on mail profile {@link MailAccount#MAIL_SETTING_PATHS}
 *
 * @author HoaLD
 */
public enum GlobalMailAccount {
    INSTANCE;

    private final MailAccount mailAccount;

    /**
     * structure
     */
    GlobalMailAccount() {
        mailAccount = createDefaultAccount();
    }

    /**
     * get mail account
     *
     * @return mail account
     */
    public MailAccount getAccount() {
        return this.mailAccount;
    }

    /**
     * create default account
     *
     * @return MailAccount
     */
    private MailAccount createDefaultAccount() {
        for (String mailSettingPath : MailAccount.MAIL_SETTING_PATHS) {
            try {
                return new MailAccount(mailSettingPath);
            } catch (IORuntimeException ignore) {
                //ignore
            }
        }
        return null;
    }
}
