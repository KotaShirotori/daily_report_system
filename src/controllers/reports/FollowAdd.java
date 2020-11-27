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
 * Servlet implementation class FollowAdd
 */
@WebServlet("/follow/add")
public class FollowAdd extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowAdd() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //元々中間テーブルが存在するか確認

            EntityManager em1 = DBUtil.createEntityManager();

            Report r = em1.find(Report.class, Integer.parseInt(request.getParameter("id")));

           List<Follow> f = (List<Follow>) em1.createNamedQuery("followSearch", Follow.class)
                    .setParameter("followedEmployee", r.getEmployee())
                    .setParameter("followingEmployee", (Employee) request.getSession().getAttribute("login_employee"))
                    .getResultList();
            //元々中間テーブルが存在する場合
            if (f.size() > 0) {
                em1.close();
                request.getSession().setAttribute("flush", "既にフォローしています。");
                response.sendRedirect(request.getContextPath() + "/reports/index");
            } else {

            EntityManager em2 = DBUtil.createEntityManager();

            Report rr = em2.find(Report.class, Integer.parseInt(request.getParameter("id")));

            Follow ff = new Follow();

            ff.setFollowingemployee((Employee) request.getSession().getAttribute("login_employee"));

            ff.setFollowedEmployee(rr.getEmployee());

            em2.getTransaction().begin();
            em2.persist(ff);
            em2.getTransaction().commit();
            em2.close();

            request.getSession().setAttribute("flush", "フォローしました。");
            response.sendRedirect(request.getContextPath() + "/reports/index");
        }
    }



    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
