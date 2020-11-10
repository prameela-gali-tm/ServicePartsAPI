ALTER TABLE spadm.SP_PART
    ADD COLUMN final_destination VARCHAR(20);
ALTER TABLE spadm.SP_PART
    ADD COLUMN eda date;
ALTER TABLE spadm.SP_ORDER
    ADD COLUMN dealer_order VARCHAR(8);