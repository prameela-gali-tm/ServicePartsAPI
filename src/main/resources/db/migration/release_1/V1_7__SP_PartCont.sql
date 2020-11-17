ALTER TABLE spadm.sp_part DROP CONSTRAINT IF EXISTS sp_part_unique;

ALTER TABLE spadm.sp_part
    ADD CONSTRAINT sp_part_unique1 UNIQUE (order_id, part_number, ddd, line_item_number);