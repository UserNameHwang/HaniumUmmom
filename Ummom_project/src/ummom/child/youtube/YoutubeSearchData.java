package ummom.child.youtube;

import android.text.format.Time;

/**
 * @class youtubesearchdata
 * @desc  검색 데이터 저장용 클래스
 * @author Lemoness
 *
 */
public class YoutubeSearchData {
	
	private String mS_VideoId;				// 비디오 아이디 저장
	private String mS_Title;				// 비디오 제목 저장
	private String mS_Url;					// 비디오 경로 URL
	private String mS_PublishedAt;			// 비디오 업로드일자 저장
	
	public YoutubeSearchData(String videoId, String title, String url, String publishedAt) {
		this.mS_VideoId = videoId;
		this.mS_Title = title;
		this.mS_Url = url;
		Time time = new Time();
		time.parse3339(publishedAt);
		this.mS_PublishedAt = time.year + "/" + (time.month+1) + "/" + time.monthDay;
	}
	public YoutubeSearchData(){
		this.mS_VideoId = "";
		this.mS_Title = "";
		this.mS_Url = "";
		this.mS_PublishedAt = "";
	}
	
	public String getVideoId() {
		return mS_VideoId;
	}
	public void setVideoId(String videoId) {
		this.mS_VideoId = videoId;
	}
	public String getTitle() {
		return mS_Title;
	}
	public void setTitle(String title) {
		this.mS_Title = title;
	}
	public String getUrl() {
		return mS_Url;
	}
	public void setUrl(String url) {
		this.mS_Url = url;
	}
	public String getPublishedAt() {
		return mS_PublishedAt;
	}
	public void setPublishedAt(String publishedAt) {
		Time time = new Time();
		time.parse3339(publishedAt);
		this.mS_PublishedAt = time.year + "/" + (time.month+1) + "/" + time.monthDay;
	}
}
