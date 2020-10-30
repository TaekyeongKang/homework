<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
<!--     <link rel="icon" href="../../favicon.ico"> -->

    <title>Signin Template for Bootstrap</title>

<!--     Bootstrap core CSS -->

	<%@ include file="/layout/commonLib.jsp" %>
 <script>
 	$(function(){
 	 	$('button').on('click',function(){
			$('form').submit();
 	 	 })
	


 	})
 </script>
  </head>
 
  
  <body>
    <div class="container" >

      <form class="form-signin" action="${cp}/login" method="POST">
        <h2 class="form-signin-heading">Please sign in</h2>
        <label for="inputUserid" class="sr-only">Email address</label>
        <input type="email" id="inputEmail" name="userId" class="form-control" placeholder="Email address" required autofocus value="brown">
        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" id="inputPassword"  name="password" class="form-control" placeholder="Password" required value="brownPass">
        <div class="checkbox">
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="button">Sign in</button>
      </form>

    </div> <!-- /container -->
    


  </body>
</html>