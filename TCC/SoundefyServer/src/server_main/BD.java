package server_main;

import java.sql.*;

public class BD
{
    private Connection conexao;
    private Statement  comando;

    public BD (String drv, String strCon,
               String usr, String senha) throws Exception
    {
        try
        {
            Class.forName (drv);
        }
        catch (ClassNotFoundException e)
        {
            throw new Exception ("driver");
        }

        try
        {
            conexao = DriverManager.getConnection (strCon, usr, senha);
        }
        catch (SQLException e)
        {
            throw new Exception ("conexao");
        }

        try
        {
            comando = conexao.createStatement
                     (ResultSet.TYPE_SCROLL_INSENSITIVE,
                      ResultSet.CONCUR_READ_ONLY);
        }
        catch (SQLException e)
        {
            throw new Exception ("comando");
        }
    }

    public void execComando (String cmdSQL) throws Exception
    {
        try
        {
            this.comando.executeUpdate (cmdSQL);
        }
        catch (SQLException e)
        {
            throw new Exception ("execucao comando");
        }
    }

    public ResultSet execConsulta (String qrySQL) throws Exception
    {
        ResultSet resultado = null;

        try
        {
            resultado = this.comando.executeQuery (qrySQL);
        }
        catch (SQLException e)
        {
            throw new Exception ("execucao consulta");
        }

        return resultado;
    }

    public void fecharConexao () throws Exception
    {
        try
        {
            this.comando.close ();
            this.comando = null;

            this.conexao.close ();
            this.conexao = null;
        }
        catch (SQLException e)
        {
            throw new Exception ("fechamento");
        }
    }
}