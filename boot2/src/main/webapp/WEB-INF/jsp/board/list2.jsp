<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, web.mvc.PageBar" %>
<%
   List<Map<String,Object>> boardList = (List<Map<String,Object>>)request.getAttribute("boardList");
   int stot = 0;
   if(session.getAttribute("stot") != null) {
	   stot = Integer.parseInt(session.getAttribute("stot").toString());
   }
   out.print("stot :" + stot);
   int pageNumber = 0;
   
   //size를 total record로 사용할 수 없다.
   int size = 0;
   if(boardList != null){
      size = boardList.size();
   }
   out.print("size: "+size);
   /////////////////////페이지 네비게이션 추가 코드
   int numPerPage = 4;
   int nowPage = 0;
   //현재 내가 보는 페이지는 보고 있는 중이니까 링크를 걸지 말자.
   //그러나 다른 페이지 숫자는 링크를 걸어야 함.
   if(request.getParameter("nowPage") != null) {
	   nowPage = Integer.parseInt(request.getParameter("nowPage"));
   } 
  
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글목록[list2.jsp]</title>
<%@ include file="/common/JEasyUICommon.jsp" %>

    <script type="text/javascript">
  //전역변수 선언하기 - 자바스크립트에서
       var g_no = 0;
       var url;
        function writeForm(){
           alert("글쓰기 호출 성공");
            $('#dlg_writeForm').dialog('open').dialog('center').dialog('setTitle','New User');
            $('#fm').form('clear');
            url = 'save_user.php';
        }
        
        //전체 조회 되도록 처리해 보기 
        function boardSelect(){
           //alert("글조회 호출 성공");
           var u_date = $('#bm_date').datebox('getValue');
           var queryString ='nowPage=0';
           if(u_date.length==10) {
        	   //alert("선택한 날짜 : " + u_date);
        	   queryString += "&bm_date=" + u_date;
           }
           location.href="boardList.sp4?" + queryString;
        }
        
        function boardInsert(){
           $("#f_write").attr("method","Post");
           $("#f_write").attr("action","/test/board/boardInsert.sp4");
           $("#f_write").submit();
        }
    </script>
</head>
<body>
	<script type="text/javascript">
	//날짜에 대한 포맷을 대한민국 정서에 맞게 바꿔보기
	$.fn.datebox.defaults.formatter = function(date){
		var y = date.getFullYear();
		var m = date.getMonth()+1;
		var d = date.getDate();
		return y + '-' +(m<10 ? "0" + m:m) + '-' + (d<10 ? "0" + d:d);
	}
	$.fn.datebox.defaults.parser = function(s){
		var t = Date.parse(s);
		if (!isNaN(t)){
			return new Date(t);
		} else {
			return new Date();
		}
	}
/*
 * 브라우저가 웹페이지를 처리하는 순서(절차이해) 
 * jsp, servlet코드는 이미 톰캣 서버내부에서 결정되고 그 결과가 브라우저에 문자열로 전송됨
 * 전송된 결과와 html코드가 만남.
 * 클라이언트측 브라우저가 다운받음.
 * DOM구성(tree)완성
 */
	$(document).ready(function(){//익명함수 선언하기
		//alert("DOM구성이 끝났을 때 - 누가? 브라우저");
		$('#dg_board').datagrid({
			onSelect: function(index, row){
				alert("row :" + row + ", 선택한 글번호 : " + row.BM_NO);
				g_no = row.BM_NO;
			  	location.href="boardDetail.sp4?bm_no="+g_no;
			}
		});
		$('#dg_board').datagrid({
			onDblClickCell: function(index,field,value){
				alert(index + ", " + field + ", " + value);
				//제목을 누른거니?
				
			}
		});
		//combobox에 값이 변경되었니?
		$('#cb_search').combobox({
			onChange: function(){
				g_combo = $(this).combobox('getValue');
				//alert(g_combo);
			}
		}); 
	    //textbox에 엔터쳤을 때 
	  	var t = $('#keyword');
		t.textbox('textbox').bind('keydown', function(e){
		if (e.keyCode == 13){	// when press ENTER key, accept the inputed value.
			//alert("엔터쳤을 때");
			//사용자가 입력한 키워드는 어떻게 가져오지?
			//사용자가 입력한 값 = $().val();
			//alert($("#keyword").val());
			location.href="boardList.sp4?nowPage=0&cb_search=" + g_combo + "&keyword=" + $("#keyword").val();
			//t.textbox('setValue', $(this).val());
		}
		});

	});
  
  
	
