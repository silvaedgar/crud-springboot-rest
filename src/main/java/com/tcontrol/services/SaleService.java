package com.tcontrol.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tcontrol.DTO.SaleDTO;
import com.tcontrol.exceptions.HandlerExceptions;
import com.tcontrol.models.Client;
import com.tcontrol.models.Coin;
import com.tcontrol.models.Product;
import com.tcontrol.models.Sale;
import com.tcontrol.models.SaleDetail;
import com.tcontrol.models.User;
import com.tcontrol.repository.ClientRepository;
import com.tcontrol.repository.SaleRepository;
import com.tcontrol.response.ResponseJson;
import com.tcontrol.services.interfaces.ISaleService;

@Service
public class SaleService implements ISaleService {

	@Autowired
	public SaleRepository saleRepository;

	@Autowired
	public ClientRepository clientRepository;

	@Autowired
	public ProductService productService;

	@Autowired
	public ClientService clientService;

	@Autowired
	public CoinService coinService;

	@Autowired
	public UserService userService;

	@Autowired
	ResponseJson returnJsonOK;

	@Autowired
	ModelMapper modelMapper;

	@Transactional
	@Override
	public ResponseEntity<Map<String, Object>> saveSale(SaleDTO saleDTO) {
		// TODO Auto-generated method stub

// el rate exchange debe tener la tasa del dia
		Sale sale = getSaleDetails(saleDTO);
		sale = dataSale(sale, saleDTO);

//		sale = modelMapper.map(saleDTO, Sale.class);  // EL model Mapper no funciona no se porque

		Client client = clientService.getClientById(saleDTO.getClientId());
		sale.setClient(client);

		Float balanceClient = client.getBalance();
		Double mountInvoice = sale.getMount();
		if (saleDTO.getConditions().compareTo("Credito") == 0) {

			List<Coin> coinBase = coinService.getCoinBase();
			List<Coin> coinCalc = coinService.getCoinCalc("Sale");

			String status = "Pendiente";

			if (sale.getCoin().getId() != coinCalc.get(0).getId() && client.getCountInBs().compareTo('S') != 0) {
				mountInvoice = (sale.getMount() / sale.getRateExchange());
				// ojo esta opcion falla si la moneda de calculo es Bs y la factura en $ debe
				// multiplica
			}
			if (client.getCountInBs().compareTo('S') == 0 && sale.getCoin().getId() != coinBase.get(0).getId())
				balanceClient += (float) (mountInvoice * sale.getRateExchange());
			else
				balanceClient = (float) (client.getBalance() + mountInvoice);

			if (client.getBalance() < 0) { // saldo a favor del cliente
				status = (balanceClient <= 0 ? "Cancelada" : "Parcial");
				mountInvoice = (balanceClient <= 0 ? sale.getMount() : -1 * client.getBalance());
			}
			if (balanceClient == 0) {
				status = "Historico";
				// ojo poner en historico el payment client
			}
			sale.setStatus(status);
			sale.setPaidMount(mountInvoice);
		}
		try {
			Client saveClient = new Client();
			saveClient = modelMapper.map(client, Client.class); // aqui si funciona
			saveClient.setBalance(balanceClient);
			saveClient.setUpdatedAt(LocalDateTime.now());
			
			Sale saveSale = saleRepository.save(sale);
			saveClient = clientRepository.save(saveClient);
			return returnJsonOK.messageOK("Factura Creada con exito. Actualizado Balance de Cliente", saveSale);
		} catch (Exception e) {
			throw new HandlerExceptions("Error en datos de Entrada o Interno del Servidor. No se creó la Factura "
					+ e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	public Sale getSaleDetails(SaleDTO saleDTO) {

		Sale sale = new Sale();
		for (int i = 0; i < saleDTO.getItemsSaleDetail().size(); i++) {
			Product product = productService.getProductById(saleDTO.getItemsSaleDetail().get(i).getProductId());
			SaleDetail saleDetail = new SaleDetail();
//			saleDetail = modelMapper.map(saleDTO.getItemsSaleDetail().get(i), SaleDetail.class);
			saleDetail.setItem(saleDTO.getItemsSaleDetail().get(i).getItem());
			saleDetail.setPrice(saleDTO.getItemsSaleDetail().get(i).getPrice());
			saleDetail.setQuantity(saleDTO.getItemsSaleDetail().get(i).getQuantity());
			saleDetail.setTax(saleDTO.getItemsSaleDetail().get(i).getTax());
			saleDetail.setTaxId(saleDTO.getItemsSaleDetail().get(i).getTaxId());
			saleDetail.setProduct(product);
			saleDetail.setStatus("Activo");
			saleDetail.setCreatedAt(LocalDateTime.now());
			saleDetail.setUpdatedAt(LocalDateTime.now());
			sale.addItemSale(saleDetail);
		}
		return sale;
	}

	public Sale dataSale(Sale sale, SaleDTO saleDTO) {

		User user = userService.getUserById(saleDTO.getUserId());
		Coin coin = coinService.getCoinById(saleDTO.getCoinId());

		String conditions = saleDTO.getConditions();
		sale.setConditions(conditions);
		sale.setDate(saleDTO.getDate());
		sale.setInvoice(saleDTO.getInvoice());
		sale.setRateExchange(saleDTO.getRateExchange());
		sale.setObservations(saleDTO.getObservations());
		sale.setCoin(coin);
		sale.setMount(sale.calculateMount());
		sale.setTaxMount(0.0);
		sale.setStatus((conditions.compareTo("Contado") != 0 ? "Pendiente" : "Cancelada"));
		sale.setUser(user);
		sale.setCreatedAt(LocalDateTime.now());
		sale.setUpdatedAt(LocalDateTime.now());
		sale.setPaidMount(0.0);

		return sale;
	}

	@Override
	public List<Sale> getSalesPendingsClient(Long client_id) {
		// TODO Auto-generated method stub
		Client client = clientService.getClientById(client_id);		
		Collection<String> statusCollection = new ArrayList<String>();
		statusCollection.add("Parcial");
		statusCollection.add("Pendiente");
	
		return saleRepository.findByStatusInAndClient(statusCollection, client);
	}
	
	public List<Map<String, Object>> findSalesByClient(Long client_id) {
		// TODO Auto-generated method stub
		return saleRepository.findSalesByClient(client_id);
		
//		Client client = clientService.getClientById(client_id);		
//		return saleRepository.findByClient(client);
	}

	@Override
	public List<Map<String, Object>> getAllSalesWithClient() {
		// TODO Auto-generated method stub
		return saleRepository.findSalesWithClient();
	}

	@Override
	public Sale getSaleById(Long id) {
		// TODO Auto-generated method stub
		return saleRepository.findById(id).orElseThrow(
				() -> new HandlerExceptions("Factura de Venta con ID: " + id + " no existe", HttpStatus.NOT_FOUND));
	}

}
