package ummom.child.youtube;

import java.util.ArrayList;

public class YoutubeDataset {
	private ArrayList<YoutubeSearchData> mData;
	private String mQuery;
	private int mTotalResult;
	private int mNowpage;
	private int mResultperPage;
	private String mNextToken;
	
	public YoutubeDataset(){
		mData = new ArrayList<YoutubeSearchData>();
		mQuery = null;
		mTotalResult = 0;
		mNowpage = 0;
		mResultperPage = 0;
		mNextToken = null;
	}
	
	public void addData(YoutubeSearchData data){
		mData.add(data);
	}
	public void addData(ArrayList<YoutubeSearchData> data){
		for(int i=0 ; i<data.size() ; i++){
			mData.add(data.get(i));
		}
	}
	public void setData(ArrayList<YoutubeSearchData> data){
		mData = data;
	}
	public void setQuery(String query){
		mQuery = query;
	}
	public void setTotalResult(int num){
		mTotalResult = num;
	}
	public void setResultperPage(int num){
		mResultperPage = num;
	}
	public void setNextToken(String str){
		mNextToken = str;
	}
	public void setNowpage(int num){
		mNowpage = num;
	}
	
	public ArrayList<YoutubeSearchData> getData(){
		return mData;
	}
	public YoutubeSearchData getData(int index){
		return mData.get(index);
	}
	public String getQuery(){
		return mQuery;
	}
	public int getTotalResult(){
		return mTotalResult;
	}
	public int getResultperPage(){
		return mResultperPage;
	}
	public String getNextToken(){
		return mNextToken;
	}
	public int getNowpage(){
		return mNowpage;
	}
}
