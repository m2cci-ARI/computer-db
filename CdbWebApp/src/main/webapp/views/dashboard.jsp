<!DOCTYPE html>
<%@page import="fr.excilys.db.dto.Computer"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard.html"> Application -
				Computer Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">
				<c:out value="${fn:length(computers)}"></c:out>
				Computers found
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="Search name" /> <input
							type="submit" id="searchsubmit" value="Filter by name"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer"
						href="servletAddingComputer">Add Computer</a> <a
						class="btn btn-default" id="editComputer" href=""
						onclick="$.fn.toggleEditMode();">Edit</a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="#" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table id="contentTable" class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th>Computer name</th>
						<th>Introduced date</th>
						<!-- Table header for Discontinued Date -->
						<th>Discontinued date</th>
						<!-- Table header for Company -->
						<th>Company</th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach var="item" items="${computers}">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="0"></td>
							<td><a href="/servletEditing" onclick=""> ${item.name }</a>
							</td>
							<td>${item.localDateIntroduction}</td>
							<td>${item.localDateDiscontinued}</td>
							<td>${item.idCompany}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<%--For displaying Previous link except for the 1st page --%>
			<c:if test="${numPage!=1 && (endPage-beginPage+1) lt 5}">
				<td><a aria-label="Previous"
					href="computerServlet?beginPage=${beginPage -1}&endPage=${endPage}&size=${size}"><span
						aria-hidden="true">&laquo;</span></a></td>
			</c:if>
			<c:if test="${numPage!=1 && (endPage-beginPage+1) ge 5}">
				<td><a aria-label="Previous"
					href="computerServlet?beginPage=${beginPage -1}&endPage=${endPage-1}&size=${size}"><span
						aria-hidden="true">&laquo;</span></a></td>
			</c:if>
			<ul class="pagination">

				<c:forEach begin="${beginPage}" end="${endPage}" var="i">
					<c:choose>
						<c:when test="${numPage eq i}">
							<li class="page-item active"><a class="page-link"> ${i}
									<span class="sr-only">(current)</span>
							</a></li>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${(endPage-beginPage+i) lt numberOfPages}">
									<li class="page-item"><a class="page-link"
										href="computerServlet?beginPage=${i}&size=${size}&endPage=${i+(endPage-beginPage)}">${i}</a></li>
								</c:when>
								<c:otherwise>
									<li class="page-item"><a class="page-link"
										href="computerServlet?beginPage=${i}&size=${size}&endPage=${endPage}">${i}</a></li>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</ul>
			<c:if test="${endPage lt numberOfPages}">
				<td><a
					href="computerServlet?beginPage=${beginPage +1}&endPage=${endPage + 1}&size=${size}">Next</a></td>
			</c:if>
			<div class="btn-group btn-group-sm pull-right" role="group">
				<a href="computerServlet?beginPage=${beginPage}&endPage=${endPage}&size=${10}">
					<button type="button" class="btn btn-default">10</button>
				</a> <a class="page-link"
					href="computerServlet?beginPage=${beginPage}&endPage=${endPage}&size=${50}">
					<button type="button" class="btn btn-default">50</button>
				</a> <a href="computerServlet?beginPage=${beginPage}&endPage=${endPage}&size=${100}">
					<button type="button" class="btn btn-default">100</button>
				</a>
			</div>
		</div>

	</footer>
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/dashboard.js"></script>
</body>
</html>