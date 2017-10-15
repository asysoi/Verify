select * from cert_view where cert_id in 
(select  a.cert_id
from (SELECT cert_id FROM (select cert_id from cert_view ORDER by issuedate ASC, cert_id ASC) where rownum <= 30) a
left join (SELECT cert_id FROM (select cert_id from cert_view ORDER by issuedate ASC, cert_id ASC) where rownum <= 0) b 
on a.cert_id = b.cert_id  where b.cert_id is null) 
ORDER BY issuedate ASC, cert_id ASC;


SELECT cert.*  
FROM (SELECT t.*, ROW_NUMBER() OVER  (ORDER BY t.issuedate asc, t.CERT_ID asc) rw  FROM CERT_VIEW t  ) cert  
WHERE cert.rw > 0 AND cert.rw <= 30;
