import java.io.*;
import javax.annotation.Resource;
import javax.naming.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.*;
import java.sql.*;


@WebServlet(name="TotalServlet", urlPatterns={"TotalServlet"})
public class TotalServlet extends HttpServlet
{
	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException
    {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        try
        {
            InitialContext  ctx= new InitialContext();
		
		DataSource   ds=(DataSource)ctx.lookup("tindi");
		Connection   con=ds.getConnection();


            Statement st = con.createStatement();

            ResultSet rs1 = st.executeQuery("SELECT COUNT(*) FROM student");

            out.println("<html><head><title>Total Students</title>");

            // CSS SAME THEME
            out.println("<style>");
            out.println("body { margin:0; font-family: Arial; background: linear-gradient(to right, #4facfe, #00f2fe); text-align:center; }");

            out.println("h1 { background-color: rgba(0,0,0,0.7); color:white; padding:20px; margin:0; }");

            out.println(".card { width:90%; margin:40px auto; background:white; padding:25px; border-radius:15px; box-shadow:0px 10px 25px rgba(0,0,0,0.3); overflow-x:auto; }");

            out.println("table { width:100%; border-collapse:collapse; }");

            out.println("th { padding:10px; background:#0072ff; color:white; }");

            out.println("td { padding:10px; background:#f2f2f2; }");

            out.println("tr:nth-child(even) td { background:#e6f2ff; }");

            out.println(".count { font-size:22px; margin:20px 0; color:#333; }");

            out.println(".btn { display:inline-block; margin-top:20px; padding:10px 15px; background:linear-gradient(to right,#0072ff,#00c6ff); color:white; text-decoration:none; border-radius:8px; }");

            out.println(".btn:hover { background:linear-gradient(to right,#0052cc,#0099cc); }");

            out.println("</style>");

            out.println("</head><body>");

            out.println("<h1>CBSE Student Portal</h1>");

            out.println("<div class='card'>");

            // Total Count
            if(rs1.next())
            {
                out.println("<div class='count'><b>Total Students : " + rs1.getInt(1) + "</b></div>");
            }

            ResultSet rs = st.executeQuery("SELECT * FROM student");

            out.println("<h2>All Students Details</h2>");

            out.println("<table>");

            out.println("<tr>");
            out.println("<th>ID</th><th>Name</th><th>Roll</th>");
            out.println("<th>Math</th><th>Hindi</th><th>English</th>");
            out.println("<th>Sanskrit</th><th>Social</th><th>Computer</th>");
            out.println("<th>Total</th><th>Percentage</th>");
            out.println("</tr>");

            while(rs.next())
            {
                out.println("<tr>");
                out.println("<td>"+rs.getInt("id")+"</td>");
                out.println("<td>"+rs.getString("name")+"</td>");
                out.println("<td>"+rs.getString("roll")+"</td>");

                out.println("<td>"+rs.getInt("math")+"</td>");
                out.println("<td>"+rs.getInt("hindi")+"</td>");
                out.println("<td>"+rs.getInt("english")+"</td>");
                out.println("<td>"+rs.getInt("sanskrit")+"</td>");
                out.println("<td>"+rs.getInt("social")+"</td>");
                out.println("<td>"+rs.getInt("computer")+"</td>");

                out.println("<td>"+rs.getInt("total")+"</td>");
                out.println("<td>"+rs.getDouble("percentage") + " %</td>");
                out.println("</tr>");
            }

            out.println("</table>");

            out.println("<a href='index.html' class='btn'>Back to Home</a>");

            out.println("</div>");

            out.println("</body></html>");
        }
        catch(Exception e)
        {
            out.println(e);
        }
    }
}