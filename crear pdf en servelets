# HttpManager
CONEXXION CON WEB SERVICES
package com.as400samplecode;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class GeneratePDF extends HttpServlet {
 private static final long serialVersionUID = 1L;

 public GeneratePDF() {
  super();
 }

 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  doPost(request, response);
 }

 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

  //get the output stream for writing binary data in the response.
  ServletOutputStream os = response.getOutputStream();
  //set the response content type to PDF
  response.setContentType("application/pdf"); 
  //create a new document
  Document doc = new Document();

  //create some special styles and font sizes
  Font bfBold18 = new Font(FontFamily.TIMES_ROMAN, 18, Font.BOLD, new BaseColor(0, 0, 0)); 
  Font bfBold12 = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLDITALIC, new BaseColor(0, 0, 0)); 
  Font bf12 = new Font(FontFamily.TIMES_ROMAN, 12); 

  Connection conn = null;             
  PreparedStatement stmt = null;      
  String sql = null;

  try{
   
   //create an instance of the PdfWriter using the output stream
   PdfWriter.getInstance(doc, os); 

   //document header properties
   doc.addAuthor("betterThanZero");
   doc.addCreationDate();
   doc.addProducer();
   doc.addCreator("MySampleCode.com");
   doc.addTitle("DemoPDF");
   doc.setPageSize(PageSize.LETTER);
   doc.open();

   //add a new paragraph
   doc.add( new Paragraph("List of Countries...", bfBold18));
   
   //connection to the MySQL database
   Context ctx = (Context) new InitialContext().lookup("java:comp/env");
   conn = ((DataSource) ctx.lookup("jdbc/mysql")).getConnection(); 

   //just get 10 countries from my database for demo
   sql = "Select * from country where name >= 'C' order by name,code LIMIT 0,10"; 
   stmt = conn.prepareStatement(sql);

   ResultSet rs = stmt.executeQuery();  
   while(rs.next()){ 
    //create an anchor reference
    Anchor anchor = new Anchor(rs.getString("name").trim(),bfBold12);
    anchor.setReference("#" + rs.getString("code").trim());
    //add the anchor to the document
    doc.add(anchor);
    //add an empty line
    doc.add( Chunk.NEWLINE );
   }                                                                          
   
   // Move cursor to the first row
   rs.first();

   while(rs.next()){ 
    //start a new page here
    doc.newPage();
    //create a new paragraph for each country
    Paragraph countryParagraph = new Paragraph();
    //create an anchor
    Anchor anchor = new Anchor(rs.getString("name"), bfBold18);
    anchor.setName(rs.getString("code").trim());
    //add the anchor to the paragraph
    countryParagraph.add(anchor);
    //add the paragraph to the document
    doc.add(countryParagraph);
    
    //add some detail information about the country
    doc.add( new Paragraph("Country Code: " + rs.getString("code").trim(), bf12));
    doc.add( new Paragraph("Continent: " + rs.getString("continent").trim(), bf12));
    doc.add( new Paragraph("Region: " + rs.getString("region").trim(), bf12));
    doc.add( new Paragraph("Life Expectancy: " + rs.getString("lifeExpectancy").trim(), bf12));
    doc.add( new Paragraph("GNP: " + rs.getString("gnp").trim(), bf12));
   }

   rs.close();                                                                
   stmt.close();                                                              
   stmt = null;                                                               

   conn.close();                                                              
   conn = null;                                                    


   doc.close(); 

  }catch(DocumentException e){
   e.printStackTrace();
  }
  catch(Exception e){
   e.printStackTrace();
  }

 }

}
