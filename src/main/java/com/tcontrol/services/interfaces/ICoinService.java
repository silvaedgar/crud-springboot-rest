package com.tcontrol.services.interfaces;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.tcontrol.DTO.CoinDTO;
import com.tcontrol.models.Coin;

public interface ICoinService {

	public List<Coin> getAllCoins();
	public ResponseEntity<Map<String, Object>> saveCoin(CoinDTO coinDTO, String option);
	public Coin getCoinById(Long id);
	
	public List<Coin> getCoinBase(); 
	public List<Coin> getCoinCalc(String option); 
	public List<Map<String, Object>> getCoinBaseRate(Long id);

}
