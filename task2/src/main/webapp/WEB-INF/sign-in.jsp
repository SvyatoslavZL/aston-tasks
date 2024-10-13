<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="parts/header.jsp" %>
<html>
<body>
<div class="container">
    <form class="form-horizontal" method="post" action="sign-in">
        <fieldset>

            <legend>log in to your account</legend>

            <div class="form-group">
                <label class="col-md-4 control-label" for="login">Login</label>
                <div class="col-md-4">
                    <input id="login"
                           name="login"
                           type="text"
                           placeholder="your login"
                           class="form-control input-md"
                           required=""
                           value="Alex">
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-4 control-label" for="password">Password</label>
                <div class="col-md-4">
                    <input id="password"
                           name="password"
                           type="password"
                           placeholder="your password"
                           class="form-control input-md"
                           required=""
                           value="admin">
                </div>
            </div>

            <div class="form-group">
                <div class="col-md-4">
                    <button id="loginButton" name="login" class="btn btn-success">Sign-in</button>
                </div>
            </div>

        </fieldset>
    </form>
</div>
</body>
</html>
<%@include file="parts/footer.jsp" %>