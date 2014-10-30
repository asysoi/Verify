--------------------------------------------------------
--  Ref Constraints for Table C_PRODUCT
--------------------------------------------------------

  ALTER TABLE "C_PRODUCT" ADD FOREIGN KEY ("CERT_ID")
	  REFERENCES "C_CERT" ("CERT_ID") ENABLE;
