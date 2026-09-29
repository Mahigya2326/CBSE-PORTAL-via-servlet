import java.io.*;
import javax.annotation.Resource;
import javax.naming.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.*;
import java.sql.*;


@WebServlet(name="ExportExcelServlet", urlPatterns={"/ExportExcelServlet"})
public class ExportExcelServlet extends HttpServlet {
	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("application/vnd.ms-excel");
        res.setHeader("Content-Disposition","attachment; filename=StudentResults.xls");

        PrintWriter out = res.getWriter();

        out.println("ID\tNAME\tROLL\tTOTAL\tPERCENTAGE");

        try {
            
			   InitialContext  ctx= new InitialContext();
		
		DataSource   ds=(DataSource)ctx.lookup("tindi");
		Connection   con=ds.getConnection();


            PreparedStatement ps =
                con.prepareStatement("select id,name,roll,total,percentage from student order by id");

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                out.println(
                    rs.getInt("id") + "\t" +
                    rs.getString("name") + "\t" +
                    rs.getString("roll") + "\t" +
                    rs.getInt("total") + "\t" +
                    rs.getDouble("percentage")
                );
            }

            rs.close();
            ps.close();
            
        }
        catch(Exception e) {
            out.println("Error\t" + e.getMessage());
        }
    }
}
