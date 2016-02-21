
select cc.*, dd.otd_name 
from (select aa.file_in_id, aa.DATE_LOAD, bb.* 
from C_FILES_IN aa left join C_CERT bb on aa.cert_id = bb.cert_id) cc 
left join C_OTD dd on cc.otd_id = dd.id;

------------------  Вариант 20.02.3016  ---------------------
select cc."FILE_IN_ID",cc."CERT_ID",cc."NOMERCERT",cc."NBLANKA",cc."DATACERT",cc."ISSUEDATE",cc."EXPERT",cc."DATE_LOAD",cc."OTD_ID", otd_name 
from (select file_in_id, aa.cert_id, nomercert, nblanka, datacert, issuedate, expert, 
DATE_LOAD, otd_id 
from C_FILES_IN aa left join C_CERT bb on aa.cert_id = bb.cert_id) cc 
left join C_OTD dd on cc.otd_id = dd.id;
-------------------------------------------------------------


select aa.file_in_id, aa.DATE_LOAD, bb.* 
from C_FILES_IN aa left join CERT_view bb on aa.cert_id = bb.cert_id;


select * from cert_report where file_in_id in 
(select  a.file_in_id
from (SELECT file_in_id FROM (select file_in_id from cert_report ORDER by CERT_ID ASC, file_in_id ASC) where rownum <= 20) a
left join (SELECT file_in_id FROM (select file_in_id from cert_report ORDER by CERT_ID ASC, file_in_id ASC) where rownum <= 10) b 
on a.file_in_id = b.file_in_id  where b.file_in_id is null) 
ORDER BY CERT_ID ASC, file_in_id ASC;


SELECT cert.*  
FROM (SELECT t.*, ROW_NUMBER() OVER  (ORDER BY t.CERT_ID asc, t.FILE_IN_ID asc) rn  FROM CERT_REPORT t  ) cert  
WHERE cert.rn > 10 AND cert.rn <= 20;