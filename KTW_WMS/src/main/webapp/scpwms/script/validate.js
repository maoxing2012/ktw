function trim(text){
	text = text.replace(/^[ |\n|\r|\t|\x0B|\0|?]+/,""); //??????
	text = text.replace(/[ |\n|\r|\t|\x0B|\0|?]+$/,""); //??????
	return text;
}
function isEmpty(str){
	return ((trim(str) == null)||(trim(str).length == 0));
}
function isDate(str){
	if(isEmpty(str)){
		return false;
	}
	var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
	if(r==null){
		return false;
	}
    var d = new Date(r[1], r[3]-1, r[4]);
	return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]);
}
//2002-1-31 12:34  ...
function isDateTime(str){
	if(isEmpty(str)){
		return false;
	}
	var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2})$/);
	if(r==null){
		return false;
	}
    var d = new Date(r[1], r[3]-1,r[4],r[5],r[6],0);
	return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]&&d.getHours()==r[5]&&d.getMinutes()==r[6]);
}

function isInteger(str){
    if(isEmpty(str))
        return false;
    if(/^(\-?)(\d+)$/.test(str))
        return true;
    else
        return false;
}

function isPositiveInteger(str){
	if(!isInteger(str)){
		return false;
	}
	if(parseInt(str) <= 0){
		return false;
	}
	return true;
}

function isNonNegative(str){
	if(!isInteger(str)){
		return false;
	}
	if(parseInt(str) < 0){
		return false;
	}
	return true;
}

function isNumber(str){
    if(isEmpty(str))
        return false;
 	return !isNaN(str) ;
}

function isPositiveNumber(str){
	if(!isNumber(str)){
		return false;
	}
	
	if(parseFloat(str) <= 0){
		return false;
	}
	return true;
}

function isNonNegativeNumber(str){
	if(!isNumber(str)){
		return false;
	}
	if(parseFloat(str) < 0){
		return false;
	}
	return true;
}

function isEmail(str)    
{    
	if(isEmpty(str)){
		return false;
	}
	else
	{
		if(/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(str))   
		    return true;
		else
		    return false;
    }
}

function isTime(str)    
{    
	var a = str.match(/^(\d{1,2}):(\d{2})$/);
    if (a == null) { return false;}
    if (a[1]>=24 || a[2]>=60){
    	return false;
    }
    return true;

}

function newRound(a_Num , a_Bit)
{
	return (( Math.round((a_Num + Math.pow(10, -a_Bit - 6)) * Math.pow (10 , a_Bit)) / Math.pow(10 , a_Bit)).toFixed(a_Bit));
}