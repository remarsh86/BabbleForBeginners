<html>
<head><title>Profile</title>
<style type="text/css">
* {
   margin:0;
   padding:0;
}

body{
   text-align:center;
   background: #efe4bf none repeat scroll 0 0;
}
form {
    display: inline;
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
	<form  action="searchbabbles" >
       <input type="submit"  value = "Search" />

      </form>
    <form name = "clicks" method="post"  >
          <input type="submit" name="block" value = "Block/Unblock" />
            <input type="submit" name="follow" value = "Follow/Unfollow" /><br/>
            A whole bunch of info about the BabbleUser <br/>
    </form>
    <form  action="createbabble" >
        <input type="submit"  value = "New Babble" /><br/>
    </form>
    <form name = "timeline" method="post"  >
        <#--<input type="submit" name="test" value = "Test" />-->

        The timeline follow here.... <br/>
    </form>


</body>
</html>