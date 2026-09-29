import java.io.*;
import javax.annotation.Resource;
import javax.naming.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.*;
import java.sql.*;


@WebServlet(name="RemoveServlet", urlPatterns={"/RemoveServlet"})
public class RemoveServlet extends HttpServlet
{
	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException
    {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        String roll = req.getParameter("roll");
        String action = req.getParameter("action"); 

        try
        {
            InitialContext  ctx= new InitialContext();
		
		DataSource   ds=(DataSource)ctx.lookup("tindi");
		Connection   con=ds.getConnection();

           /* Connection con = DriverManager.getConnection(
                ctx.getInitParameter("url"),
                ctx.getInitParameter("user"),
                ctx.getInitParameter("pass")
            );*/

            
            if("delete".equals(action))
            {
                PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM student WHERE roll=?"
                );

                ps.setString(1, roll);

                int i = ps.executeUpdate();

                if(i > 0)
                    out.println("<h3 align='center'>Record Deleted Successfully </h3>");
                else
                    out.println("<h3 align='center'>Delete Failed </h3>");
            }

            
            else
            {
                PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM student WHERE roll=?"
                );

                ps.setString(1, roll);

                ResultSet rs = ps.executeQuery();

                if(rs.next())
                {
                    out.println("<html><body>");
                    out.println("<h2 align='center'>STUDENT DETAILS</h2>");

                    out.println("<table border='1' align='center' cellpadding='10'>");

                    out.println("<tr><th>ID</th><td>"+rs.getInt("id")+"</td></tr>");
                    out.println("<tr><th>Name</th><td>"+rs.getString("name")+"</td></tr>");
                    out.println("<tr><th>Roll</th><td>"+rs.getString("roll")+"</td></tr>");

                    out.println("<tr><th>Math</th><td>"+rs.getInt("math")+"</td></tr>");
                    out.println("<tr><th>Hindi</th><td>"+rs.getInt("hindi")+"</td></tr>");
                    out.println("<tr><th>English</th><td>"+rs.getInt("english")+"</td></tr>");
                    out.println("<tr><th>Sanskrit</th><td>"+rs.getInt("sanskrit")+"</td></tr>");
                    out.println("<tr><th>Social</th><td>"+rs.getInt("social")+"</td></tr>");
                    out.println("<tr><th>Computer</th><td>"+rs.getInt("computer")+"</td></tr>");

                    out.println("<tr><th>Total</th><td>"+rs.getInt("total")+"</td></tr>");
                    out.println("<tr><th>Percentage</th><td>"+rs.getDouble("percentage")+"</td></tr>");

                    out.println("</table><br>");

                    
                    out.println("<form method='post' style='text-align:center;'>");
                    out.println("<input type='hidden' name='roll' value='"+roll+"'>");
                    out.println("<input type='hidden' name='action' value='delete'>");
                    out.println("<input type='submit' value='Delete'>");
                    out.println("</form>");

                    out.println("</body></html>");
                }
                else
                {
                    out.println("<h3 align='center'>No Record Found </h3>");
                }
            }

            con.close();

        }
        catch(Exception e)
        {
            out.println(e);
        }
    }
}