package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.sparql.exec.http.QueryExecutionHTTP;
import apache.jena.artWorld.Base;


/**
 * Servlet implementation class QuickServlet
 */
@WebServlet("/ArtOverviewServlet")
public class ArtOverviewServlet extends HttpServlet {
	   private static final long serialVersionUID = 1L;

	/**
     * this life-cycle method is invoked when this servlet is first accessed
     * by the client
     */
    public void init(ServletConfig config) {
        //writer.println("Servlet is being initialized");
    }
 
    /**
     * handles HTTP GET request
     * @throws ServletException 
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
 
    	String header ="<!DOCTYPE html>\n"
        		+ "<HTML>\n"
        		+ "<head>\n"
        		+ "<style>\n"
        		+ "table {\n"
        		+ "  border-collapse: collapse;\n"
        		+ "  width: 100%;\n"
        		+ "}\n"
        		+ "\n"
        		+ "th, td {\n"
        		+ "  text-align: left;\n"
        		+ "  padding: 8px;\n"
        		+ "}\n"
        		+ "\n"
        		+ "tr:nth-child(even) {\n"
        		+ "  background-color: #D6EEEE;\n"
        		+ "}\n"
        		+ "</style>\n"
        		+ "<title>ArtWorld_overview</title>\n"
        		+ "</head>\n"
        		+ "<body>\n"
        		+ "<h1>Fuseki ArtWorld triple store overview</h1>\n"
        		+ "<table border=\"1\">\n"
        		+ "<tr>\n"
        		+ "    <th>Named Graph</th>\n"
        		+ "    <th>Number of triples</th>\n"
        		+ "</tr>\n";
        PrintWriter writer = response.getWriter();
        //writer.println("<html>Hello Marc, I am a Java servlet!  </html>");
        //writer.flush();
        //writer.println("call "+queryService);
        
	    //Model model = ModelFactory.createDefaultModel();
	    //Model model;
	    Query queryString = QueryFactory.create("SELECT ?NamedGraph (STR(COUNT(?s)) AS ?countOfs)  \n"
        		+ "WHERE { GRAPH ?NamedGraph { ?s ?p ?o . } \n"
        		+ "  } GROUP BY (?NamedGraph)" );
        Query  query2 = QueryFactory.create("SELECT (\"default Graph\" as ?NamedGraph) (STR(COUNT(?s)) AS ?countOfs)  \n"
        		+ "WHERE { ?s ?p ?o .  \n"
        		+ "  }" );
        Query query3 = QueryFactory.create("PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
        		+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"
        		+ "PREFIX dc:  <http://purl.org/dc/elements/1.1/>\n"
        		+ "PREFIX artw: <http://www.art.org/>\n"
        		+ "PREFIX :    <.>\n"
        		+ "SELECT ?g ?o (STR(count(?o)) as ?countClass)\n"
        		+ "{\n"
        		+ "  { ?s a ?o  } UNION { GRAPH ?g { ?s a ?o } }\n"
        		+ "  filter (?g != artw:ontology || (?g = \"\")) \n"
        		+ "  filter (?o != owl:NamedIndividual)\n"
        		+ "}\n"
        		+ "group by ?g ?o\n"
        		+ "order by ?g ?o\n");

	     ResultSet results;
	     writer.println(header);
				//writer.println(queryString+"\n");
			// named Graphs
			try ( QueryExecution qExec = QueryExecutionHTTP.create()
	                    .endpoint(Base.queryService)
	                    .query(queryString)
	                    .param("timeout", "10000")
	                    .build() ) {
	            results = qExec.execSelect();
	            for ( ; results.hasNext() ; )
	            {
	            	writer.println("<tr>");
	            	QuerySolution rb = results.nextSolution() ;
	                RDFNode y = rb.get("countOfs") ;
	                RDFNode x = rb.get("NamedGraph") ;
	                writer.println("<td>"+x.toString()+"</td><td>"+y.toString()+"</td>") ;
	                writer.println("</tr>");
		        }
	            
			}
				// default graph
				try ( QueryExecution qExec2 = QueryExecutionHTTP.create()
	                    .endpoint(Base.queryService)
	                    .query(query2)
	                    .param("timeout", "10000")
	                    .build() ) {
	            results = qExec2.execSelect();
	            for ( ; results.hasNext() ; )
		            {
		            	writer.println("<tr>");
		            	QuerySolution rb = results.nextSolution() ;
		                RDFNode y = rb.get("countOfs") ;
		                RDFNode x = rb.get("NamedGraph") ;
		                writer.println("<td>"+x.toString()+"</td><td>"+y.toString()+"</td>") ;
		                writer.println("</tr>");
			        }
				}
				writer.println("</table><br/>\n"
						+ "<table border=\"1\">\n"
		        		+ "<tr>\n"
		        		+ "    <th>Named Graph</th>\n"
		        		+ "    <th>Class Name</th>\n"
		        		+ "    <th>Number of classes</th>\n"
		        		+ "</tr>\n");
			    // classes per named Graph
	            try ( QueryExecution qExec1 = QueryExecutionHTTP.create()
	                    .endpoint(Base.queryService)
	                    .query(query3)
	                    .param("timeout", "10000")
	                    .build() ) {
	            results = qExec1.execSelect();
	            for ( ; results.hasNext() ; )
	            {
	            	writer.println("<tr>");
	            	QuerySolution rb = results.nextSolution() ;
	                RDFNode y = rb.get("?countClass") ;
	                RDFNode x = rb.get("g") ;
	                RDFNode z = rb.get("o") ;
	                String zz = z.toString().replace("#","!"); //&#35;
	                String xx = x.toString()+"A";
	                if (xx.equals("A")   ) 
	                { xx ="defaultGraph";}
	                else
	                {
	                 xx = x.toString().replace("#","!"); }
	                
	                writer.println("<td>"+xx+"</td><td>");
	                writer.println("<A HREF=class?ngraph="+xx+"&rdfclass="+zz+"&cnt="+y+">"+z.toString()+"</A></td><td>"+y.toString()+"</td>") ;
	                writer.println("</tr>");
		        }
			}
		   
		   writer.println("</table><br/>\n"
		   		+ "</body>\n"
		   		+ "</HTML>\n");
		   }
 
    /**
     * handles HTTP POST request
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String paramWidth = request.getParameter("width");
        int width = Integer.parseInt(paramWidth);
 
        String paramHeight = request.getParameter("height");
        int height = Integer.parseInt(paramHeight);
 
        long area = width * height;
 
        PrintWriter writer = response.getWriter();
        writer.println("<html>Area of the rectangle is: " + area + "</html>");
        writer.flush();
 
    }
 
    /**
     * this life-cycle method is invoked when the application or the server
     * is shutting down
     */
    public void destroy() {
        //system.out.println("Servlet is being destroyed");
    }
}