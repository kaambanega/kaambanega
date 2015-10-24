/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
function onSubmit()
{
    var form = document.firstForm;
    getData_sync("/kaambanega/first.htm/insertData1", "messageDiv", getalldata(form), false);
}
function onDisplay()
{
    getData_sync("/kaambanega/first.htm/getList1", "messageDiv", "", false);
    
}

