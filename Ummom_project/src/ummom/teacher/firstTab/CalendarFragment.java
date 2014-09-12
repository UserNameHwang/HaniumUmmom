package ummom.teacher.firstTab;
 
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ummom.login.R;
import ummom.teacher.mainsliding.SlidingTabsBasicFragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;
import android.widget.Toast;
 

@SuppressLint("NewApi") 
public class CalendarFragment extends Fragment implements OnClickListener {
	
	private Calendar rightNow;
    private GregorianCalendar gCal;
    private int iYear = 0;
    private int iMonth = 0;
 
    private int startDayOfweek = 0;
    private int maxDay = 0;
    private int oneday_width =0;
    private int oneday_height =0;
 
    ArrayList<String> daylist; //일자 목록을 가지고 있는다. 1,2,3,4,.... 28?30?31? 
    ArrayList<String> actlist; //일자에 해당하는 활동내용을 가지고 있는다.
 
    TextView aDateTxt;
 
    private int dayCnt;
    private int mSelect = -1;
     
    private Oneday basisDay;
    
    
    private static int this_month=0;
    private static int prev_month=0;
    private static int prev_year =0;
    
    private static int next_month=0;
    private static int next_year =0;
    
    static Oneday tmp= null;
    static String touchDay = "";
    static String click_date;
    
    
    View calView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	
    	calView = inflater.inflate(R.layout.calendarview, container,false);

        rightNow = Calendar.getInstance();
        gCal = new GregorianCalendar();
        iYear = rightNow.get(Calendar.YEAR);
        iMonth = rightNow.get(Calendar.MONTH);
         
        ImageView btnMPrev = (ImageView)calView.findViewById(R.id.btn_calendar_prevmonth);
        btnMPrev.setOnClickListener(this);
        ImageView btnMNext = (ImageView)calView.findViewById(R.id.btn_calendar_nextmonth);
        btnMNext.setOnClickListener(this);
        
        Button btnToday = (Button)calView.findViewById(R.id.btn_today);
        btnToday.setOnClickListener(this);

        aDateTxt = (TextView)calView.findViewById(R.id.CalendarMonthTxt);
 
        makeCalendardata(iYear, iMonth);
         
        basisDay = new Oneday(calView.getContext());
    	
