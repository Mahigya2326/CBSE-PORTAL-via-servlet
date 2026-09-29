import java.io.*;
import javax.annotation.Resource;
import javax.naming.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.*;
import java.sql.*;


@WebServlet(name="UpdateServlet", urlPatterns={"/UpdateServlet"})
public class UpdateServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        try {
            InitialContext  ctx= new InitialContext();
		
		DataSource   ds=(DataSource)ctx.lookup("tindi");
		Connection   con=ds.getConnection();


            String id = req.getParameter("id").trim();
            String roll = req.getParameter("roll").trim();
            String name = req.getParameter("name");

            int math = Integer.parseInt(req.getParameter("math"));
            int hindi = Integer.parseInt(req.getParameter("hindi"));
            int english = Integer.parseInt(req.getParameter("english"));
            int sanskrit = Integer.parseInt(req.getParameter("sanskrit"));
            int social = Integer.parseInt(req.getParameter("social"));
            int computer = Integer.parseInt(req.getParameter("computer"));

            int total = math + hindi + english + sanskrit + social + computer;
            double percentage = total / 6.0;

            PreparedStatement ps = con.prepareStatement(
                "UPDATE student SET roll=?, name=?, math=?, hindi=?, english=?, sanskrit=?, social=?, computer=?, total=?, percentage=? WHERE id=?"
            );

            ps.setString(1, roll);
            ps.setString(2, name);
            ps.setInt(3, math);
            ps.setInt(4, hindi);
            ps.setInt(5, english);
            ps.setInt(6, sanskrit);
            ps.setInt(7, social);
            ps.setInt(8, computer);
            ps.setInt(9, total);
            ps.setDouble(10, percentage);
            ps.setString(11, id);

            int i = ps.executeUpdate();

            //  HTML Response Start
            out.println("<html><head><title>Update Result</title>");
            out.println("<style>");
            out.println("body{font-family:Arial;text-align:center;background:#f2f2f2;}");
            out.println(".box{margin-top:100px;background:white;padding:30px;border-radius:10px;display:inline-block;box-shadow:0 0 10px gray;}");
            out.println("a{display:inline-block;margin-top:20px;padding:10px 20px;background:#5b86e5;color:white;text-decoration:none;border-radius:5px;}");
            out.println("a:hover{background:#36d1dc;}");
            out.println("</style></head><body>");

            out.println("<div class='box'>");

            if (i > 0) {
                out.println("<h3 style='color:green;'> Record Updated Successfully</h3>");
            } else {
                out.println("<h3 style='color:red;'> Update Failed (ID not found)</h3>");
            }

            //  BACK BUTTON
            out.println("<a href='update.html'>⬅ Back</a>");

            out.println("</div></body></html>");

            

        } catch (Exception e) {
            out.println("<h3 style='color:red;'>Error: " + e + "</h3>");
        }
    }
}