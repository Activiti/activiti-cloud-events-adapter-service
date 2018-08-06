package com.alfresco.activitieventsconsolidator.from;

public class CamelRouteProperties {

    private String destinationName;
    private String fromRoute;
    private String toRoute;

    public String getDestinationName()
    {
        return destinationName;
    }

    public void setDestinationName(String destinationName)
    {
        this.destinationName = destinationName;
    }

    public String getFromRoute()
    {
        return fromRoute;
    }

    public void setFromRoute(String fromRoute)
    {
        this.fromRoute = fromRoute;
    }
    public String getToRoute()
    {
        return toRoute;
    }

    public void setToRoute(String toRoute)
    {
        this.toRoute = toRoute;
    }

}
