import handlers.GetDiaryHandler;
import utils.DbUtil;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/GetDiary")
@MultipartConfig(location = "D:/CatTemp/somewords/temp",maxFileSize = 1024*1024*5)
public class GetDiary extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String year = request.getParameter("year");
        DbUtil dbUtil = new DbUtil();
        String res="";
        try {
            dbUtil.init();
            if(!year.equals("2021"))
                dbUtil.exec("CREATE TABLE if NOT exists sw"+year+" like sw2021");
            res=dbUtil.exec("SELECT * FROM sw"+year, new GetDiaryHandler());
            dbUtil.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(res);
    }
}

