--------------------------------------------------------
--  Ref Constraints for Table C_CERT
--------------------------------------------------------

  ALTER TABLE "C_CERT" ADD FOREIGN KEY ("OTD_ID")
	  REFERENCES "C_OTD" ("ID") ENABLE;
