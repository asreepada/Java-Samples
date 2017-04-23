import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class Solution1 {	
	public int solution(String S) {
		String key = null;		
		int amountPayable = 0;
		HashMap<String,List<String>> hMap = new HashMap<String,List<String>>();
		HashMap<String, Integer> resultMap = new HashMap<String,Integer>();		
		
		String[] rows = S.split("\n");			
		for(int i=0;i<rows.length;i++) {
			//split each row by ,
			String[] s = rows[i].split(",");
			
			//remove : so that I can compare values later
			String resultKey = s[1].replaceAll("[-+.^:,]","").trim();
			
			//add the key value pay to hash map
			// key - mobile number value - duration
			//if there are multiple duration for the same ph number its added to list
			if(hMap.containsKey(resultKey)==true) {
				
	        	List<String> ld = hMap.get(resultKey);
	        	ld.add(s[0]);
	        	hMap.put(resultKey, ld);
	        }
	        else {
	        	List<String> value = new ArrayList<String>();
	        	value.add(s[0]);
	            hMap.put(resultKey, value);
	        }	
		}
		
		for (Entry<String, List<String>> ee : hMap.entrySet()) {
			int duration = 0;
			key = ee.getKey();
		    
		    List<String> values = ee.getValue();
		    
		    for(int i =0;i<values.size();i++) {	    	
		    	String[] timeStr = values.get(i).trim().split(":");  	
		    	
		    	int hours = Integer.parseInt(timeStr[0]);		    	
		    	int minutes = Integer.parseInt(timeStr[1]);		    	
		    	int seconds = Integer.parseInt(timeStr[2]);	
		    	duration += 3600 *hours +  60*minutes + seconds;;
		    	
		    }				    
		    resultMap.put(key, duration);	    
		}
		
		int maxTimeValueInMap=(Collections.max(resultMap.values()));		
		String wavePhTime = null;
		
		//Calculate total amount
		for(String k : resultMap.keySet()) {
			
			int timeToKnowWhichTimeValeToWave = resultMap.get(k);
			int timeToCalcAmountPayable = getTimeLessThanFive(hMap.get(k));
							
			//in case multiple numbers have max call duration
			if(maxTimeValueInMap == timeToKnowWhichTimeValeToWave) {
				
				if(wavePhTime==null) {
					wavePhTime = k;
				}
				else if(Integer.valueOf(k) < Integer.valueOf(wavePhTime)) {
					wavePhTime = k;
				}				
			}
			
			if(timeToCalcAmountPayable < 5) {				
				amountPayable += 3*getTime(hMap.get(k),0);				
			}
			if(timeToCalcAmountPayable >= 5) {				
				amountPayable += 150*getTime(hMap.get(k),1);				
			}			
		}		
		int deductableAmount = amountToDeduct(hMap.get(wavePhTime));
		amountPayable = amountPayable - deductableAmount;	
		return amountPayable;
	}
	
	//check if the duration is less than 5 minutes
	public int getTimeLessThanFive(List<String> list) {
		
		int sumOfTalkTimes = 0;
		for(String s : list) {			
			String[] timeStr = s.trim().split(":");  	    	
	    	int hours = Integer.parseInt(timeStr[0]);		    	
	    	int minutes = Integer.parseInt(timeStr[1]);		    	
	    	sumOfTalkTimes += 60 *hours +  minutes;
		}		
		return sumOfTalkTimes;
		
	}
	
	//The amount to be waved off
	public int amountToDeduct(List<String> list) {
		int amount = 0;
		int timeToReturn = 0;
		int timeCheck = 0;
		
		for(String s : list) {			
			String[] timeStr = s.trim().split(":");	    	
	    	int hours = Integer.parseInt(timeStr[0]);		    	
	    	int minutes = Integer.parseInt(timeStr[1]);		    	
	    	timeCheck += 60 *hours + minutes;
		}
		
		if(timeCheck < 5) {
			for(String s : list) {				
				String[] timeStr = s.trim().split(":"); 		    	
		    	int hours = Integer.parseInt(timeStr[0]);		    	
		    	int minutes = Integer.parseInt(timeStr[1]);		    	
		    	int seconds = Integer.parseInt(timeStr[2]); 	    	
		    		
		    	timeToReturn += 3600 *hours + 60*minutes+seconds;
			}
			amount = timeToReturn*3;			
		}
		else {
			for(String s : list) {				
				String[] timeStr = s.trim().split(":"); 		    	
		    	int hours = Integer.parseInt(timeStr[0]);		    	
		    	int minutes = Integer.parseInt(timeStr[1]);		    	
		    	int seconds = Integer.parseInt(timeStr[2]);	    	
		    	timeToReturn += 60 *hours + minutes+seconds;
			}
			amount = timeToReturn*150;		
		}		
		return amount;
	}
	
	//Gets the time to calculate the amount payable
	public int getTime(List<String> list,int flag) {
		int timeToReturn = 0;
		for(String s : list) {
			
			String[] timeStr = s.trim().split(":");	    	
	    	int hours = Integer.parseInt(timeStr[0]);		    	
	    	int minutes = Integer.parseInt(timeStr[1]);		    	
	    	int seconds = Integer.parseInt(timeStr[2]);
	    	if(flag == 0) {
	    		timeToReturn += 3600 *hours +  60*minutes+seconds;
	    	}
	    	else {
	    		timeToReturn += 60 *hours + minutes+seconds;
	    	}	    	
		}		
		return timeToReturn;
	}
	
	public static void main(String[] args){
		String S =  "00:01:07,400-234-090\n"
				  +" 00:05:01,701-080-080\n"
				   +"00:05:00,400-234-090\n";
		Solution1 s1 = new Solution1();
		System.out.println("Amount payable is : "+s1.solution(S));
	}
}
