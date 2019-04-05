package com.example.controle_leds; // NOME DO PACOTE REFERENTE A CLASSE

//IMPORTAÇÃO DAS BIBLIOTECAS NECESSÁRIAS PARA EXECUÇÃO DO CÓDIGO

//

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/

public class Controle_Leds_Activity extends ActionBarActivity implements OnClickListener {
	
	// DECLARAÇÃO DE VARIÁVEIS
	ImageButton btOnA,btOffA,btOnB,btOffB,btOnC,btOffC,btConectar;
	TextView tvInternet,tvStatusA,tvStatusB,tvStatusC;
	EditText et_Ip;
	String L,N,M, hostIp = null;
	Handler mHandler;
	long lastPress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		telaIp(); // FAZ A CHAMADA DO MÉTODO "telaIp"
	}
	// MÉTODO "telaIp"
		public void telaIp(){
			setContentView(R.layout.tela_ip); // INICIALIZA A TELA
			et_Ip = (EditText)findViewById(R.id.et_Ip); // ESTANCIA O EDITTEXT
			
	    	btConectar = (ImageButton) findViewById(R.id.btConectar); // ESTANCIA O IMAGEBUTTON
	        btConectar.setOnClickListener(this); // ATIVA O CLICK DO BOTÃO
	    	
	    	if(btConectar.isPressed()){ // SE O BOTÃO FOR PRESSIONADO
	    		onClick(btConectar); // EXECUTA A FUNÇÃO REFERENTE AO BOTÃO
	    	}
	    }
		// MÉTODO "telaPrincipal"
		public void telaPrincipal(){   	
			setContentView(R.layout.activity_controle__leds_); // INICIALIZA A TELA
			
			mHandler = new Handler(); // VARIÁVEL "mHandler" INICIALIZADA
	        mHandler.post(mUpdate);	 // VARIÁVEL "mHandler" CHAMA O MÉTODO "mUpdate"
	        /**MASTERWALKER SHOP*/
			btOnA = (ImageButton) findViewById(R.id.btOnA); // ESTANCIA O IMAGEBUTTON
	    	btOffA = (ImageButton) findViewById(R.id.btOffA);
	    	btOnB = (ImageButton) findViewById(R.id.btOnB);
	     	btOffB = (ImageButton) findViewById(R.id.btOffB);
	     	btOnC = (ImageButton) findViewById(R.id.btOnC);
	     	btOffC = (ImageButton) findViewById(R.id.btOffC);
	     	/**MASTERWALKER SHOP*/
	    	btOnA.setOnClickListener(this); // ATIVA O CLICK DO BOTÃO
	    	btOffA.setOnClickListener(this);
	    	btOnB.setOnClickListener(this);
	    	btOffB.setOnClickListener(this);
	    	btOnC.setOnClickListener(this);
	    	btOffC.setOnClickListener(this);
	    	/**MASTERWALKER SHOP*/
	    	tvStatusA = (TextView) findViewById(R.id.tvStatusA); // ESTANCIA O TEXTVIEW
	     	tvStatusB = (TextView) findViewById(R.id.tvStatusB);
	     	tvStatusC = (TextView) findViewById(R.id.tvStatusC);
	     	/**MASTERWALKER SHOP*/
			if(btOnA.isPressed()){ // SE O BOTÃO FOR PRESSIONADO
				onClick(btOnA); // EXECUTA A FUNÇÃO REFERENTE AO BOTÃO
			}
			if(btOffA.isPressed()){
				onClick(btOffA);
			}
			if(btOnB.isPressed()){
				onClick(btOnB);
			}
			if(btOffB.isPressed()){
				onClick(btOffB);
			}
			if(btOnC.isPressed()){
				onClick(btOnC);
			}
			if(btOffC.isPressed()){
				onClick(btOffC);
			}
		}/**MASTERWALKER SHOP*/
		// MÉTODO QUE EXECUTA A ATUALIZAÇÃO DO TEXTVIEW COM INFORMAÇÃO RECEBIDA DO ARDUINO
		private Runnable mUpdate = new Runnable() {
	    	public void run() {
	    		arduinoStatus("http://"+hostIp+"/"); // CHAMA O MÉTODO "arduinoStatus"
	    		mHandler.postDelayed(this, 1000); // TEMPO DE INTERVALO PARA ATUALIZAR NOVAMENTE A INFORMAÇÃO (500 MILISEGUNDOS)
	    	}
	    };/**MASTERWALKER SHOP*/
	 // MÉTODO "arduinoStatus"
	    public void arduinoStatus(String urlArduino){
	    	/**MASTERWALKER SHOP*/
			String urlHost = urlArduino; // CRIA UMA STRING
			String respostaRetornada = null; // CRIA UMA STRING CHAMADA "respostaRetornada" QUE POSSUI VALOR NULO
			
			//INICIO DO TRY CATCH
			try{
				respostaRetornada = ConectHttpClient.executaHttpGet(urlHost); // STRING "respostaRetornada" RECEBE RESPOSTA RETORNADA PELO ARDUINO
				String resposta = respostaRetornada.toString(); // STRING "resposta"
				resposta = resposta.replaceAll("\\s+", ""); 
				/**MASTERWALKER SHOP*/
				String[] b = resposta.split(","); // O VETOR "String[] b" RECEBE  O VALOR DE "resposta.split(",")"  	     

				if(b[0].equals("AC1")){ // SE POSIÇÃO 0 DO VETOR IGUAL A "AC1"				
					tvStatusA.setText("ON"); // TEXTVIEW RECEBE ON
				}
				else{
					if(b[0].equals("AP1")){  // SE POSIÇÃO 0 DO VETOR IGUAL A "AP1"	
						tvStatusA.setText("OFF"); // TEXTVIEW RECEBE OFF				
					}
				}			
				if(b[1].equals("AC2")){				
					tvStatusB.setText("ON");				
				}
				else{
					if(b[1].equals("AP2")){
						tvStatusB.setText("OFF");					
					}
				}
				if(b[2].equals("AC3")){
					tvStatusC.setText("ON");				
				}
				else{
					if(b[2].equals("AP3")){
						tvStatusC.setText("OFF");					
					}
				}
			}
			catch(Exception erro){
			}
		}
	    /**MASTERWALKER SHOP*/
		@Override
		public void onClick(View bt) { // MÉTODO QUE GERENCIA OS CLICK'S NOS BOTÕES
			
			if(bt == btConectar){ // SE BOTÃO CLICKADO
				if(et_Ip.getText().toString().equals("")){ // SE EDITTEXT ESTIVER VAZIO
					Toast.makeText(getApplicationContext(), // FUNÇÃO TOAST
					"Digite o IP do Ethernet Shield!", Toast.LENGTH_SHORT).show(); // EXIBE A MENSAGEM
				}else{ // SENÃO
				hostIp = et_Ip.getText().toString(); // STRING "hostIp" RECEBE OS DADOS DO EDITTEXT CONVERTIDOS EM STRING
				// FUNÇÃO QUE OCULTA O TECLADO APÓS CLICAR EM CONECTAR
				InputMethodManager escondeTeclado = (InputMethodManager)getSystemService(
			    Context.INPUT_METHOD_SERVICE);
			    escondeTeclado.hideSoftInputFromWindow(et_Ip.getWindowToken(), 0);
				telaPrincipal(); // FAZ A CHAMADA DO MÉTODO "telaPrincipal"
				}	
			}
			/**MASTERWALKER SHOP*/
	String url = null; // CRIA UMA STRING CHAMADA "url" QUE POSSUI VALOR NULO
			
			if(bt == btOnA){ // SE BOTÃO CLICKADO
				url = "http://"+hostIp+"/?L=1"; // STRING "url" RECEBE O VALOR APÓS O SINAL DE "="
			}
			if(bt == btOffA){
				url = "http://"+hostIp+"/?L=0";		
			}
			if(bt == btOnB){
				url = "http://"+hostIp+"/?M=1";	
			}
			if(bt == btOffB){
				url = "http://"+hostIp+"/?M=0";
			}
			if(bt == btOnC){
				url = "http://"+hostIp+"/?N=1";
			}
			if(bt == btOffC){
				url = "http://"+hostIp+"/?N=0";
			}
			/**MASTERWALKER SHOP*/
			String urlGetHost = url; // CRIA UMA STRING CHAMADA "urlGetHost" QUE RECEBE O VALOR DA STRING "url"
			/**MASTERWALKER SHOP*/
			//INICIO DO TRY CATCH
			try{
				ConectHttpClient.executaHttpGet(urlGetHost); // PASSA O PARÂMETRO PARA O O MÉTODO "executaHttpGet" NA CLASSE "ConectHttpClient" E ENVIA AO ARDUINO
			}
			catch(Exception erro){ // FUNÇÃO DE EXIBIÇÃO DO ERRO
			} // FIM DO TRY CATCH	
		}
		/**MASTERWALKER SHOP*/
		// MÉTODO QUE VERIFICA O BOTÃO DE VOLTAR DO DISPOSITIVO ANDROID E ENCERRA A APLICAÇÃO SE PRESSIONADO 2 VEZES SEGUIDAS
	    public void onBackPressed() {		
		    long currentTime = System.currentTimeMillis();
		    if(currentTime - lastPress > 5000){
		        Toast.makeText(getBaseContext(), "Pressione novamente para sair.", Toast.LENGTH_LONG).show();
		        lastPress = currentTime;  
		    }else{
		        super.onBackPressed();
		        android.os.Process.killProcess(android.os.Process.myPid());
		    }
		}
}
/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/