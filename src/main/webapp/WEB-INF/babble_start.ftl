<html>
<head><title>Babble</title>
<style type="text/css">
* {
   margin:0;
   padding:0;
}

body{
   text-align:center;
   background: #efe4bf none repeat scroll 0 0;
}

#wrapper{
   width:960px;
   margin:0 auto;
   text-align:left;
   background-color: #fff;
   border-radius: 0 0 10px 10px;
   padding: 20px;
   box-shadow: 1px -2px 14px rgba(0, 0, 0, 0.4);
}

#header{
 color: #fff;
 background-color: #2c5b9c;
 height: 3.5em;
 padding: 1em 0em 1em 1em;
 
}

#site{
    background-color: #fff;
    padding: 20px 0px 0px 0px;
}
.centerBlock{
	margin:0 auto;
}
</style>

<body>
	<div id="wrapper">
		<div id="header">
		<h1> Babble Website </h1>
		</div>
	   
		<div id="site">
		<p>
			Die Datenbank "${db2name}" ist ${db2exists}
		</p>
		</div>
	</div>

    <form name="login" action="babble" method="post">
        Enter Username: <input type="text" name="username"  /> <br/>
        <input type="submit" value="Submit" />
    </form>
    <#--<form action="profile">-->

        <#--<input type="hidden" name="varname" value="username" />-->
        <#--<input type="submit" value="Send data">-->
    <#--</form>-->

    <form name="gotoprofile" action="profile" method="post">

        <input type="submit" value="Go to your profile page" />
    </form>
</body>
</html>