    	return calView;
    }
   
    
    /////////////////////////// Start /////////////////////////////////
  //달력의 일자를 표시한다. 
    private void printDate(String thisYear, String thisMonth)
    {
 
        if(thisMonth.length() == 1) {
            aDateTxt.setText(String.valueOf(thisYear) + "." + "0"+ thisMonth);
        }
        else{
            aDateTxt.setText(String.valueOf(thisYear) + "." + thisMonth);
        }
    }
 
  //달력에 표시할 일자를 배열에 넣어 구성한다. 
    private void makeCalendardata(int thisYear, int thisMonth)
    {
        printDate(String.valueOf(thisYear),String.valueOf(thisMonth+1));
         
        rightNow.set(thisYear, thisMonth, 1);
        gCal.set(thisYear, thisMonth, 1);
        startDayOfweek = rightNow.get(Calendar.DAY_OF_WEEK);
 
 
        maxDay = gCal.getActualMaximum ((Calendar.DAY_OF_MONTH));
        if(daylist==null)daylist = new ArrayList<String>();
        daylist.clear();
 
        if(actlist==null)actlist = new ArrayList<String>();
        actlist.clear();
 
        daylist.add("일");actlist.add("");
        daylist.add("월");actlist.add("");
        daylist.add("화");actlist.add("");
        daylist.add("수");actlist.add("");
        daylist.add("목");actlist.add("");
        daylist.add("금");actlist.add("");
        daylist.add("토");actlist.add("");
 
        if(startDayOfweek != 1) {
            gCal.set(thisYear, thisMonth-1, 1);
            int prevMonthMaximumDay = (gCal.getActualMaximum((Calendar.DAY_OF_MONTH))+2);
            for(int i=startDayOfweek;i>1;i--){
                daylist.add(Integer.toString(prevMonthMaximumDay-i));
                actlist.add("p");
            }
        }
 
        for(int i=1;i<=maxDay;i++) //일자를 넣는다.
        {
            daylist.add(Integer.toString(i));
            actlist.add("");
        }
 
 
        int dayDummy = (startDayOfweek-1)+maxDay;
        if(dayDummy >35)
        {
            dayDummy = 42 - dayDummy;
        }else{
            dayDummy = 35 - dayDummy;
        }
         
      //자투리..그러니까 빈칸을 넣어 달력 모양을 이쁘게 만들어 준다.
        if(dayDummy != 0)
        {
            for(int i=1;i<=dayDummy;i++) 
            {
                daylist.add(Integer.toString(i));
                actlist.add("n");
            }
        }
 
        makeCalendar(thisMonth);
    }
 
 
 
    @SuppressLint("ClickableViewAccessibility") 
    private void makeCalendar(int month)
    {
    	
    	
    	final Oneday[] oneday = new Oneday[daylist.size()];
    	
        final Calendar today = Calendar.getInstance();
        
        
        this_month = month;
        
        final TableLayout tl =(TableLayout)calView.findViewById(R.id.tl_calendar_monthly);
        tl.removeAllViews();
 
        dayCnt = 0;
        int maxRow = ((daylist.size() > 42)? 7:6);
        int maxColumn = 7;
 
 
        oneday_width = 1020;
        oneday_height = 1300;
        
        oneday_height = ((((oneday_height >= oneday_width)?oneday_height:oneday_width) - tl.getTop()) / (maxRow+1))-10;
        oneday_width = (oneday_width / maxColumn)+1;
 
        int daylistsize =daylist.size()-1;
        for(int i=1;i<=maxRow;i++ )
        {
            TableRow tr = new TableRow(calView.getContext());
            tr.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
            for(int j=1;j<=maxColumn;j++)
            {
                //calender_oneday를 생성해 내용을 넣는다. 
                oneday[dayCnt] = new Oneday(getActivity().getApplicationContext());
 
                //요일별 색상 정하기 
                if((dayCnt % 7) == 0){
                    oneday[dayCnt].setTextDayColor(Color.RED);
                } else if((dayCnt % 7) == 6){
                    oneday[dayCnt].setTextDayColor(Color.BLUE);
                } else {
                    oneday[dayCnt].setTextDayColor(Color.BLACK);
                }
                 
                //요일 표시줄 설정
                if(dayCnt >= 0 && dayCnt < 7)
                {
                    oneday[dayCnt].setBgDayPaint(Color.parseColor("#6495ed")); //배경색상 
                    oneday[dayCnt].setTextDayTopPadding(15); //일자표시 할때 top padding 
                    oneday[dayCnt].setTextDayColor(Color.WHITE); //일자의 글씨 색상 
                    oneday[dayCnt].setTextDaySize(40); //일자의 글씨크기 
                    oneday[dayCnt].setLayoutParams(new LayoutParams(oneday_width,55)); //일자 컨트롤 크기 
                    oneday[dayCnt].isToday = false;
                     
                }else{
                     
                    oneday[dayCnt].isToday = false;
                    oneday[dayCnt].setDayOfWeek(dayCnt % 7 + 1);
                    oneday[dayCnt].setDay(Integer.valueOf(daylist.get(dayCnt)).intValue());
                    
                    /* 오늘 이라고 표시 */
                    oneday[dayCnt].setTextActcntSize(25);
                    oneday[dayCnt].setTextActcntColor(Color.RED);
                    oneday[dayCnt].setTextActcntTopPadding(35);
                    oneday[dayCnt].setBgSelectedDayPaint(Color.rgb(0, 162, 232));
                    oneday[dayCnt].setBgTodayPaint(Color.WHITE);
                    oneday[dayCnt].setBgActcntPaint(Color.rgb(251, 247, 176));
                    oneday[dayCnt].setLayoutParams(new LayoutParams(oneday_width,oneday_height));
                    
                    //이전 달 블럭 표시
                    if(actlist.get(dayCnt).equals("p")){
                         oneday[dayCnt].setTextDaySize(35);
                         actlist.set(dayCnt, "");
                         oneday[dayCnt].setTextDayTopPadding(-4);
                         
                         if(iMonth - 1 < Calendar.JANUARY){
                             oneday[dayCnt].setMonth(Calendar.DECEMBER);                             
                             oneday[dayCnt].setYear(iYear - 1);
                             
                         }  else {
                        	 
                             oneday[dayCnt].setMonth(iMonth - 1);
                             oneday[dayCnt].setYear(iYear);
                         }
                         
                         
                         
                         oneday[dayCnt].setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {							
								
								// TODO Auto-generated method stub
								if((this_month-1) <= 0){
                            		prev_month = 12;
                            		prev_year = today.get(Calendar.YEAR) - 1;
                            	}else{
                            		prev_month = this_month -1;
                            		prev_year = today.get(Calendar.YEAR);
                            	}                       	
								
                             	Toast.makeText(getActivity().getBaseContext(), "Touch3 -> "+ prev_year +"-" + prev_month +"-"+oneday[v.getId()].getTextDay(), Toast.LENGTH_LONG).show();
                             	
                             	
							}
						});
                     
                    // 다음 달 블럭 표시
                    } else if(actlist.get(dayCnt).equals("n")){
                        oneday[dayCnt].setTextDaySize(35);
                        actlist.set(dayCnt, "");
                        oneday[dayCnt].setTextDayTopPadding(-4);
                        if(iMonth + 1 > Calendar.DECEMBER){
                            oneday[dayCnt].setMonth(Calendar.JANUARY);
                            oneday[dayCnt].setYear(iYear + 1);
                            
                        }  else {
                        	
                            oneday[dayCnt].setMonth(iMonth + 1);
                            oneday[dayCnt].setYear(iYear);
                        }                       
                        
                        oneday[dayCnt].setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								if( (this_month+1) > Calendar.DECEMBER ){
                            		next_month = 1;
                            		next_year = today.get(Calendar.YEAR) + 1;
                            	}else{
                            		next_month = this_month + 1;
                            		next_year = today.get(Calendar.YEAR);
                            	}                	
                            	
                            	Toast.makeText(getActivity().getBaseContext(), "Touch2 -> "+ next_year +"-"+ next_month +"-"+oneday[v.getId()].getTextDay(), Toast.LENGTH_LONG).show();
   
							}
						});
                    // 현재 달 블력 표시
                    }else{
                        oneday[dayCnt].setTextDaySize(55);
                        oneday[dayCnt].setYear(iYear);
                        oneday[dayCnt].setMonth(iMonth);
                        
                         
                        
                        //오늘 표시
                        if(oneday[dayCnt].getDay() == today.get(Calendar.DAY_OF_MONTH)
                                && oneday[dayCnt].getMonth() == today.get(Calendar.MONTH)
                                && oneday[dayCnt].getYear() == today.get(Calendar.YEAR)){
                             	
                             	oneday[dayCnt].isToday = true;
                                actlist.set(dayCnt,"오늘");
                                oneday[dayCnt].invalidate();
                                mSelect = dayCnt;                            
                                 
                                
                                int year = today.get(Calendar.YEAR);
                                int month1 = today.get(Calendar.MONTH) + 1;
                                int day = today.get(Calendar.DAY_OF_MONTH);
                                
                                if(month1 > Calendar.DECEMBER){
                                	month1 = 12;
                            	}else{
                            		month1 = oneday[dayCnt].getMonth() + 1;
                            	} 
                                
                                touchDay = Integer.toString(year) + "-" + 
                                Integer.toString(month1) + "-" + Integer.toString(day);
                                
                                click_date = touchDay;
                        }
                        
                        this_month = oneday[dayCnt].getMonth() + 1;
                        
                        
                        // Touch시 year-month-day 표시하는 부분.
                        oneday[dayCnt].setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								
								// TODO Auto-generated method stub
								if(this_month > Calendar.DECEMBER){
                            		this_month = 12;
                            	}else{
                            		this_month = oneday[dayCnt].getMonth();
                            	}             	
                            	
								if(tmp == null){                            		
                            		oneday[v.getId()].setBgDayPaint(Color.BLUE);
                            		tmp = oneday[v.getId()];
                            		
                            		
                            		//v.refreshDrawableState();
                            		
                            	}else{
                            		tmp.setBgDayPaint(Color.WHITE);
                            		oneday[v.getId()].setBgDayPaint(Color.BLUE);
                            		tmp = oneday[v.getId()];
                            		
                            		
                            		//v.refreshDrawableState();
                            	}
						
                            	click_date = iYear +"-"+ this_month+"-"+oneday[v.getId()].getTextDay();
                            	
                            	CalendarThread calendarthread = new CalendarThread(click_date, "test");
                            	
                            	calendarthread.start();
                            	
                            	try {
                            		ScheduleHomeworkFragment homefrag = (ScheduleHomeworkFragment) getFragmentManager().findFragmentById(R.id.fragment_scedule_homework);
                                	ScheduleEventsFragment eventsfrag = (ScheduleEventsFragment) getFragmentManager().findFragmentById(R.id.fragment_scedule_evnets);
									calendarthread.join();									
									homefrag.viewClear();
									eventsfrag.viewClear();
									
									if (calendarthread.getCount() > 0) {
										JSONArray array = calendarthread.getJarray();										
										for(int i=0; i < calendarthread.getCount() ; i++){											
											JSONObject tmp = array.getJSONObject(i);											
											int type = Integer.parseInt(tmp.getString("schedule_type"));											
											if(type == 1){
												homefrag.setOnView(tmp.getString("schedule_title"), 
														tmp.getString("schedule_des"), tmp.getString("schedule_id"));
											}else{
												eventsfrag.setOnView(tmp.getString("schedule_title"), 
														tmp.getString("schedule_des"), tmp.getString("schedule_id"));
											}		
											
										}
									}else {
										
									}
									
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
                            	
                            	
                            }
						});
                        
                        
                    }          
                }                
                 
                
                oneday[dayCnt].setTextDay(daylist.get(dayCnt).toString()); //요일,일자 넣기 
                oneday[dayCnt].setTextActCnt(actlist.get(dayCnt).toString());//활동내용 넣기 
                oneday[dayCnt].setId(dayCnt); //생성한 객체를 구별할수 있는 id넣기 
                oneday[dayCnt].invalidate();
                
                tr.addView(oneday[dayCnt]);
                
                if(daylistsize != dayCnt)
                {
                    dayCnt++;
                }else{
                    break;
                }
            }
            
            tl.addView(tr,new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
 
        }
    }
     
     
     
    
    /**
     * 숫자를 2자리 문자로 변환, 2 -> 02
     * @param value
     * @return
     */
    protected String doubleString(int value)
    {
        String temp;
 
        if(value < 10){
            temp = "0"+ String.valueOf(value);
             
        }else {
            temp = String.valueOf(value);
        }
        return temp;
    }
 
    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
        case R.id.btn_calendar_nextmonth:
            if(iMonth == 11)
            {
                iYear = iYear + 1;
                iMonth = 0;
            }
            else
            {
                iMonth = iMonth + 1;
            }
            
            
            makeCalendardata(iYear,iMonth);
            break;
        case R.id.btn_calendar_prevmonth:
            if(iMonth == 0)
            {
                iYear = iYear - 1;
                iMonth = 11;
            }else{
                iMonth = iMonth - 1;
            }
            
            
            makeCalendardata(iYear,iMonth);
            break;
        
        case R.id.btn_today:
        	gotoToday();
        	break;
        }
        
    }
    
    /**
     * 해당 일이 기준일 범위 안에 있는지 검사
     * @param test 검사할 날짜
     * @param basis 기준 날짜
     * @param during 기간(일)
     * @return
     */
    protected boolean isInside(Oneday test, Oneday basis, int during){
        Calendar calbasis = Calendar.getInstance();
        calbasis.set(basis.getYear(), basis.getMonth(), basis.getDay());
        calbasis.add(Calendar.DAY_OF_MONTH, during);
         
        Calendar caltest = Calendar.getInstance();
        caltest.set(test.getYear(), test.getMonth(), test.getDay());
         
        if(caltest.getTimeInMillis() < calbasis.getTimeInMillis()){
            return true;
        }
        return false;
    }
     
    /**
     *오늘 달력으로 이동 
     */
    public void gotoToday(){
        final Calendar today = Calendar.getInstance();
        iYear = today.get(Calendar.YEAR);
        iMonth = today.get(Calendar.MONTH);
        makeCalendardata(today.get(Calendar.YEAR),today.get(Calendar.MONTH));
    }   
     
}