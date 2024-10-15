<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:import url="parts/header.jsp"/>
<html>
<body>
<div class="container">
    <jsp:useBean id="user" scope="session" type="org.aston.application.dto.UserTo"/>

    <div class="px-4 py-5 my-5 text-center">
        <p class="lead mb-4">
        <h1 class="display-3 fw-bold">User login: ${user.login}</h1>
        <h3 class="display-5 fw-bold">User role: ${user.role}</h3>
        <div class="col-lg-6 mx-auto">
            <form class="form-horizontal" action="profile" method="post">
                <div class="d-grid gap-2 d-sm-flex justify-content-sm-center">
                    <input type="hidden" name="id" value="${user.id}">
                    <button type="submit" name="user" class="btn btn-primary btn-lg px-4 gap-3">Edit</button>
                    <button type="submit" name="logout" class="btn btn-outline-secondary btn-lg px-4">Logout</button>
                </div>
            </form>
        </div>
    </div>

</div>
</body>
</html>
<c:import url="parts/footer.jsp"/>