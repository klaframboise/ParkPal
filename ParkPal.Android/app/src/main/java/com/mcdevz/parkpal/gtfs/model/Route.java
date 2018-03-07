/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.341.10e47e87 modeling language!*/

package com.mcdevz.parkpal.gtfs.model;
import java.util.*;

// line 26 "../../../../../../../../ump/tmp356049/model.ump"
// line 186 "../../../../../../../../ump/tmp356049/model.ump"
public class Route
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Route Attributes
  private Agency agency;
  private String id;
  private String shortName;
  private String longName;
  private String desc;
  private int type;
  private String url;
  private String color;
  private String textColor;
  private String sortOrder;

  //Route Associations
  private List<Trip> trips;
  private List<FareRule> fareRules;

  //Helper Variables
  private int cachedHashCode;
  private boolean canSetAgency;
  private boolean canSetId;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Route(String aId, String aShortName, String aLongName, int aType)
  {
    cachedHashCode = -1;
    canSetAgency = true;
    canSetId = true;
    id = aId;
    shortName = aShortName;
    longName = aLongName;
    desc = null;
    type = aType;
    url = null;
    color = null;
    textColor = null;
    sortOrder = null;
    trips = new ArrayList<Trip>();
    fareRules = new ArrayList<FareRule>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setAgency(Agency aAgency)
  {
    boolean wasSet = false;
    if (!canSetAgency) { return false; }
    agency = aAgency;
    wasSet = true;
    return wasSet;
  }

  public boolean setId(String aId)
  {
    boolean wasSet = false;
    if (!canSetId) { return false; }
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public boolean setShortName(String aShortName)
  {
    boolean wasSet = false;
    shortName = aShortName;
    wasSet = true;
    return wasSet;
  }

  public boolean setLongName(String aLongName)
  {
    boolean wasSet = false;
    longName = aLongName;
    wasSet = true;
    return wasSet;
  }

  public boolean setDesc(String aDesc)
  {
    boolean wasSet = false;
    desc = aDesc;
    wasSet = true;
    return wasSet;
  }

  public boolean setType(int aType)
  {
    boolean wasSet = false;
    type = aType;
    wasSet = true;
    return wasSet;
  }

  public boolean setUrl(String aUrl)
  {
    boolean wasSet = false;
    url = aUrl;
    wasSet = true;
    return wasSet;
  }

  public boolean setColor(String aColor)
  {
    boolean wasSet = false;
    color = aColor;
    wasSet = true;
    return wasSet;
  }

  public boolean setTextColor(String aTextColor)
  {
    boolean wasSet = false;
    textColor = aTextColor;
    wasSet = true;
    return wasSet;
  }

  public boolean setSortOrder(String aSortOrder)
  {
    boolean wasSet = false;
    sortOrder = aSortOrder;
    wasSet = true;
    return wasSet;
  }

  public Agency getAgency()
  {
    return agency;
  }

  public String getId()
  {
    return id;
  }

  public String getShortName()
  {
    return shortName;
  }

  public String getLongName()
  {
    return longName;
  }

  public String getDesc()
  {
    return desc;
  }

  public int getType()
  {
    return type;
  }

  public String getUrl()
  {
    return url;
  }

  public String getColor()
  {
    return color;
  }

  public String getTextColor()
  {
    return textColor;
  }

  public String getSortOrder()
  {
    return sortOrder;
  }
  /* Code from template association_GetMany */
  public Trip getTrip(int index)
  {
    Trip aTrip = trips.get(index);
    return aTrip;
  }

  public List<Trip> getTrips()
  {
    List<Trip> newTrips = Collections.unmodifiableList(trips);
    return newTrips;
  }

  public int numberOfTrips()
  {
    int number = trips.size();
    return number;
  }

  public boolean hasTrips()
  {
    boolean has = trips.size() > 0;
    return has;
  }

  public int indexOfTrip(Trip aTrip)
  {
    int index = trips.indexOf(aTrip);
    return index;
  }
  /* Code from template association_GetMany */
  public FareRule getFareRule(int index)
  {
    FareRule aFareRule = fareRules.get(index);
    return aFareRule;
  }

  public List<FareRule> getFareRules()
  {
    List<FareRule> newFareRules = Collections.unmodifiableList(fareRules);
    return newFareRules;
  }

  public int numberOfFareRules()
  {
    int number = fareRules.size();
    return number;
  }

  public boolean hasFareRules()
  {
    boolean has = fareRules.size() > 0;
    return has;
  }

  public int indexOfFareRule(FareRule aFareRule)
  {
    int index = fareRules.indexOf(aFareRule);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTrips()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addTrip(Trip aTrip)
  {
    boolean wasAdded = false;
    if (trips.contains(aTrip)) { return false; }
    trips.add(aTrip);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTrip(Trip aTrip)
  {
    boolean wasRemoved = false;
    if (trips.contains(aTrip))
    {
      trips.remove(aTrip);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTripAt(Trip aTrip, int index)
  {  
    boolean wasAdded = false;
    if(addTrip(aTrip))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTrips()) { index = numberOfTrips() - 1; }
      trips.remove(aTrip);
      trips.add(index, aTrip);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTripAt(Trip aTrip, int index)
  {
    boolean wasAdded = false;
    if(trips.contains(aTrip))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTrips()) { index = numberOfTrips() - 1; }
      trips.remove(aTrip);
      trips.add(index, aTrip);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTripAt(aTrip, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfFareRules()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addFareRule(FareRule aFareRule)
  {
    boolean wasAdded = false;
    if (fareRules.contains(aFareRule)) { return false; }
    if (fareRules.contains(aFareRule)) { return false; }
    fareRules.add(aFareRule);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeFareRule(FareRule aFareRule)
  {
    boolean wasRemoved = false;
    if (fareRules.contains(aFareRule))
    {
      fareRules.remove(aFareRule);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addFareRuleAt(FareRule aFareRule, int index)
  {  
    boolean wasAdded = false;
    if(addFareRule(aFareRule))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfFareRules()) { index = numberOfFareRules() - 1; }
      fareRules.remove(aFareRule);
      fareRules.add(index, aFareRule);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveFareRuleAt(FareRule aFareRule, int index)
  {
    boolean wasAdded = false;
    if(fareRules.contains(aFareRule))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfFareRules()) { index = numberOfFareRules() - 1; }
      fareRules.remove(aFareRule);
      fareRules.add(index, aFareRule);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addFareRuleAt(aFareRule, index);
    }
    return wasAdded;
  }

  public boolean equals(Object obj)
  {
    if (obj == null) { return false; }
    if (!getClass().equals(obj.getClass())) { return false; }

    Route compareTo = (Route)obj;
  
    if (getAgency() == null && compareTo.getAgency() != null)
    {
      return false;
    }
    else if (getAgency() != null && !getAgency().equals(compareTo.getAgency()))
    {
      return false;
    }

    if (getId() == null && compareTo.getId() != null)
    {
      return false;
    }
    else if (getId() != null && !getId().equals(compareTo.getId()))
    {
      return false;
    }

    return true;
  }

  public int hashCode()
  {
    if (cachedHashCode != -1)
    {
      return cachedHashCode;
    }
    cachedHashCode = 17;
    if (getAgency() != null)
    {
      cachedHashCode = cachedHashCode * 23 + getAgency().hashCode();
    }
    else
    {
      cachedHashCode = cachedHashCode * 23;
    }

    if (getId() != null)
    {
      cachedHashCode = cachedHashCode * 23 + getId().hashCode();
    }
    else
    {
      cachedHashCode = cachedHashCode * 23;
    }

    canSetAgency = false;
    canSetId = false;
    return cachedHashCode;
  }

  public void delete()
  {
    trips.clear();
    fareRules.clear();
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "shortName" + ":" + getShortName()+ "," +
            "longName" + ":" + getLongName()+ "," +
            "desc" + ":" + getDesc()+ "," +
            "type" + ":" + getType()+ "," +
            "url" + ":" + getUrl()+ "," +
            "color" + ":" + getColor()+ "," +
            "textColor" + ":" + getTextColor()+ "," +
            "sortOrder" + ":" + getSortOrder()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "agency" + "=" + (getAgency() != null ? !getAgency().equals(this)  ? getAgency().toString().replaceAll("  ","    ") : "this" : "null");
  }
}