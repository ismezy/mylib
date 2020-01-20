package com.zy.mylib.webmvc.data.jpa.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.zy.mylib.data.jpa.DefaultHibernateModule;
import com.zy.mylib.data.jpa.ModulesObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author ASUS
 */
public class BaseJpaWebConfig extends DelegatingWebMvcConfiguration {
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("******************************Configuring swagger resource handler");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }

    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        System.out.println("*** addCorsMappings called");
        registry.addMapping("/v2/api-docs").allowedOrigins("*");
        super.addCorsMappings(registry);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        ModulesObjectMapper mapper = new ModulesObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(SerializationFeature.WRAP_EXCEPTIONS, true);
        mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
//        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
//        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH,false);
//        mapper.configure(Des)
//        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
//                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
//        mapper.setFilterProvider()
        List<Module> maperModules = new ArrayList<>();
        DefaultHibernateModule hModule = new DefaultHibernateModule();
        maperModules.add(hModule);
        // 添加page jsonview支持
        SimpleModule pageModule = new SimpleModule("jackson-page-with-jsonview", Version.unknownVersion());
        pageModule.addSerializer(Page.class, new PageSerializer());
        maperModules.add(pageModule);
        mapper.setModules(maperModules);

//        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
//        List<MediaType> stringSupports = Lists.newArrayList();
//        stringSupports.add(MediaType.valueOf("text/html;charset=UTF-8"));
//        stringSupports.add(MediaType.valueOf("text/xml;charset=UTF-8"));
//        stringSupports.add(MediaType.TEXT_XML);

//        jsonConvert.setObjectMapper(mapper);

        int xmlMapIndex = -1;
        for (int i = 0; i < converters.size(); i++) {
            HttpMessageConverter c = converters.get(i);
            if (c instanceof MappingJackson2HttpMessageConverter) {
                ((MappingJackson2HttpMessageConverter) c).setObjectMapper(mapper);
            } else if (c instanceof MappingJackson2XmlHttpMessageConverter) {
                xmlMapIndex = i;
            }
        }
        if (xmlMapIndex >= 0) {
            converters.remove(xmlMapIndex);
        }
        super.extendMessageConverters(converters);
    }

    class PageSerializer extends StdSerializer<Page> {

        public PageSerializer() {
            super(Page.class);
        }

        @Override
        public void serialize(Page value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeStartObject();
            gen.writeNumberField("number", value.getNumber());
            gen.writeNumberField("numberOfElements", value.getNumberOfElements());
            gen.writeNumberField("totalElements", value.getTotalElements());
            gen.writeNumberField("totalPages", value.getTotalPages());
            gen.writeNumberField("size", value.getSize());
            gen.writeFieldName("content");
            provider.defaultSerializeValue(value.getContent(), gen);
            gen.writeEndObject();
        }

    }
}

