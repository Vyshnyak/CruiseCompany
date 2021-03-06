<%@ include file="include.jsp"%>
<nav class="navbar navbar-expand-lg navbar navbar-dark bg-primary">
    <a class="navbar-brand" href="#"><fmt:message key="brand_link"/></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="${pageContext.request.contextPath}/company/main"><fmt:message key="home_link"/> <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="${pageContext.request.contextPath}/company/cruises?page=1"><fmt:message key="cruises_link"/></a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="${pageContext.request.contextPath}/company/my-cruises"><fmt:message key="my_cruises_link"/></a>
            </li>
        </ul>
        <form class="form-inline my-2 my-lg-0" method="post" action="${pageContext.request.contextPath}/company/logout">
            <div class="col-auto pl-0">
                <input type="hidden" name="pageRole" value="client"/>
                <button class="btn btn-warning font-weight-bold" type="submit"><fmt:message key="logout_button"/></button>
            </div>
        </form>
    </div>
</nav>
