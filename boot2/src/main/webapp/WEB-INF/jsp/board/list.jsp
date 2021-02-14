<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판목록보기[list.jsp]</title>
<%@ include file="/common/JEasyUICommon.jsp"%>
<script type="text/javascript">
//전역변수 선언하기 - 자바스크립트에서 
    
    var g_no = 0;
	function writeForm(){
		//alert("글쓰기 호출 성공");
		   $('#dlg_writeForm').dialog('open').dialog('center').dialog('setTitle','글 쓰기');
	}
	function boardSelect(){
		alert("글조회 호출 성공");
	}
	function boardInsert(){
		$("#f_write").attr("method","post");
		$("#f_write").attr("action","/board/boardInsert.hk");
		$("#f_write").submit();
	}
</script>
</head>
<body>
	<script type="text/javascript">
	$(document).ready(function(){//익명함수 선언하기
		//alert("DOM구성이 끝났을 때 - 누가? 브라우저");
		$('#dg_board').datagrid({
			onSelect: function(index, row){
				alert("row :" + row + ", 선택한 글번호 : " + row.BM_NO);
				g_no = row.BM_NO;
			  	location.href="boardDetail.hk?bm_no="+g_no;
			}
		});
		$('#dg_board').datagrid({
			onDblClickCell: function(index,field,value){
				alert(index + ", " + field + ", " + value);
				//제목을 누른거니?
				
			}
		});
	});
</script>

	<table id="dg_board" class="easyui-datagrid" title="MVC패턴을 적용한 계층형게시판"
		style="width: 700px; height: 450px"
		data-options="singleSelect:true,collapsible:true,url:'jsonBoardList.sp4?bm_no=0',method:'get',toolbar:'#tbar_board'">
		<thead>
			<tr>
				<th data-options="field:'BM_NO',width:80">글번호</th>
				<th data-options="field:'BM_TITLE',width:250">제목</th>
				<th data-options="field:'BS_FILE',width:100">첨부파일</th>
				<th data-options="field:'BM_WRITER',width:80,align:'right'">작성자</th>
				<th data-options="field:'BM_HIT',width:80,align:'right'">조회수</th>
			</tr>
		</thead>
	</table>

	<!--==============툴바시작하기 시작===============-->
	<div id="tbar_board">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-edit" plain="true" onclick="writeForm()">글쓰기</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-search" plain="true" onclick="boardSelect()">조회</a>
	</div>
	<!--==============툴바시작하기 끝===============-->
	<!--==============글쓰기화면 시작===============-->
	<div id="dlg_writeForm" class="easyui-dialog" style="width: 400px"
		data-options="closed:true,modal:true,border:'thin',buttons:'#writeForm-buttons'">
		<form id="f_write" novalidate style="margin: 0; padding: 20px 50px">
			<!-- 
board_master_t테이블에서 개발자가 필요해서 추가된 컬럼이 있다.
bm_group, bm_pos, bm_step
화면과는 상관이 없는 컬럼으므로 서버에 전달할때 hidden속성으로 넘겨준다
 -->
			<input type="hidden" id="bm_no" name="bm_no" value="0"> 
			<input type="hidden" id="bm_group" name="bm_group" value="0"> 
			<input type="hidden" id="bm_pos" name="bm_pos" value="0"> 
			<input type="hidden" id="bm_step" name="bm_step" value="0">
			<input name="bm_group" type="hidden" value="0">
			<h3>글쓰기</h3>
			<div style="margin-bottom: 10px">
				<input name="bm_title" class="easyui-textbox" required="true" label="제목" style="width: 100%">
			</div>
			<div style="margin-bottom: 10px">
				<input name="bm_writer" class="easyui-textbox" required="true" label="작성자" style="width: 100%">
			</div>
			<div style="margin-bottom: 10px">
				<input name="bm_email" class="easyui-textbox" required="true" label="이메일" style="width: 100%">
			</div>
			<div style="margin-bottom: 10px">
				<input name="bm_content" class="easyui-textbox" required="true" data-options="multiline:'true', width:'350px', height:'90px'" label="내용" style="width: 100%">
			</div>
			<div style="margin-bottom: 10px">
				<input name="bm_pw" class="easyui-textbox" required="true" data-options="width:'100%'" label="비번" style="width: 100%">
			</div>
		</form>
	</div>
	<div id="writeForm-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton c6"
			iconCls="icon-ok" onclick="boardInsert()" style="width: 90px">저장</a>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel"
			onclick="javascript:$('#dlg_writeForm').dialog('close')"
			style="width: 90px">취소</a>
	</div>
	<!--==============글쓰기화면 끝===============-->

</body>
</html>