<html xmlns="http://www.w3.org/1999/html">
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

.boxed {
    border: 1px cornflowerblue ;
}

input{
        height: 60px;
}



</style>




<body>
    <#--The Search  button is a link to the searchbabble page-->
	<form  action="searchbabbles" >
       <input type="submit"  value = "Search" />

    </form>
    <#--Clicks on the other buttons in Header are handled by the post method-->
    <form name = "clicks" method="post"  >
          <input type="submit" name="block" value = "Block/Unblock" />
            <input type="submit" name="follow" value = "Follow/Unfollow" /><br/>
            You are logged in as @${primaryuser} <br/>
    </form>
    <#--The New Babble button is a link to the create babble page-->
    <form  action="createbabble" >
        <input height="40px" type="submit"  value = "New Babble" /><br/>
        <table style="margin-left:30%; color: cornflowerblue; background-color: floralwhite">
            <tr>
                <th>Username: </th> <th>${user.username} </th>
            </tr>
            <tr>
                <th>Name: </th> <th>${user.name} </th>
            </tr>
            <tr>
                <th>Status: </th> <th>${user.status} </th>
            </tr>


        </table>


    </form>
    <#--Timeline is handled in the post method-->

    <#--&lt;#&ndash;<table class="datatable">&ndash;&gt;-->

    <#list users as babble>
    <#--<tr><td>${babble.creator}</td>-->
        <div class="boxed"> <br/>

            <a href="profile?clickedUser=${babble.creator}"><h3>Student@${babble.creator}</h3> </a>
            <#--<h3>Student@${babble.creator}</h3>-->
            <p>${babble.text}</p>
            <td>${babble.created}</td> <br/>
        </div>
    </tr>
    </#list>



</body>
</html>