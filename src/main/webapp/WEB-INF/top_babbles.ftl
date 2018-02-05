<html>
<head><title>Create Babble</title>
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
<form name="babble" action="topbabbles" method="post">
    The top 5 most liked babbles: <br/>
</form>

<table style="margin-left:30%; color: cornflowerblue">
    <#list babblelist as babble>
        <a href="profile?clickedUser=${babble.creator}"><h3>Student@${babble.creator}</h3> </a> <br/>
        <div style=" color: cornflowerblue" > <br/>

            <p> <a href="detail?clickedBabble=${babble.id}"> ${babble.text}</a> <br/> ${babble.created}</p>



        </div>

    </#list>

</table>

</body>
</html>
