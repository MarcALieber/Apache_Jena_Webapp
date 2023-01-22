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


public class ctryServlet extends HttpServlet {
       
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter out = res.getWriter();
        String queryString = "PREFIX person:  <http://www.art.org/ontology/person#> \n"
        		+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
        		+ "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"
        		+ "prefix world:     <http://www.art.org/ontology/world#> \n"
        		+ "select distinct ?itemID (str(?ctryName) as ?countryName)\n"
        		+ " where { ?person person:isCitizenOf ?ctry .\n"
        		+ "  {GRAPH <http://www.art.org/world> {?ctry rdf:type world:country  ; rdfs:label ?ctryName FILTER ( lang(?ctryName) = \"en\" ) . }}\n"
        		+ "   BIND(replace(str(?ctry),\"http://www.art.org/ontology/world/country#\",\"\") as ?itemID )\n"
        		+ "}\n"
        		+ "order by ?ctryName";
        
		out.println("<label for=\"ctry\">Country</label>\n"
				+ "		  <select id=\"ctry\" name=\"ctry\">\n"
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
            RDFNode y = rb.get("countryName") ;
            out.println("<option value=\""+x.toString()+"\">"+y.toString()+"</option>") ;
	        }
        
		}
		out.println("</select><br/><br/>");
	}
}
