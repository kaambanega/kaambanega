
function getAJAXResponse(dataSource, param,divID,flag)
{
    var XMLHttpRequestObject =false;
    if(window.XMLHttpRequest)
    {
        XMLHttpRequestObject = new XMLHttpRequest();
    }else if (window.ActiveXObject)
    {
        XMLHttpRequestObject = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(XMLHttpRequestObject)
    {
        obj = document.getElementById(divID);
        if(obj != null)
        {
        	//var url='<c:url value="/resources/images/loading.gif" />';
        	obj.innerHTML='<center><div><img src="/resources/images/loading.gif" width="50" height="50" ><br><font color=darkblue><b>Loading Page... </b></font></div></center>';
        }
        XMLHttpRequestObject.open("POST",dataSource,flag);
        XMLHttpRequestObject.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
        XMLHttpRequestObject.send(param);

        if(XMLHttpRequestObject.readyState == 4 )
        {
            var t=XMLHttpRequestObject.responseText;
            if(obj != null)
            {
                obj.innerHTML="";
            }
            return t;
        }
    }
}
function getData(dataSource, divID){   
    var XMLHttpRequestObject =false;
    if(window.XMLHttpRequest){
        XMLHttpRequestObject = new XMLHttpRequest();
    }else if (window.ActiveXObject){
        XMLHttpRequestObject = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(XMLHttpRequestObject){
        var obj = document.getElementById(divID);
        XMLHttpRequestObject.open("POST",dataSource);
        XMLHttpRequestObject.onreadystatechange = function()
        {
            if(XMLHttpRequestObject.readyState == 4  && XMLHttpRequestObject.status == 200)
            {
                obj.innerHTML = XMLHttpRequestObject.responseText;

            }
        };
        XMLHttpRequestObject.send(null);
    }
}
function getData(dataSource, divID, param){
    var XMLHttpRequestObject =false;
    if(window.XMLHttpRequest){
        XMLHttpRequestObject = new XMLHttpRequest();
    }else if (window.ActiveXObject){
        XMLHttpRequestObject = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(XMLHttpRequestObject){
        var obj = document.getElementById(divID);
        //var url='<c:url value="/resources/images/loading.gif" />';
        obj.innerHTML='<center><div><img src="/resources/images/loading.gif" width="50" height="50" ><br><font color=darkblue><b>Loading Page... </b></font></div></center>';
        XMLHttpRequestObject.open("POST",dataSource);
        XMLHttpRequestObject.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
        XMLHttpRequestObject.onreadystatechange = function(){
            if(XMLHttpRequestObject.readyState == 4  && XMLHttpRequestObject.status == 200){
                obj.innerHTML = XMLHttpRequestObject.responseText;

            }
        };
        XMLHttpRequestObject.send(param);
    }
}
function getData_sync(dataSource, divID, param,flag){
    var XMLHttpRequestObject =false;
    if(window.XMLHttpRequest){
        XMLHttpRequestObject = new XMLHttpRequest();
    }else if (window.ActiveXObject){
        XMLHttpRequestObject = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(XMLHttpRequestObject){
        var obj = document.getElementById(divID);
        //var url='<c:url value="/resources/images/loading.gif" />';
        //obj.innerHTML='<center><div><img src="<c:url value="'+url+'" width="50" height="50" ><br><font color=darkblue><b>Loading Page... </b></font></div></center>';
        XMLHttpRequestObject.open("POST",dataSource,flag);
        XMLHttpRequestObject.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
        XMLHttpRequestObject.send(param);

        if(XMLHttpRequestObject.readyState == 4){
            obj.innerHTML = XMLHttpRequestObject.responseText;
            
        }
    }
}

function getDataValid(dataSource, param)
{   
    var XMLHttpRequestObject =false;
    if(window.XMLHttpRequest)
    {
        XMLHttpRequestObject = new XMLHttpRequest();
    }else if (window.ActiveXObject)
    {
        XMLHttpRequestObject = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(XMLHttpRequestObject)
    {
        XMLHttpRequestObject.open("POST",dataSource,false);
        XMLHttpRequestObject.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
        XMLHttpRequestObject.send(param);

        if(XMLHttpRequestObject.readyState == 4 )
        {
            var t=XMLHttpRequestObject.responseText;
            return Trim(t);
        }
    }
}
function getData_sync_without_img(dataSource, divID, param,flag){
    //alert(dataSource+"   "+divID+"   "+param);
    var XMLHttpRequestObject =false;
    if(window.XMLHttpRequest){
        XMLHttpRequestObject = new XMLHttpRequest();
    }else if (window.ActiveXObject){
        XMLHttpRequestObject = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(XMLHttpRequestObject){
        var obj = document.getElementById(divID);

        obj.innerHTML='<center><div><img src="/resources/images/loading.gif" width="50" height="50" ><br><font color=darkblue><b>Loading Page... </b></font></div></center>';
        XMLHttpRequestObject.open("POST",dataSource,flag);
        XMLHttpRequestObject.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
        XMLHttpRequestObject.send(param);

        if(XMLHttpRequestObject.readyState == 4){
            obj.innerHTML = XMLHttpRequestObject.responseText;


        }

    }
}



function getalldata(oForm) {
    var aParams = new Array();
    var sParam = '';
    for (var i=0 ; i < oForm.elements.length; i++) {
        if (oForm.elements[i].tagName == "SELECT")
        {
            for(var j=0; j < oForm.elements[i].options.length; j++)
            {
                if(oForm.elements[i].options[j].selected){
                    sParam = encodeURIComponent(oForm.elements[i].name);
                    sParam += "=";
                    sParam += encodeURIComponent(oForm.elements[i][j].value);
                    aParams.push(sParam);
                }
            }
        }

        if(oForm.elements[i].type == "checkbox" && oForm.elements[i].checked == true)
        {
            sParam = encodeURIComponent(oForm.elements[i].name);
            sParam += "=";
            sParam += encodeURIComponent(oForm.elements[i].value);
            aParams.push(sParam);
        }

        if(oForm.elements[i].type == "radio" && oForm.elements[i].checked==true )
        {
            sParam = encodeURIComponent(oForm.elements[i].name);
            sParam += "=";
            sParam += encodeURIComponent(oForm.elements[i].value);
            aParams.push(sParam);
        }

        if(oForm.elements[i].tagName == "INPUT" && oForm.elements[i].type=="text" )
        {
            sParam = encodeURIComponent(oForm.elements[i].name);
            sParam += "=";
            sParam += encodeURIComponent(oForm.elements[i].value);
            aParams.push(sParam);
        }
        if(oForm.elements[i].tagName == "INPUT" && oForm.elements[i].type=="hidden" )
        {
            sParam = encodeURIComponent(oForm.elements[i].name);
            sParam += "=";
            sParam += encodeURIComponent(oForm.elements[i].value);
            aParams.push(sParam);
        }
        if(oForm.elements[i].tagName == "INPUT" && oForm.elements[i].type=="file")
        {
            sParam = encodeURIComponent(oForm.elements[i].name);
            sParam += "=";
            sParam += encodeURIComponent(oForm.elements[i].value);
            aParams.push(sParam);
        }
        if(oForm.elements[i].type == "textarea")
        {

            sParam = encodeURIComponent(oForm.elements[i].name);
            sParam += "=";
            sParam += encodeURIComponent(oForm.elements[i].value);
            aParams.push(sParam);
        }
    //alert("hello"+sParam);
    }

    return aParams.join("&");
}

//This function will be used when you want only the response text of some other page
//and you dont want it to be displayed in any div tag.
//This function will simply return whatever is printed (response text) on the page whose name is passed.
function getResponseText_sync(dataSource, param, flag)
{
    var XMLHttpRequestObject =false;
    if(window.XMLHttpRequest)
    {
        XMLHttpRequestObject = new XMLHttpRequest();
    }
    else if (window.ActiveXObject)
    {
        XMLHttpRequestObject = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(XMLHttpRequestObject)
    {
        XMLHttpRequestObject.open("POST",dataSource,flag);
        XMLHttpRequestObject.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
        XMLHttpRequestObject.send(param);

        if(XMLHttpRequestObject.readyState == 4)
        {
            return XMLHttpRequestObject.responseText;
        }
    }
}

function getDataResponse(dataSource,divID,param,function_name)
{

    var XMLHttpRequestObject =false;
    if(window.XMLHttpRequest)
    {
        XMLHttpRequestObject = new XMLHttpRequest();
    }
    else if (window.ActiveXObject)
    {
        XMLHttpRequestObject = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(XMLHttpRequestObject)
    {
        var obj = document.getElementById(divID);
        //var url='<c:url value="/resources/images/loading.gif" />';
        obj.innerHTML='<center><div><img src="/resources/images/loading.gif" width="50" height="50" ><br><font color=darkblue><b>Loading Page... </b></font></div></center>';
        XMLHttpRequestObject.open("POST",dataSource);
        XMLHttpRequestObject.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
        XMLHttpRequestObject.onreadystatechange = function()
        {
            if(XMLHttpRequestObject.readyState == 4  && XMLHttpRequestObject.status == 200){
                obj.innerHTML = XMLHttpRequestObject.responseText;
                if(function_name!='' && function_name!='getDataResponse')
                {
                    eval(function_name+'();');
                }
            }
        };
        //alert(param);
        XMLHttpRequestObject.send(param);
    }
}