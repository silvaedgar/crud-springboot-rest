package com.tcontrol.restapi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcontrol.DTO.CoinDTO;
import com.tcontrol.models.Coin;
import com.tcontrol.repository.CoinRepository;
import com.tcontrol.response.ResponseJson;
import com.tcontrol.services.CoinService;


@RestController
@RequestMapping("api/t-control/coins/")
public class CoinRest {

	@Autowired
	public CoinService coinService;

	@Autowired
	CoinRepository coinRepository;
	
	@Autowired
	ResponseJson returnJsonOK;
	
	@GetMapping
	public ResponseEntity<Map<String, Object>> getAllCoins() {
		return returnJsonOK.messageOK("Moneda Obtenida", coinService.getAllCoins());		
	}
		
	@GetMapping("/base_calc/{option}")
	public ResponseEntity<Map<String, Object>> getBaseCalcValueCoin(@PathVariable String option) {
		List<Coin> coinBase  =  coinService.getCoinBase(); // obtiene la moneda base
		List<Coin> coinCalc  = coinService.getCoinCalc(option); // obtiene la moneda de calculo 
		List<Map<String, Object>> currencyValues  = coinService.getCoinBaseRate(coinBase.get(0).getId());  // obtiene la relacion compra venta de la moneda de calculo y base		
		Map<String,Object> dataList = new HashMap<String,Object>();
		dataList.put("coinBase", coinBase.get(0));
		dataList.put("coinCalc", coinCalc.get(0));
		dataList.put("valueCoinData", currencyValues.get(0));
		dataList.put("status", "Ok");		
		return new ResponseEntity<Map<String,Object>>(dataList,HttpStatus.OK);		
	}

	@GetMapping("{id}")
	public ResponseEntity<Map<String, Object>> getCoin(@PathVariable Long id) {
		return returnJsonOK.messageOK("Moneda Obtenida", coinService.getCoinById(id));
	}

	@PostMapping()
	public ResponseEntity<Map<String, Object>> createCoin(@Valid @RequestBody CoinDTO coinDTO) {
		return coinService.saveCoin(coinDTO, "Create");
	}

	@PutMapping
	public ResponseEntity<Map<String, Object>> updateCoin(@Valid @RequestBody CoinDTO coinDTO) {
		return coinService.saveCoin(coinDTO, "Upate");
	}

}
