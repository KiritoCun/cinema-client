package vn.udn.dut.cinema.common.translation.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import vn.udn.dut.cinema.common.translation.annotation.TranslationType;
import vn.udn.dut.cinema.common.translation.core.TranslationInterface;
import vn.udn.dut.cinema.common.translation.core.handler.TranslationBeanSerializerModifier;
import vn.udn.dut.cinema.common.translation.core.handler.TranslationHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Translation module configuration class
 *
 * @author HoaLD
 */
@Slf4j
@AutoConfiguration
public class TranslationConfig {

    @Autowired
    private List<TranslationInterface<?>> list;

    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        Map<String, TranslationInterface<?>> map = new HashMap<>(list.size());
        for (TranslationInterface<?> trans : list) {
            if (trans.getClass().isAnnotationPresent(TranslationType.class)) {
                TranslationType annotation = trans.getClass().getAnnotation(TranslationType.class);
                map.put(annotation.type(), trans);
            } else {
                log.warn(trans.getClass().getName() + " translation implementation class is not annotated with the TranslationType annotation!");
            }
        }
        TranslationHandler.TRANSLATION_MAPPER.putAll(map);
        // Set Bean Serialization Modifier
        objectMapper.setSerializerFactory(
            objectMapper.getSerializerFactory()
                .withSerializerModifier(new TranslationBeanSerializerModifier()));
    }

}
