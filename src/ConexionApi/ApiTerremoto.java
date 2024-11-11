//Fernando de Jesus Barron Mojica
//Topicos para el Despliegue de aplicaciones
package ConexionApi;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiTerremoto {

    public static void main(String[] args) {
        // Crear la ventana
        JFrame frame = new JFrame("Earthquake Data");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        
        // Crear un JTextArea para mostrar la información
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        
        // Añadir el JTextArea a un JScrollPane para poder hacer scroll
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Mostrar la ventana
        frame.setVisible(true);

        try {
            // URL de la API
            String url = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2014-01-01&endtime=2014-01-02";

            // Crear objeto URL
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // Configurar la solicitud
            con.setRequestMethod("GET");

            // Leer la respuesta
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Parsear el JSON
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray features = jsonResponse.getJSONArray("features");

            // Construir la cadena de texto para mostrar
            StringBuilder textToShow = new StringBuilder();
            for (int i = 0; i < features.length(); i++) {
                JSONObject feature = features.getJSONObject(i);
                JSONObject properties = feature.getJSONObject("properties");
                textToShow.append("Lugar: ").append(properties.getString("place")).append("\n");
                textToShow.append("Magnitud: ").append(properties.getDouble("mag")).append("\n");
                textToShow.append("Fecha: ").append(properties.getLong("time")).append("\n");
                textToShow.append("--------------------------------\n");
            }

            // Mostrar la información en el JTextArea
            textArea.setText(textToShow.toString());

        } catch (Exception e) {
            e.printStackTrace();
            textArea.setText("Error al obtener los datos.");
        }
    }
}

