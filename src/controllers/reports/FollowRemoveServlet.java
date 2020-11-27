package controllers.reports;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
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
 * Servlet implementation class FollowRemoveServlet
 */
@WebServlet("/follow/remove")
public class FollowRemoveServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowRemoveServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

        List<Follow> f =  em.createNamedQuery("followSearch", Follow.class)
                .setParameter("followedEmployee", r.getEmployee())
                .setParameter("followingEmployee", (Employee) request.getSession().getAttribute("login_employee"))
                .getResultList();

        if(f.size() > 0) {
        em.getTransaction().begin();
        em.remove(f.get(0));
        em.getTransaction().commit();
        em.close();

        request.getSession().setAttribute("flush", "フォローを外しました。");

        response.sendRedirect(request.getContextPath() + "/reports/index");
        } else {
            em.close();
            request.getSession().setAttribute("flush", "元々フォローしていません。");

            response.sendRedirect(request.getContextPath() + "/reports/index");
        }
        }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
