<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="right_col" role="main">
	<div class="">

		<div class="clearfix"></div>
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel">
				<div class="x_title">
					<h2>History List</h2>

					<div class="clearfix"></div>
				</div>


				<div class="x_content">
					<div class="container" style="padding: 50px;">
						<%--@elvariable id="searchForm" type=""--%>
						<form:form modelAttribute="searchForm" cssClass="form-horizontal form-label-left" servletRelativeAction="/history/list/1" method="POST">
							
							<div class="form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12" for="code">Category</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<form:input path="productInfo.category.name" cssClass="form-control col-md-7 col-xs-12" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Action </label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<form:input path="actionName" cssClass="form-control col-md-7 col-xs-12" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Type </label>

								<div class="col-md-6 col-sm-6 col-xs-12">
									<form:select path="type" cssClass="form-control">
										<form:options items="${mapType}" />
									</form:select>
								</div>
							</div>


							<div class="form-group">
								<div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
									<button type="submit" class="btn btn-success">Search</button>
									<button class="btn btn-success"><a href="<c:url value="/history/getAll/1"/>"  style="color: white">Get All</a></button>

								</div>
							</div>

						</form:form>
					</div>

					<div class="table-responsive">
						<table class="table table-striped jambo_table bulk_action">
							<thead>
								<tr class="headings">
									<th class="column-title">#</th>
									<th class="column-title">Category</th>

									<th class="column-title">Name</th>
									<th class="column-title">Qty</th>
									<th class="column-title">Price</th>
									<th class="column-title">Type</th>
									<th class="column-title">User</th>
									<th class="column-title">Update Date</th>

									<th class="column-title">Action</th>
								</tr>
							</thead>

							<tbody>
								<c:forEach items="${histories}" var="history" varStatus="loop">

									<c:choose>
										<c:when test="${loop.index%2==0 }">
											<tr class="even pointer">
										</c:when>
										<c:otherwise>
											<tr class="odd pointer">
										</c:otherwise>
									</c:choose>
									<td class=" ">${pageInfo.getOffset()+loop.index+1}</td>
									<td class=" ">${history.productInfo.category.name }</td>

									<td class=" ">${history.productInfo.name }</td>
									<td class="">${history.qty}</td>
									<td class="price" style="font-size: 14px">${history.price }</td>
									<c:choose>
										<c:when test="${history.type==1}">
											<td>Goods Receipt</td>
										</c:when>
										<c:otherwise>
											<td>Goods Issues</td>
										</c:otherwise>
									</c:choose>
									<td>${history.user.name}</td>
									<td>${history.updateDate}</td>
									<td>${history.actionName}</td>
									</tr>
								</c:forEach>

							</tbody>
						</table>
						<jsp:include page="../layout/paging.jsp"></jsp:include>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	function gotoPage(page) {
		$('#searchForm').attr('action',
				'<c:url value="/history/list/"/>' + page);
		$('#searchForm').submit();
	}
	$(document).ready(function() {
		processMessage();
	});
	function processMessage() {
		var msgSuccess = '${msgSuccess}';
		var msgError = '${msgError}';
		if (msgSuccess) {
			new PNotify({
				title : ' Success',
				text : msgSuccess,
				type : 'success',
				styling : 'bootstrap3'
			});
		}
		if (msgError) {
			new PNotify({
				title : ' Error',
				text : msgError,
				type : 'error',
				styling : 'bootstrap3'
			});
		}
	}
</script>