<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<ul class="am-pagination">
	<c:choose>
		<c:when test="${page.page <= 1}">
			<li class="am-disabled"><a href="#">Prev</a></li>
		</c:when>
		<c:otherwise>
			<li><a href="${page.url}&page=${page.page - 1}">Prev</a></li>
		</c:otherwise>
	</c:choose>

	<c:choose>
		<c:when test="${page.pageCount > 13}">
			<c:choose>
				<c:when test="${page.page > 7}">
					<c:choose>
						<c:when test="${page.page + 6 <= page.pageCount}">
							<c:forEach var="num" begin="${page.page - 6}" end="${page.page + 6}">
								<c:choose>
									<c:when test="${num == page.page}">
										<li class="am-active"><a href="#">${num}</a></li>
									</c:when>
									<c:otherwise>
										<li><a href="${page.url}&page=${num}">${num}</a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:when>
						<c:when test="${page.page == page.pageCount}">
							<c:forEach var="num" begin="${page.page - 12}" end="${page.page}">
								<c:choose>
									<c:when test="${num == page.page}">
										<li class="am-active"><a href="#">${num}</a></li>
									</c:when>
									<c:otherwise>
										<li><a href="${page.url}&page=${num}">${num}</a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<c:forEach var="num" begin="${page.page - 7}"
								end="${page.pageCount}">
								<c:choose>
									<c:when test="${num == page.page}">
										<li class="am-active"><a href="#">${num}</a></li>
									</c:when>
									<c:otherwise>
										<li><a href="${page.url}&page=${num}">${num}</a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<c:forEach var="num" begin="1" end="13">
						<c:choose>
							<c:when test="${num == page.page}">
								<li class="am-active"><a href="#">${num}</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="${page.url}&page=${num}">${num}</a></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			<c:forEach var="num" begin="1" end="${page.pageCount}">
				<c:choose>
					<c:when test="${num == page.page}">
						<li class="am-active"><a href="#">${num}</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="${page.url}&page=${num}">${num}</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</c:otherwise>
	</c:choose>

	<c:choose>
		<c:when test="${page.pageCount == page.page || page.count == 0}">
			<li class="am-disabled"><a href="#">Next</a></li>
		</c:when>
		<c:otherwise>
			<li><a href="${page.url}&page=${page.page + 1}">Next</a></li>
		</c:otherwise>
	</c:choose>
</ul>