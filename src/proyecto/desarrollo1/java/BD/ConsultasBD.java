
package proyecto.desarrollo1.java.BD;


import proyecto.desarrollo1.java.Modelo.Cliente;
import proyecto.desarrollo1.java.Modelo.Usuario;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.*;

public class ConsultasBD {
    
    private AccesoBD accesobd = new AccesoBD();
    private Connection connec;

    
    public void empezarConexion() throws SQLException
    {
        connec = accesobd.getConnection();
    }
    
    public void cerrarConexion() throws SQLException
    {
        accesobd.cerrarConexion(connec);
    }
    
    // Inicio Consultas Usuario
    
    public int usuario_ConsultaLoginPassword_DevuelveActivo(String login, String password) throws SQLException
    {
        String consulta_sql_login = "SELECT * FROM usuario WHERE usuario_login='" + login + "' AND usuario_password='" + password + "';";
        Statement st = connec.createStatement();
        ResultSet rs = st.executeQuery(consulta_sql_login);
        if(rs.next())
        {   
            int activo = 0;
            activo = rs.getInt(9);
            return activo;
        }
        else
        {
            return -1;
        }
    }
    
    public int usuario_ConsultaLogin_DevuelveTipo(String login) throws SQLException
    {
        String consulta_sql_login = "SELECT * FROM usuario WHERE usuario_login='" + login + "'" + ";";
        Statement st = connec.createStatement();
        ResultSet rs = st.executeQuery(consulta_sql_login);
        if(rs.next())
        {   
            int tipo = 0;
            tipo = rs.getInt(2);
            return tipo;
        }
        else
        {
            return -1;
        }
    }
    
