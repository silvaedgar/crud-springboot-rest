package com.tcontrol.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tcontrol.DTO.CoinDTO;
import com.tcontrol.exceptions.HandlerExceptions;
import com.tcontrol.models.Coin;
import com.tcontrol.models.User;
import com.tcontrol.repository.CoinRepository;
import com.tcontrol.repository.UserRepository;
import com.tcontrol.services.interfaces.ICoinService;

@Service
public class CoinService implements ICoinService {

	@Autowired
	public CoinRepository coinRepository;

	@Autowired
	public UserRepository userRepository;

	@Autowired
	public ModelMapper modelMapper;
	
	@Override
	public ResponseEntity<Map<String, Object>> saveCoin(CoinDTO coinDTO, String option) {
	
		Map<String, Object> map = new HashMap<String, Object>();

		Coin coin = new Coin();		
		coin = modelMapper.map(coinDTO, Coin.class);			
		switch (option) {
		case "Create":
			coin.setCreatedAt(LocalDateTime.now());
			coin.setStatus("Activo");
			coin.setBaseCurrency('N');
			coin.setCalcCurrencyPurchase('N');
			coin.setCalcCurrencySale('N');
			break;
		default:
			Coin coinCurrent = getCoinById(coinDTO.getId());
			coin.setId(coinCurrent.getId());
			coin.setCreatedAt(coinCurrent.getCreatedAt());
			coin.setStatus(coinCurrent.getStatus());
			coin.setBaseCurrency(coinCurrent.getBaseCurrency());
			coin.setCalcCurrencyPurchase(coinCurrent.getCalcCurrencyPurchase());
			coin.setCalcCurrencySale(coinCurrent.getCalcCurrencyPurchase());
			break;
		}
		coin.setUpdatedAt(LocalDateTime.now());
		Optional<User> user = userRepository.findById(coinDTO.getUserId());
		coin.setUser(user.get());
		try {
			Coin coinSave = coinRepository.save(coin);
			map.put("Data", coinSave);
			map.put("message", "Moneda " + coin.getName() + ". "
					+ (option == "Create" ? "Creada con exito" : "Actualizada con exito"));
			map.put("status", "Ok");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		} catch (Exception e) {
			throw new HandlerExceptions(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public List<Coin> getAllCoins() {
		// TODO Auto-generated method stub
		return coinRepository.findAll();
	}

	@Override
	public Coin getCoinById(Long id) {		
		return coinRepository.findById(id)
				.orElseThrow(() -> new HandlerExceptions("ID de Moneda: " + id + " no existe", HttpStatus.NOT_FOUND));
	}
	
	public List<Coin> getCoinBase() {
		// TODO Auto-generated method stub
		return coinRepository.findByStatusAndBaseCurrency("Activo", 'S');
	}

	public List<Coin> getCoinCalc(String option) {
		// TODO Auto-generated method stub
		return (option == "Sale" ? coinRepository.findByStatusAndCalcCurrencySale("Activo", 'S') : 
								coinRepository.findByStatusAndCalcCurrencyPurchase("Activo", 'S'));
	}

	public List<Map<String, Object>> getCoinBaseRate(Long id) {
		// TODO Auto-generated method stub
		return coinRepository.findCurrencyValues(id);
	}

}
