package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class headerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String title = (String) req.getAttribute("title");
		String header ="<!DOCTYPE html>\n"
        		+ "<HTML>\n"
        		+ "<head>\n"
        		+ "<style>\n"
        		+ "table {\n"
        		+ "  border-collapse: collapse;\n"
        		+ "  width: 100%;\n"
        		+ "}\n"
        		+ "img {\n"
        		+ "	border: 0;\n"
        		+ "}"
        		+ "\n"
        		+ "th, td {\n"
        		+ "  text-align: left;\n"
        		+ "  padding: 8px;\n"
        		+ "}\n"
        		+ "\n"
        		+ "tr:nth-child(even) {\n"
        		+ "  background-color: #D6EEEE;\n"
        		+ "}\n"
        		+ "form {\n"
        		+ "  /* Center the form on the page */\n"
        		+ "  margin: 0 auto;\n"
        		+ "  width: 600px;\n"
        		+ "  /* Form outline */\n"
        		+ "  padding: 1em;\n"
        		+ "  border: 1px solid #ccc;\n"
        		+ "  border-radius: 1em;\n"
        		+ "}\n"
        		+ "\n"
        		+ "ul {\n"
        		+ "  list-style: none;\n"
        		+ "  padding: 0;\n"
        		+ "  margin: 0;\n"
        		+ "}\n"
        		+ "\n"
        		+ "form li + li {\n"
        		+ "  margin-top: 1em;\n"
        		+ "}\n"
        		+ "\n"
        		+ "label {\n"
        		+ "  /* Uniform size & alignment */\n"
        		+ "  display: inline-block;\n"
        		+ "  width: 200px;\n"
        		+ "  text-align: left;\n"
        		+ "}\n"
        		+ "input,\n"
        		+ "textarea {\n"
        		+ "  /* To make sure that all text fields have the same font settings\n"
        		+ "     By default, textareas have a monospace font */\n"
        		+ "  font: 1em sans-serif;\n"
        		+ "\n"
        		+ "  /* Uniform text field size */\n"
        		+ "  width: 600px;\n"
        		+ "  box-sizing: border-box;\n"
        		+ "\n"
        		+ "  /* Match form field borders */\n"
        		+ "  border: 1px solid #999;\n"
        		+ "}\n"
        		+ "\n"
        		+ "input:focus,\n"
        		+ "textarea:focus {\n"
        		+ "  /* Additional highlight for focused elements */\n"
        		+ "  border-color: #000;\n"
        		+ "}\n"
        		+ "\n"
        		+ "textarea {\n"
        		+ "  /* Align multiline text fields with their labels */\n"
        		+ "  vertical-align: top;\n"
        		+ "\n"
        		+ "  /* Provide space to type some text */\n"
        		+ "  height: 5em;\n"
        		+ "}\n"
        		+ "\n"
        		+ ".button {\n"
        		+ "  padding-left: 50px; \n"
        		+ "}\n"
        		+ "\n"
        		+ "button {\n"
        		+ "  /* This extra margin represent roughly the same space as the space\n"
        		+ "     between the labels and their text fields */\n"
        		+ "  margin-left: 0.5em;\n"
        		+ "}"
        		+ "</style>\n"
        		+ "<title>ArtWorldArtists</title>\n"
        		+ "</head>\n"
        		+ "<body>\n"
        		+ "<img src=\"images/LogoMakr-5kXT6J.png\" alt=\"\">"
        		+ "<h1>"+title+"</h1>\n";
		PrintWriter out = res.getWriter();
		out.println(header);
	}


}
