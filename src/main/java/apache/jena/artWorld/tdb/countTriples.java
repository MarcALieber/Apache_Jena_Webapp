package apache.jena.artWorld.tdb;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.sparql.exec.http.QueryExecutionHTTP;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import apache.jena.artWorld.Base;

public class countTriples extends  HttpServlet {
	 
	public void doGet(HttpServletRequest req, HttpServletResponse res) 
            throws IOException, ServletException {
		 
	        Query queryString = QueryFactory.create("SELECT ?NamedGraph (COUNT(?s) as ?countOfs)  \n"
	        		+ "WHERE { GRAPH ?NamedGraph { ?s ?p ?o . } \n"
	        		+ "  } GROUP BY (?NamedGraph)" );
	        Query  query2 = QueryFactory.create("SELECT (\"default\" as ?NamedGraph) (COUNT(?s) as ?countOfs)  \n"
	        		+ "WHERE { ?s ?p ?o .  \n"
	        		+ "  }" );
	        
	      PrintWriter out = res.getWriter();
	  	  String title = "Fuseki ArtWorld triple store count triples";
	  	  req.setAttribute("title",title);
	  	  RequestDispatcher rd = req.getRequestDispatcher("header");
	  	  rd.include(req, res);
	  	  
	        
	        ResultSet results;
		   // out.println(header);
		    out.println("<textarea rows=\"10\" cols=\"160\" >"+queryString+"</textarea><br/>");
		    out.println("<br/>");
		    out.println("<table border=\"1\">\n"
		      		+ "<tr>\n"
		      		+ "    <th>Named Graph</th>\n"
		      		+ "    <th>Number of triples</th>\n"
		      		+ "</tr>\n");	
		    // named Graphs
 			try ( QueryExecution qExec = QueryExecutionHTTP.create()
 	                    .endpoint(Base.queryService)
 	                    .query(queryString)
 	                    .param("timeout", "10000")
 	                    .build() ) {
 	            results = qExec.execSelect();
 	            for ( ; results.hasNext() ; )
 	            {
 	            	out.println("<tr>");
 	            	QuerySolution rb = results.nextSolution() ;
 	                RDFNode y = rb.get("countOfs") ;
 	                RDFNode x = rb.get("NamedGraph") ;
 	                out.println("<td>"+x.toString()+"</td><td>"+y.toString()+"</td>") ;
 	                out.println("</tr>");
 		        }
 	            
 			}
 			try ( QueryExecution qExec = QueryExecutionHTTP.create()
	                    .endpoint(Base.queryService)
	                    .query(query2)
	                    .param("timeout", "10000")
	                    .build() ) {
	            results = qExec.execSelect();
	            for ( ; results.hasNext() ; )
	            {
	            	out.println("<tr>");
	            	QuerySolution rb = results.nextSolution() ;
	                RDFNode y = rb.get("countOfs") ;
	                RDFNode x = rb.get("NamedGraph") ;
	                out.println("<td>"+x.toString()+"</td><td>"+y.toString()+"</td>") ;
	                out.println("</tr>");
		        }
	            
			}
 			out.println("</table><br/>\n"
 			   		+ "</body>\n"
 			   		+ "</HTML>\n");
	 }

}
