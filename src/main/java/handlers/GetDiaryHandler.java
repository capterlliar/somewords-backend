package handlers;

import com.google.gson.Gson;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

class DiaryItem {
    String date;
    String location;
    String type;
    String source;
    String diary;
    DiaryItem(String d, String l, String t, String s,String di){
        date=d;location=l;
        type=t;source=s;
        diary=di;
    }
}

class MonthItem {
    String month;
    ArrayList<DiaryItem> content;
    MonthItem(){
        content=new ArrayList<>();
    }
}

public class GetDiaryHandler implements DbHandler {
    @Override
    public String handle(ResultSet resultSet) throws SQLException {
        Gson gson = new Gson();
        ArrayList<MonthItem> res = new ArrayList<>();
        String now = "";
        MonthItem nowItem=null;
        while (resultSet.next()){
            String month = resultSet.getString("yue");
            String date = resultSet.getString("riqi");
            String location = resultSet.getString("location");
            String diary = resultSet.getString("message");
            String type = resultSet.getString("type");
            String source = resultSet.getString("source");
            if(!Objects.equals(month, now)){
                if(nowItem!=null) res.add(nowItem);
                now=month;
                nowItem=new MonthItem();
                nowItem.month=month;
            }
            nowItem.content.add(new DiaryItem(date,location,type,source,diary));
        }
        if(nowItem!=null) res.add(nowItem);
        return gson.toJson(res);
    }
}
