package me.Alex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {
	
	public static String url;
	
	public static void main(String[] args) {
		
		System.out.println("\n  ===== SimpleDiscordWebHook by CorruptedBytes =====\n");
		
		if (args.length > 2) {
			String msg = "";
			for (int i = 2; i < args.length; i++) {
				if (i != args.length) {
					msg += args[i] + " ";						
				}
				else
				{
					msg += args[i];
				}
			}
			
			Main.url = read();
			System.out.println("~ " + args[0] + " sent: " + msg);
			Main.discord(args[0], args[1], msg);
			
		} else {
			System.out.println("DiscordWebhook.jar <Name> <ProfilePircture> <Message>");
		}
		
	}
	
	public static String read() {
		StringBuilder builder = new StringBuilder();
	 try {
	      File myObj = new File("url.yml");
	      Scanner myReader = new Scanner(myObj);
	      while (myReader.hasNextLine()) {
	        String data = myReader.nextLine();
	        builder.append(data);
	      }
	      myReader.close();
	    } catch (FileNotFoundException e) {
	      System.out.println("Error: Could not find 'url.yml'.\nThe system could not find the specified path.");
	    }
	return builder.toString();
	}
	
	public static void discord(String name, String img, String message) {
		try {
			URL ul = new URL(Main.url);
            HttpURLConnection connection = (HttpURLConnection)ul.openConnection();
            try {
              connection.setRequestMethod("POST");
            } catch (ProtocolException e) {
              e.printStackTrace();
            } 
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; U; Linux i686) Gecko/20071127 Firefox/2.0.0.11");
            connection.setDoOutput(true);
            Charset charset = StandardCharsets.UTF_8;
            OutputStream outputStream = connection.getOutputStream();
            try {
              byte[] input = ("{\"username\":\"" + name + "\",\"content\":\"" + message + "\",\"avatar_url\":\"" + img + "\"}").getBytes(charset);
              outputStream.write(input, 0, input.length);
              if (outputStream != null)
                outputStream.close(); 
            } catch (Throwable throwable) {
              if (outputStream != null)
                try {
                  outputStream.close();
                } catch (Throwable throwable1) {
                  throwable.addSuppressed(throwable1);
                }  
              throw throwable;
            } 
            if (connection.getResponseCode() != 201)
              return; 
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
            try {
              StringBuilder response = new StringBuilder();
              String inputLine;
              while ((inputLine = bufferedReader.readLine()) != null)
                response.append(inputLine); 
              System.out.println(response.toString());
              bufferedReader.close();
            } catch (Throwable throwable) {
              try {
                bufferedReader.close();
              } catch (Throwable throwable1) {
                throwable.addSuppressed(throwable1);
              } 
              throw throwable;
            } 
          } catch (IOException e) {
           // e.printStackTrace();
        	  
          }
	}
	
}
