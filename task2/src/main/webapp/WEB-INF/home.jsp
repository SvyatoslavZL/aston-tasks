<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="parts/header.jsp" %>
<html>
<body>
<div class="container">

    <h1 class="text-center">Book reader</h1>

    <div class="row g-4 py-5 row-cols-1 row-cols-lg-3">
        <c:forEach var="book" items="${requestScope.books}">
            <div class="feature col">
                <h3 class="fs-2">${book.title}</h3>
                <a href="read-book?bookId=${book.id}" class="icon-link d-inline-flex align-items-center">
                    Read a book
                    <svg class="bi" width="1em" height="1em">
                        <use xlink:href="#chevron-right"></use>
                    </svg>
                </a>
                <c:if test='${sessionScope.user.role=="ADMIN"}'>
                    <a href="book?id=${book.id}" class="icon-link d-inline-flex align-items-center">
                        Edit
                        <svg class="bi" width="1em" height="1em">
                            <use xlink:href="#chevron-right"></use>
                        </svg>
                    </a>
                </c:if>
            </div>
        </c:forEach>
    </div>
</div>
<%@include file="parts/footer.jsp" %>

<select name="bookNames">
    <c:forEach items="${bookNames}" var="bookName">
        <option value="${bookName}">${bookName}</option>
    </c:forEach>
</select>

</body>
</html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
