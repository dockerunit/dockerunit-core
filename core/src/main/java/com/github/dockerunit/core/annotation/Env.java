package com.github.dockerunit.core.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.github.dockerunit.core.annotation.impl.EnvExtensionInterpreter;

/**
 * Allows declaration of environment variables for your svc.
 * Declared values will be passed to every instance of your svc.
 * <p>
 * The example below set variables FOO and BAR to values foo and bar respectively.
 *
 * <pre>
 * 	&#64;Env({"FOO=foo", "BAR=bar"})
 * </pre>
 */
@Retention(RUNTIME)
@Target(TYPE)
@ExtensionMarker(EnvExtensionInterpreter.class)
public @interface Env {

    String[] value();

}
