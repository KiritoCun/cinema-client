package vn.udn.dut.cinema.common.doc.config.properties;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.tags.Tag;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.List;

/**
 * swagger configuration properties
 *
 * @author HoaLD
 */
@Data
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {

    /**
     * Document Basic Information
     */
    @NestedConfigurationProperty
    private InfoProperties info = new InfoProperties();

    /**
     * Extended document address
     */
    @NestedConfigurationProperty
    private ExternalDocumentation externalDocs;

    /**
     * Label
     */
    private List<Tag> tags = null;

    /**
     * path
     */
    @NestedConfigurationProperty
    private Paths paths = null;

    /**
     * components
     */
    @NestedConfigurationProperty
    private Components components = null;

    /**
     * <p>
     * The basic attribute information of the document
     * </p>
     *
     * @see io.swagger.v3.oas.models.info.Info
     *
     * In order to automatically generate configuration prompts for springboot, copy a class here
     */
    @Data
    public static class InfoProperties {

        /**
         * title
         */
        private String title = null;

        /**
         * describe
         */
        private String description = null;

        /**
         * contact information
         */
        @NestedConfigurationProperty
        private Contact contact = null;

        /**
         * license
         */
        @NestedConfigurationProperty
        private License license = null;

        /**
         * Version
         */
        private String version = null;

    }

}
