<html xmlns="http://www.w3.org/1999/html">
<head><title>Detail Page</title>
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

        /*div{*/
        /*text-align: left;*/
        /**/
        /*}*/



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

        .button1{
            color: red;
        }

        .button2{
            color: green;
        }
        .button3{
            color: green;
        }



    </style>




<body>
<#--&lt;#&ndash;The Search  button is a link to the searchbabble page&ndash;&gt;-->
<#--<form  action="searchbabbles" >-->
    <#--<input type="submit"  value = "Search" />-->

<#--</form>-->
    <table style="margin-left:30%; color: cornflowerblue">
        <div style=" color: cornflowerblue" > <br/>

            <a href="profile?clickedUser=${babble.creator}"><h3>Student@${babble.creator}</h3> </a> <br/>
            ${babble.text} <br/>
            ${babble.created}

        </div>
    </table>



<#--&lt;#&ndash;Clicks on the other buttons in Header are handled by the post method&ndash;&gt;-->
    <form name = "clicks"   action = "detail" method="post"  >

        <#--if like is in dislikeslist then possible to remove entry from table-->
        <#list dislikedlikedlist as l >
                <#--if entry is a dislike, then make it red-->
                <#list dislikedlist as l1>
                    <input type="submit" name="like" value = "Like" class = "button2" />
                    <input type="submit" name="dislike" value = "Dislike" class = "button1"/>
                </#list>
                <#--if entry is a like, then make it red-->
                <#list likedlist as l2>
                    <input type="submit" name="like" value = "Like" class = "button1" />
                    <input type="submit" name="dislike" value = "Dislike" class = "button2"/>
                </#list>
        <#--case when list is empty-->
        <#else>
            <input type="submit" name="like" value = "Like" class = "button2" />
            <input type="submit" name="dislike" value = "Dislike" class = "button2"/>
        </#list>



            <#--if rebabble is in list then possible to un-rebabble-->
        <#list rebabblelist as reb >
             <input type="submit" name="norebabble" value = "Un-Rebabble" class = "button1" />
        <#--case when list is empty-->
        <#else>
            <input type ="submit" name ="rebabble" value = "Rebabble" class = "button2" />
        </#list>


        <#--<#if primaryuser.username == babble.creator >-->
               <#--<input type ="submit" name ="delete" value="Delete Babble"/>-->
        <#--</#if>-->


    </form>

    <form action = "detail" method="post" >
         <#if primaryuser.username == babble.creator >
             <input type ="submit" name = "delete"  value="Delete Babble"/>
         </#if>
    </form>

    <form action = "profile" method="post" >

        <input type ="submit" name = "gohome"  value="Home"/>

    </form>




</body>
</html>