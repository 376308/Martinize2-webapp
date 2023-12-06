package nl.bioinf.servlets;

import nl.bioinf.config.WebConfig;
import org.thymeleaf.context.WebContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
@WebServlet(name = "IndexServlet", value = "/index")

public class IndexServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        System.out.println("Initializing Thymeleaf template engine");
        final ServletContext servletContext = this.getServletContext();
        WebConfig.createTemplateEngine(servletContext);
    }
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String message = "";
        WebConfig.configureResponse(response);
        WebContext ctx = new WebContext(
                request,
                response,
                request.getServletContext());
        int index = 1;
        try {
            Collection<Part> parts = request.getParts();
            for (Part filePart : parts){
                String fileName = getFilename(filePart);
                filePart.write(fileName);
                ctx.setVariable("file" + index, fileName);
                index += 1;
            }
            message = "Your files have been uploaded successfully!";
        } catch (ServletException e) {
            message = "Error uploading file:" + e.getMessage();
        }

        ctx.setVariable("message", message);
        WebConfig.createTemplateEngine(getServletContext()).
                process("index", ctx, response.getWriter());;
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        process(request, response);
    }
    public void process(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        //this step is optional; standard settings also suffice
        WebConfig.configureResponse(response);
        WebContext ctx = new WebContext(
                request,
                response,
                request.getServletContext(),
                request.getLocale());
        ctx.setVariable("currentDate", new Date());
        WebConfig.createTemplateEngine(getServletContext()).
                process("index", ctx, response.getWriter());
    }
    private String getFilename(Part part){
        String contentDisposition = part.getHeader("content-disposition");
        if (!contentDisposition.contains("filename=")){
            return null;
        }
        int beginIndex = contentDisposition.indexOf("filename=") + 10;
        int endIndex = contentDisposition.length() - 1;
        return contentDisposition.substring(beginIndex, endIndex);
    }
}
