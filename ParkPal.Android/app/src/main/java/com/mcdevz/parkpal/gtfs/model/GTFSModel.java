/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.341.10e47e87 modeling language!*/

package com.mcdevz.parkpal.gtfs.model;
import java.util.*;
import java.sql.Date;

// line 3 "../../../../../../../../ump/tmp356049/model.ump"
// line 248 "../../../../../../../../ump/tmp356049/model.ump"
public class GTFSModel
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static GTFSModel theInstance = null;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GTFSModel Associations
  private List<Agency> agencies;
  private List<Transfer> transfers;
  private List<FeedInfo> feedInfos;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  private GTFSModel()
  {
    agencies = new ArrayList<Agency>();
    transfers = new ArrayList<Transfer>();
    feedInfos = new ArrayList<FeedInfo>();
  }

  public static GTFSModel getInstance()
  {
    if(theInstance == null)
    {
      theInstance = new GTFSModel();
    }
    return theInstance;
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Agency getAgency(int index)
  {
    Agency aAgency = agencies.get(index);
    return aAgency;
  }

  public List<Agency> getAgencies()
  {
    List<Agency> newAgencies = Collections.unmodifiableList(agencies);
    return newAgencies;
  }

  public int numberOfAgencies()
  {
    int number = agencies.size();
    return number;
  }

  public boolean hasAgencies()
  {
    boolean has = agencies.size() > 0;
    return has;
  }

  public int indexOfAgency(Agency aAgency)
  {
    int index = agencies.indexOf(aAgency);
    return index;
  }
  /* Code from template association_GetMany */
  public Transfer getTransfer(int index)
  {
    Transfer aTransfer = transfers.get(index);
    return aTransfer;
  }

  public List<Transfer> getTransfers()
  {
    List<Transfer> newTransfers = Collections.unmodifiableList(transfers);
    return newTransfers;
  }

  public int numberOfTransfers()
  {
    int number = transfers.size();
    return number;
  }

  public boolean hasTransfers()
  {
    boolean has = transfers.size() > 0;
    return has;
  }

  public int indexOfTransfer(Transfer aTransfer)
  {
    int index = transfers.indexOf(aTransfer);
    return index;
  }
  /* Code from template association_GetMany */
  public FeedInfo getFeedInfo(int index)
  {
    FeedInfo aFeedInfo = feedInfos.get(index);
    return aFeedInfo;
  }

  public List<FeedInfo> getFeedInfos()
  {
    List<FeedInfo> newFeedInfos = Collections.unmodifiableList(feedInfos);
    return newFeedInfos;
  }

  public int numberOfFeedInfos()
  {
    int number = feedInfos.size();
    return number;
  }

  public boolean hasFeedInfos()
  {
    boolean has = feedInfos.size() > 0;
    return has;
  }

  public int indexOfFeedInfo(FeedInfo aFeedInfo)
  {
    int index = feedInfos.indexOf(aFeedInfo);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAgencies()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addAgency(Agency aAgency)
  {
    boolean wasAdded = false;
    if (agencies.contains(aAgency)) { return false; }
    agencies.add(aAgency);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeAgency(Agency aAgency)
  {
    boolean wasRemoved = false;
    if (agencies.contains(aAgency))
    {
      agencies.remove(aAgency);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addAgencyAt(Agency aAgency, int index)
  {  
    boolean wasAdded = false;
    if(addAgency(aAgency))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAgencies()) { index = numberOfAgencies() - 1; }
      agencies.remove(aAgency);
      agencies.add(index, aAgency);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAgencyAt(Agency aAgency, int index)
  {
    boolean wasAdded = false;
    if(agencies.contains(aAgency))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAgencies()) { index = numberOfAgencies() - 1; }
      agencies.remove(aAgency);
      agencies.add(index, aAgency);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addAgencyAt(aAgency, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTransfers()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addTransfer(Transfer aTransfer)
  {
    boolean wasAdded = false;
    if (transfers.contains(aTransfer)) { return false; }
    transfers.add(aTransfer);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTransfer(Transfer aTransfer)
  {
    boolean wasRemoved = false;
    if (transfers.contains(aTransfer))
    {
      transfers.remove(aTransfer);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTransferAt(Transfer aTransfer, int index)
  {  
    boolean wasAdded = false;
    if(addTransfer(aTransfer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTransfers()) { index = numberOfTransfers() - 1; }
      transfers.remove(aTransfer);
      transfers.add(index, aTransfer);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTransferAt(Transfer aTransfer, int index)
  {
    boolean wasAdded = false;
    if(transfers.contains(aTransfer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTransfers()) { index = numberOfTransfers() - 1; }
      transfers.remove(aTransfer);
      transfers.add(index, aTransfer);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTransferAt(aTransfer, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfFeedInfos()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addFeedInfo(FeedInfo aFeedInfo)
  {
    boolean wasAdded = false;
    if (feedInfos.contains(aFeedInfo)) { return false; }
    feedInfos.add(aFeedInfo);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeFeedInfo(FeedInfo aFeedInfo)
  {
    boolean wasRemoved = false;
    if (feedInfos.contains(aFeedInfo))
    {
      feedInfos.remove(aFeedInfo);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addFeedInfoAt(FeedInfo aFeedInfo, int index)
  {  
    boolean wasAdded = false;
    if(addFeedInfo(aFeedInfo))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfFeedInfos()) { index = numberOfFeedInfos() - 1; }
      feedInfos.remove(aFeedInfo);
      feedInfos.add(index, aFeedInfo);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveFeedInfoAt(FeedInfo aFeedInfo, int index)
  {
    boolean wasAdded = false;
    if(feedInfos.contains(aFeedInfo))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfFeedInfos()) { index = numberOfFeedInfos() - 1; }
      feedInfos.remove(aFeedInfo);
      feedInfos.add(index, aFeedInfo);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addFeedInfoAt(aFeedInfo, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    agencies.clear();
    transfers.clear();
    feedInfos.clear();
  }

}