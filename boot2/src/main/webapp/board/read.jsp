<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.*" %>
<%
	List<Map<String, Object>> boardDetail = (List<Map<String, Object>>) request.getAttribute("boardDetail");
	int size = 0;
	String bm_no = null;
	String bm_pw = null;
	String bs_file = null;
	
	if(boardDetail != null) {
		size = boardDetail.size();
		bm_no = boardDetail.get(0).get("BM_NO").toString();
		bm_pw = boardDetail.get(0).get("BM_PW").toString();
		bs_file = boardDetail.get(0).get("BS_FILE").toString();
	}
	out.print("size : " + size);//1

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글상세보기[read.jsp]</title>
<%@ include file="/common/JEasyUICommon.jsp"%>
<script type="text/javascript">
	function boardInsert(){
		$("#f_reple").attr("method","post");
		//board폴더이름-작업이름사용, boardUpdate.hk에서
		//hk를 잘라낸 command.lastIndexOf(".")
		//boardUpdate를 메소드 이름으로 사용한다.
		
		$("#f_reple").attr("action","/board/boardInsert.hk");
		$("#f_reple").submit();
	}
	function boardUpdate(){
		$("#f_update").attr("method","post");
		$("#f_update").attr("action","/test/board/boardUpdate.sp4");
		$("#f_update").submit();
	}
	
	function boardDelete(){
		$("#f_delete").attr("method","post");
		$("#f_delete").attr("action","/test/board/boardDelete.sp4");
		$("#f_delete").submit();
	}
	
	function repleForm() {//댓글쓰기
		 $('#dlg_reple').dialog('open').dialog('center').dialog('setTitle','글 쓰기');
	}
	function updateForm() {//수정하기
		 $('#dlg_update').dialog('open').dialog('center').dialog('setTitle','수정');
	}
	function deleteForm() {//삭제하기
		$('#dlg_delete').dialog('open').dialog('center').dialog('setTitle','삭제');
	}
	function boardDelete() {
		//오라클 서버에서 꺼낸 비번
		var db_pw = <%=bm_pw%>;
		//사용자가 입력한 비번
		var ubm_pw = $("#ubm_pw").textbox('getValue');
		if(db_pw == ubm_pw) {
			$.messager.confirm('Confirm', '정말 삭제하시겠습니까?', function(r){
				if(r){
					location.href="./boardDelete.sp4?bm_no=<%=bm_no%>&bs_file=<%=bs_file%>";
				}
			});
		}else{
			alert("비번이 일치하지 않습니다.");
			$("#ubm_pw").textbox('setValue','');
		}
	}
	function boardList() {//목록
		location.href="boardList.sp4?nowPage=0";
	}
</script>
</head>
<body>

	<form id="f_read">
