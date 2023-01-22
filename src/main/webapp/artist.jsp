<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <title>Contact form</title>
    <style>
      html, body {
      min-height: 100%;
      padding: 0;
      margin: 0;
      font-family: Roboto, Arial, sans-serif;
      font-size: 14px;
      color: #667;
      }
      h1 {
      margin: 20px 40px 10px;
      font-weight: 400;
      color: #1c87c9;
      }
      p,label {
      margin: 0 5px 15px;
      }
      li {
      margin: 0 0 5px;
      }
      .main-block {
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      min-height: 20vh;
      background: #1c87c9;
      }
      form {
      padding: 25px;
      margin: 25px;
      box-shadow: 0 2px 5px #f5f5f5; 
      background: #f5f5f5; 
      }
      .fas {
      margin: 25px 10px 0;
      font-size: 72px;
      color: #fff;
      }
      .fa-1 {
      transform: rotate(-20deg);
      }
      input, textarea {
      width: calc(50% - 28px);
      padding: 8px;
      margin-bottom: 10px;
      border: 1px solid #1c87c9;
      outline: none;
      }
      input::placeholder {
      color: #666;
      }
      button {
      padding: 10px;
      border: none;
      background: #1c87c9; 
      font-size: 16px;
      font-weight: 400;
      color: #fff;
      }
      .left-part, form {
      width: 30%;
      }
      .fa-1 {
      margin-top: 0;
      margin-left: 20%;
      }
      }
    </style>
  </head>
  <body>
    <img src="images/LogoMakr-5kXT6J.png" alt="">  
    <h1>Artists parameter screen</h1>
	
	<form action="artist" method="get">
	  <label for="personID">Artist ID</label><br/>
	  <input type="text" id="personID" name="pid"><br/><br/>
	
	  <label for="fname">Label english ( like) </label><br/>
	  <input type="text" id="fname" name="lbl"><br/><br/>
	  
      <jsp:include page="/country" />  
      
	  <jsp:include page="/movement" />  

	  <label for="artists">Artist type</label>
	  <select id="artists" name="artistType">
	    <option value="all">all</option>
	    <option value="painter">Painter</option>
	    <option value="sculptor">Sculptor</option>
	    <option value="ceramicist">Ceramicist</option>
	    <option value="architect">Architect</option>
	  </select><br/><br/>
	  
	  <label for="gender">Gender</label>
	  <select id="gender" name="gender">
        <option value="all" selected >all</option>
	    <option value="male">male</option>
	    <option value="female">female</option>
	  </select><br/><br/>
      
      <br/><input type="submit" value="Submit">
      <input type="reset">
	</form>

</body>
</html>