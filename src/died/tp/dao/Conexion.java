package died.tp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class Conexion {


	private static final String url = "jdbc:mysql://localhost:3306/trabajopracticodied";
	private static final String usuario = "root";
	private static final String clave = "onestepcloser01";
	private static Properties pr;
	private static boolean tablasCreadas;

	private static String crearTablaCamion = 	
			"CREATE TABLE IF NOT EXISTS Camion ("
			+ "id_camion INT PRIMARY KEY AUTO_INCREMENT,"
			+ "patente VARCHAR(8),"
			+ "marca VARCHAR(15),"
			+ "modelo VARCHAR(25),"
			+ "kmRecorridos DOUBLE,"
			+ "costoHora DOUBLE,"
			+ "costoKM DOUBLE,"
			+ "fechaCompra DATE"
			+ ")";
	
	private static String crearTablaInsumo =
			"CREATE TABLE IF NOT EXISTS Insumo("
			+ "id_insumo INT PRIMARY KEY AUTO_INCREMENT,"
			+ "descripcion VARCHAR(50) NOT NULL UNIQUE,"
			+ "unidadMedida VARCHAR(10),"
			+ "costoUnidad DOUBLE,"
			+ "peso DOUBLE,"
			+ "densidad DOUBLE"
			+ ")";
	
	private static String crearTablaPlanta = 
			"CREATE TABLE IF NOT EXISTS Planta("
			+ "id_planta INT PRIMARY KEY AUTO_INCREMENT,"
			+ "nombrePlanta VARCHAR(15) NOT NULL UNIQUE"
			+ ")";
	private static String crearTablaPlantaStock = 
			"CREATE TABLE IF NOT EXISTS PlantaStock("
			+ "id_planta INT REFERENCES Planta(id_planta),"
			+ "id_insumo INT REFERENCES Insumo(id_insumo),"
			+ "cantidad INT,"
			+ "puntoPedido INT,"
			+ "PRIMARY KEY(id_planta,id_insumo)"
			+ ")";
	
	private static String crearTablaOrdenPedido = 
			"CREATE TABLE IF NOT EXISTS OrdenPedido("
			+ "id_ordenPedido INT PRIMARY KEY AUTO_INCREMENT,"
			+ "plantaOrigen INT REFERENCES Planta(id_planta),"
			+ "plantaDestino INT REFERENCES Planta(id_planta),"
			+ "costoEnvio DOUBLE,"
			+ "estado ENUM ('creada','procesada','entregada','cancelado'),"
			+ "camionAsignado INT REFERENCES Camion(id_camion),"
			+ "fechaEntrega DATE"
			+ ")";
	
	
	//CREO ESTA TABLA NUEVA PARA MANTENER LAS FORMAS NORMALES
	private static String crearTablaOrdenInsumos = 
			"CREATE TABLE IF NOT EXISTS OrdenInsumos("
			+ "id_ordenPedido INT REFERENCES OrdenPedido(id_ordenPedido),"
			+ "id_insumo INT REFERENCES Insumo(id_insumo),"
			+ "cantidadInsumo INT,"
			+ "PRIMARY KEY(id_ordenPedido,id_insumo)"
			+ ")";
	
	private static String crearTablaRuta = 
			"CREATE TABLE IF NOT EXISTS Ruta("
			+ "plantaOrigen INT REFERENCES Planta(id_planta),"
			+ "plantaDestino INT REFERENCES Planta(id_planta),"
			+ "distancia INT,"
			+ "horasEstimadas Double,"
			+ "pesoMaximo INT,"
			+ "PRIMARY KEY(plantaOrigen, plantaDestino)"
			+ ")";
	
	
	public Conexion() {
		
	}

	public static Connection conectar() {
		pr = new Properties();
		pr.put("user", usuario);
		pr.put("password",clave);
		Connection c = null;
		try {
			c = DriverManager.getConnection(url,pr);
		} catch(SQLException e) {
				System.out.println("Error en la conexión");
				e.printStackTrace();
		} 
	
	return c;
	}

	public static Connection getConexion() throws SQLException {
		crearTablas();
		return conectar();
	}
	
	public static void crearTablas() throws SQLException {
		if(!tablasCreadas) {

		Connection con = null;

		PreparedStatement stmt = null;
		try {
			  con = Conexion.conectar();
			  stmt = con.prepareStatement(crearTablaCamion);  
			  stmt.execute(); 
			  stmt = con.prepareStatement(crearTablaInsumo);
			  stmt.execute();
			  stmt = con.prepareStatement(crearTablaPlanta);
			  stmt.execute();
			  stmt = con.prepareStatement(crearTablaPlantaStock);
			  stmt.execute();
			  stmt = con.prepareStatement(crearTablaOrdenPedido);
			  stmt.execute();
			  stmt = con.prepareStatement(crearTablaRuta);
			  stmt.execute();	
			  stmt = con.prepareStatement(crearTablaOrdenInsumos);
			  stmt.execute();
			  stmt.close();	     
			  tablasCreadas = true;
			} catch (SQLException sqle) { 
			  System.out.println("Error en la ejecución: " 
			    + sqle.getErrorCode() + " " + sqle.getMessage());    
			}
		}
	}
}