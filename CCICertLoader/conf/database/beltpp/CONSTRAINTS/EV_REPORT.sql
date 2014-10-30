--------------------------------------------------------
--  Constraints for Table EV_REPORT
--------------------------------------------------------

  ALTER TABLE "EV_REPORT" ADD CONSTRAINT "REPORT_PK" PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "EV_REPORT" MODIFY ("ID_OFFICE" NOT NULL ENABLE);
  ALTER TABLE "EV_REPORT" MODIFY ("QUARTER" NOT NULL ENABLE);
  ALTER TABLE "EV_REPORT" MODIFY ("YEAR" NOT NULL ENABLE);
  ALTER TABLE "EV_REPORT" MODIFY ("ID" NOT NULL ENABLE);
