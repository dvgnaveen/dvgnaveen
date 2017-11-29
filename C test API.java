package adword.UpdateDisplay;
import java.io.FileReader;
/**
 * @author Naveen
 *
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.api.ads.adwords.axis.v201710.cm.AdGroup;
import com.google.api.ads.adwords.axis.v201710.cm.AdSchedule;
import com.google.api.ads.adwords.axis.v201710.cm.AdServingOptimizationStatus;
import com.google.api.ads.adwords.axis.v201710.cm.AdvertisingChannelType;
import com.google.api.ads.adwords.axis.v201710.cm.ApiException;
import com.google.api.ads.adwords.axis.v201710.cm.BiddingStrategyConfiguration;
import com.google.api.ads.adwords.axis.v201710.cm.BiddingStrategyType;
import com.google.api.ads.adwords.axis.v201710.cm.Budget;
import com.google.api.ads.adwords.axis.v201710.cm.BudgetBudgetDeliveryMethod;
import com.google.api.ads.adwords.axis.v201710.cm.BudgetOperation;
import com.google.api.ads.adwords.axis.v201710.cm.BudgetServiceInterface;
import com.google.api.ads.adwords.axis.v201710.cm.Campaign;
import com.google.api.ads.adwords.axis.v201710.cm.CampaignCriterion;
import com.google.api.ads.adwords.axis.v201710.cm.CampaignCriterionOperation;
import com.google.api.ads.adwords.axis.v201710.cm.CampaignCriterionReturnValue;
import com.google.api.ads.adwords.axis.v201710.cm.CampaignCriterionServiceInterface;
import com.google.api.ads.adwords.axis.v201710.cm.CampaignOperation;
import com.google.api.ads.adwords.axis.v201710.cm.CampaignReturnValue;
import com.google.api.ads.adwords.axis.v201710.cm.CampaignServiceInterface;
import com.google.api.ads.adwords.axis.v201710.cm.CampaignStatus;
import com.google.api.ads.adwords.axis.v201710.cm.ConstantDataServiceInterface;
import com.google.api.ads.adwords.axis.v201710.cm.ContentLabel;
import com.google.api.ads.adwords.axis.v201710.cm.ContentLabelType;
import com.google.api.ads.adwords.axis.v201710.cm.DayOfWeek;
import com.google.api.ads.adwords.axis.v201710.cm.FrequencyCap;
import com.google.api.ads.adwords.axis.v201710.cm.GeoTargetTypeSetting;
import com.google.api.ads.adwords.axis.v201710.cm.GeoTargetTypeSettingPositiveGeoTargetType;
import com.google.api.ads.adwords.axis.v201710.cm.Language;
import com.google.api.ads.adwords.axis.v201710.cm.Level;
import com.google.api.ads.adwords.axis.v201710.cm.Location;
import com.google.api.ads.adwords.axis.v201710.cm.ManualCpcBiddingScheme;
import com.google.api.ads.adwords.axis.v201710.cm.MinuteOfHour;
import com.google.api.ads.adwords.axis.v201710.cm.MobileDevice;
import com.google.api.ads.adwords.axis.v201710.cm.MobileDeviceDeviceType;
import com.google.api.ads.adwords.axis.v201710.cm.Money;
import com.google.api.ads.adwords.axis.v201710.cm.NegativeCampaignCriterion;
import com.google.api.ads.adwords.axis.v201710.cm.NetworkSetting;
import com.google.api.ads.adwords.axis.v201710.cm.OperatingSystemVersion;
import com.google.api.ads.adwords.axis.v201710.cm.Operator;
import com.google.api.ads.adwords.axis.v201710.cm.ServingStatus;
import com.google.api.ads.adwords.axis.v201710.cm.TargetCpaBiddingScheme;
import com.google.api.ads.adwords.axis.v201710.cm.TargetSpendBiddingScheme;
import com.google.api.ads.adwords.axis.v201710.cm.TimeUnit;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import adword.Connector.Session;
import adword.database.MySingleton;
import adword.interfaces.Bidding;
import adword.interfaces.DailyBudget;
import adword.interfaces.DisplayCampaign;
import adword.interfaces.MaximizeClicks;
import adword.interfaces.Networksetting;
import adword.interfaces.TargetCPA;
import adword.json.json_encoder;


 class results {

    private Boolean success;
    private String result;
    private String error;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}

public class CampaignsAPI {
	
	static Logger errorlogger = LogManager.getLogger("err");
    static Logger infologger = LogManager.getLogger("inf");

    
	 public static String updateDisplayCampaign(Session session, DisplayCampaign camp,int commission) throws Exception {
		 long id = 0;
		   results res = new results();
		   MongoClient client =null;
		 try{
				  
				System.out.println("In campaign");
				Campaign campaign = new Campaign();
				if( camp.getBudgetId()!=0){
					

	    			
//		    		BudgetServiceInterface budgetService
//		            = session.services.get(session.session, BudgetServiceInterface.class);
//
//		    			Budget sharedBudget = new Budget();
//		    			Money budgetAmount = new Money();
//		    			
//		    			DailyBudget dailybudget = camp.getBudget();
//		    			
//		    			budgetAmount.setMicroAmount(dailybudget.getAmount() * 1000000L);
//		    			sharedBudget.setAmount(budgetAmount);
//		    			if (dailybudget.getDeliveryMethod().equals("Standard")) {
//		    			    sharedBudget.setDeliveryMethod(BudgetBudgetDeliveryMethod.STANDARD);
//		    			} else {
//		    			    sharedBudget.setDeliveryMethod(BudgetBudgetDeliveryMethod.ACCELERATED);
//		    			}
//		    			sharedBudget.setIsExplicitlyShared(false);
//		    			sharedBudget.setBudgetId(camp.getBudgetId());
//		    			BudgetOperation budgetOperation = new BudgetOperation();
//		    			budgetOperation.setOperand(sharedBudget);
//		    			budgetOperation.setOperator(Operator.SET);
//		    			
//		    			// Add the budget
//		    			Long budgetId
//		    			        = budgetService.mutate(new BudgetOperation[]{budgetOperation}).getValue(0).getBudgetId();
//		    		    campaign.setBudget(sharedBudget);

				BudgetServiceInterface budgetService
		        = session.services.get(session.session, BudgetServiceInterface.class);

					Budget sharedBudget = new Budget();
					Money budgetAmount = new Money();
					
					DailyBudget dailybudget = camp.getBudget();
					budgetAmount.setMicroAmount(dailybudget.getAmount() * 1000000-(dailybudget.getAmount() * 1000000*commission/100));
					sharedBudget.setAmount(budgetAmount);
					sharedBudget.setBudgetId(camp.getBudgetId());
					if (dailybudget.getDeliveryMethod().equals("Standard")) {
					    sharedBudget.setDeliveryMethod(BudgetBudgetDeliveryMethod.STANDARD);
					} else {
					    sharedBudget.setDeliveryMethod(BudgetBudgetDeliveryMethod.ACCELERATED);
					}
					sharedBudget.setIsExplicitlyShared(false);
					BudgetOperation budgetOperation = new BudgetOperation();
					budgetOperation.setOperand(sharedBudget);
					budgetOperation.setOperator(Operator.SET);
					
					// Add the budget
					budgetService.mutate(new BudgetOperation[]{budgetOperation}).getValue(0).getBudgetId();
					
				}
					// Get the CampaignService.
					CampaignServiceInterface campaignService
					        = session.services.get(session.session, CampaignServiceInterface.class);
					
					// Create campaign.
				campaign.setId(camp.getCampaignId());
					campaign.setName(camp.getName());
					campaign.setStatus(CampaignStatus.ENABLED);
					Budget budget = new Budget();
					budget.setBudgetId(camp.getBudgetId());
					campaign.setBudget(budget);
					campaign.setTrackingUrlTemplate(camp.getCampaignUrlOption());
					 BiddingStrategyConfiguration biddingStrategyConfiguration = new BiddingStrategyConfiguration();
			            Bidding bid_strategy = camp.getBidStrategy();
			            if (bid_strategy.getManualCPC() != null) {
			            	biddingStrategyConfiguration.setBiddingStrategyType(BiddingStrategyType.MANUAL_CPC);
			            	  ManualCpcBiddingScheme cpcBiddingScheme = new ManualCpcBiddingScheme();
			            		if(bid_strategy.getManualCPC().isEnableEnhancedCPC()==false){
			                   cpcBiddingScheme.setEnhancedCpcEnabled(false);
			            		}else{
			            			 cpcBiddingScheme.setEnhancedCpcEnabled(true);
			            		}
			                   biddingStrategyConfiguration.setBiddingScheme(cpcBiddingScheme);
			            }
			            if(bid_strategy.getTargetCPA()!=null){
			            	biddingStrategyConfiguration.setBiddingStrategyType(BiddingStrategyType.TARGET_CPA);
			            	TargetCPA targetCPA = bid_strategy.getTargetCPA();
			            TargetCpaBiddingScheme tcbs = new  TargetCpaBiddingScheme();
			            Money money = new Money();
			            money.setMicroAmount(targetCPA.getCpa()*1000000);
			            tcbs.setTargetCpa(money);
			            biddingStrategyConfiguration.setBiddingScheme(tcbs);
			            	
			            }
			            
			            if(bid_strategy.getMaximizeClicks()!=null){
			            	biddingStrategyConfiguration.setBiddingStrategyType(BiddingStrategyType.TARGET_SPEND);
			            	MaximizeClicks target_spend = bid_strategy.getMaximizeClicks();
			            	TargetSpendBiddingScheme tcbs = new  TargetSpendBiddingScheme();
			            	Money money = new Money();
			                 money.setMicroAmount(target_spend.getMaxCPC()*1000000);
			            	tcbs.setSpendTarget(money);
			            	if(target_spend.isEnableEnhancedCPC()==true){
			            		tcbs.setEnhancedCpcEnabled(true);
			            	}else{
			            		tcbs.setEnhancedCpcEnabled(false);
			            	}
			             biddingStrategyConfiguration.setBiddingScheme(tcbs);
			            	
			            }
			            if(bid_strategy.getEnhancedCPC()!=null){
			            	biddingStrategyConfiguration.setBiddingStrategyType(BiddingStrategyType.ENHANCED_CPC);
			            	
			            	
			            }
			          if(bid_strategy.getMaximizeConversions()!=null) {
			        	  biddingStrategyConfiguration.setBiddingStrategyType(BiddingStrategyType.MAXIMIZE_CONVERSIONS);
			          }
			         
			            // You can optionally provide a bidding scheme in place of the type.
			       
			            
			            campaign.setBiddingStrategyConfiguration(biddingStrategyConfiguration);
		            
		            campaign.setStartDate(camp.getStartDate());
		            if(camp.getEndDate()!=null) {
		            campaign.setEndDate(camp.getEndDate());
		            }
		           System.out.println("-----------1");
		            if ("Rotate".equals(camp.getAdRotation())) {
		                campaign.setAdServingOptimizationStatus(AdServingOptimizationStatus.ROTATE);
		            }
		            if ("Rotate Indefinitely".equals(camp.getAdRotation())) {
		                campaign.setAdServingOptimizationStatus(AdServingOptimizationStatus.ROTATE_INDEFINITELY);
		            }
		            if ("Optimize".equals(camp.getAdRotation())) {
		                campaign.setAdServingOptimizationStatus(AdServingOptimizationStatus.OPTIMIZE);
		            }
		            if ("Conversion_Optimize".equals(camp.getAdRotation())) {
		                campaign.setAdServingOptimizationStatus(AdServingOptimizationStatus.CONVERSION_OPTIMIZE);
		            }
		            
		            if (camp.getFrequencyCapping() != null) {
		            FrequencyCap fc = new FrequencyCap();
		            fc.setImpressions(camp.getFrequencyCapping().getImpressions());
		            if (camp.getFrequencyCapping().getTimeUnit() != null) {
		            	if(camp.getFrequencyCapping().getTimeUnit() == "DAY")
		            	{
		            	fc.setTimeUnit(TimeUnit.DAY);
		            	}
		            	if(camp.getFrequencyCapping().getTimeUnit() .equals("WEEK"))
		            	{
		            	fc.setTimeUnit(TimeUnit.WEEK);
		            	}if(camp.getFrequencyCapping().getTimeUnit().equals("MONTH"))
		            	{
		            	fc.setTimeUnit(TimeUnit.MONTH);
		            	}
					}
		            if(camp.getFrequencyCapping().getLevel()!= null){
		            	 if(camp.getFrequencyCapping().getLevel().equals("AD"))	{
		            		 fc.setLevel(Level.CREATIVE);
		            	 }
		            	 if(camp.getFrequencyCapping().getLevel().equals("ADGROUP"))	{
		            		 fc.setLevel(Level.ADGROUP);
		            	 }
		            	 if(camp.getFrequencyCapping().getLevel().equals("CAMPAIGN"))	{
		            		 fc.setLevel(Level.CAMPAIGN);
		            	 }
		            	 if(camp.getFrequencyCapping().getLevel().equals("UNKNOWN"))	{
		            		 fc.setLevel(Level.UNKNOWN);
		            	 }
		            }
		            
					}
		            if (camp.getChannelType().toLowerCase().equals("search")) {
		                campaign.setAdvertisingChannelType(AdvertisingChannelType.SEARCH);
		                NetworkSetting networkSetting = new NetworkSetting();
		                networkSetting.setTargetGoogleSearch(true);
		                Networksetting network_setting = camp.getNetworkSetting();
		                networkSetting.setTargetSearchNetwork(network_setting.isTargetSearchNetwork());
		                networkSetting.setTargetContentNetwork(network_setting.isTargetContentNetwork());
		                networkSetting.setTargetPartnerSearchNetwork(false);
		                campaign.setNetworkSetting(networkSetting);
		            } else if (camp.getChannelType().toLowerCase().equals("display")) {
		                campaign.setAdvertisingChannelType(AdvertisingChannelType.DISPLAY);
		                NetworkSetting networkSetting = new NetworkSetting();
		                //networkSetting.setTargetGoogleSearch(true);
		                Networksetting network_setting = camp.getNetworkSetting();
		                networkSetting.setTargetSearchNetwork(network_setting.isTargetSearchNetwork());
		                networkSetting.setTargetContentNetwork(network_setting.isTargetContentNetwork());
		                networkSetting.setTargetPartnerSearchNetwork(false);
		                campaign.setNetworkSetting(networkSetting);
		            } else if (camp.getChannelType().toLowerCase().equals("shopping")) {
		                campaign.setAdvertisingChannelType(AdvertisingChannelType.SHOPPING);
		                NetworkSetting networkSetting = new NetworkSetting();
		                // networkSetting.setTargetGoogleSearch(true);
		                Networksetting network_setting = camp.getNetworkSetting();
		                networkSetting.setTargetSearchNetwork(network_setting.isTargetSearchNetwork());
		                networkSetting.setTargetContentNetwork(network_setting.isTargetContentNetwork());
		                networkSetting.setTargetPartnerSearchNetwork(false);
		                campaign.setNetworkSetting(networkSetting);
		            } else if (camp.getChannelType().toLowerCase().equals("multichannel")) {
		                campaign.setAdvertisingChannelType(AdvertisingChannelType.MULTI_CHANNEL);
		                NetworkSetting networkSetting = new NetworkSetting();
		                // networkSetting.setTargetGoogleSearch(true);
		                Networksetting network_setting = camp.getNetworkSetting();
		                networkSetting.setTargetSearchNetwork(network_setting.isTargetSearchNetwork());
		                networkSetting.setTargetContentNetwork(network_setting.isTargetContentNetwork());
		                networkSetting.setTargetPartnerSearchNetwork(false);
		                campaign.setNetworkSetting(networkSetting);
		            } else {
		                campaign.setAdvertisingChannelType(AdvertisingChannelType.SEARCH);
		                NetworkSetting networkSetting = new NetworkSetting();
		                networkSetting.setTargetGoogleSearch(true);
		                Networksetting network_setting = camp.getNetworkSetting();
		                networkSetting.setTargetSearchNetwork(network_setting.isTargetSearchNetwork());
		                networkSetting.setTargetContentNetwork(network_setting.isTargetContentNetwork());
		                networkSetting.setTargetPartnerSearchNetwork(false);
		                campaign.setNetworkSetting(networkSetting);
		            }
		            System.out.println("--------------2");
		     
		          
		            GeoTargetTypeSetting geoTarget = new GeoTargetTypeSetting();
		            adword.interfaces.Location camp_loc = camp.getLocations();
		            long[] includeCountryIds = null;
		            long[] excludeCountryIds = null;
		            String setting = camp_loc.getSetting();
		            if (setting.toLowerCase().equals("all")) {
		                geoTarget.setPositiveGeoTargetType(GeoTargetTypeSettingPositiveGeoTargetType.DONT_CARE);
		            } else {
		                adword.interfaces.Location loc = camp.getLocations();
		                long[] target = loc.getTargeted();
		                long[] excluded = loc.getExcluded();
		                if (target != null) {
		                    includeCountryIds = loc.getTargeted();
		                }
		                if (excluded != null) {
		                    excludeCountryIds = loc.getExcluded();
		                }
		            }

		            ConstantDataServiceInterface constantDataService
		                    = session.services.get(session.session, ConstantDataServiceInterface.class);
		            String[] language1 = camp.getLanguages();
		            
		            Language[] languages = constantDataService.getLanguageCriterion();
		            
		            CampaignOperation operation = new CampaignOperation();
		            operation.setOperand(campaign);
		            operation.setOperator(Operator.SET);
		            System.out.println("-------------23----------");
		            CampaignOperation[] operations = new CampaignOperation[]{operation};

		            // Add campaigns.
		            CampaignReturnValue result = campaignService.mutate(operations);
		            
		            System.out.println("to find error");
		            List<Campaign> list1 = new ArrayList<Campaign>();
		            // Display campaigns.
		            System.out.println("to find error ---2");
		          
		            if(result!=null){
		        		System.out.println(result);
		        		if(result.getPartialFailureErrors()!=null){
		        			System.out.println(result.getPartialFailureErrors());
		        		System.out.println("---->");
		        			throw new ApiException("error", "", result.getPartialFailureErrors());
		        		}
		            
		           
		            for (Campaign campaignResult : result.getValue()) {
		            	System.out.println(result);
		                list1.add(campaignResult);
		                id = campaignResult.getId();
		                System.out.println(campaignResult);
		                JSONParser parser=new JSONParser();
	    			   	Object object = parser.parse(new FileReader("/home/dvgnaveen/Adwords api/adwords/new_test_adword/src/config.json"));
	    			   	JSONObject jsonobject=(JSONObject) object;
	    			   	String  db_name=(String) jsonobject.get("db_name");
	    			   	 client = MySingleton.getInstance();
	    			   @SuppressWarnings("deprecation")
	    			   	DB db = client.getDB(db_name);         

		                DBCollection campaign_collection = db.getCollection("adwords_DisplayCampaigns");
		                BasicDBObject newDocument = new BasicDBObject();
		               try {
		                
		                newDocument.put("localId", camp.getLocalId());
		                newDocument.put("campaign_id", campaignResult.getId());
		                newDocument.put("customer_id", session.session.getClientCustomerId());
		                newDocument.put("name", campaignResult.getName());
                        AdvertisingChannelType ch_type = campaignResult.getAdvertisingChannelType();
                        newDocument.put("channel_type", ch_type.getValue());
                        //AdvertisingChannelSubType ch_subtype = result.getAdvertisingChannelSubType();
                        //	docBuilder.add("sub_channel_type", ch_subtype.getValue());
                        System.out.println("------1");
                        Budget budget1 = campaignResult.getBudget();
                        Money money = budget1.getAmount();
                        newDocument.put("amount", money.getMicroAmount());
                        //docBuilder.add("label", result.getl);
                        System.out.println("------2");
                        newDocument.put("start_date", campaignResult.getStartDate());
                        newDocument.put("end_date", campaignResult.getEndDate());
                        System.out.println("------4");
                        CampaignStatus status = campaignResult.getStatus();
                        newDocument.put("status", status.getValue());
                        ServingStatus serving_status = campaignResult.getServingStatus();
                        newDocument.put("serving_status", serving_status.getValue());
                      
                        System.out.println("------3");
		                
                        BasicDBObject searchQuery = new BasicDBObject().append("campaign_id",id);
	    				campaign_collection.update(searchQuery, newDocument);   
		                
		            } catch (Exception e) {
					throw e;
					}
		               
		            }
		         

		                
		            }
		            
		            System.out.println("to find error ---3");

		            List<CampaignCriterionOperation> operations1 = new ArrayList();
		           
		            for (String language11 : language1) {
		                for (Language language : languages) {
		                    if (language11.toLowerCase().equals(language.getName().toLowerCase())) {
		                        Language lang = new Language();
		                        lang.setId(language.getId());
		                        CampaignCriterionOperation operate = new CampaignCriterionOperation();
		                        CampaignCriterion campaignCriterion = new CampaignCriterion();
		                        campaignCriterion.setCampaignId(id);
		                        campaignCriterion.setCriterion(lang);
		                        operate.setOperand(campaignCriterion);
		                        operate.setOperator(Operator.SET);
		                        operations1.add(operate);
		                    }
		                }
		            }
		     
		      
		            adword.interfaces.AdSchedule[] adscchedule = camp.getAdSchedule();
		           for(int i = 0;i<adscchedule.length;i++){
		        	      AdSchedule finalschedule = new AdSchedule();
		            if(adscchedule[i] != null){
		            
		            	ArrayList<String> sl = new ArrayList<String>();
		            	
		            	String s = adscchedule[i].getDayOfWeek();
		            	if(s.equals("WEEKENDS"))
		            	{
		            	 sl.add("SATURDAY");
		            	 sl.add("SUNDAY");
		            		
		            	}
		            	else if (s.equals("ALL")) {
							sl.add("SATURDAY");
							sl.add("MONDAY");
							sl.add("TUESDAY");
							sl.add("WEDNESDAY");
							sl.add("THURSDAY");
							sl.add("FRIDAY");
			            	 sl.add("SUNDAY");
						}
		            	else if (s.equals("WEEKDAYS") )
		            	{
		            		sl.add("MONDAY");
							sl.add("TUESDAY");
							sl.add("WEDNESDAY");
							sl.add("THURSDAY");
							sl.add("FRIDAY");
		            	}
		            	else
		            	{
		            		sl.add(adscchedule[i].getDayOfWeek());
		            	}
		            	
		            	for(int j = 0;j<sl.size();j++)
		            	{ 
		            		
		            		if(("MONDAY").equals(sl.get(j))){
		            		finalschedule.setDayOfWeek(DayOfWeek.MONDAY);
		            		}
		            		if(("TUESDAY").equals(sl.get(j))){
		                		finalschedule.setDayOfWeek(DayOfWeek.TUESDAY);
		                		}
		            		if("WEDNESDAY".equals(sl.get(j))){
		                		finalschedule.setDayOfWeek(DayOfWeek.WEDNESDAY);
		                		}
		            		if("THURSDAY".equals(sl.get(j))){
		                		finalschedule.setDayOfWeek(DayOfWeek.THURSDAY);
		                		}
		            		if("FRIDAY".equals(sl.get(j))){
		                		finalschedule.setDayOfWeek(DayOfWeek.FRIDAY);
		                		}
		            		if("SATURDAY".equals(sl.get(j))){
		                		finalschedule.setDayOfWeek(DayOfWeek.SATURDAY);
		                		}
		            		if("SUNDAY".equals(sl.get(j))){
		                		finalschedule.setDayOfWeek(DayOfWeek.SUNDAY);
		                		}
		            	}
		            }
		            System.out.println("------------3");
		           String[] startTime = adscchedule[i].getStartTime().split(":");
		          String[] endTime = adscchedule[i].getEndTime().split(":");
		            finalschedule.setStartHour(Integer.parseInt(startTime[0]));
		            finalschedule.setStartMinute(convertMinute(startTime[1]));
		           finalschedule.setEndHour(Integer.parseInt(endTime[0]));
		           finalschedule.setEndMinute(convertMinute(endTime[1]));
		           CampaignCriterionOperation operate = new CampaignCriterionOperation();
		           CampaignCriterion campaignCriterion = new CampaignCriterion();
		           campaignCriterion.setCampaignId(id);
		           campaignCriterion.setCriterion(finalschedule);
		           
		           operate.setOperand(campaignCriterion);
		           operate.setOperator(Operator.SET);
		           operations1.add(operate);

		           }
		           System.out.println("---------");
		        
		            if (null != includeCountryIds) {
		                for (long countryId : includeCountryIds) {
		                    Location location = new Location();
		                    location.setId(countryId);
		                    CampaignCriterionOperation operate = new CampaignCriterionOperation();
		                    CampaignCriterion campaignCriterion = new CampaignCriterion();
		                    campaignCriterion.setCampaignId(id);
		                    campaignCriterion.setCriterion(location);
		                    operate.setOperand(campaignCriterion);
		                    operate.setOperator(Operator.SET);
		                    operations1.add(operate);
		                }
		            }
		            if (excludeCountryIds != null) {
		                for (long countryId : excludeCountryIds) {
		                    Location location = new Location();
		                    location.setId(countryId);
		                    CampaignCriterionOperation operate = new CampaignCriterionOperation();
		                    NegativeCampaignCriterion NegativeCampaignCriterion = new NegativeCampaignCriterion();
		                    NegativeCampaignCriterion.setCampaignId(id);
		                    NegativeCampaignCriterion.setCriterion(location);
		                    operate.setOperand(NegativeCampaignCriterion);
		                    operate.setOperator(Operator.SET);
		                    operations1.add(operate);
		                }
		            }
		            
		            ContentLabel clabel = new ContentLabel();
		            if (camp.getContentExclusions()!= null) {
		            	 List<String> l1 = Arrays.asList(camp.getContentExclusions());
		            	 for (int i = 0; i < l1.size(); i++) {
		            		
		            		 if(l1.get(i).equals("ADULTISH"))
		            		 {
		            			 clabel.setContentLabelType(ContentLabelType.ADULTISH);
		            		 }
		            		 
		            		 if(l1.get(i).equals("BELOW_THE_FOLD"))
		            		 {
		            			 clabel.setContentLabelType(ContentLabelType.BELOW_THE_FOLD);
		            		 }
		            		 
		            		 if(l1.get(i).equals("DP"))
		            		 {
		            			 clabel.setContentLabelType(ContentLabelType.DP);
		            		 }
		            		 if(l1.get(i).equals("EMBEDDED_VIDEO"))            		 {
		            			 clabel.setContentLabelType(ContentLabelType.EMBEDDED_VIDEO);
		            		 }
		            		 if(l1.get(i).equals("GAMES"))
		            		 {
		            			 clabel.setContentLabelType(ContentLabelType.GAMES);
		            		 }
		            		 if(l1.get(i).equals("JUVENILE"))
		            		 {
		            			 clabel.setContentLabelType(ContentLabelType.JUVENILE);
		            		 }
		            		 if(l1.get(i).equals("LIVE_STREAMING_VIDEO"))
		            		 {
		            			 clabel.setContentLabelType(ContentLabelType.LIVE_STREAMING_VIDEO);
		            		 }
		            		 if(l1.get(i).equals("PROFANITY"))
		            		 {
		            			 clabel.setContentLabelType(ContentLabelType.PROFANITY);
		            		 }
		            		 
		            		 if(l1.get(i).equals("TRAGEDY"))
		            		 {
		            			 clabel.setContentLabelType(ContentLabelType.TRAGEDY);
		            		 }
		            		 if(l1.get(i).equals("UNKNOWN"))
		            		 {
		            			 clabel.setContentLabelType(ContentLabelType.UNKNOWN);
		            		 }
		            		 if(l1.get(i).equals("VIDEO"))
		            		 {
		            			 clabel.setContentLabelType(ContentLabelType.VIDEO);
		            		 }
		            		 if(l1.get(i).equals("VIDEO_NOT_YET_RATED"))
		            		 {
		            			 clabel.setContentLabelType(ContentLabelType.VIDEO_NOT_YET_RATED);
		            		 }
		            		 if(l1.get(i).equals("VIDEO_RATING_DV_G"))
		            		 {
		            			 clabel.setContentLabelType(ContentLabelType.VIDEO_RATING_DV_G);
		            		 }
		            		 if(l1.get(i).equals("VIDEO_RATING_DV_MA"))
		            		 {
		            			 clabel.setContentLabelType(ContentLabelType.VIDEO_RATING_DV_MA);
		            		 }
		            		 if(l1.get(i).equals("VIDEO_RATING_DV_PG"))
		            		 {
		            			 clabel.setContentLabelType(ContentLabelType.VIDEO_RATING_DV_PG);
		            		 }
		            		 if(l1.get(i).equals("VIDEO_RATING_DV_T"))
		            		 {
		            			 clabel.setContentLabelType(ContentLabelType.VIDEO_RATING_DV_T);
		            		 }
		            	}
		            	 CampaignCriterionOperation operate = new CampaignCriterionOperation();
		                 CampaignCriterion campaignCriterion = new CampaignCriterion();
		                 campaignCriterion.setCampaignId(id);
		                 campaignCriterion.setCriterion(clabel);
		                 
		                 operate.setOperand(campaignCriterion);
		                 operate.setOperator(Operator.SET);
		                 operations1.add(operate);
		            }
		            
		            MobileDevice m = new MobileDevice();
	                 if(camp.getDevices().getMobileDevice()!=null){
	               
	                	 adword.interfaces.MobileDevice[] l2 = camp.getDevices().getMobileDevice();	
	                	for (int k = 0; k < l2.length; k++) {
	                		if(l2[k].getDeviceName()!=null) {
	                		m.setDeviceName(l2[k].getDeviceName());
	                		}
	                		if(l2[k].getManufacturerName()!= null)
	                		{
	                		m.setManufacturerName(l2[k].getManufacturerName());
	                		}
	                		if(l2[k].getDeviceType().equalsIgnoreCase("Mobile"))
	                		{
	                		m.setDeviceType(MobileDeviceDeviceType.DEVICE_TYPE_MOBILE);
	                		}
	                		if(l2[k].getDeviceType().equalsIgnoreCase("tablet"))
	                		{
	                		m.setDeviceType(MobileDeviceDeviceType.DEVICE_TYPE_TABLET);
	                		}
	                		if(l2[k].getOperatingSystemName()!=null)
	                		{
	                        m.setOperatingSystemName(l2[k].getOperatingSystemName());
	                		}
	                        CampaignCriterionOperation operate = new CampaignCriterionOperation();
		                     CampaignCriterion campaignCriterion = new CampaignCriterion();
		                     campaignCriterion.setCampaignId(id);
		                     campaignCriterion.setCriterion(m);
		                     
		                     operate.setOperand(campaignCriterion);
		                     operate.setOperator(Operator.SET);
		                     operations1.add(operate);
	                	}
	                 }
		                	OperatingSystemVersion osv = new OperatingSystemVersion();
		                	
		                	if (camp.getDevices().getOperatingSystem()!= null) {
		                		
		                		List<adword.interfaces.OperatingSystemVersion> l3 = Arrays.asList(camp.getDevices().getOperatingSystem());
								for (int j = 0; j < l3.size(); j++) 
								{
								   osv.setName(l3.get(j).getName());
								  osv.setOsMinorVersion(l3.get(j).getMinorVersion());
								  osv.setOsMajorVersion(l3.get(j).getMajorVersion());
										
								}
									
								CampaignCriterionOperation operate = new CampaignCriterionOperation();
			                     CampaignCriterion campaignCriterion = new CampaignCriterion();
			                     campaignCriterion.setCampaignId(id);
			                     campaignCriterion.setCriterion(osv);
			                     
			                     operate.setOperand(campaignCriterion);
			                     operate.setOperator(Operator.SET);
			                     operations1.add(operate);
								}
		                
		            
		            
		            try {
		                CampaignCriterionServiceInterface campaignCriterionService
		                        = session.services.get(session.session, CampaignCriterionServiceInterface.class);
		                CampaignCriterionReturnValue result2 = campaignCriterionService.mutate(operations1.toArray(new CampaignCriterionOperation[operations1.size()]));
		                for (CampaignCriterion criterionResult : result2.getValue()) {
		                	System.out.println(criterionResult);
		                }
		                System.out.println("campaign Creation");
		               try {
		            	   
		                List<AdGroup> adGroupResult = adword.UpdateDisplay.AdgroupAPI.updateAdgroup(session, camp);
		                res.setSuccess(true);
			               res.setResult(Long.toString(id));
		               }
		               catch (Exception e) {
		            	   res.setSuccess(false);
			               res.setResult("");
		            	   res.setError(e.getMessage());
						throw e;
					}
		            } catch (Exception ed) {
		                errorlogger.error(ed);
		            }
		            
			}catch(Exception e){
				System.out.println("in catch");
				System.out.println(e.getMessage());
				throw e;
			}finally {
				//client.close();
			}
			
			   
	            return json_encoder.javaToJson(res);
			
			 }	
	 

		public static MinuteOfHour convertMinute(String s){
			 
		    switch (Integer.parseInt(s)) {
			case 0:
				return MinuteOfHour.ZERO;
			case 15:
				return MinuteOfHour.FIFTEEN;
			case 30:
				return MinuteOfHour.THIRTY;
			case 45:
				return MinuteOfHour.FORTY_FIVE;
			}
		    return MinuteOfHour.ZERO;
			
		}
}
