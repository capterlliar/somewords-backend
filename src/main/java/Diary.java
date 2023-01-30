import utils.DbUtil;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/Diary")
@MultipartConfig(location = "D:/CatTemp/somewords/temp",maxFileSize = 1024*1024*5)
public class Diary extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String content=request.getParameter("content");
        log(content);
        String time=request.getParameter("time");
        String location=request.getParameter("location");
        String month=request.getParameter("month");
        String year=time.substring(0,4);
        String type="richText";
        String source="myself";

        String s1="D:\\CatTemp\\somewords\\temp\\";
        String s2="D:\\CatTemp\\somewords\\final\\";
        String pattern="http://localhost:8080/somewords/temp/(.*?)\"";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(content);
        while (m.find()){
            FileInputStream fis = new FileInputStream(s1+m.group(1));
            FileOutputStream fos = new FileOutputStream(s2+m.group(1));
            FileChannel inputChannel = fis.getChannel();
            FileChannel outputChannel = fos.getChannel();
            inputChannel.transferTo(0, inputChannel.size(), outputChannel);
            outputChannel.close();
            inputChannel.close();
            fos.close();
            fis.close();
        }
        File file=new File(s1);
        File[] files = file.listFiles();//将file子目录及子文件放进文件数组
        if (files != null) {//如果包含文件进行删除操作
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {//删除子文件
                    files[i].delete();
                }
                files[i].delete();//删除子目录
            }
        }
        content=content.replaceAll("(http://localhost:8080/somewords/)(temp)(/)","$1final$3");

        DbUtil dbUtil=new DbUtil();
        try {
            dbUtil.init();
            dbUtil.exec("INSERT INTO sw"+
                    year+
                    " (yue,riqi,location,message,type,source) VALUES" +
                    " ('"+month+"','"+time+"','"+location+"','"+content+"','"+type+"','"+source+"')");
            dbUtil.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print("更新成功QWQ");
    }
}

