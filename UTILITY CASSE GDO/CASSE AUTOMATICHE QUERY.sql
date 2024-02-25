
SELECT sum(s.sell_price) as total FROM sells s WHERE s.sell_date = CURRENT_DATE GROUP BY s.sell_date;

SELECT s.sell_pro_id AS pro_id, (SELECT p.pro_description  FROM products p WHERE p.pro_id = s.sell_pro_id) AS product,sum(s.sell_quantity) as quantity, sum(s.sell_price) as total FROM sells s WHERE s.sell_date = '2024-02-23' GROUP BY s.sell_pro_id;

SELECT p.pro_department_id  AS department_id, (SELECT d.department_description AS department FROM departments d WHERE d.department_id = p.pro_department_id),  sum(s.sell_price) as total FROM sells s JOIN products p ON p.pro_id = s.sell_pro_id WHERE s.sell_date = '2024-02-23' GROUP BY p.pro_department_id;

SELECT p.pro_department_id  AS department_id, (SELECT d.department_description AS department FROM departments d WHERE d.department_id = p.pro_department_id),  sum(s.sell_price) as total, min(extract('year' from s.sell_date)) AS year FROM sells s JOIN products p ON p.pro_id = s.sell_pro_id WHERE extract('year' FROM s.sell_date) = '2024' GROUP BY p.pro_department_id;
