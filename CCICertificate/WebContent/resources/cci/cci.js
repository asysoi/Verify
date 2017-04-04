/**
 *   BelCCI portal javascript
 */

/**
 *  Check required field to be not empty 
 */
function checkRequired() {
	   var checkRequired = true;
	   var inputs = $(".required");
	   
	   $.each(inputs, function(i, item) {
		   if (item.value.trim() == "") {
		      item.style.borderColor = "red";
		      checkRequired = false;
		   } else {
			  item.style.borderColor = "";
		   }
	   } 
	   );
	   return checkRequired;
}

transliterate = (
		    function() {
		        var
		            rus = "щ   ш  ч  ц  ю  я  ё  ж  ъ  ы  э  а б в г д е з и й к л м н о п р с т у ф х ь".split(/ +/g),
		            eng = "shh sh ch cz yu ya yo zh `` y' e` a b v g d e z i j k l m n o p r s t u f x `".split(/ +/g)
		        ;
		        return function(text, engToRus) {
		            var x;

		            for(x = 0; x < rus.length; x++) {
		                text = text.split(engToRus ? eng[x] : rus[x]).join(engToRus ? rus[x] : eng[x]);
		                text = text.split(engToRus ? eng[x].toUpperCase() : rus[x].toUpperCase()).join(engToRus ? rus[x].toUpperCase() : eng[x].toUpperCase());
		            }
		            return text;
		        }
		    }
)();

function translit(source, target) {
	target.val(transliterate(source.val()));
}

function goBack() {
    window.history.back();
}

/**
 *  FS Certificate common function 
 */
function openFSCertificate(certid) {
	memo = "Воспроизведение бумажной версии сертификата. <p>" + 
	       "Результат воспроизведения может незначительно отличаться по форме и стилю отображения," +
	       "но полностью воспроизводит содержание документа.</p>"
	url = "fscert.do?certid=" + certid;
	var win=window.open(url,'_blank');
	win.focus();
}

function editFSCertificate(certid) {
	url = "fsedit.do?certid=" + certid;
	//window.location.href = url;
	var win=window.open(url, "_self");
	win.focus();
}

function printOriginalFSCertificate(certid) {
	url = "fsprint.do?certid=" + certid+"&type=org";
	var win=window.open(url, "_blank");
	win.focus();
}

function printCopyFSCertificate(certid) {
	url = "fsprint.do?certid=" + certid+"&type=copy";
	var win=window.open(url, "_blank");
	win.focus();
}

function printOriginalFSCertificate() {
	url = "fsprint.do";
	$.ajaxSetup({async:false});
	$.post(url, $("#fscert").serialize());
	var win=window.open(url, "_blank");
	win.focus();
}

function submitForm(formName) {
    document.getElementById(formName).submit();
}

