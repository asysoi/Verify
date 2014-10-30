--------------------------------------------------------
--  Ref Constraints for Table EV_GROUP_ACTION
--------------------------------------------------------

  ALTER TABLE "EV_GROUP_ACTION" ADD CONSTRAINT "ACTION_FK" FOREIGN KEY ("ID_ACTION")
	  REFERENCES "EV_ACTION" ("ID") ENABLE;
  ALTER TABLE "EV_GROUP_ACTION" ADD CONSTRAINT "GROUP_FK" FOREIGN KEY ("ID_GROUP")
	  REFERENCES "EV_GROUP" ("ID") ENABLE;
