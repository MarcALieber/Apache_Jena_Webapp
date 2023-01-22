package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.sparql.exec.http.QueryExecutionHTTP;

import apache.jena.artWorld.Base;


public class mvmtServlet extends HttpServlet {
       
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter out = res.getWriter();
        String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
        		+ "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"
        		+ "prefix mvmt: <http://www.art.org/ontology/movement#> \n"
        		+ "select ?itemID (str(?movementName) as ?mvmt)\n"
        		+ "where {\n"
        		+ "     GRAPH <http://www.art.org/movement> {?movement rdf:type <http://www.art.org/ontology#movement> ; rdfs:label ?movementName FILTER ( lang(?movementName) = \"en\" ) . }\n"
        		+ "   BIND(replace(str(?movement),\"http://www.art.org/ontology/movement#\",\"\") as ?itemID )\n"
        		+ "  }\n"
        		+ "order by ?movementName";
        
		out.println("<label for=\"mvmt\">Movement</label>\n"
				+ "		  <select id=\"mvmt\" name=\"mvmt\">\n"
				+ "	        <option value=\"all\" selected >all</option>" );
		
		try ( QueryExecution qExec = QueryExecutionHTTP.create()
                .endpoint(Base.queryService)
                .query(queryString)
                .param("timeout", "10000")
                .build() ) {
		ResultSet results = qExec.execSelect();
        
        
        for ( ; results.hasNext() ; )
        {
        	QuerySolution rb = results.nextSolution() ;
            RDFNode x = rb.get("itemID") ;
            RDFNode y = rb.get("mvmt") ;
            out.println("<option value=\""+x.toString()+"\">"+y.toString()+"</option>") ;
	        }
        
		}
		out.println("</select><br/><br/>");
	}
}
