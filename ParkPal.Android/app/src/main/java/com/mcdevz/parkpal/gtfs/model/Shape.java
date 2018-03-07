/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.341.10e47e87 modeling language!*/

package com.mcdevz.parkpal.gtfs.model;
import java.util.*;

// line 117 "../../../../../../../../ump/tmp356049/model.ump"
// line 218 "../../../../../../../../ump/tmp356049/model.ump"
public class Shape
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Shape Attributes
  private String id;
  private List<Double> lat;
  private List<Double> lon;
  private List<Integer> sequence;
  private List<Integer> distTraveled;

  //Helper Variables
  private int cachedHashCode;
  private boolean canSetId;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Shape(String aId)
  {
    cachedHashCode = -1;
    canSetId = true;
    id = aId;
    lat = new ArrayList<Double>();
    lon = new ArrayList<Double>();
    sequence = new ArrayList<Integer>();
    distTraveled = new ArrayList<Integer>();
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
  /* Code from template attribute_SetMany */
  public boolean addLat(Double aLat)
  {
    boolean wasAdded = false;
    wasAdded = lat.add(aLat);
    return wasAdded;
  }

  public boolean removeLat(Double aLat)
  {
    boolean wasRemoved = false;
    wasRemoved = lat.remove(aLat);
    return wasRemoved;
  }
  /* Code from template attribute_SetMany */
  public boolean addLon(Double aLon)
  {
    boolean wasAdded = false;
    wasAdded = lon.add(aLon);
    return wasAdded;
  }

  public boolean removeLon(Double aLon)
  {
    boolean wasRemoved = false;
    wasRemoved = lon.remove(aLon);
    return wasRemoved;
  }
  /* Code from template attribute_SetMany */
  public boolean addSequence(Integer aSequence)
  {
    boolean wasAdded = false;
    wasAdded = sequence.add(aSequence);
    return wasAdded;
  }

  public boolean removeSequence(Integer aSequence)
  {
    boolean wasRemoved = false;
    wasRemoved = sequence.remove(aSequence);
    return wasRemoved;
  }
  /* Code from template attribute_SetMany */
  public boolean addDistTraveled(Integer aDistTraveled)
  {
    boolean wasAdded = false;
    wasAdded = distTraveled.add(aDistTraveled);
    return wasAdded;
  }

  public boolean removeDistTraveled(Integer aDistTraveled)
  {
    boolean wasRemoved = false;
    wasRemoved = distTraveled.remove(aDistTraveled);
    return wasRemoved;
  }

  public String getId()
  {
    return id;
  }
  /* Code from template attribute_GetMany */
  public Double getLat(int index)
  {
    Double aLat = lat.get(index);
    return aLat;
  }

  public Double[] getLat()
  {
    Double[] newLat = lat.toArray(new Double[lat.size()]);
    return newLat;
  }

  public int numberOfLat()
  {
    int number = lat.size();
    return number;
  }

  public boolean hasLat()
  {
    boolean has = lat.size() > 0;
    return has;
  }

  public int indexOfLat(Double aLat)
  {
    int index = lat.indexOf(aLat);
    return index;
  }
  /* Code from template attribute_GetMany */
  public Double getLon(int index)
  {
    Double aLon = lon.get(index);
    return aLon;
  }

  public Double[] getLon()
  {
    Double[] newLon = lon.toArray(new Double[lon.size()]);
    return newLon;
  }

  public int numberOfLon()
  {
    int number = lon.size();
    return number;
  }

  public boolean hasLon()
  {
    boolean has = lon.size() > 0;
    return has;
  }

  public int indexOfLon(Double aLon)
  {
    int index = lon.indexOf(aLon);
    return index;
  }
  /* Code from template attribute_GetMany */
  public Integer getSequence(int index)
  {
    Integer aSequence = sequence.get(index);
    return aSequence;
  }

  public Integer[] getSequence()
  {
    Integer[] newSequence = sequence.toArray(new Integer[sequence.size()]);
    return newSequence;
  }

  public int numberOfSequence()
  {
    int number = sequence.size();
    return number;
  }

  public boolean hasSequence()
  {
    boolean has = sequence.size() > 0;
    return has;
  }

  public int indexOfSequence(Integer aSequence)
  {
    int index = sequence.indexOf(aSequence);
    return index;
  }
  /* Code from template attribute_GetMany */
  public Integer getDistTraveled(int index)
  {
    Integer aDistTraveled = distTraveled.get(index);
    return aDistTraveled;
  }

  public Integer[] getDistTraveled()
  {
    Integer[] newDistTraveled = distTraveled.toArray(new Integer[distTraveled.size()]);
    return newDistTraveled;
  }

  public int numberOfDistTraveled()
  {
    int number = distTraveled.size();
    return number;
  }

  public boolean hasDistTraveled()
  {
    boolean has = distTraveled.size() > 0;
    return has;
  }

  public int indexOfDistTraveled(Integer aDistTraveled)
  {
    int index = distTraveled.indexOf(aDistTraveled);
    return index;
  }

  public boolean equals(Object obj)
  {
    if (obj == null) { return false; }
    if (!getClass().equals(obj.getClass())) { return false; }

    Shape compareTo = (Shape)obj;
  
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
            "id" + ":" + getId()+ "]";
  }
}