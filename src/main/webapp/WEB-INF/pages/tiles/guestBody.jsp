<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:choose>
  <c:when test="${requestScope.presentation eq 'registration' }">
    <c:import url="/WEB-INF/pages/tiles/registration.jsp" />
  </c:when>
  
  <c:otherwise>
    <c:import url="/WEB-INF/pages/tiles/guestInfo.jsp" />
  </c:otherwise>
</c:choose>
