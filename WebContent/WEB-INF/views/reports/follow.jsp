<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
    <c:choose>
    <c:when test="${flush != null}">
    <div id="followEmpty">
    <c:out value="${flush}" ></c:out>
    </div>
    </c:when>
    <c:otherwise>

            <h2>フォローした従業員の日報一覧</h2>
            <table id="report_list">
                <tbody>
                    <tr>
                        <th class="report_name">氏名</th>
                        <th class="report_date">日付</th>
                        <th class="report_title">タイトル</th>
                        <th class="report_action">操作</th>
                    </tr>
                    <c:forEach var="report" items="${reports}" varStatus="status">
                        <tr>

                            <td class="report_name"><c:out value="${report.employee.name}" /></td>
                            <td class="report_date"><fmt:formatDate value='${report.report_date}' pattern='yyyy-MM-dd' /></td>
                            <td class="report_title">${report.title}</td>
                            <td class="report_action"><a href="<c:url value='/reports/show?id=${report.id}' />">詳細を見る</a></td>
                        </tr>
                    </c:forEach>


                </tbody>
            </table>
            </c:otherwise>
            </c:choose>
    </c:param>

</c:import>