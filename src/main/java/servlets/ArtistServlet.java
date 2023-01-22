package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
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
 * Servlet implementation class ArtistServlet
 */
@WebServlet("/ArtistServlet")
public class ArtistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ArtistServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      String pid = req.getParameter("pid");
      String lbl = req.getParameter("lbl");
      String ctry = req.getParameter("ctry");	
      String gender = req.getParameter("gender");
      String artistType = req.getParameter("artistType");
      String mvmt = req.getParameter("mvmt");
      String queryString ;
      
	  PrintWriter out = res.getWriter();
	  String title = "List of Artists";
	  req.setAttribute("title",title);
	  RequestDispatcher rd = req.getRequestDispatcher("header");
	  rd.include(req, res);
	  String pidFilter = "";
	  String genFilter = "";
	  String typFilter = "";
	  String lblFilter = "       FILTER ( lang(?lbl)=\"en\") .\n";
	  String mvmFilter = "";
	  String ctryFilter = "";
	  
	  if (pid.length()>0) 
	     {pidFilter = "       FILTER(?artist = person:"+pid+") .\n"; 
	      out.println("<h3>Person ID "+pid+"</h3>") ; }
	  if (!lbl.isEmpty()) 
	     {lblFilter = "       FILTER ( lang(?lbl) = \"en\" && regex(?lbl,\""+lbl+"\",\"i\") ) .\n"; 
	     out.println("<h3>Artist label like \""+lbl+"\"</h3>"); 
	     }
	  if (!artistType.equals("all"))  
	     { typFilter = "       FILTER(?artistType = person:"+artistType+") .\n"; 
	     out.println("<h3>artistType = "+artistType+"</h3>");}
	  if (!gender.equals("all")) 
	     { genFilter = "       FILTER(?gender = gender:"+gender+") .\n";
		  out.println("<h3>gender = "+gender+"</h3>"); }
	  if (!mvmt.equals("all"))  
	     { mvmFilter = "       FILTER(?movement = mvmt:"+mvmt+") .\n"; 
	     out.println("<h3>Movement = mvmt:"+mvmt+"</h3>");}
	  if (!ctry.equals("all")) 
	     { ctryFilter = "       FILTER(?ctry = ctry:"+ctry+") .\n";
	     out.println("<h3>ctry = ctry:"+ctry+"</h3>");}
	  
	  queryString = "prefix owl: <http://www.w3.org/2002/07/owl#> \n"
	  		+ "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
	  		+ "prefix mvmt: <http://www.art.org/ontology/movement#> \n"
	  		+ "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"
	  		+ "prefix skos: <http://www.w3.org/2004/02/skos/core#> \n"
	  		+ "prefix world: <http://www.art.org/ontology/world#> \n"
	  		+ "prefix ctry: <http://www.art.org/ontology/world/country#> \n"
	  		+ "prefix person: <http://www.art.org/ontology/person#> \n"
	  		+ "prefix gender: <http://www.art.org/ontology/person/gender#> \n"
	  		+ "select ?artistID ?artist ?artistType (str(?typeLabel) as ?typeName) ?wikidata (str(?lbl) as ?name) ?gender (str(?genderLabel) as ?genderName) ?movement (str(?movementName) as ?mvmtName)  ?ctry ?ctryID (str(?ctryLabel) as ?ctryName)\n"
	  		+ "where {\n"
	  		+ "    ?artist a ?artistType . FILTER(?artistType != owl:NamedIndividual) .  \n"
	  		+ typFilter
	  		+ pidFilter
	  		+ "    ?artist rdfs:label ?lbl FILTER ( lang(?lbl) = \"en\" )  .\n"
	  		+ lblFilter
	  		+ "    ?artist person:hasGender ?gender . \n"
	  		+ "{graph <http://www.art.org/ontology> {\n"
	  		+ "       ?gender rdfs:label ?genderLabel FILTER ( lang(?genderLabel) = \"en\" ) .\n"
	  		+ "       ?artistType rdfs:label ?typeLabel FILTER ( lang(?typeLabel) = \"en\" ) .\n"
	  		+ "    }}"
	  		+ genFilter
	  		+ "    OPTIONAL { ?artist person:isCitizenOf ?ctry .\n"
	  		+ "               GRAPH <http://www.art.org/world> {?ctry rdfs:label ?ctryLabel FILTER ( lang(?ctryLabel) = \"en\" ) . }}\n"
	  		+ ctryFilter
	  		+ "    OPTIONAL{?artist owl:sameAs ?wikidata FILTER ( regex(str(?wikidata), 'wikidata', \"i\")  ) . } \n"
	  		+ "    OPTIONAL {?artist person:isPartofMovement ?movement . \n"
	  		+ "    OPTIONAL { GRAPH <http://www.art.org/movement> {?movement rdfs:label ?movementName FILTER ( lang(?movementName) = \"en\" ) . }}\n"
	  		+ "  }\n"
	  		+ mvmFilter
	  		+ "bind(replace(str(?artist),\"http://www.art.org/ontology/person#\",\"\") as ?artistID )\n"
	  		+ "bind(replace(str(?ctry),\"http://www.art.org/ontology/world/country#\",\"\") as ?ctryID )\n"
	  		+ "  }\n"
	  		+ "order by ?name"; 
	  
	  out.println("<textarea rows=\"10\" cols=\"160\" >"+queryString+"</textarea><br/>");
	  ResultSet results;
	  try ( QueryExecution qExec = QueryExecutionHTTP.create()
              .endpoint(Base.queryService)
              .query(queryString)
              .param("timeout", "10000")
              .build() ) {
      
		  out.println( "<table border=\"1\">\n"
			  		+ "<tr>\n"
			  		+ "    <th>row</th>\n"
			  		+ "    <th>Artist ID</th>\n"
			  		+ "    <th>type</th>\n"
			  		+ "    <th>name</th>\n"
			  		+ "    <th>gender</th>\n"
			  		+ "    <th>movement</th>\n"
			  		+ "    <th>citizen of</th>\n"
			  		+ "    <th>Wikidata</th>\n"
			  		+ "</tr>\n");		  
	  results = qExec.execSelect();
      int rownum =0;
      
      for ( ; results.hasNext() ; )
      {
   	    rownum++;
      	out.println("<tr>");
      	QuerySolution rb = results.nextSolution() ;
          //RDFNode x = rb.get("artist") ;
          RDFNode xid = rb.get("artistID") ;
          RDFNode y = rb.get("typeName") ;
          RDFNode z = rb.get("name") ;
          RDFNode gend = rb.get("genderName") ;
          RDFNode country = rb.get("ctryName") ;
          RDFNode wikidata = rb.get("wikidata") ;
          //RDFNode movement = rb.get("movement") ;
          RDFNode mvmtName = rb.get("mvmtName") ;
          
          out.println("<td>"+rownum+"</td>");
          //out.println("<td>"+xid.toString()+"</td>");
          out.println("<td><A HREF=artistForm?pid="+xid.toString()+">"+xid.toString()+"</A></td>"); 
          
          out.println("<td>"+y.toString()+"</td>");
          out.println("<td>"+z.toString()+"</td><td>");
       	  out.println(gend.toString());
       	   //out.println("<A HREF=entity?id="+z.toString().replace("#","!")+"&ng=unknown>"+z.toString()+"</A>"); 
       	   // out.println("<A HREF="+z+">"+z.toString()+"</A>"); 
          out.println("</td>") ;
          
          if (mvmtName!=null) {out.println("<td>"+mvmtName.toString()+"</td>") ;} 
          else
          {out.println("<td>"+"-"+"</td>");};
          
          out.println("<td>"+country.toString()+"</td>");
          
          if (wikidata!=null) {out.println("<td><A HREF="+wikidata.toString()+">"+wikidata.toString()+"</A></td>") ;} 
          else
          {out.println("<td>"+"-"+"</td>");};
          
          
          out.println("</tr>");
       }
      
	}
  
	  out.println("</table><br/>\n"
		   		+ "</body>\n"
		   		+ "</HTML>\n");
	}



}
