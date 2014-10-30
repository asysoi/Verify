--------------------------------------------------------
--  Ref Constraints for Table PCH_PURCHASE
--------------------------------------------------------

  ALTER TABLE "PCH_PURCHASE" ADD CONSTRAINT "FK_ID_COMPANY" FOREIGN KEY ("ID_COMPANY")
	  REFERENCES "PCH_COMPANY" ("ID") ENABLE;
  ALTER TABLE "PCH_PURCHASE" ADD CONSTRAINT "FK_ID_PRODUCT" FOREIGN KEY ("ID_PRODUCT")
	  REFERENCES "PCH_PRODUCT" ("ID") ENABLE;
