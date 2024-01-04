package vn.udn.dut.cinema.common.translation.core;

import vn.udn.dut.cinema.common.translation.annotation.TranslationType;

/**
 * Translation interface (the implementation class needs to be marked with {@link TranslationType} annotation to indicate the translation type)
 *
 * @author HoaLD
 */
public interface TranslationInterface<T> {

    /**
     * translate
     *
     * @param key   the key to be translated (not null)
     * @param other Other parameters
     * @return Returns the value corresponding to the key
     */
    T translation(Object key, String other);
}
