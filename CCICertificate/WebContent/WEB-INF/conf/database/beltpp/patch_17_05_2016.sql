grant ctxapp to beltpp;

grant execute on ctxsys.ctx_cls to beltpp;
grant execute on ctxsys.ctx_ddl to beltpp;
grant execute on ctxsys.ctx_doc to beltpp;
grant execute on ctxsys.ctx_output to beltpp;
grant execute on ctxsys.ctx_query to beltpp;
grant execute on ctxsys.ctx_report to beltpp;
grant execute on ctxsys.ctx_thes to beltpp;
grant execute on ctxsys.ctx_ulexer to beltpp;

create index indx_cproduct_context on C_PRODUCT(tovar) 
  indextype is ctxsys.context 
  parameters ('DATASTORE CTXSYS.DEFAULT_DATASTORE');

//begin
//ctx_ddl.create_index_set('cproduct_set');
//ctx_ddl.add_index('cproduct_set', 'cert_id');
//end;

create index indx_cproduct_ctxcat on C_PRODUCT(tovar) indextype is CTXSYS.CTXCAT parameters ('index set cproduct_set');

EXEC CTX_DDL.SYNC_INDEX('indx_cproduct_context');

// add indexes to product_denorm table
create index indx_cproduct_denorm_ctx on C_PRODUCT_DENORM(tovar) indextype is ctxsys.context;
  
CREATE UNIQUE INDEX INDX_PRODUCT_DENORM_CERTID ON C_PRODUCT_DENORM ("CERT_ID");


 
  
