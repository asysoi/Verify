--------------------------------------------------------
--  Constraints for Table C_COUNTRY
--------------------------------------------------------

  ALTER TABLE "C_COUNTRY" MODIFY ("CODE" NOT NULL ENABLE);
  ALTER TABLE "C_COUNTRY" MODIFY ("NAME" NOT NULL ENABLE);
  ALTER TABLE "C_COUNTRY" ADD CONSTRAINT "C_COUNTRY_PK" PRIMARY KEY ("CODE") ENABLE;
