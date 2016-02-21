

SELECT cert."CERT_ID",cert."FORMS",cert."UNN",cert."KONTRP",cert."KONTRS",
cert."ADRESS",cert. "POLUCHAT",cert."ADRESSPOL",cert."DATACERT",cert."ISSUEDATE", 
cert."NOMERCERT",cert."EXPERT",cert."NBLANKA",cert."RUKOVOD",cert."TRANSPORT", 
cert."MARSHRUT",cert."OTMETKA",cert."STRANAV",cert."STRANAPR",cert."STATUS",
cert."KOLDOPLIST",cert."FLEXP",cert."UNNEXP",cert."EXPP",cert."EXPS",
cert."EXPADRESS",cert."FLIMP",cert."IMPORTER",cert."ADRESSIMP",cert."FLSEZ",
cert."SEZ",cert."FLSEZREZ",cert."STRANAP",cert."OTD_ID",cert."PARENTNUMBER",
cert."PARENTSTATUS", cert."CODESTRANAV", cert."CODESTRANAPR", cert."CODESTRANAP",
cert."CATEGORY", cert."PARENT_ID",cert."OTD_NAME",cert."OTD_ADDR_INDEX",
cert."OTD_ADDR_CITY",cert."OTD_ADDR_LINE",cert."OTD_ADDR_BUILDING",
a.tovar, bb.DATE_LOAD 
FROM C_FILES_IN bb
LEFT JOIN CERT_VIEW cert on bb.cert_id = cert.cert_id
LEFT JOIN C_PRODUCT_DENORM a on bb.CERT_ID=a.CERT_ID