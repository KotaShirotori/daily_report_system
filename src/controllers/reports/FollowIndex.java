package controllers.reports;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Follow;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class FollowIndex
 */
@WebServlet("/follow/index")
public class FollowIndex extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowIndex() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");

        List<Follow> f = (List<Follow>) em.createNamedQuery("follow", Follow.class)
                                .setParameter("followingEmployee", login_employee)
                                .getResultList();

        long Follow_count = (long)em.createNamedQuery("followCount", Long.class)
                                .setParameter("followingEmployee", login_employee)
                                .getSingleResult();
        List<Report> reports = new ArrayList<>();

        for(int i = 0; i< Follow_count; i++) {


                reports.add(em.createNamedQuery("getMyAllReports", Report.class)
                                    .setParameter("employee", f.get(i).getFollowedEmployee())
                                    .getSingleResult());
        }
        if( reports.size() < 1) {
            em.close();
            request.getSession().setAttribute("flush", "フォローしている従業員はいません");

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/follow.jsp");
            rd.forward(request, response);
        }else {

        em.close();

        request.setAttribute("reports", reports);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/follow.jsp");
        rd.forward(request, response);
        }
    }


}