    public void registrarUsuario(Usuario user) throws SQLException
    {
        int operacion;
        String consulta_sql_registrar_usuario = "INSERT INTO usuario (id_usuario, usuario_tipo, usuario_login, usuario_password, usuario_nombre, usuario_apellidos, usuario_cc, usuario_telefono, usuario_estado) VALUES (default, " + user.getTipo() + ", '" + user.getLogin() + "', '" + user.getPassword() + "', '" + user.getNombre() + "', '" + user.getApellido() + "', '" + user.getCedula() + "', '" + user.getTelefono() + "', " + user.getActivo() + ");";
        try
        {
            Statement st = connec.createStatement();
            operacion = st.executeUpdate(consulta_sql_registrar_usuario);
            System.out.println("Registro de usuario exitoso");
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    
    public int verificarSiExisteUsuarioConLogin(String login) throws SQLException
    {
        String consulta_sql_login = "SELECT * FROM usuario WHERE usuario_login='" + login + "';";
        Statement st = connec.createStatement();
        ResultSet rs = st.executeQuery(consulta_sql_login);
        if(rs.next())
        {   
            System.out.println("Si hay usuario valido");
            return 1;
        }
        else
        {
            return -1;
        }
    }
    
    public void modificarUsuario(String loginviejo, int tipo, String nombre, String apellido, String cedula, String telefono, String login, String contraseña) throws SQLException
    {
        int operacion;
        String string_tipo = "";
        String string_nombre = "";
        String string_apellido = "";
        String string_cedula = "";
        String string_telefono = "";
        String string_login = "";
        String string_password = "";
        switch (tipo) {
            case 1:
                string_tipo = "usuario_tipo = 1";
                break;
            case 2:
                string_tipo = "usuario_tipo = 2";
                break;
            case 3:
                string_tipo = "usuario_tipo = 3";
                break;
            default:
                break;
        }
        
        if(!nombre.equals(""))
        {
            string_nombre = ", usuario_nombre = '" + nombre + "'";
        }
        
        if(!apellido.equals(""))
        {
            string_apellido = ", usuario_apellidos = '" + apellido + "'";
        }
        
        if(!cedula.equals(""))
        {
            string_cedula = ", usuario_cc = '" + cedula + "'";
        }
        
        if(!telefono.equals(""))
        {
            string_telefono = ", usuario_telefono = '" + telefono  + "'";
        }
        
        if(!login.equals(""))
        {
            string_login = ", usuario_login = '" + login + "'";
        }
        
        if(!contraseña.equals(""))
        {
            string_password = ", usuario_password = '" + contraseña + "'";
        }
        
        String consulta_sql_modificar_usuario = "UPDATE usuario SET " + string_tipo + string_nombre + string_apellido + string_cedula + string_telefono + string_login + string_password + " WHERE usuario_login='" + loginviejo + "';";
        System.out.println(consulta_sql_modificar_usuario);
        
        try
        {
            Statement st = connec.createStatement();
            operacion = st.executeUpdate(consulta_sql_modificar_usuario);
            System.out.println("Modificacion de usuario exitosa");
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    public int verificarEstadoDeUsuarioConLogin(String login) throws SQLException
    {
        int activo = 0;
        String consulta_sql_login = "SELECT * FROM usuario WHERE usuario_login='" + login + "';";
        Statement st = connec.createStatement();
        ResultSet rs = st.executeQuery(consulta_sql_login);
        if(rs.next())
        {   
            activo = rs.getInt(9);
            return activo;
        }
        else
        {
            return -1;
        }
    }
    
    
    public void cambiarEstadoDeUsuario(String loginviejo, int activo)
    {
        int operacion;
        String consulta = "UPDATE usuario SET usuario_estado = " + activo + " WHERE usuario_login = '" + loginviejo + "';";
        try
        {
            Statement st = connec.createStatement();
            operacion = st.executeUpdate(consulta);
            System.out.println("Modificacion de estado de usuario exitosa");
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    public Usuario consultaUsuario(String login) throws SQLException
    {
        String consulta_sql_login = "SELECT * FROM usuario WHERE usuario_login='" + login + "'" + ";";
        Statement st = connec.createStatement();
        ResultSet rs = st.executeQuery(consulta_sql_login);
        rs.next();
        Usuario miUsuario; 
        miUsuario = new Usuario(rs.getInt(1),rs.getInt(2),rs.getString(3),
                                rs.getString(4),rs.getString(5),rs.getString(6),
                                rs.getString(7),rs.getString(8),rs.getInt(9));
           
            return miUsuario;
        
        
    }
    
    public void borrarUsuarioConLogin(String login) throws SQLException
    {
        int operacion;
        String consulta_borrar = "DELETE FROM usuario WHERE usuario_login = '" + login + "';";
        try
        {
            Statement st = connec.createStatement();
            operacion = st.executeUpdate(consulta_borrar);
            System.out.println("Borrado de usuario exitoso");
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    // Fin Consultas Usuario
    
    // Inicio Consultas Cliente
    
    public void registrarCliente(Cliente client) throws SQLException
    {
        int operacion;
        String consulta_sql_registrar_cliente = "INSERT INTO cliente (id_cliente, cliente_tipo, cliente_nombre, cliente_apellidos, cliente_direccion, cliente_ciudad, cliente_cc, cliente_estado) VALUES (default, " + client.getTipo() + ", '" + client.getNombre() + "', '" + client.getApellido() + "', '" + client.getDireccion() + "', '" + client.getCiudad() + "', '" + client.getCedula() + "', default);";
        try
        {
            Statement st = connec.createStatement();
            operacion = st.executeUpdate(consulta_sql_registrar_cliente);
            System.out.println("Registro de cliente exitoso");
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    public int verificarSiExisteClienteConCedula(String cedula) throws SQLException
    {
        String consulta_sql_login = "SELECT * FROM cliente WHERE cliente_cc='" + cedula + "';";
        Statement st = connec.createStatement();
        ResultSet rs = st.executeQuery(consulta_sql_login);
        if(rs.next())
        {   
            System.out.println("Si hay cliente valido");
            return 1;
        }
        else
        {
            return -1;
        }
    }
    
    public int obtenerTipoClienteConCedula(String cedula) throws SQLException
    {
        String consulta_sql_login = "SELECT * FROM cliente WHERE cliente_cc='" + cedula + "'" + ";";
        Statement st = connec.createStatement();
        ResultSet rs = st.executeQuery(consulta_sql_login);
        if(rs.next())
        {   
            int tipo = 0;
            tipo = rs.getInt(2);
            return tipo;
        }
        else
        {
            return -1;
        }
    }
    
    public Cliente consultaCliente(String cedula) throws SQLException
    {
        Cliente miCliente;
        String consulta_sql_estado = "SELECT * FROM cliente WHERE cliente_cc = '" + cedula + "';";
        Statement st = connec.createStatement();
        ResultSet rs = st.executeQuery(consulta_sql_estado);
        rs.next();
        miCliente = new Cliente(rs.getInt(1),
                                   rs.getInt(2),
                                   rs.getString(3),
                                    rs.getString(4),
                                    rs.getString(5),
                                    rs.getString(6),
                                    rs.getString(7),
                                    rs.getInt(8));
        return miCliente;

    }
    
    public void modificarCliente(String documento_cliente, int tipo, String nombre, String apellido , String direccion,String ciudad) throws SQLException
    {
        System.out.println("TIPO :" + tipo);
        int operacion;
        String string1 = "";
        String string2 = "";
        String string3 = "";
        String string4 = "";
        String string5 = "";
        switch (tipo) {
            case 1:
                string1 = "cliente_tipo = 1";
                break;
            case 2:
                string1 = "cliente_tipo = 2";
                break;
            default:
                break;
        }
        
        if(!nombre.equals(""))
        {
            string2 = ", cliente_nombre = '" + nombre + "'";
        }
        
        if(!apellido.equals(""))
        {
            string3 = ", cliente_apellidos = '" + apellido + "'";
        }
        if(!direccion.equals(""))
        {
            string4 = ", cliente_direccion = '" + direccion + "'";
        }
        if(!ciudad.equals(""))
        {
            string5 = ", cliente_ciudad = '" + ciudad + "'";
        }
        
        
        String consulta_sql_modificar_cliente;
        consulta_sql_modificar_cliente= "UPDATE cliente SET " + string1 + string2 + string3 + string4 + string5 + " WHERE cliente_cc='" + documento_cliente + "';";
        System.out.println(string1);
        try
        {
            Statement st = connec.createStatement();
            operacion = st.executeUpdate(consulta_sql_modificar_cliente);
            System.out.println("Modificacion de cliente exitosa");
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    public void cambiarEstadoDeCliente(String cedula, int nuevo)
    {
        int operacion;
        String consulta = "UPDATE cliente SET cliente_estado = " + nuevo + " WHERE cliente_CC = '" + cedula + "';";
        try
        {
            Statement st = connec.createStatement();
            operacion = st.executeUpdate(consulta);
            System.out.println("Modificacion de estado de cliente exitosa");
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    public int obtenerEstadoClienteConCedula(String cedula) throws SQLException
    {
        String consulta_sql_login = "SELECT * FROM cliente WHERE cliente_cc='" + cedula + "'" + ";";
        Statement st = connec.createStatement();
        ResultSet rs = st.executeQuery(consulta_sql_login);
        if(rs.next())
        {   
            int estado = 0;
            estado = rs.getInt(8);
            return estado;
        }
        else
        {
            return -1;
        }
    }
    
    
    public void borrarClienteConCedula(String cedula) throws SQLException
    {
        int operacion;
        String consulta_borrar = "DELETE FROM cliente WHERE cliente_cc = '" + cedula + "';";
        try
        {
            Statement st = connec.createStatement();
            operacion = st.executeUpdate(consulta_borrar);
            System.out.println("Borrado de cliente exitoso");
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    public int obtenerIDClienteConCedula(String cedula) throws SQLException
    {
        String consulta_sql_login = "SELECT * FROM cliente WHERE cliente_cc='" + cedula + "'" + ";";
        Statement st = connec.createStatement();
        ResultSet rs = st.executeQuery(consulta_sql_login);
        if(rs.next())
        {   
            int id = 0;
            id = rs.getInt(1);
            return id;
        }
        else
        {
            return -1;
        }
    }
    
    public int obtenerIDPlanConNombre(String plan_nombre) throws SQLException
    {
        int idPlan = 0;
        String consulta_sql_planid_nombre = "SELECT * FROM plan WHERE plan_nombre = '" + plan_nombre + "';";
        Statement st = connec.createStatement();
        ResultSet rs = st.executeQuery(consulta_sql_planid_nombre);
        if(rs.next())
        {
            idPlan = rs.getInt(1);
            return idPlan;
        }
        else
        {
            return -1;
        }
    }
    
    public void registrarPlan(int id_plan, int id_cliente, String telefono) throws SQLException
    {
        int operacion;
        LocalDateTime localdatetime = LocalDateTime.now();
        String consulta_sql_registrar_plan;
        consulta_sql_registrar_plan = "INSERT INTO cliente_contrata_plan (id_contrato, id_cliente, id_plan, cliente_telefono, contrato_fecha, contrato_mensajes_consumidos, contrato_datos_consumidos, contrato_min_consumidos) VALUES (default, " + id_cliente + "," + id_plan + ", '" + telefono + "', '" + java.sql.Timestamp.valueOf(localdatetime) + "', default, default, default);";
        try
        {
            Statement st = connec.createStatement();
            operacion = st.executeUpdate(consulta_sql_registrar_plan);
            System.out.println("Registro de plan exitoso");
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    
}