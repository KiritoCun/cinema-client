package vn.udn.dut.cinema.common.translation.core.handler;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

import java.util.List;

/**
 * Bean serialization modifier solves the problem that Null is handled separately
 *
 * @author HoaLD
 */
public class TranslationBeanSerializerModifier extends BeanSerializerModifier {

    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc,
                                                     List<BeanPropertyWriter> beanProperties) {
        for (BeanPropertyWriter writer : beanProperties) {
            // If the serializer is TranslationHandler, hand over the Null value to him as well
            if (writer.getSerializer() instanceof TranslationHandler serializer) {
                writer.assignNullSerializer(serializer);
            }
        }
        return beanProperties;
    }

}
