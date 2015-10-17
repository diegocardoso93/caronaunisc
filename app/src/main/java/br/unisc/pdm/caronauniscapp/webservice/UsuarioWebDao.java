package br.unisc.pdm.caronauniscapp.webservice;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.unisc.pdm.caronauniscapp.Home;
import br.unisc.pdm.caronauniscapp.Logar;
import br.unisc.pdm.caronauniscapp.database.Usuario;

/**
 * Created by Diego on 05/10/2015.
 */
public class UsuarioWebDao {
    public Context context;
    public UsuarioTela tela;

    public UsuarioWebDao (UsuarioTela t){
        this.context = (Context) t;
        this.tela = t;
    }

    public List<Usuario> getAllUsuarios() {
        //public void getAllUsuarios() {
        String url = Usuario.BASE_URL + "/usuario";
        List<Usuario> pessoas = new ArrayList<Usuario>();

        JsonArrayRequest req = new JsonArrayRequest(
                url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response){

                List<Usuario> pessoas = new ArrayList<Usuario>();

                //Fazendo PARSE do JSON
                String valores = "";
                try {
                    for(int i =0; i < response.length(); i++) {
                        JSONObject jsonKeyValue = response.getJSONObject(i);
                        Usuario pessoa = jsobjToUsuario(jsonKeyValue);
                        pessoas.add(pessoa);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("WBS", pessoas.toString());
                tela.popularView(pessoas);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "Problema ao buscar dados da web", Toast.LENGTH_SHORT).show();
                Log.d("WBS", error.toString());
            }
        }
        );

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(req);

        return pessoas;
    }


    private Usuario jsobjToUsuario(JSONObject json){
        Usuario p = new Usuario();
        try{
            p.setId(json.getInt("id"));
            p.setMatricula(json.getInt("matricula"));
            p.setNome(json.getString("nome"));
            p.setSenha(json.getString("senha"));
            p.setSexo(json.getString("sexo"));
            p.setCadastroTipo(json.getInt("usuario_tipo"));
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return p;
    }

    public void getUsuarioByMat(String mat) {
        String url = Usuario.BASE_URL + "/usuario/"+mat;
        Log.d("WBS","URL: "+url);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response){
                        Usuario p;
                        p = jsobjToUsuario(response);
                        Log.d("WBS", p.toString());
                        List<Usuario> pessoas = new ArrayList<Usuario>();
                        pessoas.add(p);
                        tela.popularView(pessoas);
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Toast.makeText(context,"Problema ao executar sua solicitacao",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsObjRequest);

    }

    public void editUsuario(Usuario p){
        String url = Usuario.BASE_URL + "/usuario/"+p.getMatricula();
        Log.d("WBS","URL: "+url);

        //========= CRIAR JSON COM DADOS DO ESTUDANTE ===============
        final JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("matricula",p.getMatricula());
            jsonBody.put("nome",p.getNome());
            jsonBody.put("senha",p.getSenha());
            jsonBody.put("sexo",p.getSexo());
            jsonBody.put("usuario_tipo",p.getCadastroTipo());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("WBS",jsonBody.toString());
        //========== FAZER REQUEST PASSANDO O JSON E tratando o retorno ===========
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.PUT, url, jsonBody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response){
                        Log.d("WBS", "Retornou do request!");
                        Log.d("WBS", response.toString());
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("WBS","caiu no onErrorResponse");
                        Log.d("WBS", error.toString());
                    }
                });


        RequestQueue queue = Volley.newRequestQueue(context);

        queue.add(jsObjRequest);

    }

    public void insertUsuario(Usuario p){
        String url = Usuario.BASE_URL + "/usuario";
        Log.d("WBS","URL: "+url);

        //========= CRIAR JSON COM DADOS DO ESTUDANTE ===============
        final JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("matricula",p.getMatricula());
            jsonBody.put("nome",p.getNome());
            jsonBody.put("senha",p.getSenha());
            jsonBody.put("sexo",p.getSexo());
            jsonBody.put("usuario_tipo",p.getCadastroTipo());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("WBS",jsonBody.toString());
        //========== FAZER REQUEST PASSANDO O JSON E tratando o retorno ===========
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response){
                        Log.d("WBS", "Retornou do request!");
                        Log.d("WBS", response.toString());
                        try {
                            Log.d("WBS", response.getString("result"));
                            if (response.getString("result").equals("Inserido com sucesso!")) {
                                Toast.makeText(context, response.getString("result"), Toast.LENGTH_SHORT).show();
                                context.startActivity(new Intent(context, Logar.class));
                            } else {
                                Toast.makeText(context, response.getString("result"), Toast.LENGTH_SHORT).show();
                            }
                        }catch(JSONException e){

                        }
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("WBS","caiu no onErrorResponse");
                        Log.d("WBS", error.toString());
                    }
                });


        RequestQueue queue = Volley.newRequestQueue(context);

        queue.add(jsObjRequest);

    }

    public void loginUsuario(String matricula, String senha){
        String url = Usuario.BASE_URL + "/login";
        //========= CRIAR JSON COM DADOS DO ESTUDANTE ===============
        final JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("matricula",matricula);
            jsonBody.put("senha",senha);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("WBS",jsonBody.toString());
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response){
                        Log.d("WBS", "Retornou do request!");
                        Log.d("WBS", response.toString());
                        try {
                            Log.d("WBS", response.getString("result"));
                            if (response.getString("result").equals("Logado com sucesso!")) {
                                Intent home = new Intent(context, Home.class);
                                home.putExtra("matricula",jsonBody.getString("matricula"));
                                context.startActivity(home);
                            } else {
                                Toast.makeText(context, response.getString("result"), Toast.LENGTH_SHORT).show();
                            }
                        }catch(JSONException e){

                        }
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("WBS","caiu no onErrorResponse");
                        Log.d("WBS", error.toString());
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(context);

        queue.add(jsObjRequest);
    }
}