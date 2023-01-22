package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
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

public class RDFClassServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException 
	{
	  
	  int cnt = Integer.parseInt(req.getParameter("cnt"));  	 
      String namedGraph = req.getParameter("ngraph");
      String RDFClass = req.getParameter("rdfclass").replace("!","#");	
      String queryString ;
      
	  PrintWriter out = res.getWriter();
	  String title = "Entities";
	  req.setAttribute("title",title);
	  RequestDispatcher rd = req.getRequestDispatcher("header");
	  rd.include(req, res);

	  out.println("<h2>rdfclass = "+RDFClass+"</h2>" );
	  out.println("<p>ngraph = "+namedGraph+"</p>");
	  out.println("<p>count result is "+cnt+"</p>");
	  
	  if (namedGraph.equals("defaultGraph")) 
	  {  queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"+
	    		"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"+
	    		"SELECT ?id ?lbl \nWHERE { ?id a <"+RDFClass+"> . \n"+
	    		"	    OPTIONAL { ?id rdfs:label ?label . FILTER(lang(?label)=\"en\")  . } \n"+
	    		"BIND(IF(BOUND(?label),STR(?label),\"??\") AS ?lbl).\n"+
	    		"} order by ?lbl  LIMIT 500\n";}
	  else
	  {  queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"+
		    		"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"+
		    		"SELECT ?id ?lbl \nWHERE { GRAPH <"+namedGraph+"> { ?id a <"+RDFClass+"> . \n"+
		    		"	    OPTIONAL { ?id rdfs:label ?label . FILTER(lang(?label)=\"en\")  . } \n"+
		    		"BIND(IF(BOUND(?label),STR(?label),\"??\") AS ?lbl).\n"+
		    		"}} order by ?lbl  LIMIT 500\n";} 
	  
	  
	  out.println("<textarea rows=\"10\" cols=\"160\" >"+queryString+"</textarea><br/>");
	  ResultSet results;
	  out.println("<table border=\"1\">\n"
      		+ "<tr>\n"
      		+ "    <th>row</th>\n"
      		+ "    <th>ID</th>\n"
      		+ "    <th>Label</th>\n"
      		+ "</tr>\n");	
      try ( QueryExecution qExec = QueryExecutionHTTP.create()
                   .endpoint(Base.queryService)
                   .query(queryString)
                   .param("timeout", "10000")
                   .build() ) {
           results = qExec.execSelect();
           int rownum =0;
           
           for ( ; results.hasNext() ; )
           {
        	rownum++;
           	out.println("<tr>");
           	QuerySolution rb = results.nextSolution() ;
               RDFNode x = rb.get("id") ;
               RDFNode y = rb.get("lbl") ;
               out.println("<td>"+rownum+"</td><td>");
               String xx = x.toString().replace("#","!");
               out.println("<A HREF=entity?id="+xx+"&ng="+namedGraph+">"+x.toString()+"</A>");
               out.println("</td><td>"+y.toString()+"</td>") ;
               out.println("</tr>");
	        }
           
		}
      out.println("</table><br/>\n"
		   		+ "</body>\n"
		   		+ "</HTML>\n");
	  
	}
}