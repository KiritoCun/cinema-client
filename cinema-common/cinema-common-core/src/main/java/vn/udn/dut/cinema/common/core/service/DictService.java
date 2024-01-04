package vn.udn.dut.cinema.common.core.service;

/**
 * General Dictionary Service
 *
 * @author HoaLD
 */
public interface DictService {

    /**
     * delimiter
     */
    String SEPARATOR = ",";

    /**
     * Get dictionary labels based on dictionary type and dictionary value
     *
     * @param dictType  dictionary type
     * @param dictValue dictionary value
     * @return dictionary tag
     */
    default String getDictLabel(String dictType, String dictValue) {
        return getDictLabel(dictType, dictValue, SEPARATOR);
    }

    /**
     * Get dictionary value based on dictionary type and dictionary label
     *
     * @param dictType  dictionary type
     * @param dictLabel dictionary tag
     * @return dictionary value
     */
    default String getDictValue(String dictType, String dictLabel) {
        return getDictValue(dictType, dictLabel, SEPARATOR);
    }

    /**
     * Get dictionary labels based on dictionary type and dictionary value
     *
     * @param dictType  dictionary type
     * @param dictValue dictionary value
     * @param separator delimiter
     * @return dictionary tag
     */
    String getDictLabel(String dictType, String dictValue, String separator);

    /**
     * Get dictionary value based on dictionary type and dictionary label
     *
     * @param dictType  dictionary type
     * @param dictLabel dictionary tag
     * @param separator delimiter
     * @return dictionary value
     */
    String getDictValue(String dictType, String dictLabel, String separator);

}
