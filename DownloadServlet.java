import java.io.*;
import javax.annotation.Resource;
import javax.naming.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.*;
import java.sql.*;


@WebServlet(name="DownloadServlet", urlPatterns={"/DownloadServlet"})
public class DownloadServlet extends HttpServlet {
	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String roll = req.getParameter("roll");
        String id = req.getParameter("id");

        res.setContentType("application/vnd.ms-excel");
        res.setHeader("Content-Disposition","attachment; filename=StudentResult.xls");

        PrintWriter out = res.getWriter();

        // Header
        out.println("ID\tNAME\tROLL\tTOTAL\tPERCENTAGE");

        try {
            InitialContext  ctx= new InitialContext();
		
		DataSource   ds=(DataSource)ctx.lookup("tindi");
		Connection   con=ds.getConnection();

            
            PreparedStatement ps =
                con.prepareStatement("SELECT id,name,roll,total,percentage FROM student WHERE id=? AND roll=?");

            ps.setString(1, id);
            ps.setString(2, roll);

            ResultSet rs = ps.executeQuery();

            boolean found = false;

            while(rs.next()) {
                found = true;

                out.println(
                    rs.getInt("id") + "\t" +
                    rs.getString("name") + "\t" +
                    rs.getString("roll") + "\t" +
                    rs.getInt("total") + "\t" +
                    rs.getDouble("percentage")
                );
            }

            if(!found) {
                out.println("No Record Found");
            }

            rs.close();
            ps.close();
            
        }
        catch(Exception e) {
            out.println("Error\t" + e.getMessage());
        }
    }
}