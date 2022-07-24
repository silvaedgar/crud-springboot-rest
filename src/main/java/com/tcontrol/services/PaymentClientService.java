package com.tcontrol.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tcontrol.DTO.PaymentClientDTO;
import com.tcontrol.exceptions.HandlerExceptions;
import com.tcontrol.models.Client;
import com.tcontrol.models.Coin;
import com.tcontrol.models.PaymentClient;
import com.tcontrol.models.PaymentForm;
import com.tcontrol.models.Sale;
import com.tcontrol.models.User;
import com.tcontrol.repository.PaymentClientRepository;
import com.tcontrol.repository.SaleRepository;
import com.tcontrol.response.ResponseJson;
import com.tcontrol.services.interfaces.IPaymentClient;

@Service
public class PaymentClientService implements IPaymentClient {

	
	@Autowired
	PaymentClientRepository paymentClientRepository;

	@Autowired
	UserService userService;

	@Autowired
	CoinService coinService;

	@Autowired
	PaymentFormService paymentFormService;

	@Autowired
	ClientService clientService;
	
	@Autowired
	SaleService saleService;

	@Autowired
	SaleRepository saleRepository;
	
	@Autowired
	ResponseJson responseJson;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public List<PaymentClient> getPaymentAllClients() {
		// TODO Auto-generated method stub
		return paymentClientRepository.findAll();
	}

