package vn.udn.dut.cinema.common.translation.core.handler;

import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

import vn.udn.dut.cinema.common.core.utils.StringUtils;
import vn.udn.dut.cinema.common.core.utils.reflect.ReflectUtils;
import vn.udn.dut.cinema.common.translation.annotation.Translation;
import vn.udn.dut.cinema.common.translation.core.TranslationInterface;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * translation processor
 *
 * @author HoaLD
 */
public class TranslationHandler extends JsonSerializer<Object> implements ContextualSerializer {

    /**
     * Global translation implements class mapper
     */
    public static final Map<String, TranslationInterface<?>> TRANSLATION_MAPPER = new ConcurrentHashMap<>();

    private Translation translation;

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        TranslationInterface<?> trans = TRANSLATION_MAPPER.get(translation.type());
        if (ObjectUtil.isNotNull(trans)) {
            // If the mapped field is not empty, take the value of the mapped field
            if (StringUtils.isNotBlank(translation.mapper())) {
                value = ReflectUtils.invokeGetter(gen.getCurrentValue(), translation.mapper());
            }
            // If null write it out directly
            if (ObjectUtil.isNull(value)) {
                gen.writeNull();
                return;
            }
            //Object result = trans.translation(value, translation.other());
            //gen.writeObject(result);
            gen.writeObject(value);
        } else {
            gen.writeObject(value);
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        Translation translation = property.getAnnotation(Translation.class);
        if (Objects.nonNull(translation)) {
            this.translation = translation;
            return this;
        }
        return prov.findValueSerializer(property.getType(), property);
    }
}
