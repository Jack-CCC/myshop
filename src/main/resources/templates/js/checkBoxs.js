function checkAll() {
    var all = document.getElementById('allbox');
    var boxs = document.getElementsByClassName('single');
    if(all.checked==true){
        for(var i = 0;i<boxs.length;i++){
            boxs[i].checked=true;
        }
    }else{
        for(var j = 0;j<boxs.length;j++){
            boxs[j].checked=false;
        }
    }
}
function checkOne() {
    var all = document.getElementById('allbox');
    var boxs = document.getElementsByClassName('single');
    if(all.checked==true){
        all.checked=false;
    }
}