<!-- 
	그룹번호, 순번, 차수는 사용자로부터 입력받는 값이 아니다. 따라서 화면은 필요 없다. 그러나 그 값은 넘겨주어야 한다.
	개발자가 서블릿으로 전달.
	id는 jquery와 easyui 혹은 ajax에서 사용하고 name 값은 서블릿 혹은 xxxController에서 사용한다.
 -->		
	<input type="hidden" id="bm_group" name="bm_group" value="0">
	<input type="hidden" id="bm_pos" name="bm_pos" value="0">
	<input type="hidden" id="bm_step" name="bm_step" value="0">
	</form>
	<table id="pn_read" class="easyui-panel" title="글 상세보기" 
        style="width:670px;height:380px;padding:10px;background:#fafafa;"
        data-options="footer:'#ft_read', iconCls:'icon-save',closable:true,
                collapsible:true,minimizable:true,maximizable:true">
         
         <%
         
         	if(size==0) {
         		
        %>
         <!-- ============ 조회된 결과가 없을 때 ===================== -->
         <tr>
         	<td colspan="2">조회 결과가 없습니다.</td>
         </tr>
     	<% 
         	} else {
     	%>
         <!-- ============ 조회된 결과가 있을 때 ===================== -->
         
	    <tr>
	    	<td>제목</td>
	    	<td>
	    	<input name="bm_title" class="easyui-textbox" value="<%=boardDetail.get(0).get("BM_TITLE")%>" required="true" style="width: 500px">
	    	</td>
	    </tr> 
	    <tr>
	    	<td>작성자</td>
	    	<td>
	    	<input name="bm_writer" class="easyui-textbox" value="<%=boardDetail.get(0).get("BM_WRITER") %>"required="true" style="width: 150px">
	    	</td>
	    </tr> 
	    <tr>
	    	<td>이메일</td>
	    	<td>
	    	<input name="bm_email" class="easyui-textbox" value="<%=boardDetail.get(0).get("BM_EMAIL") %>"required="true" style="width: 200px">
	    	</td>
	    </tr> 
	    <tr>
	    	<td>내용</td>
	    	<td>
	    	<input name="bm_content" class="easyui-textbox"data-options="multiline:'true', width:'350px', height:'90px'" value="<%=boardDetail.get(0).get("BM_CONTENT") %>"required="true">
	    	</td>
	    </tr> 
	  	<%
       		} ///////////// end of 조회결과가 있을 때
       	%>
    </table>
    <!-- 버튼 4개 추가하기 시작 -->
    <div id="ft_read" style="padding:2px 5px;">
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="repleForm()">댓글쓰기</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="updateForm()">수정</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteForm()">삭제</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="boardList()">목록</a>
    </div>
    <!-- 버튼 4개 추가하기 끝-->
    <!-- 화면이 몇개가 필요한가요? 3개[댓글쓰기, 수정, 삭제] -->
    <!--=========댓글쓰기 화면 시작 ===========  -->
    	<div id="dlg_reple" class="easyui-dialog" style="width: 400px"
		data-options="closed:true,modal:true,border:'thin',buttons:'#reple-buttons'">
		<form id="f_reple" novalidate style="margin: 0; padding: 20px 50px">
			<input type="hidden" id="bm_no" name="bm_no" value="<%=boardDetail.get(0).get("BM_NO") %>"> 
			<input type="hidden" id="bm_group" name="bm_group" value="<%=boardDetail.get(0).get("BM_GROUP") %>"> 
			<input type="hidden" id="bm_pos" name="bm_pos" value="<%=boardDetail.get(0).get("BM_POS") %>"> 
			<input type="hidden" id="bm_step" name="bm_step" value="<%=boardDetail.get(0).get("BM_STEP") %>">
			
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
	<div id="reple-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="boardInsert()" style="width: 90px">저장</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_reple').dialog('close')" style="width: 90px">취소</a>
	</div>
	
    <!--=========댓글쓰기 화면 끝 ===========  -->
    <!--=========수정 화면 시작 ===========  -->
   
   	<div id="dlg_update" class="easyui-dialog" style="width: 400px" data-options="closed:true,modal:true,border:'thin',buttons:'#update-buttons'">
		<form id="f_update" novalidate style="margin: 0; padding: 20px 50px">
			<input type="hidden" id="bm_no" name="bm_no" value="<%=boardDetail.get(0).get("BM_NO") %>"> 
			<h3>수정하기</h3>
			<div style="margin-bottom: 10px">
				<input name="bm_title" class="easyui-textbox" required="true" label="제목" value="<%=boardDetail.get(0).get("BM_TITLE")%>" style="width: 100%">
			</div>
			<div style="margin-bottom: 10px">
				<input name="bm_writer" class="easyui-textbox" required="true" value="<%=boardDetail.get(0).get("BM_WRITER")%>" label="작성자" style="width: 100%">
			</div>
			<div style="margin-bottom: 10px">
				<input name="bm_email" class="easyui-textbox" required="true" value="<%=boardDetail.get(0).get("BM_EMAIL")%>" label="이메일" style="width: 100%">
			</div>
			<div style="margin-bottom: 10px">
				<input name="bm_content" class="easyui-textbox" required="true" data-options="multiline:'true', width:'350px', height:'90px'" value="<%=boardDetail.get(0).get("BM_CONTENT")%>" label="내용" style="width: 100%">
			</div>
			<div style="margin-bottom: 10px">
				<input name="bm_pw" class="easyui-textbox" required="true" data-options="width:'100%'" label="비번" style="width: 100%">
			</div>
		</form>
	</div>
	<div id="update-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="boardUpdate()" style="width: 90px">저장</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_update').dialog('close')" style="width: 90px">취소</a>
	</div>
	
    <!--=========수정 화면 끝 ===========  -->
    <!--=========삭제 화면 시작 ===========  -->
    <div id="dlg_delete" class="easyui-dialog" style="width: 400px" data-options="closed:true,modal:true,border:'thin',buttons:'#delete-buttons'">
			<input type="hidden" id="bm_no" name="bm_no" value="<%=boardDetail.get(0).get("BM_NO") %>"> 
			<h3>삭제하기</h3>
			<div style="margin-bottom: 10px">
				<input id="ubm_pw" name="bm_pw" class="easyui-textbox" required="true" label="비밀번호" style="width: 100%">
			</div>
	</div>
	<div id="delete-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="boardDelete()" style="width: 90px">저장</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_delete').dialog('close')" style="width: 90px">취소</a>
	</div>
    <!--=========삭제 화면 끝 ===========  -->
</body>
</html>