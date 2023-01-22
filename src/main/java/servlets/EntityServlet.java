package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.sparql.exec.http.QueryExecutionHTTP;

import apache.jena.artWorld.Base;

/**
 * Servlet implementation class EntityServlet
 */
@WebServlet("/EntityServlet")
public class EntityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EntityServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
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
        		+ "<title>ArtWorld_class</title>\n"
        		+ "</head>\n"
        		+ "<body>\n"
        		+ "<h1>Properties</h1>\n"
        		+ "<table border=\"1\">\n"
        		+ "<tr>\n"
        		+ "    <th>row</th>\n"
        		+ "    <th>Label</th>\n"
        		+ "    <th>predicate</th>\n"
        		+ "    <th>object</th>\n"
        		+ "</tr>\n";	
      String namedGraph = req.getParameter("ng");
      String RDFID = req.getParameter("id").replace("!","#");	
      String queryString ;
      String queryString2 ;
      
	  PrintWriter out = res.getWriter();

	  out.println("<h2>Entity ID = "+RDFID+"</h2>" );
	  out.println("<p>ngraph = "+namedGraph+"</p>");
	  
	  if (namedGraph.equals("defaultGraph")) 
	  {   queryString = "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
	  		+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"
	  		+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
	  		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"
	  		+ "SELECT ?lbl ?p ?o (str(isURI(?o)) as ?dt) ( str(STRSTARTS(str(?o) ,\"http://www.art.org/ontology\")) as ?isLocal) \n"
	  		+ "WHERE { <"+RDFID+"> ?p ?o  FILTER(?p  IN (rdf:type, rdfs:comment, rdfs:label ,owl:sameAs )). \n"
	  		+ "  Optional {<"+RDFID+"> rdfs:label ?label FILTER(lang(?label)=\"en\") }\n"
	  		+ "BIND(IF(BOUND(?label),STR(?label),\"??\") AS ?lbl)."
	  		+ "}  \n"
	  		+ "order by ?p"; 
	      queryString2 = "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
	      		+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"
	      		+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
	      		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"
	      		+ "SELECT ?p ( str(count(?o)) as ?cnt ) (max(?o) as ?value) (str(isURI(max(?o))) as ?dt) (str(STRSTARTS(str(max(?o)) ,\"http://www.art.org/ontology\")) as ?isLocal) \n"
	      		+ "WHERE { <"+RDFID+"> ?p ?o  FILTER(?p NOT IN (rdf:type, rdfs:comment, rdfs:label ,owl:sameAs )). \n"
	      		+ "}  \n"
	      		+ "group by ?p\n"
	      		+ "order by ?cnt ?p"; }
	  else if (namedGraph.equals("unknown"))
	  {   queryString = "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
		  		+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"
		  		+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
		  		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"
		  		+ "SELECT ?lbl ?p ?o (str(isURI(?o)) as ?dt) (str(STRSTARTS(str(?o) ,\"http://www.art.org/ontology\")) as ?isLocal) \n"
		  		+ "WHERE { GRAPH ?g { <"+RDFID+"> ?p ?o  FILTER(?p  IN (rdf:type, rdfs:comment, rdfs:label ,owl:sameAs )). \n"
		  		+ "  Optional {<"+RDFID+"> rdfs:label ?label FILTER(lang(?label)=\"en\") }\n"
		  		+ "BIND(IF(BOUND(?label),STR(?label),\"??\") AS ?lbl)."
		  		+ "}}  \n"
		  		+ "order by ?p";
	      queryString2 = "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
	      		+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"
	      		+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
	      		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"
	      		+ "SELECT ?p ( str(count(?o)) as ?cnt ) (max(?o) as ?value) (str(isURI(max(?o))) as ?dt) (str(STRSTARTS(str(max(?o)) ,\"http://www.art.org/ontology\")) as ?isLocal) \n"
	      		+ "WHERE { GRAPH ?g { <"+RDFID+"> ?p ?o  FILTER(?p NOT IN (rdf:type, rdfs:comment, rdfs:label ,owl:sameAs )). \n"
	      		+ "}}  \n"
	      		+ "group by ?p\n"
	      		+ "order by ?cnt ?p"; 
	  	  }
	  else
	  {   queryString = "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
		  		+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"
		  		+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
		  		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"
		  		+ "SELECT ?lbl ?p ?o (str(isURI(?o)) as ?dt) (str(STRSTARTS(str(?o) ,\"http://www.art.org/ontology\")) as ?isLocal) \n"
		  		+ "WHERE { GRAPH <"+namedGraph+"> { <"+RDFID+"> ?p ?o  FILTER(?p  IN (rdf:type, rdfs:comment, rdfs:label ,owl:sameAs )). \n"
		  		+ "  Optional {<"+RDFID+"> rdfs:label ?label FILTER(lang(?label)=\"en\") }\n"
		  		+ "BIND(IF(BOUND(?label),STR(?label),\"??\") AS ?lbl)."
		  		+ "}}  \n"
		  		+ "order by ?p";
	      queryString2 = "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
	      		+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"
	      		+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
	      		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"
	      		+ "SELECT ?p ( str(count(?o)) as ?cnt ) (max(?o) as ?value) (str(isURI(max(?o))) as ?dt) (str(STRSTARTS(str(max(?o)) ,\"http://www.art.org/ontology\")) as ?isLocal) \n"
	      		+ "WHERE { GRAPH <"+namedGraph+"> { <"+RDFID+"> ?p ?o  FILTER(?p NOT IN (rdf:type, rdfs:comment, rdfs:label ,owl:sameAs )). \n"
	      		+ "}}  \n"
	      		+ "group by ?p\n"
	      		+ "order by ?cnt ?p"; 
	  	  }
	  
	  out.println("<textarea rows=\"10\" cols=\"160\" >"+queryString+"</textarea><br/>");
	  ResultSet results;
	  out.println(header);
	  
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
               RDFNode x = rb.get("p") ;
               RDFNode z = rb.get("o") ;
               RDFNode y = rb.get("lbl") ;
               RDFNode dt = rb.get("dt") ;
               RDFNode isLocal = rb.get("isLocal") ;
               out.println("<td>"+rownum+"</td>");
               out.println("<td>"+y.toString()+"</td>");
               //out.println("<td>"+dt.toString()+"</td>");
               out.println("<td>"+x.toString()+"</td><td>");
               if (dt.toString().equals("true"))
               { 
            	 if (isLocal.toString().equals("true"))
            	  { out.println("<A HREF=entity?id="+z.toString().replace("#","!")+"&ng=unknown>"+z.toString()+"</A>"); }
            	 else
            	  {  out.println("<A HREF="+z+">"+z.toString()+"</A>"); }
               }
               else
                 {out.println(z.toString()); }
               out.println("</td>") ;
               out.println("</tr>");
	        }
           
		}
      out.println("</table><br/><table border=\"1\">\n"
		+ "<tr>\n"
		+ "    <th>row</th>\n"
		+ "    <th>count</th>\n"
		+ "    <th>predicate</th>\n"
		+ "    <th>max(value)</th>\n"
		+ "</tr>\n");
      try ( QueryExecution qExec = QueryExecutionHTTP.create()
              .endpoint(Base.queryService)
              .query(queryString2)
              .param("timeout", "10000")
              .build() ) {
      results = qExec.execSelect();
      int rownum =0;
      rownum = 0;
      for ( ; results.hasNext() ; )
      {
   	rownum++;
      	out.println("<tr>");
      	QuerySolution rb = results.nextSolution() ;
          RDFNode x = rb.get("p") ;
          RDFNode y = rb.get("cnt") ;
          RDFNode z = rb.get("value") ;
          RDFNode dt = rb.get("dt") ;
          RDFNode isLocal = rb.get("isLocal") ;
          out.println("<td>"+rownum+"</td>");
          out.println("<td>"+y.toString()+"</td>");
          //out.println("<td>"+dt.toString()+"</td>");
          out.println("<td>"+x.toString()+"</td><td>");
          if (dt.toString().equals("true"))
          {
        	 if (isLocal.toString().equals("true"))
        	  { out.println("<A HREF=entity?id="+z.toString().replace("#","!")+"&ng=unknown>"+z.toString()+"</A>"); }
        	 else
        	  {  out.println("<A HREF="+z+">"+z.toString()+"</A>"); }
          }
          else
            {out.println(z.toString()); }
          out.println("</td>") ;
          out.println("</tr>");
       }
      
	}
      
      out.println("</table><br/>\n"
		   		+ "</body>\n"
		   		+ "</HTML>\n");
	  
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
