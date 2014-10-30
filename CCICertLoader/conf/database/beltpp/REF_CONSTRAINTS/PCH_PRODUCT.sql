--------------------------------------------------------
--  Ref Constraints for Table PCH_PRODUCT
--------------------------------------------------------

  ALTER TABLE "PCH_PRODUCT" ADD CONSTRAINT "FK_ID_PARENT" FOREIGN KEY ("ID_PARENT")
	  REFERENCES "PCH_PRODUCT" ("ID") ENABLE;
