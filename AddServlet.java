import java.io.*;
import javax.annotation.Resource;
import javax.naming.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.*;
import java.sql.*;


@WebServlet(name="AddServlet", urlPatterns={"/AddServlet"})
public class AddServlet extends HttpServlet
{
	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException
    {
		
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        String name = req.getParameter("name");
        String roll = req.getParameter("roll");

        int math = Integer.parseInt(req.getParameter("math"));
        int hindi = Integer.parseInt(req.getParameter("hindi"));
        int english = Integer.parseInt(req.getParameter("english"));
        int sanskrit = Integer.parseInt(req.getParameter("sanskrit"));
        int social = Integer.parseInt(req.getParameter("social"));
        int computer = Integer.parseInt(req.getParameter("computer"));

        int total = math + hindi + english + sanskrit + social + computer;
        double percentage = total / 6.0;

        try
        {
            InitialContext  ctx= new InitialContext();
		
		DataSource   ds=(DataSource)ctx.lookup("tindi");
		Connection   con=ds.getConnection();


            
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO student VALUES(student_seq.NEXTVAL,?,?,?,?,?,?,?,?,?,?)");

            ps.setString(1, name);
            ps.setString(2, roll);
            ps.setInt(3, math);
            ps.setInt(4, hindi);
            ps.setInt(5, english);
            ps.setInt(6, sanskrit);
            ps.setInt(7, social);
            ps.setInt(8, computer);
            ps.setInt(9, total);
            ps.setDouble(10, percentage);

            ps.executeUpdate();

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM student ORDER BY id DESC");

            if(rs.next())
            {
                out.println("<html><body>");
                out.println("<h2 align='center'>STUDENT MARKSHEET</h2>");

                out.println("<table border='1' align='center' cellpadding='10'>");

                out.println("<tr><th>ID</th><td>"+rs.getInt("id")+"</td></tr>");
                out.println("<tr><th>Name</th><td>"+rs.getString("name")+"</td></tr>");
                out.println("<tr><th>Roll No</th><td>"+rs.getString("roll")+"</td></tr>");

                out.println("<tr><th>Math</th><td>"+rs.getInt("math")+"</td></tr>");
                out.println("<tr><th>Hindi</th><td>"+rs.getInt("hindi")+"</td></tr>");
                out.println("<tr><th>English</th><td>"+rs.getInt("english")+"</td></tr>");
                out.println("<tr><th>Sanskrit</th><td>"+rs.getInt("sanskrit")+"</td></tr>");
                out.println("<tr><th>Social</th><td>"+rs.getInt("social")+"</td></tr>");
                out.println("<tr><th>Computer</th><td>"+rs.getInt("computer")+"</td></tr>");

                out.println("<tr><th>Total</th><td>"+rs.getInt("total")+"</td></tr>");
                out.println("<tr><th>Percentage</th><td>"+rs.getDouble("percentage")+"</td></tr>");

                out.println("</table>");
                out.println("</body></html>");
            }      
        }
        catch(Exception e)
        {
            out.println(e);
        }
    }
}