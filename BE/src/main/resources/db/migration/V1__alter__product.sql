ALTER TABLE product DROP COLUMN quantity,DROP COLUMN price;
ALTER TABLE product ADD price_default DECIMAL(15,2);


