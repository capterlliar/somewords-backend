import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Collection;
import java.util.UUID;

@WebServlet("/Picture")
@MultipartConfig(location = "D:/CatTemp/somewords/temp",maxFileSize = 1024*1024*5)
public class Picture extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Collection<Part> files = request.getParts();
        StringBuilder res = new StringBuilder();
        for(Part part : files){
            String originName = part.getSubmittedFileName();
            int index = originName.lastIndexOf('.');
            String suf="";
            if(index!=-1) suf = originName.substring(index);
            String filename = UUID.randomUUID() + suf;
            part.write(filename);
            res.append(",").append(filename);
        }
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(res);
    }
}

