ALTER TABLE spadm.sp_order
    ADD COLUMN FINAL_DESTINATION_DESC VARCHAR(20);
    
ALTER TABLE spadm.SP_PART
    DROP COLUMN final_destination;

ALTER TABLE spadm.SP_PART
    DROP COLUMN DIRECT_SHIP;
    
ALTER TABLE spadm.SP_PART
    DROP COLUMN DEALER;
    
 