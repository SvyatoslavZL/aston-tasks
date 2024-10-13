<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="parts/header.jsp" %>
<html>
<body>
<div class="container">

  <c:forEach var="user" items="${requestScope.users}">
    Edit user <a href="edit-user?id=${user.id}">${user.login}</a> <br> <br>
  </c:forEach>

  <p>
    <a href="sign-up" class="top-nav">Create new user</a>
  </p>
</div>
</body>
</html>
<%@include file="parts/footer.jsp" %>
