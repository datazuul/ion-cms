/* 
Calendrier en popup

Site	: www.phassler.com
Date	: 15/03/2002

*/

var DaysList;
var MonthsList;
var MonthLength = new Array("Mois_longueur_vide",31,29,31,30,31,30,31,31,30,31,30,31);

/* Definition des style du calendrier */
/* Couleur */
var contour_tableau='black';
var fond_tableau='#FFFFFF';
var fond_semaine='white';
var lettre_semaine='black';
var lettre_jours='black';
var lettre_moisan='black';
var jours_selectionne='#bbbbbb';
var jours_actif='RED';
var jours_contours='#bbbbbb';
var lettre_button='black';

calStyle = "<STYLE TYPE=\"text/css\">";
calStyle += ".calTable1 {";
calStyle += "	background-color: "+contour_tableau;
calStyle += "}";
calStyle += ".calTable2 {";
calStyle += "	background-color: "+fond_tableau;
calStyle += "}";
calStyle += ".calcolHeader {";
calStyle += "	background: "+fond_semaine+";";
calStyle += "	font-family: Verdana, Arial, Helvetica, sans-serif;";
calStyle += "	font-size: 9px;";
calStyle += "	font-weight: bold;";
calStyle += "	border:0.5pt solid "+ contour_tableau+";";
calStyle += "	color: "+lettre_semaine;
calStyle += "}";
calStyle += ".calAction {";
calStyle += "	font-family: Verdana, Arial, Helvetica, sans-serif;";
calStyle += "	font-size: 9px;";
calStyle += "	font-weight: bold;";
calStyle += "	color: "+lettre_jours;
calStyle += "}";
calStyle += ".calTdAction {";
calStyle += "   background-color: #eeeeee;";
calStyle += "	font-family: Verdana, Arial, Helvetica, sans-serif;";
calStyle += "	font-size: 9px;";
calStyle += "	font-weight: bold;";
calStyle += "	color: "+lettre_moisan+";";
calStyle += "	border:0.5pt solid "+ jours_contours +";";
calStyle += "	text-align:center";
calStyle += "}";
calStyle += ".calButton {";
calStyle += "	font-family: Verdana, Arial, Helvetica, sans-serif;";
calStyle += "	font-size: 9px;";
calStyle += "	font-weight: bold;";
calStyle += "	color: "+lettre_button;
calStyle += "}";



calStyle += "</STYLE>";
document.write(calStyle);	

// Taille de la fenetre
if (navigator.userAgent.indexOf("MSIE") >= 0){   
    	maxX = document.body.scrollLeft + document.body.clientWidth;
	maxY = document.body.scrollTop  + document.body.clientHeight ;
  }else{	
        // AVOIR pour le scroll
        maxX = window.innerWidth; 	
	maxY = window.innerHeight;	
}	

var xp=0;
var yp=0;  

/* Ecriture des DIV */
calhtml = "<DIV onmouseover=\"kill();\" id=caleck1 style=\"position:absolute;z-index:99;visibility:hidden;top:0px;left:0px;width:"+maxX+";height:"+maxY+";\")";
calhtml += "<TABLE width=100% height=100% border=0><TR><TD></TD></TR></TABLE>";
calhtml += "</DIV>";
calhtml += "<DIV id=caleck style=\"position:absolute;z-index:100;VISIBILITY:hidden\"></DIV>";
document.write(calhtml);	

var calflag=0;
var calchamp; /* nom du champ date a modifie dans la page */
var calskn = document.getElementById("caleck").style;
var calskn1 = document.getElementById("caleck1").style;
var calcontent = "";
var calHTML = "";
var FirstDay = 0;
var CalToday = new Date();
var CalDay = CalToday.getDate();
var CalYear = CalToday.getYear();
var CalMonth = CalToday.getMonth() + 1;
if (navigator.userAgent.indexOf("MSIE") >= 0){   
var calxpos = "event.x";
var calypos = "event.y";
}else{
var calxpos = "e.pageX";
var calypos = "e.pageY";
}

document.onmousemove = mouseMove;

