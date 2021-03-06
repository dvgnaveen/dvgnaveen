package adword.UpdateDisplay;
import java.io.FileReader;
/**
 * @author Naveen
 *
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.api.ads.adwords.axis.v201710.cm.AdGroupAd;
import com.google.api.ads.adwords.axis.v201710.cm.AdGroupAdOperation;
import com.google.api.ads.adwords.axis.v201710.cm.AdGroupAdReturnValue;
import com.google.api.ads.adwords.axis.v201710.cm.AdGroupAdServiceInterface;
import com.google.api.ads.adwords.axis.v201710.cm.AdGroupAdStatus;
import com.google.api.ads.adwords.axis.v201710.cm.AdType;
import com.google.api.ads.adwords.axis.v201710.cm.Dimensions;
import com.google.api.ads.adwords.axis.v201710.cm.Image;
import com.google.api.ads.adwords.axis.v201710.cm.Media;
import com.google.api.ads.adwords.axis.v201710.cm.MediaMediaType;
import com.google.api.ads.adwords.axis.v201710.cm.MediaServiceInterface;
import com.google.api.ads.adwords.axis.v201710.cm.MediaSize;
import com.google.api.ads.adwords.axis.v201710.cm.Operator;
import com.google.api.ads.adwords.axis.v201710.cm.ResponsiveDisplayAd;
import com.google.api.ads.common.lib.utils.Maps;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import Path.ImagePath;
import adword.Connector.Session;
import adword.database.MySingleton;
import adword.interfaces.DisplayCampaign;

public class CreateAdsAPI {


	 
	 
	 public static void createUploadAd(Session session,DisplayCampaign camp,long id) throws Exception{
		 
		 System.out.println("create upload Ads");
		 ImagePath gpath = new ImagePath();
		 String path =gpath.getPath();
		 MongoClient client =null;
		 try
		{
			System.out.println("Upload display ");
		 MediaServiceInterface mediaService = session.services.get(session.session, MediaServiceInterface.class);

			    // Create image.
			    Image image = new Image();
			    image.setData(
			        com.google.api.ads.common.lib.utils.Media.getMediaDataFromFile(path+camp.getCreatAd().getUploadDisplayAds().getImage()));
			    image.setType(MediaMediaType.IMAGE);

			    Media[] media = new Media[] {image};

			    // Upload image.
			    Media[] result = mediaService.upload(media);

			    // Display images.
			    image = (Image) result[0];
			    Map<MediaSize, Dimensions> dimensions = Maps.toMap(image.getDimensions());
			    System.out.printf("Image with ID %d, dimensions %dx%d, and MIME type '%s' was "
			        + "uploaded.%n", image.getMediaId(), dimensions.get(MediaSize.FULL).getWidth(),
			        dimensions.get(MediaSize.FULL).getHeight(), image.getMediaType());
			    System.out.println(dimensions);
			    JSONParser parser=new JSONParser();
			   	Object object = parser.parse(new FileReader("/home/dvgnaveen/Adwords api/adwords/new_test_adword/src/config.json"));
			   	JSONObject jsonobject=(JSONObject) object;
			   	String  db_name=(String) jsonobject.get("db_name");
			   	 client = MySingleton.getInstance();
			   @SuppressWarnings("deprecation")
			   	DB db = client.getDB(db_name);  
                DBCollection campaign_collection = db.getCollection("adwords_DisplayAds");
                BasicDBObject newDocument = new BasicDBObject();
                
                    try {
                    	
                    	 newDocument.put("localId", camp.getLocalId());
                    	 newDocument.put("mediaId", image.getMediaId());
                    	 newDocument.put("mediaType", image.getMediaType());
                    	 newDocument.put("creationtime", image.getCreationTime());
                    	 newDocument.put("adgroup_id", id);
                    	 newDocument.put("campaign_Id", camp.getCampaignId());
                    	 BasicDBObject searchQuery = new BasicDBObject().append("campaign_id",id);
 	    				campaign_collection.update(searchQuery, newDocument);  
     	                
     	            }
     	            

     	        catch (Exception e) {
     	            throw e;
     	        }
			  } catch (Exception e) {
				// TODO: handle exception
				  throw e;
			}finally{
	        	//client.close();
	        }

	 }
	 
	 
	 public static List<ResponsiveDisplayAd> updateResponsiveAds(Session session,DisplayCampaign camp,long id,String localImageId) throws Exception {
		 

		 System.out.println("Responsive Display Ads");
ImagePath gpath = new ImagePath();
String path =gpath.getPath();
	 
	 System.out.println("In ads :"+path);
	 MongoClient client =null;
		 try {
			
			 ResponsiveDisplayAd rda = new ResponsiveDisplayAd();
			 List<ResponsiveDisplayAd> list1=new ArrayList<ResponsiveDisplayAd>();
			 
		if(localImageId.equals(camp.getCreatAd().getResponsiveAd().getImage())){
			System.out.println("Delete the adgroupad");
			 System.out.println("Responsive display ");
			 MediaServiceInterface mediaService = session.services.get(session.session, MediaServiceInterface.class);
      System.out.println("________1");

      Image marketingImage = new Image();
      marketingImage.setMediaId(camp.getMediaId());
      rda.setMarketingImage(marketingImage);

      
			
			AdGroupAdServiceInterface adGroupAdService =
	            	session.services.get(session.session, AdGroupAdServiceInterface.class);
	            List<AdGroupAdOperation> operations = new ArrayList<AdGroupAdOperation>();
      System.out.println("________3");
      if(camp.getCreatAd().getResponsiveAd().getShortHeadline()!=null)
      {
      rda.setShortHeadline(camp.getCreatAd().getResponsiveAd().getShortHeadline());
      }
      if(camp.getCreatAd().getResponsiveAd().getLongHeadline()!=null)
      {
	  rda.setLongHeadline(camp.getCreatAd().getResponsiveAd().getLongHeadline());
      }
      if(camp.getCreatAd().getResponsiveAd().getDescription()!=null)
      {
	  rda.setDescription(camp.getCreatAd().getResponsiveAd().getDescription());
      }
      if(camp.getCreatAd().getResponsiveAd().getBussinessName()!=null)
      {
	  rda.setBusinessName(camp.getCreatAd().getResponsiveAd().getBussinessName());
      }
      if(camp.getCreatAd().getResponsiveAd().getFinalUrl()!=null)
      {
	  rda.setFinalUrls(camp.getCreatAd().getResponsiveAd().getFinalUrl());
      }
      if(camp.getCreatAd().getResponsiveAd().getAdvanceUrlOptions()!=null)
      {
	  rda.setTrackingUrlTemplate(camp.getCreatAd().getResponsiveAd().getAdvanceUrlOptions().getTrackingUrl());
      }
      System.out.println("________4");
      AdGroupAd responsiveAd = new AdGroupAd();
      responsiveAd.setAdGroupId(camp.getAdGroupId());
      responsiveAd.setAd(rda);
      responsiveAd.setStatus(AdGroupAdStatus.ENABLED);
      System.out.println("________5");
		 AdGroupAdOperation responsiveDisplayAd = new AdGroupAdOperation();
		 responsiveDisplayAd.setOperand(responsiveAd);
		 responsiveDisplayAd.setOperator(Operator.REMOVE);
		    System.out.println("________6");
		 operations.add(responsiveDisplayAd);
		 
		    System.out.println("________7");
		  AdGroupAdReturnValue result =
			        adGroupAdService.mutate(operations.toArray(new AdGroupAdOperation[operations.size()]));
		  System.out.println(result);
		  ResponsiveDisplayAd newad = null;
		  for (AdGroupAd adGroupAdResult : result.getValue()) {
			  System.out.println(adGroupAdResult);
		       newad = (ResponsiveDisplayAd) adGroupAdResult.getAd();
		      System.out.printf(
		          "Responsive display ad with ID %d and short headline '%s' was removed .%n",
		          newad.getId(), newad.getBusinessName());
		         
		          System.out.println(adGroupAdResult);
					
					
		  }
		  JSONParser parser=new JSONParser();

		   	Object object = parser.parse(new FileReader("/home/dvgnaveen/Adwords api/adwords/new_test_adword/src/config.json"));
		   	JSONObject jsonobject=(JSONObject) object;
		   	String  db_name=(String) jsonobject.get("db_name");
	   	 client =MySingleton.getInstance();
		

		   @SuppressWarnings("deprecation")
		   	DB db = client.getDB(db_name);   	
		  DBCollection lang_collection = db.getCollection("adwords_DisplayAds");
	      BasicDBObject dataD = new BasicDBObject();
	      dataD.put("LocalImageId",camp.getCreatAd().getResponsiveAd().getImage());
	     
	      DBCursor cursor1 = lang_collection.find(dataD);
	      List<String> resp_list = new ArrayList<String>();
	         
	           
	      while(cursor1.hasNext()) {
	    	  cursor1.remove();
				}
		}
			 
		else if(!localImageId.equals(camp.getCreatAd().getResponsiveAd().getImage()))
			{
			 System.out.println("Responsive display ");
			 MediaServiceInterface mediaService = session.services.get(session.session, MediaServiceInterface.class);
      System.out.println("________1");
			    // Create image.
			    Image image = new Image();
			    image.setType(MediaMediaType.IMAGE);
			    System.out.println("_____IMAGE___1");
			    System.out.println("C:\\Users\\Lenovo\\Desktop\\"+camp.getCreatAd().getResponsiveAd().getImage());
			    image.setData(com.google.api.ads.common.lib.utils.Media.getMediaDataFromFile(path+camp.getCreatAd().getResponsiveAd().getImage()));
			    System.out.println(path+camp.getCreatAd().getResponsiveAd().getImage());
			    System.out.println("__IMAGE______2");
			    // Upload image.
			    image = (Image) mediaService.upload(new Media[] {image})[0];
			    System.out.println("_____IMAGE___3");
			    
	            System.out.println("________2");
      
      Image marketingImage = new Image();
      marketingImage.setMediaId(image.getMediaId());
      rda.setMarketingImage(marketingImage);
      if(image.getMediaId()!=null){
      camp.setMediaId(image.getMediaId());
      }	
      
			
			AdGroupAdServiceInterface adGroupAdService =
	            	session.services.get(session.session, AdGroupAdServiceInterface.class);
	            List<AdGroupAdOperation> operations = new ArrayList<AdGroupAdOperation>();
      System.out.println("________3");
      if(camp.getCreatAd().getResponsiveAd().getShortHeadline()!=null)
      {
      rda.setShortHeadline(camp.getCreatAd().getResponsiveAd().getShortHeadline());
      }
      if(camp.getCreatAd().getResponsiveAd().getLongHeadline()!=null)
      {
	  rda.setLongHeadline(camp.getCreatAd().getResponsiveAd().getLongHeadline());
      }
      if(camp.getCreatAd().getResponsiveAd().getDescription()!=null)
      {
	  rda.setDescription(camp.getCreatAd().getResponsiveAd().getDescription());
      }
      if(camp.getCreatAd().getResponsiveAd().getBussinessName()!=null)
      {
	  rda.setBusinessName(camp.getCreatAd().getResponsiveAd().getBussinessName());
      }
      if(camp.getCreatAd().getResponsiveAd().getFinalUrl()!=null)
      {
	  rda.setFinalUrls(camp.getCreatAd().getResponsiveAd().getFinalUrl());
      }
      if(camp.getCreatAd().getResponsiveAd().getAdvanceUrlOptions()!=null)
      {
	  rda.setTrackingUrlTemplate(camp.getCreatAd().getResponsiveAd().getAdvanceUrlOptions().getTrackingUrl());
      }
      System.out.println("________4");
      AdGroupAd responsiveAd = new AdGroupAd();
      responsiveAd.setAdGroupId(camp.getAdGroupId());
      responsiveAd.setAd(rda);
      responsiveAd.setStatus(AdGroupAdStatus.ENABLED);
      System.out.println("________5");
		 AdGroupAdOperation responsiveDisplayAd = new AdGroupAdOperation();
		 responsiveDisplayAd.setOperand(responsiveAd);
		 responsiveDisplayAd.setOperator(Operator.ADD);
		    System.out.println("________6");
		 operations.add(responsiveDisplayAd);
		 
		    System.out.println("________7");
		  AdGroupAdReturnValue result =
			        adGroupAdService.mutate(operations.toArray(new AdGroupAdOperation[operations.size()]));
		  System.out.println(result);
		  ResponsiveDisplayAd newad = null;
		  for (AdGroupAd adGroupAdResult : result.getValue()) {
			  System.out.println(adGroupAdResult);
		       newad = (ResponsiveDisplayAd) adGroupAdResult.getAd();
		      System.out.printf(
		          "Responsive display ad with ID %d and short headline '%s' was added.%n",
		          newad.getId(), newad.getBusinessName());
		          list1.add(newad);
		          System.out.println(adGroupAdResult);
		          
		          JSONParser parser=new JSONParser();

  			   	Object object = parser.parse(new FileReader("/home/dvgnaveen/Adwords api/adwords/new_test_adword/src/config.json"));
  			   	JSONObject jsonobject=(JSONObject) object;
  			   	String  db_name=(String) jsonobject.get("db_name");
	   	 client =MySingleton.getInstance();
  			   	

  			   @SuppressWarnings("deprecation")
  			   	DB db = client.getDB(db_name);   
	                DBCollection campaign_collection = db.getCollection("adwords_DisplayAds");
	                BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();
	                
	                    try {
	                    	 ObjectId oid = ObjectId.get();
	     	  				docBuilder.add("_id", oid);
	     	  				docBuilder.add("localId", camp.getLocalId());
	     	  				docBuilder.add("adId", newad.getId());
	     	  				docBuilder.add("adgroup_id", id);
	     	  				docBuilder.add("campaign_Id", camp.getCampaignId());
	     	  				docBuilder.add("budget_id",camp.getBudgetId());
	     	  				docBuilder.add("ad_type", newad.getAdType());
	    		    		if(newad.getType()!=null){
	    		    		AdType adtype = newad.getType();
	    		    		docBuilder.add("type", adtype.getValue());
	    		    		}
	    		    		docBuilder.add("LocalImageId", camp.getCreatAd().getResponsiveAd().getImage());
	    		    		docBuilder.add("mediaId", camp.getMediaId());	
	    		    		docBuilder.add("display_url",newad.getDisplayUrl());
	    		    		docBuilder.add("description1",newad.getDescription());
	    		    		docBuilder.add("Url",newad.getUrl());
	    		    		docBuilder.add("DevicePreference",newad.getDevicePreference());
	    		    		docBuilder.add("FinalURL",newad.getFinalUrls());
	    		    		docBuilder.add("ShortHeadline",newad.getShortHeadline());
	    		    		docBuilder.add("LongHeadline",newad.getLongHeadline());
	    		    		docBuilder.add("ImagePath", path+camp.getCreatAd().getResponsiveAd().getImage());
	     	  				DBObject insert_object=docBuilder.get();
	     	  				campaign_collection.insert(insert_object);
	     	                
	     	            }
	     	            

	     	        catch (Exception e) {
	     	            throw e;
	     	        }
		          
		    }	        
			}
          return list1;
		 }catch (Exception e) {
			 System.out.println("In catch block");
			 System.out.println(e.getMessage());
             throw e;
         }finally{
         	//client.close();
         }

	
		
	 
	 }

	 
	 
}
