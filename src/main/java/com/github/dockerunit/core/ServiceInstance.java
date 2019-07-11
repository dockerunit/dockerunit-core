package com.github.dockerunit.core;

import com.github.dockerunit.core.annotation.PublishPort;
import com.github.dockerunit.core.annotation.PublishPorts;
import com.github.dockerunit.core.discovery.DiscoveryProvider;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.With;

/**
 * Represents an instance of a discoverable svc and wraps a Docker container.
 *
 * @see Service
 * @see ServiceContext
 */
@Builder
@With
@EqualsAndHashCode(of = { "containerId" })
public class ServiceInstance {

    private final String containerName;
    private final String gatewayAddress;
    private final int gatewayPort;
    private final int containerPort;

    private String containerIp;
    private String containerId;
    private String statusDetails;
    private Status status;

    public static enum Status {
        /**
         * The underlying Docker container has been started but not discovered yet
         */
        STARTED,

        /**
         * The underlying Docker container has been started
         * and successfully discovered by the selected {@linkplain DiscoveryProvider}
         */
        DISCOVERED,

        /**
         * The instance startup has been aborted  due to an error
         * and the underlying Docker container is going to be deleted.
         * Common errors are port conflicts or Docker container name conflicts.
         */
        ABORTED,

        /**
         * The instance has been successfully terminated either because the
         * test has completed or because there was an error and the previous state
         * was {@literal ABORTED}
         */
        TERMINATED,

        /**
         * The underlying Docker container could not be terminated,
         * hence Dockerunit could not cleanup all the started containers.
         */
        TERMINATION_FAILED;
    }

    /**
     * @return the name that has been assigned to the underlying docker container.
     */
    public String getContainerName() {
        return containerName;
    }

    /**
     * Provides the ip or host name of the gateway to reach this svc instance.
     * By default Dockerunit uses {@literal 172.17.42.1}.
     * You can override this by using the {@literal -Ddocker.bridge.ip} system property.
     *
     * <pre>
     * mvn test -Ddocker.bridge.ip=172.17.0.1
     * </pre>
     *
     * @return the ip of the gateway to reach the svc instance.
     */
    public String getGatewayAddress() {
        return gatewayAddress;
    }

    /**
     * Provides the gateway level port this instance is listening on.
     * If you are running a single instance and you are using {@linkplain PublishPort},
     * then this returns the value of the {@literal host} property.
     * If you are running multiple instances, then you need to use {@linkplain PublishPorts}
     * otherwise Docker will detect a port conflict and fail to start the corresponding container.
     * If neither {@linkplain PublishPort} nor {@linkplain PublishPorts} has been used, this returns 0.
     *
     *
     * @return the gateway level port this instance is listening on.
     */
    public int getGatewayPort() {
        return gatewayPort;
    }

    /**
     * @return the containerId Docker has assigned to the underlying container.
     */
    public String getContainerId() {
        return containerId;
    }

    /**
     *
     * @return the ip of the underlying container
     */
    public String getContainerIp() {
        return containerIp;
    }

    /**
     *
     * @return the exposed port of the underlying container. If the container exposes multiple ports,
     * this returns the one that is used by the health-check definition.
     */
    public int getContainerPort() {
        return containerPort;
    }

    /**
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @return the statusDetails
     */
    public String getStatusDetails() {
        return statusDetails;
    }

    /**
     * Checks whether the instance is in the specified status.
     *
     * @param status the specified status
     * @return true if the instance is in the specified status, false otherwise.
     */
    public boolean hasStatus(Status status) {
        return this.status.equals(status);
    }

}
