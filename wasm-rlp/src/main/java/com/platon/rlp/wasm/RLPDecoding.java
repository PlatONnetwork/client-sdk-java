package com.platon.rlp.wasm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@SuppressWarnings("rawtypes")
public @interface RLPDecoding {
	Class<? extends RLPDecoder> value() default RLPDecoder.None.class;

	Class<?> as() default Void.class;
}
