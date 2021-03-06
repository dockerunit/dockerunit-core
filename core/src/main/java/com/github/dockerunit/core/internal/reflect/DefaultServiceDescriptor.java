package com.github.dockerunit.core.internal.reflect;

import com.github.dockerunit.core.annotation.Svc;
import com.github.dockerunit.core.internal.ServiceDescriptor;
import lombok.Builder;
import lombok.Getter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

@Getter
@Builder
public class DefaultServiceDescriptor implements ServiceDescriptor {

    private Svc svcDefinition;
    private List<? extends Annotation> options;
    private Method customisationHook;
    private int replicas;
    private int priority;
    private String containerName;
    private Object instance;

    @Override
    public String getSvcName() {
        return svcDefinition.value().length() > 0 ? svcDefinition.value() : svcDefinition.name();
    }
}
