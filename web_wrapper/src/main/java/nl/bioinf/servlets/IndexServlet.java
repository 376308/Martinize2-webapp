package nl.bioinf.servlets;

import nl.bioinf.config.WebConfig;
import nl.bioinf.scripts.CommandlineBuilder;
import org.thymeleaf.context.WebContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
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
        CommandlineBuilder cmdBuilder = new CommandlineBuilder();
        HttpSession session = request.getSession(true);
        String sessionId = session.getId();
        File sessionDir = new File(getServletContext().getInitParameter("temp-dir") + "/" + sessionId + "/");
        sessionDir.mkdir();

        try {
            Collection<Part> parts = request.getParts();
            for (Part filePart : parts) {
                switch (filePart.getName()) {
                    case "PDBfile":
                        String file1 = filePart.getSubmittedFileName();
                        ctx.setVariable("file1", file1);
                        filePart.write(sessionDir + "" + "\\" + file1);
                        cmdBuilder.setPdbFile(sessionDir + "\\" + file1);
                }
                message = "Finished processing";
            }
        } catch (ServletException e) {
            message = "Error uploading file:" + e.getMessage();
        }


        if (request.getParameter("forcefield").length() >  1) {
            String forceField = request.getParameter("forcefield");
            cmdBuilder.setForcefield(forceField);
            System.out.println(forceField);
        }
        cmdBuilder.buildLine(sessionDir);

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
}
