package died.tp.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import died.tp.controllers.InformacionOrdenController;
import died.tp.controllers.OrdenPedidoController;
import died.tp.dao.InsumoDao;
import died.tp.dao.PlantaDao;
import died.tp.dao.PlantaStockDao;
import died.tp.dominio.Insumo;
import died.tp.dominio.InsumoGeneral;
import died.tp.dominio.InsumoLiquido;
import died.tp.dominio.OrdenDePedido;
import died.tp.dominio.Planta;
import died.tp.dominio.Stock;


class Ejercicio5Test {

	//Atributos
	InsumoDao insumoDao;
	PlantaDao pd ;
	Planta p;
	Planta p2;
	OrdenDePedido orden;
	InformacionOrdenController infoController;
	OrdenPedidoController ordenController;
	Map<Insumo,Integer> insumos;
	InsumoLiquido insumoLiquido;
	InsumoGeneral insumoGeneral;
	Date date = new Date(System.currentTimeMillis());
	
	@BeforeEach
	void setup() {
		insumos = new HashMap<Insumo,Integer>();
		ordenController = new OrdenPedidoController(null);
		infoController = new InformacionOrdenController(null);
		insumoLiquido = new InsumoLiquido("insumoGenerico1","cm3",150,150.00);
		insumoGeneral = new InsumoGeneral("insumoGenerico2","kg",200,150.00);
		pd = new PlantaDao();
		insumoDao = new InsumoDao();


		
	}
	
	
	@Test
	void testAgregarOrden() {
		p = new Planta ("planta nro 1");
		pd.altaPlanta(p.getNombrePlanta());

		
		ordenController.actualizarValorCompra(5, 5);
		assertTrue(ordenController.agregarOrden(date, p.getNombrePlanta()));
	}
	
	@Test
	void testControlarStock(){
		insumoDao.altaActualizacionInsumo(insumoGeneral);
		insumoDao.altaActualizacionInsumo(insumoLiquido);	
		insumos.put(insumoGeneral, 5);
		insumos.put(insumoLiquido, 6);
		p2 = new Planta("planta nro 2");
		ordenController.agregarOrden(date, p2.getNombrePlanta());
		infoController.traerTodasOrdenes(1);
		// no contiene insumos la orden
		assertFalse(infoController.controlarStock("2"));
		
	}


	
}
