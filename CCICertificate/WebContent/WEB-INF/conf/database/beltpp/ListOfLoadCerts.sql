select nomercert CertNumber, nblanka Blank, TO_DATE(issuedate, 'DD-MM-YYYY') CERT_DATE, cc.name_syn Branch, TO_DATE(bb.DATE_LOAD, 'DD-MM-YYYY') datein, bb.file_in_name as FILE_NAME from C_CERT aa 
left join C_FILES_IN bb on aa.cert_id = bb.cert_id 
left join C_OTD cc on aa.otd_id = cc.id 
where bb.date_load >= '25-01-2016' AND bb.date_load < '01-02-2016'
order by datein;