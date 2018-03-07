/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.341.10e47e87 modeling language!*/

package com.mcdevz.parkpal.gtfs.model;

// line 76 "../../../../../../../../ump/tmp356049/model.ump"
// line 207 "../../../../../../../../ump/tmp356049/model.ump"
public class Stop
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Stop Attributes
  private String id;
  private String code;
  private String name;
  private String desc;
  private double lat;
  private double lon;
  private String zoneId;
  private String url;
  private int locationType;
  private String parentStation;
  private String timezone;
  private int wheelchairBoarding;

  //Helper Variables
  private int cachedHashCode;
  private boolean canSetId;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Stop(String aId, String aName, double aLat, double aLon)
  {
    cachedHashCode = -1;
    canSetId = true;
    id = aId;
    code = null;
    name = aName;
    desc = null;
    lat = aLat;
    lon = aLon;
    zoneId = null;
    url = null;
    resetLocationType();
    parentStation = null;
    timezone = null;
    resetWheelchairBoarding();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(String aId)
  {
    boolean wasSet = false;
    if (!canSetId) { return false; }
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public boolean setCode(String aCode)
  {
    boolean wasSet = false;
    code = aCode;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
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

  public boolean setLat(double aLat)
  {
    boolean wasSet = false;
    lat = aLat;
    wasSet = true;
    return wasSet;
  }

  public boolean setLon(double aLon)
  {
    boolean wasSet = false;
    lon = aLon;
    wasSet = true;
    return wasSet;
  }

  public boolean setZoneId(String aZoneId)
  {
    boolean wasSet = false;
    zoneId = aZoneId;
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
  /* Code from template attribute_SetDefaulted */
  public boolean setLocationType(int aLocationType)
  {
    boolean wasSet = false;
    locationType = aLocationType;
    wasSet = true;
    return wasSet;
  }

  public boolean resetLocationType()
  {
    boolean wasReset = false;
    locationType = getDefaultLocationType();
    wasReset = true;
    return wasReset;
  }

  public boolean setParentStation(String aParentStation)
  {
    boolean wasSet = false;
    parentStation = aParentStation;
    wasSet = true;
    return wasSet;
  }

  public boolean setTimezone(String aTimezone)
  {
    boolean wasSet = false;
    timezone = aTimezone;
    wasSet = true;
    return wasSet;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setWheelchairBoarding(int aWheelchairBoarding)
  {
    boolean wasSet = false;
    wheelchairBoarding = aWheelchairBoarding;
    wasSet = true;
    return wasSet;
  }

  public boolean resetWheelchairBoarding()
  {
    boolean wasReset = false;
    wheelchairBoarding = getDefaultWheelchairBoarding();
    wasReset = true;
    return wasReset;
  }

  public String getId()
  {
    return id;
  }

  public String getCode()
  {
    return code;
  }

  public String getName()
  {
    return name;
  }

  public String getDesc()
  {
    return desc;
  }

  public double getLat()
  {
    return lat;
  }

  public double getLon()
  {
    return lon;
  }

  public String getZoneId()
  {
    return zoneId;
  }

  public String getUrl()
  {
    return url;
  }

  public int getLocationType()
  {
    return locationType;
  }
  /* Code from template attribute_GetDefaulted */
  public int getDefaultLocationType()
  {
    return 0;
  }

  public String getParentStation()
  {
    return parentStation;
  }

  public String getTimezone()
  {
    return timezone;
  }

  public int getWheelchairBoarding()
  {
    return wheelchairBoarding;
  }
  /* Code from template attribute_GetDefaulted */
  public int getDefaultWheelchairBoarding()
  {
    return 0;
  }

  public boolean equals(Object obj)
  {
    if (obj == null) { return false; }
    if (!getClass().equals(obj.getClass())) { return false; }

    Stop compareTo = (Stop)obj;
  
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
    if (getId() != null)
    {
      cachedHashCode = cachedHashCode * 23 + getId().hashCode();
    }
    else
    {
      cachedHashCode = cachedHashCode * 23;
    }

    canSetId = false;
    return cachedHashCode;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "code" + ":" + getCode()+ "," +
            "name" + ":" + getName()+ "," +
            "desc" + ":" + getDesc()+ "," +
            "lat" + ":" + getLat()+ "," +
            "lon" + ":" + getLon()+ "," +
            "zoneId" + ":" + getZoneId()+ "," +
            "url" + ":" + getUrl()+ "," +
            "locationType" + ":" + getLocationType()+ "," +
            "parentStation" + ":" + getParentStation()+ "," +
            "timezone" + ":" + getTimezone()+ "," +
            "wheelchairBoarding" + ":" + getWheelchairBoarding()+ "]";
  }
}