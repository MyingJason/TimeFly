package scoreFactory;


import java.util.ArrayList;
import java.util.List;



import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class ScoreFactory{
	int MAX=10;

	private ParseObject last=null;
	private int lowest = 0;
	private List<ParseObject> objects;
	
	ArrayList<String> nameList;
	ArrayList<Integer> scoreList;
	private ParseObject local=null;
	
	public ScoreFactory(){
		ListLowestLast();
		nameList = new ArrayList<String>();
		scoreList = new ArrayList<Integer>();
		setLists();	
		setLocal();
	}
	
	private void setLists(){
		Integer temp;
		for(int i =0; i<10; i++){
			nameList.add(objects.get(i).getString("name"));
			temp = Integer.valueOf(objects.get(i).getInt("score"));
			scoreList.add(temp);
		}
	}
	
	private void setLocal(){
		ParseQuery<ParseObject> query = ParseQuery.getQuery("TimeFly");
		query.fromLocalDatastore();
		try {
			local = query.find().get(0);
		}catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getLocal(){
		return local.getInt("score");
	}
	
	private void ListLowestLast(){
		 ParseQuery<ParseObject> query = ParseQuery.getQuery("TimeFly");
		 query.addDescendingOrder("score");
		 try {
			objects = query.find();
			if(objects.size()>MAX-1){
				last = objects.get(MAX-1);
				lowest = last.getInt("score");
				last = objects.get(MAX-1);
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	//rank =11 means out of 10;
	public int getRank(int score){
		int rank = 0;
		for (int i=0;i<MAX;i++){
			if (objects.get(i).getInt("score")<score)break;
			rank++;
		}
		rank++;
		return rank;
	}
	private ParseObject genResult(String name, int score){
		ParseObject result = new ParseObject("TimeFly");
		result.put("name", name);
		result.put("score", score);
		return result;
	}

	//get the 10th score, if there are less than 10 in the board, lowest = 0
	public boolean isNeedPost(ParseObject result){
		if(result.getInt("score")>lowest)return true;
		return false;
	}
	public boolean isNeedPost(int score){
		if(score>lowest)return true;
		return false;
	}
	
	public boolean saveToServer(String name, int score){
		ParseObject result = genResult(name,score);
		
		try{
			result.saveInBackground();
			deleteLast();
			return true;
			
		}catch(Exception e){
			return false;
		}
		

		
	}
	public void saveInLocal(String name, int score){
		if(score>getLocal()){
			ParseObject result = genResult(name,score);
			result.pinInBackground();
		}
	}
	
	private void deleteLast(){
		if(last!=null)last.deleteInBackground();
	}
	
	public int nextScore(int rank){
		if(rank ==1)return objects.get(0).getInt("score");
		return objects.get(rank-2).getInt("score");
	}
	
	public ArrayList<Integer> getScoreList(){
		return scoreList;
	}
	
	public ArrayList<String> getNameList(){
		return nameList;
	}

}