/* Function principale appellee par la page HTML*/       
/* Parametre : calchamp = le champ date a mettre jour */
/* Parametre : callangue = la langue */
function pop(lechamp,callangue){

/* Liste des jours et mois dans la langue demandee francais par defaut */
if (callangue == 'en'){
  DaysList = new Array("Jour_Vide", "Mo", "Tu", "We", "Th", "Fr", "Sa", "Su");
  MonthsList = new Array("Mois_Vide", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");	
} else {
  DaysList = new Array("Jour_Vide", "Lun", "Mar", "Mer", "Jeu", "Ven", "Sam", "Dim");
  MonthsList = new Array("Mois_Vide", "Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre");	
}
    calchamp = lechamp;
    try{    
    	var ladate = document.getElementById(calchamp).value;
    	if (ladate.length == 10){
		if (ladate.substring(0,1) == 0)
	        	CalDay = ladate.substring(1,2);
		else
		        CalDay = ladate.substring(0,2);        	

		if (ladate.substring(3,4) == 0)
	        	CalMonth = ladate.substring(4,5);
		else        	
        		CalMonth = ladate.substring(3,5);        	        	
        	
	        CalYear = ladate.substr(6,4);
	 }else{
        	CalDay = CalDay;
        	CalMonth = CalMonth;
        	CalYear = CalYear;	 
	 }

    } catch(exception) {
        CalDay = CalDay;
        CalMonth = CalMonth;
        CalYear = CalYear;
    }

//    var x = eval(calxpos);
//    var y = eval(calypos)+document.body.scrollTop;

var x=xp;
var y=yp;

    if ((x+170) > maxX)
        calskn.left = x-150;
    else	   
        calskn.left = x-10;
	
    if ((y+140) > maxY)	    
    	calskn.top  = y-120; 
    else
    	calskn.top  = y-12;         	

    CalAffiche();
    
    calskn1.visibility = "visible";	    
    calskn.visibility = "visible";    
      
}

/* Mouvement de la souris */
function mouseMove(e) {
        // AVOIR pour le scroll	
	xp = eval(calxpos);
	if (navigator.userAgent.indexOf("MSIE") >= 0)   	
		yp = eval(calypos)+document.body.scrollTop;
	else		
		yp = eval(calypos);	
}

/* Teste une annee pour determiner si elle est bissextile ou pas*/
function CalBissextile(yy){
   	if (((yy % 4 == 0) && (yy % 100 != 0)) || (yy % 400 == 0)){ return 29; }
      	else { return 28; }	
}

/* Retour de la date selectionnee */
function CalSelecteur(obj){
calvaleur = document.getElementById("caltd"+obj).value;
if (calvaleur > 0) {
	if (calvaleur < 10 )
	 	retjrs = "0"+calvaleur;
	else
	 	retjrs = calvaleur;
	 	
	if (document.getElementById('Month').value.length < 2)
	 	retmoi = "0"+document.getElementById('Month').value;
	else
		retmoi = document.getElementById('Month').value; 	
	 
	document.getElementById(calchamp).value=retjrs + "/" + retmoi + "/" + document.getElementById('Year').value;
	kill();
}
}


/* Produit le code HTML qui formera le calendrier */
function CalAffiche(){
  /* Recherche si l'annee est bissextile */
  if (CalMonth == 2)
  	MonthLength[2] = CalBissextile(CalYear);
    
  calHTML = "<TABLE border=0 class=calTable1 cellspacing=0 cellspading=0><TR><TD >";
  calHTML += "<TABLE border=0 class=calTable2 ><TR><TD valign=CENTER>";
  calHTML += "<input type=hidden id=Month value="+CalMonth+">";
  calHTML += "<input class=calAction type=text size=11 id=MonthLib style=\"text-align:center;cursor:'default'\" value="+MonthsList[CalMonth]+" READONLY>";
  calHTML += "<input class=\"button2\" type=\"button\" value=\"<\" class=calButton onmouseover=\"style.cursor='hand'\" onClick=\"Change(0)\">";
  calHTML += "<input class=\"button2\" type=\"button\" value=\">\" class=calButton onmouseover=\"style.cursor='hand'\" onClick=\"Change(1)\">";
  calHTML += "<input class=calAction type=text id=Year size=6 style=\"text-align:center;cursor:'default'\" value="+CalYear+" READONLY >";
  calHTML += "<input class=\"button2\" type=\"button\" value=\"<\" class=calButton onmouseover=\"style.cursor='hand'\" onClick=\"ChangeY(0)\">";
  calHTML += "<input class=\"button2\" type=\"button\" value=\">\" class=calButton onmouseover=\"style.cursor='hand'\" onClick=\"ChangeY(1)\"></CENTER></TD></TR>";
  calHTML += "<TR><TD bgcolor="+fond_tableau+">";
  calHTML += "<CENTER><table cellspacing=0 cellpadding=0 border=0 style='border:0.5pt solid "+contour_tableau+"; border-collapse:collapse'>";
  calHTML += "<tr align=center>";

  for (s=1; s<8; s++)
	calHTML += "<td class=calcolHeader width=23 style=\"cursor:'default'\">" + DaysList[s] + "</td>"; 	
	


  calHTML += "</tr>";
  	
  a = 0;
  b = 1;
  // Calcul du premier jour du mois
  var caldefjour = CalDefDateDay(CalYear,CalMonth,1);
  for (i=(1-caldefjour); i<MonthLength[CalMonth]; i++){	
	calHTML += "<tr id=\"calTR"+(a+1)+"\" align=center>";
	for (j=1; j<8; j++){
		calHTML += "<td width=23 class=calAction onClick=\"CalSelecteur("+b+")\">";				
		if ((i+j) <= 0) { 
			calHTML += "<input class=calTdAction size=1 type=text id=\"caltd"+b+"\" style=\"border-color:"+fond_tableau+";background:'';cursor:'default'\" value=\"\" READONLY onmouseover=\"mousesur("+b+")\" onmouseout=\"mousepassur("+b+")\">";				
		}else if ((i+j) > MonthLength[CalMonth]) {
			calHTML += "<input class=calTdAction size=1 type=text id=\"caltd"+b+"\" style=\"border-color:"+fond_tableau+";background:'';cursor:'default'\" value=\"\" READONLY onmouseover=\"mousesur("+b+")\" onmouseout=\"mousepassur("+b+")\">";								
		} else { 
			if ((i+j) == CalDay )
			  calHTML += "<input class=calTdAction style=\"background:RED;cursor:'hand'\" size=1 type=text id=\"caltd"+b+"\" value="+(i+j)+" READONLY onmouseover=\"mousesur("+b+")\" onmouseout=\"mousepassur("+b+")\">";					    	   	    		    
			else	
			  calHTML += "<input class=calTdAction size=1 type=text id=\"caltd"+b+"\" value="+(i+j)+" style=\"background: '';cursor:'hand'\" READONLY onmouseover=\"mousesur("+b+")\" onmouseout=\"mousepassur("+b+")\">";					    	   	    		    
		}
	  calHTML += "</TD>";		
	  b++;
        }
	calHTML += "</tr>";
	a++;
	i = i + 6;
  }

  for (aa=a+1;aa<=6;aa++){
	calHTML += "<tr id=\"calTR"+aa+"\" align=center style=\"display:none\">";
	for (j=1; j<8; j++){
		calHTML += "<td class=calAction onClick=\"CalSelecteur("+b+")\">";				
		calHTML += "<input class=calTdAction size=1 type=text id=\"caltd"+b+"\" value=\"\" READONLY onmouseover=\"mousesur("+b+")\" onmouseout=\"mousepassur("+b+")\"></td>";					    	   	    		    
	  b++;		
        }
	calHTML += "</tr>";
   }
   
  calHTML += "</table>";
  calHTML += "</TD></TR></TABLE>";  
  calHTML += "</TD></TR></TABLE>";    
    document.getElementById("caleck").innerHTML = calHTML;
}

function kill() { 
	calskn.visibility = "hidden"; 
	calskn1.visibility = "hidden"; 	
}

function Change(typ){
	var moi=document.getElementById('Month').value;
	var an=document.getElementById('Year').value;
	if (typ == 1){
		if ( moi == 12){
			an++;
			CalMonth =1;
			CalYear=an;
		}else{	
			moi++;
			CalMonth = moi;
		}			
	}else{
		if ( moi == 1){
			an--;
			CalMonth =12;
			CalYear=an;
		}else{	
			moi--;
			CalMonth = moi;
		}			
	}

CalAffiche2();
}

function ChangeY(typ){
	var an=document.getElementById('Year').value;

	if (typ == 1){
		an++;
		CalYear=an;
	}else{
		an--;
		CalYear=an;
	}			
	

CalAffiche2();
}

/* Renvoie le numero du jour de la semaine correspondant a la date requise */
function CalDefDateDay(yy,mm,dd){
	return Math.floor((CalDate2Days(yy,mm,dd)-2) % 7) + 1;
}

/* Transforme la date en nb de jours theoriques */
function CalDate2Days(yy,mm,dd){
	if (mm > 2){
		var bis = Math.floor(yy/4) - Math.floor(yy/100) + Math.floor(yy/400);
		var zy = Math.floor(yy * 365 + bis);
		var zm = (mm-1) * 31 - Math.floor(mm * 0.4 + 2.3);
		return (zy + zm + dd);
	}else{
    		var bis = Math.floor((yy-1)/4) - Math.floor((yy-1)/100) + Math.floor((yy-1)/400);
		var zy = Math.floor(yy * 365 + bis);
		return (zy + (mm-1) * 31 + dd);
	}
}


function mousesur(lid){
	if (document.getElementById("caltd"+lid).value != "" ){
	  document.getElementById("caltd"+lid).style.background = jours_selectionne;	
 	  document.getElementById("caltd"+lid).style.borderColor= jours_contours;		
	}else{
	  document.getElementById("caltd"+lid).style.background='';		
 	  document.getElementById("caltd"+lid).style.borderColor=fond_tableau;	    	
	}
}
function mousepassur(lid){
	if (document.getElementById("caltd"+lid).value == CalDay ){
		document.getElementById("caltd"+lid).style.background=jours_actif;
	}else if (document.getElementById("caltd"+lid).value != "" ){
		document.getElementById("caltd"+lid).style.background=fond_tableau;	
		document.getElementById("caltd"+lid).style.borderColor=jours_contours;
	}else{
		document.getElementById("caltd"+lid).style.background=fond_tableau;		
		document.getElementById("caltd"+lid).style.borderColor=fond_tableau;	    			
	 }
}

/* Produit le code HTML qui formera le calendrier */
function CalAffiche2(){
  if (CalMonth == 2)
  	MonthLength[2] = CalBissextile(CalYear);
    
  document.getElementById("Month").value=CalMonth;
  document.getElementById("MonthLib").value=MonthsList[CalMonth];
  document.getElementById("Year").value=CalYear;

  if (CalDay > MonthLength[CalMonth])
  	CalDay = MonthLength[CalMonth];
  	  
  a = 0;
  b = 1;
  // Calcul du premier jour du mois
  caldefjour = CalDefDateDay(CalYear,CalMonth,1);
  for (i=(1-caldefjour); i<MonthLength[CalMonth]; i++){	
	for (j=1; j<8; j++){
		if ((i+j) <= 0){
		   document.getElementById("caltd"+b).style.borderColor=fond_tableau;			   
		   document.getElementById("caltd"+b).value="";			
		   document.getElementById("caltd"+b).style.background=fond_tableau;
		   document.getElementById("caltd"+b).style.cursor='default';		   		   
		} else if ((i+j) > MonthLength[CalMonth]){
		   document.getElementById("caltd"+b).style.borderColor=fond_tableau;			   
		   document.getElementById("caltd"+b).value="";			
		   document.getElementById("caltd"+b).style.background=fond_tableau;		   
		   document.getElementById("caltd"+b).style.cursor='default';		   		   		   
		}else{ 
 	  	   document.getElementById("caltd"+b).style.borderColor= jours_contours;				
		   document.getElementById("caltd"+b).value=(i+j);			
		   document.getElementById("caltd"+b).style.cursor='hand';		   
		   document.getElementById("caltd"+b).style.background=fond_tableau;		   		   
		}
		
		if ((i+j) == CalDay )
		   document.getElementById("caltd"+b).style.background=jours_actif;		

	b++;		
	}
	a++;
	i = i + 6;
  }


  if ( b > 29)
    	document.getElementById("calTR5").style.display='block';
  else
  	document.getElementById("calTR5").style.display='none';  
  	    	
  if ( b > 36)
  	document.getElementById("calTR6").style.display='block';
  else
  	document.getElementById("calTR6").style.display='none';  
  
  for (i=b;i<=42;i++){
 	document.getElementById("caltd"+i).style.background=fond_tableau;
	document.getElementById("caltd"+i).value="";			
  }
}
