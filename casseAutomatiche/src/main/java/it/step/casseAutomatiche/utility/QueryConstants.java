package it.step.casseAutomatiche.utility;

public class QueryConstants {
	/*Rendere disponibile un endpoint che calcoli l'incasso della giornata*/
	public static final String INCASSO_GIORNATA = "SELECT sum(s.sell_price) as total FROM sells s WHERE s.sell_date = CURRENT_DATE GROUP BY s.sell_date;";
	
	/*Rendere disponibile un endpoint che calcoli, per ogni articolo, i pezzi venduti e l'incasso dell'articolo, data una giornata.*/
	public static final String INCASSO_DATA_UNA_GIORNATA_PER_ARTICOLO = "SELECT s.sell_pro_id AS pro_id, (SELECT p.pro_description  FROM products p WHERE p.pro_id = s.sell_pro_id) AS product, sum(s.sell_price) as total FROM sells s WHERE s.sell_date = ? GROUP BY s.sell_pro_id;";
	
	/*- Rendere disponibile un endpoint che calcoli l'incasso per reparto, data una giornata*/
	public static final String INCASSO_PER_REPARTO_DATA_UNA_GIORNATA = "SELECT p.pro_department_id  AS department_id, (SELECT d.department_description AS department FROM departments d WHERE d.department_id = p.pro_department_id),  sum(s.sell_price) as total FROM sells s JOIN products p ON p.pro_id = s.sell_pro_id WHERE s.sell_date = ? GROUP BY p.pro_department_id;";
	
	/*- Rendere disponibile un endpoint che calcoli l'incasso per reparto, dato un anno*/
	public static final String INCASSO_PER_REPARTO_DATO_UN_ANNO = "SELECT p.pro_department_id  AS department_id, (SELECT d.department_description AS department FROM departments d WHERE d.department_id = p.pro_department_id),  sum(s.sell_price) as total, min(extract('year' from s.sell_date)) AS year FROM sells s JOIN products p ON p.pro_id = s.sell_pro_id WHERE extract('year' FROM s.sell_date) = ? GROUP BY p.pro_department_id;";

}
