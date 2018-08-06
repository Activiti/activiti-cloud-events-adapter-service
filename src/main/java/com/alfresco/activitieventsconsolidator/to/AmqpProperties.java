/*
 * Copyright 2005-2018 Alfresco Software, Ltd. All rights reserved.
 *
 * License rights for this program may be obtained from Alfresco Software, Ltd.
 * pursuant to a written agreement and any use of this program without such an
 * agreement is prohibited.
 */

package com.alfresco.activitieventsconsolidator.to;


import com.alfresco.activitieventsconsolidator.from.CamelRouteProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * AMQP configuration properties.
 *
 * @author Jamal Kaabi-Mofrad
 */
@ConfigurationProperties(prefix = "messaging.to.activemq")
public class AmqpProperties
{
    protected final CamelRouteProperties camelRoute = new CamelRouteProperties();
    protected String host;
    protected int port;
    protected String username;
    protected String password;
    protected String url;

    public CamelRouteProperties getCamelRoute()
    {
        return camelRoute;
    }

    public String getHost()
    {
        return host;
    }

    public void setHost(String host)
    {
        this.host = host;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
