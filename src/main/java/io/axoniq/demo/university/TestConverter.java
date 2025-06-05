package io.axoniq.demo.university;

import org.axonframework.serialization.Converter;

import java.nio.charset.StandardCharsets;

public class TestConverter implements Converter {

    @Override
    public boolean canConvert(Class<?> sourceType, Class<?> targetType) {
        return byte[].class.isAssignableFrom(targetType) || String.class.isAssignableFrom(sourceType);
    }

    @Override
    public <T> T convert(Object original, Class<?> sourceType, Class<T> targetType) {
        if (byte[].class.isAssignableFrom(targetType)) {
            return (T) original.toString().getBytes(StandardCharsets.UTF_8);
        } else if (String.class.isAssignableFrom(targetType)) {
            //noinspection unchecked
            return (T) original.toString();
        } else {
            throw new IllegalArgumentException("Only supports byte[] and String.");
        }
    }
}