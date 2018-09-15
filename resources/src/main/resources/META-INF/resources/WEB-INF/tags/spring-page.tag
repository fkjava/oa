<%@ tag language="java" pageEncoding="UTF-8"%>
<%-- 引入其他标签库 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- 标签库参数 --%>
<%-- required="true" 表示必须的 --%>
<%-- rtexprvalue="true" 为true表示参数可以用EL表达式传入值 --%>
<%@ attribute name="url" 
	required="true" 
	rtexprvalue="true"
	type="java.lang.String" %>
<%-- 如果传入的URL包含了问号，后面加页面参数用&符号 --%>
<c:if test="${url.indexOf('?') >= 0 }">
	<c:set var="url" value="${url }&"/>
</c:if>
<c:if test="${url.indexOf('?') < 0 }">
	<c:set var="url" value="${url }?"/>
</c:if>
<nav aria-label="分页导航">
    <ul class="pagination">
        <li>
            <a href="${url }pageNumber=${page.number gt 1 ? page.number - 1 : 0 }" 
            	aria-label="上一页">
                <span aria-hidden="true">&laquo;</span>
            </a>
        </li>
        <%--
        假设4页：那么开始从0、结束到3
        假设5页： 那么开始从0、结束到5
        假设7页：如果要显示5个按钮，需要按照当前页面来循环
        	如果当前页码是3，那么左边1和2、右边4和5
        	如果当前页码是1，那么左边为0，右边是2、3、4
        	如果当前页码是6，那么左边3、4、5，右边是7
         --%>
        <c:set var="begin" value="${page.number - 2 }"/>
        <c:set var="end" value="${page.totalPages - 1 }"/>
        
        <c:if test="${begin < 0 }">
        	<c:set var="begin" value="0"/>
        </c:if>
        <!-- 总页数 - begin，大于5的时候意味着超出5个按钮的范围 -->
        <c:if test="${(end - begin) >= 5 }">
        	<c:set var="end" value="${begin + 4 }"/> 
        </c:if>
        <c:if test="${end >= page.totalPages - 1 }">
        	<%-- page.totalPages - begin 先算出现在有多少个按钮（得到2的时候，其实是3个按钮） --%>
        	<%-- 5 - (page.totalPages - begin) 算出缺多少个按钮 --%>
        	<%-- begin - 数字 ： 算出要往左边移动多少个按钮 --%>
        	<c:set var="begin" value="${begin -  (5 - (page.totalPages - begin))}" />
        	<c:set var="end" value="${page.totalPages - 1 }"/> 
        </c:if>

        <c:forEach begin="${begin }" end="${end }" var="num">
	        <li class="${num eq page.number ? "active" : "" }"><a href="${url }pageNumber=${num }">${num + 1 }</a></li>
        </c:forEach>
        <li>
            <a href="${url }pageNumber=${page.number lt page.totalPages - 1 ? page.number + 1 : page.totalPages - 1 }" 
            	aria-label="下一页">
                <span aria-hidden="true">&raquo;</span>
            </a>
        </li>
    </ul>
</nav>