</script>
   <!-- =============다이얼로그 창 시작============= -->
    <div id="dlg_writeForm" class="easyui-dialog" style="width:600px" data-options="closed:true,modal:true,border:'thin',buttons:'#writeForm-buttons'">
        <form id="f_write" enctype="multipart/form-data" novalidate style="margin:0;padding:20px 50px">
 <!-- 
    board_master_t 테이블에서 개발자가 필요해서 추가된 컬럼이 있다.
    bm_group, bm_pos, bm_step
    화면과는 상관이 없는 컬럼이므로 서버에 전달할 때 hidden속성으로 넘겨줌.
  -->
           <input type="hidden" name="bm_no" value="0">
           <input type="hidden" name="bm_group" value="0">
           <input type="hidden" name="bm_pos" value="0">
           <input type="hidden" name="bm_step" value="0">
            <h3>글쓰기</h3>
            <div style="margin-bottom:10px">
                <input name="bm_title" class="easyui-textbox" required="true" label="제목:" style="width:100%">
            </div>
            <div style="margin-bottom:10px">
                <input name="bm_writer" class="easyui-textbox" required="true" label="작성자:" style="width:100%">
            </div>
            <div style="margin-bottom:10px">
                <input name="bm_email" class="easyui-textbox" required="true" label="이메일:" style="width:100%">
            </div>
            <div style="margin-bottom:10px">
                <input name="bm_content" class="easyui-textbox" required="true" data-options="multiline:'true',width:'350px',height:'90px'" label="내용:" style="width:100%">
            </div>
            <div style="margin-bottom:10px">
                <input name="bm_pw" class="easyui-textbox" required="true" label="비밀번호:" style="width:100%">
            </div>
            <div style="margin-bottom:10px">
                <input name="bs_file" class="easyui-filebox" label="첨부파일:" style="width:500px">
            </div>
        </form>
    </div>
    <div id="writeForm-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="boardInsert()" style="width:90px">저장</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_writeForm').dialog('close')" style="width:90px">취소</a>
    </div>
    <!-- =============다이얼로그 창 끝============= -->
    
    <table id="dg_board" class="easyui-datagrid" title="MVC패턴을 적용한 계층형게시판" style="width:950px;height:450px"
            data-options="singleSelect:true,collapsible:true"
            toolbar="#tbar_board">
        <thead>
            <tr>
                <th data-options="field:'BM_NO',width:80, align:'center'">글번호</th>
                <th data-options="field:'BM_TITLE',width:400" >글제목</th>
                <th data-options="field:'BS_FILE',width:200, align:'center'">첨부 파일</th>
                <th data-options="field:'BM_WRITER',width:140,align:'center'">작성자</th>
                <th data-options="field:'BM_HIT',width:80, align:'center'">조회수</th>
            </tr>
        </thead>
        <tbody>
    <!-- 데이터가 존재하니? -->
    <%
       if(size>0){
          for(int i=0; i<size;i++){
             Map<String,Object> rMap = boardList.get(i);
       
    %>
            <tr>
                <td><%=rMap.get("BM_NO") %></td>
                
 <!-- 너 혹시 댓글이니? -->
 				<td>
 <%
   String imgPath = "\\images\\";
   if(Integer.parseInt(rMap.get("BM_POS").toString()) >0 ) {
	   for(int j=0; j<Integer.parseInt(rMap.get("BM_POS").toString()); j++) {
		   out.print("&nbsp;&nbsp");
	   }//////end of for
 %>
   <img src="<%=imgPath %>reply.gif" border="0">

 <!-- html땅  -->
 <%
   }////////////end of 댓글일 때 사전처리]
 
 %>
                	<%=rMap.get("BM_TITLE") %></td>
                	<td>
<%
	if(!"파일없음".equals(rMap.get("BS_FILE"))) {
%>             	                
                <a href="downLoad.jsp?bs_file=<%=rMap.get("BS_FILE") %>">
                <%=rMap.get("BS_FILE") %>
                </a>
<%
	}else{
%>		
	<%= rMap.get("BS_FILE")%> 
<% 
    }
%>	
				
				</td>
                <td><%=rMap.get("BM_WRITER") %></td>
                <td><%=rMap.get("BM_HIT") %></td>
            </tr>
    <%   
          }///////////////////////////end of for문
       } //조회결과가 있을때
       else{
    %>
       <tr>
          <td colspan="5" align="center">조회결과가 없습니다.</td>
       </tr>
    <!-- 조회결과가 없는거야? -->
    <%
       } //조회결과가 없을때
    %>
        </tbody>
    </table>
    <!-- ===============페이지 네비게이션 추가하기 시작 ====================-->
    	<table border="1" borderColor="red" style="width:950px">
    		<tr>
    			<td align="center">
    			1 2 3 4 5 6 7 8 9 10
    			<br>
	<%
		String pagePath = "boardList.sp4";
//numPerPage : 한 페이지에 출력할 글의 로우수 
//stot : 전체 레코드 숫자
//	
		PageBar pb = new PageBar(numPerPage, stot, nowPage, pagePath);
		String pagination = null;
		pagination = pb.getPageBar();//<a href=xxx.sp4?nowPage=1></a>
		out.print(pagination);
		session.setAttribute("pageSize", numPerPage);
		
	%>
    			</td>
    		</tr>
    	</table>
    <!-- ===============페이지 네비게이션 추가하기 끝 ====================-->
    <!-- ===============툴바 추가하기 시작=============== -->
        <div id="tbar_board">
        	<table>
        		<tr>
       				<td>
  			   		 <!-- 조건 검색 추가 시작 -->
     
			     	<select id="cb_search" name="cb_search" class="easyui-combobox" style="width:120px;">
			     		<option selected value="">선택</option>
			     		<option value="bm_title">제목</option>
			     		<option value="bm_content">내용</option>
			     		<option value="bm_writer">작성자</option>
			     	</select>     
			     	<input class="easyui-textbox" id="keyword" name="keyword" style="width:300px">
			     	작성일 : <input class="easyui-datebox" id="bm_date" name="bm_date" style="width:120px">
 
    				<!-- 조건 검색 추가 끝 -->
       				</td>
        		</tr>
        		<tr>
       			<td>버튼</td>
        		</tr>
        	</table>	
        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="writeForm()">글쓰기</a>
        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="boardSelect()">조회</a>
    </div>
    <!-- ===============툴바 추가하기 시작=============== -->
</body>
</html>