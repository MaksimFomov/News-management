<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/pages/tiles/localization/localizationBase.jsp" %>

<div class="body-title">
	<a href="">${goback_news} </a> ${newslist_goback_current}
</div>

<form action="controller" method="post">
	<c:forEach var="news" items="${requestScope.news}">
		<div class="single-news-wrapper">
			<div class="single-news-header-wrapper">
				<div class="news-title">
					<c:out value="${news.title}" />
				</div>
				
				<div class="news-date">
					<c:out value="${news.newsDate}" />
				</div>

				<div class="news-content">
					<c:out value="${news.briefNews}" />
				</div>
				
				<div class="news-link-to-wrapper">
					<div class="link-position">
						<c:if test="${sessionScope.role eq 'admin'}">
						      <a href="controller?command=go_to_edit_news&idNews=${news.idNews}">${edit} </a>
						</c:if>
						
						<span>&nbsp&nbsp</span>
						
						<a href="controller?command=go_to_view_news&idNews=${news.idNews}">${newslist_view} </a>
   					    <c:if test="${sessionScope.role eq 'admin'}">
   					         <input type="checkbox" name="idNews" value="${news.idNews }" />
   					    </c:if>
					</div>
				</div>
			</div>
		</div>
	</c:forEach>

    <c:if test="${(sessionScope.role eq 'admin') and (not (requestScope.news eq null))}">
        <div class="delete-button-position">
            <input type="hidden" name="command" value="do_delete_news" />
            <input type="submit" value="${delete}" />
        </div>
    </c:if>

	<div class="no-news">
		<c:if test="${requestScope.news eq null}">
        ${nonews}
	</c:if>
	</div>
</form>
