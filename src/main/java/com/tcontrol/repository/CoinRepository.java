package com.tcontrol.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tcontrol.models.Coin;

public interface CoinRepository extends JpaRepository<Coin, Long>{

	List<Coin> findByStatusAndBaseCurrency(String status, Character baseCurrency);

	List<Coin> findByStatusAndCalcCurrencySale(String status, char calcCurrencySale);	
	List<Coin> findByStatusAndCalcCurrencyPurchase(String status, Character calcCurrencyPurchase);


	@Query(value = "SELECT cv.purchase_price, cv.sale_price FROM  coins c INNER JOIN currency_values cv ON " + 
				" c.id = cv.coin_id WHERE cv.status = 'Activo' AND c.id = ?1", nativeQuery = true)
	List<Map<String, Object>> findCurrencyValues(Long id);


	//	List<Coin> findByBaseCurrency
//    return Coin::select('*')->selectRaw('1 as purchase_price')->selectRaw('1 as sale_price')
//            ->where($calc_coin,'S')->where('status','Activo');

	
}
