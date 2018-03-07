/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.341.10e47e87 modeling language!*/

package com.mcdevz.parkpal.gtfs.model;

// line 108 "../../../../../../../../ump/tmp356049/model.ump"
// line 253 "../../../../../../../../ump/tmp356049/model.ump"
public class FareRule
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //FareRule Attributes
  private FareAttribute attribute;
  private String originId;
  private String destinationId;
  private String containsId;

  //FareRule Associations
  private Route route;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public FareRule(FareAttribute aAttribute)
  {
    attribute = aAttribute;
    originId = null;
    destinationId = null;
    containsId = null;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setAttribute(FareAttribute aAttribute)
  {
    boolean wasSet = false;
    attribute = aAttribute;
    wasSet = true;
    return wasSet;
  }

  public boolean setOriginId(String aOriginId)
  {
    boolean wasSet = false;
    originId = aOriginId;
    wasSet = true;
    return wasSet;
  }

  public boolean setDestinationId(String aDestinationId)
  {
    boolean wasSet = false;
    destinationId = aDestinationId;
    wasSet = true;
    return wasSet;
  }

  public boolean setContainsId(String aContainsId)
  {
    boolean wasSet = false;
    containsId = aContainsId;
    wasSet = true;
    return wasSet;
  }

  public FareAttribute getAttribute()
  {
    return attribute;
  }

  public String getOriginId()
  {
    return originId;
  }

  public String getDestinationId()
  {
    return destinationId;
  }

  public String getContainsId()
  {
    return containsId;
  }
  /* Code from template association_GetOne */
  public Route getRoute()
  {
    return route;
  }

  public boolean hasRoute()
  {
    boolean has = route != null;
    return has;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setRoute(Route aNewRoute)
  {
    boolean wasSet = false;
    route = aNewRoute;
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    route = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "originId" + ":" + getOriginId()+ "," +
            "destinationId" + ":" + getDestinationId()+ "," +
            "containsId" + ":" + getContainsId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "attribute" + "=" + (getAttribute() != null ? !getAttribute().equals(this)  ? getAttribute().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "route = "+(getRoute()!=null?Integer.toHexString(System.identityHashCode(getRoute())):"null");
  }
}