<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="parts/header.jsp" %>
<html>
<body>
<div class="container">
    <form class="form-horizontal" method="post"
          action="edit-user?id=${requestScope.user.id == null ? 0 : requestScope.user.id}">
        <fieldset>

            <legend>Edit user</legend>

            <div class="form-group">
                <label class="col-md-4 control-label" for="login">Login</label>
                <div class="col-md-4">
                    <input id="login"
                           name="login"
                           type="text"
                           value="${requestScope.user.login}"
                           placeholder="your login"
                           class="form-control input-md"
                           required="">
                    <span class="help-block">min 4 symbols</span>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-4 control-label" for="password">Password</label>
                <div class="col-md-4">
                    <input id="password"
                           name="password"
                           type="password"
                           value="${requestScope.user.password}"
                           placeholder="your password"
                           class="form-control input-md"
                           required="">
                    <span class="help-block">min 8 symbols</span>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-4 control-label" for="role">Role</label>
                <div class="col-md-4">
                    <select id="role" name="role" class="form-control">
                        <c:forEach var="role" items="${applicationScope.roles}">
                            <option value="${role}" ${role == requestScope.user.role ? "selected" : ""}>${role}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-4 control-label" for="createButton">Operation</label>
                <div class="col-md-8">
                    <c:if test="${requestScope.user == null}">
                        <button id="createButton" name="create" class="btn btn-success">Create</button>
                    </c:if>
                    <c:if test="${requestScope.user != null}">
                        <button value="update" id="updateButton" name="action" class="btn btn-primary">Update</button>
                        <button value="delete" id="deleteButton" name="action" class="btn btn-primary">Delete</button>
                    </c:if>
                </div>
            </div>
        </fieldset>
    </form>
</div>
</body>
</html>
<%@include file="parts/footer.jsp" %>
