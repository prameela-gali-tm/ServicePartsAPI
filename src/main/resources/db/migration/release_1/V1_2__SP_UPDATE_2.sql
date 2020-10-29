ALTER TABLE spadm.sp_order
    ADD COLUMN final_destination VARCHAR(20);

ALTER TABLE spadm.sp_order
    ADD COLUMN dealer_code VARCHAR(5);

ALTER TABLE spadm.sp_order
    ADD COLUMN direct_ship_flag boolean;

ALTER TABLE spadm.sp_order
    ADD COLUMN trans_code VARCHAR(5);

 