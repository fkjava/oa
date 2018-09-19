<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta name="decorator" content="/WEB-INF/layouts/main.jsp">
<title>首页</title>
<style type="text/css">
.col-md-4 .panel-body
{
	height: 130px;
	overflow: auto;
}
</style>
</head>
<body>
	<div class="col-md-4">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<span class="glyphicon glyphicon-tasks"></span>待办任务
			</div>
			<div class="panel-body">
				<div>任务1  还剩5小时</div>
				<div>任务1  还剩5小时</div>
				<div>任务1  还剩5小时</div>
				<div>任务1  还剩5小时</div>
				<div>任务1  还剩5小时</div>
			</div>
		</div>
	</div>
	<div class="col-md-4">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<span class="glyphicon glyphicon-bell"></span>
				最新公告
			</div>
			<div class="panel-body">
				<div>习近平向2018年国际和平日纪念活动致贺信</div>
				<div>朝鲜将废除东仓里导弹发射基地</div>
				<div>长春长生被取消高新技术企业资格</div>
				<div>税务机关不得自行清查此前社保欠费</div>
				<div>普京:伊尔-20军机被击落是＂悲剧性偶然因素＂所致</div>
			</div>
		</div>
	</div>
	<div class="col-md-4">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<span class="glyphicon glyphicon-bullhorn"></span>
				公司动态
			</div>
			<div class="panel-body">
				<div>贫困乡农户家中百万现金失窃 竟是北京＂股神＂盗走</div>
				<div>高铁站内老人心跳骤停 “白衣天使”跪地紧急救人</div>
				<div>韩国青瓦台：朝韩领导人20日将登白头山</div>
				<div>见证土星北极六边形风暴</div>
				<div>易读|阿里发布城市大脑2.0：可实时指挥200名交警</div>
				<div>易读|马云谈制造业：保护落后力量会破坏创新</div>
			</div>
		</div>
	</div>
	<div class="col-md-4">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<span class="glyphicon glyphicon-globe"></span>
				科技动态
			</div>
			<div class="panel-body">
				<div>紫光赵伟国：再有5年，中国集成电路将站稳脚跟</div>
				<div>易读|京东电子营业执照区块链应用场景落地</div>
				<div>美团无人配送车“小袋”亮相2018世界人工智能大会</div>
				<div>社交大败局：别傻了，00后不需要专属于他们的社交产品</div>
				<div>中国探测器2021年着陆火星，长征九号拟2028年首飞</div>
				<div>美团公布配售结果：国际发售部分获大幅超额认购</div>
			</div>
		</div>
	</div>
	<div class="col-md-4">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<span class="glyphicon glyphicon-eye-open"></span>
				IT业界
			</div>
			<div class="panel-body">
				<div>英特尔前总裁再创业：用ARM构架生产数据中心芯</div>
				<div>乔布斯高效：百万美元买亚马逊专利只用一次电话</div>
				<div>苹果已上缴完争议税款，与爱尔兰一起等待欧盟裁</div>
				<div>马斯克私有化声明遭美司法部调查 或涉及犯罪</div>
				<div>首位月球旅客:放弃大学去唱摇滚 有钱后疯买艺术</div>
				<div>2023年的环月旅行 马斯克会与日本亿万富翁同行</div>
			</div>
		</div>
	</div>
</body>
</html>