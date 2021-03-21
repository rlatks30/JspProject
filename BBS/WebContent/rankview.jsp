<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="schedule.ScheduleDAO" %>
<%@ page import="schedule.Schedule" %>
<%@ page import="schedule.Rank" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>View User Rank</title>
</head><style>
#container {
  height: 700px; 
}

.highcharts-figure, .highcharts-data-table table {
  min-width: 500px; 
  max-width: 1200px;
  margin: 1em auto;
}

.highcharts-data-table table {
  font-family: Verdana, sans-serif;
  border-collapse: collapse;
  border: 1px solid #EBEBEB;
  margin: 10px auto;
  text-align: center;
  width: 100%;
  max-width: 500px;
}
.highcharts-data-table caption {
  padding: 1em 0;
  font-size: 15px;
  color: #555;
}
.highcharts-data-table th {
	font-weight: 600;
  padding: 0.5em;
}
.highcharts-data-table td, .highcharts-data-table th, .highcharts-data-table caption {
  padding: 0.5em;
}
.highcharts-data-table thead tr, .highcharts-data-table tr:nth-child(even) {
  background: #f8f8f8;
}
.highcharts-data-table tr:hover {
  background: #f1f7ff;
}
</style>

<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/slider.css">
<title>JSP 목표를 향해</title>
<link type="text/css" rel="stylesheet" href="css/styles.css">
<body>


	<%@include file="header.jsp"%>


	<%
	String[] uID = new String[5];
	int i = 0;
	int[] point = new int[5];
	try {
		ArrayList<Rank> list = new ArrayList<Rank>();
		ScheduleDAO scheduleDAO = new ScheduleDAO();
		list = scheduleDAO.getRankList();
		for (Rank rank : list) {
			uID[i] = rank.getUserID();
			point[i] = rank.getPoint();
			System.out.println(point[i]);
			i++;
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	System.out.println(uID[0]);
	%>

<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/highcharts-more.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script src="https://code.highcharts.com/modules/export-data.js"></script>
<script src="https://code.highcharts.com/modules/accessibility.js"></script>



<figure class="highcharts-figure">
  <div id="container"></div>
  <p class="highcharts-description">
    <h3 style="text-align:center;">목표 달성 1위부터 5위까지 기록한 랭킹 차트표, 목표 달성을 기원합니다!!!</h3>
  </p>
</figure>

<script type="text/javascript">
const chart = Highcharts.chart('container', {
    title: {
        text: 'Chart.update'
    },
    subtitle: {
        text: 'Plain'
    },
    xAxis: {
        categories: [<%=uID[1]%>, <%=uID[1]%>, <%=uID[2]%>, <%=uID[3]%>, <%=uID[4]%>]
    },
    series: [{
        type: 'column',
        colorByPoint: true,
        data: [<%=point[0]%>,<%=point[1]%>,<%=point[2]%>,<%=point[3]%>,<%=point[4]%>],
        showInLegend: false
    }]
});

document.getElementById('plain').addEventListener('click', () => {
    chart.update({
        chart: {
            inverted: false,
            polar: false
        },
        subtitle: {
            text: 'Plain'
        }
    });
});

document.getElementById('inverted').addEventListener('click', () => {
    chart.update({
        chart: {
            inverted: true,
            polar: false
        },
        subtitle: {
            text: 'Inverted'
        }
    });
});

document.getElementById('polar').addEventListener('click', () => {
    chart.update({
        chart: {
            inverted: false,
            polar: true
        },
        subtitle: {
            text: 'Polar'
        }
    });
});

</script>
	<%@include file="footer.jsp"%>
</body>
</html>