	@Override
	public PaymentClient getPaymentClientById(Long id) {
		// TODO Auto-generated method stub
		return paymentClientRepository.findById(id)
					.orElseThrow(() -> new HandlerExceptions("ID " + id + " de Pago de Cliente no encontrado", HttpStatus.NOT_FOUND));
	}

	
	@Transactional
	@Override
	public ResponseEntity<Map<String, Object>> savePaymentClient(PaymentClientDTO paymentClientDTO, String option) {
		// TODO Auto-generated method stub
		
		PaymentClient paymentClient = new PaymentClient();
		paymentClient = modelMapper.map(paymentClientDTO, PaymentClient.class);
		paymentClient.setUpdatedAt(LocalDateTime.now());
		User user = userService.getUserById(paymentClientDTO.getUserId());
		paymentClient.setUser(user);
		Coin coin = coinService.getCoinById(paymentClientDTO.getCoinId());
		paymentClient.setCoin(coin);
		Client client = clientService.getClientById(paymentClientDTO.getClientId());
		paymentClient.setClient(client);
		PaymentForm paymentForm = paymentFormService.getPaymentFormById(paymentClientDTO.getPaymentFormId());
		paymentClient.setPaymentForm(paymentForm);
		switch (option) {
		case "Create":
			paymentClient.setStatus("Procesado");
			paymentClient.setCreatedAt(LocalDateTime.now());
			break;
		default:
			PaymentClient paymentClientCurrent = getPaymentClientById(paymentClientDTO.getId());
			paymentClient.setStatus(paymentClientCurrent.getStatus());
			paymentClient.setId(paymentClientCurrent.getId());
			paymentClient.setCreatedAt(paymentClientCurrent.getCreatedAt());			
			break;
		}		
		
		List<Sale> pendingSales = new ArrayList<Sale>();
		pendingSales = saleService.getSalesPendingsClient(client.getId());		
		try {
//			Map<String, Object> hashMap = new HashMap<String,Object>();
			Double mount = paymentClient.getMount();
			for (Sale sale : pendingSales) {				
//				hashMap.put(sale.getId().toString(), mount);
				if (mount > 0) {
					Map<String, Object> dataReturnMap =  verifyDataSale(paymentClientDTO,sale, mount);
					sale.setPaidMount(sale.getPaidMount() + (Double) dataReturnMap.get("paidMount") );
					sale.setStatus((String) dataReturnMap.get("status"));
					sale.setUpdatedAt(LocalDateTime.now());
					saleRepository.save(sale);
					mount = (Double) dataReturnMap.get("pendingMount");
				}
			}		
			client.setBalance(client.getBalance() - calcBalanceClient(paymentClientDTO, client));			
			clientService.clientRepository.save(client);	
			if (client.getBalance() == 0) {
				saleRepository.updateStatusSale(client);
				paymentClientRepository.updateStatusPaymentClient(client);
				
			}
			return responseJson.messageOK("Pago de Cliente Registrado con exito", paymentClientRepository.save(paymentClient));
		} catch (Exception e) {
			throw new HandlerExceptions("Error en Datos de Ingreso o Interno del Servidor" + e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	

	private Float calcBalanceClient(PaymentClientDTO paymentClientDTO, Client client) {
		// TODO Auto-generated method stub
		List<Coin> coinBase = coinService.getCoinBase();
		List<Coin> coinCalc = coinService.getCoinCalc("Sale");
		
		Float mount = (float) 0.0;
		if(client.getCountInBs().compareTo('S') == 0) {
			if (paymentClientDTO.getCoinId() != coinBase.get(0).getId())
				mount = (float) (paymentClientDTO.getMount() * paymentClientDTO.getRateExchange());
			else
				mount = (float) (paymentClientDTO.getMount() * 1);
			return mount;
		}
		if (paymentClientDTO.getCoinId() == coinCalc.get(0).getId()) 
			return (float) (paymentClientDTO.getMount() * 1);

		if (coinCalc.get(0).getId() == coinBase.get(0).getId())  // Moneda de calculo es base son Bs
			mount = (float) (paymentClientDTO.getMount() * paymentClientDTO.getRateExchange());
		else
			mount = (float) (paymentClientDTO.getMount() / paymentClientDTO.getRateExchange());
		return mount;
		
	}

	public Map<String, Object> verifyDataSale(PaymentClientDTO paymentClientDTO, Sale sale, Double mount) {
		
		List<Coin> coinCalc = coinService.getCoinCalc("Sale");		
		double balanceSale = sale.getMount() - sale.getPaidMount(); 
		
		if (paymentClientDTO.getCoinId() == sale.getCoin().getId()) { // MOneda de Pago igual a la FC
			Map<String, Object> jsonMap = calcBalanceSale(mount, balanceSale);
			jsonMap.put("pendingMount",jsonMap.get("mount")); 
			return jsonMap;
		}
		if (paymentClientDTO.getCoinId() == coinCalc.get(0).getId()) { // Moneda de Pago igual a la de Calculo entonces la factura son Bs casi siempre
			Double mountPayment = mount * paymentClientDTO.getRateExchange();
			Map<String, Object> jsonMap = calcBalanceSale(mountPayment, balanceSale);
			jsonMap.put("pendingMount", (Double) jsonMap.get("mount") / paymentClientDTO.getRateExchange()); 
			return jsonMap;
		}
		Double mountPayment = mount / paymentClientDTO.getRateExchange();
		Map<String, Object> jsonMap = calcBalanceSale(mountPayment, balanceSale);
		jsonMap.put("pendingMount", (Double) jsonMap.get("mount") * paymentClientDTO.getRateExchange()); 
		return jsonMap;
	}
	
	public Map<String, Object> calcBalanceSale(Double mountPayment,  Double balanceSale) {
		Map<String, Object> map = new HashMap<String,Object>();
		
		Double paidMount =  (mountPayment >= balanceSale ? balanceSale : mountPayment);
		Double mount = (mountPayment >= balanceSale ? mountPayment - balanceSale : 0);
		String status = (mountPayment >= balanceSale ? "Cancelada" : "Parcial");
		map.put("paidMount", paidMount);
		map.put("mount", mount);
		map.put("status", status);		
		return map;		
	}

	@Override
	public List<Map<String, Object>> getPaymentClientByClient(Long client_id) {
		// TODO Auto-generated method stub
		return paymentClientRepository.findPaymentClientByClient(client_id);
	}
}
