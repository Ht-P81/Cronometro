package com.example.cronometro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class MainActivity extends AppCompatActivity {

    enum EstadoCrono{
        ACTIVO, PAUSADO, STOP
    }

    private CountDownTimer timer;
    private Long timerLegthSeconds = 0L;
    private Long secondsRemaining = 0L;
    private EstadoCrono estadoCrono = EstadoCrono.STOP;
    private Button btn_iniciar, btn_pausa, btn_stop;
    private MaterialProgressBar progress_countdown; //Circulo en el layout
    private TextView textView; //TextView que tenemos en el layout


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_iniciar = findViewById(R.id.btn_Start);
        btn_pausa = findViewById(R.id.btn_Pausa);
        btn_stop = findViewById(R.id.btn_Stop);

        progress_countdown = findViewById(R.id.progress_countdown);    // 1 NO ESTABA

        textView = findViewById(R.id.crono); // 2 NO ESTABA


        btn_iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCronometro();
                estadoCrono = EstadoCrono.ACTIVO; // 3 NO ESTABA
            }
        });

        btn_pausa.setOnClickListener(new View.OnClickListener() { // 4 NO ESTABA (FALTABA METODO)
            @Override
            public void onClick(View v) {
                timer.cancel();
                estadoCrono = EstadoCrono.PAUSADO;
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() { // 5 NO ESTABA (FALTABA METODO)
            @Override
            public void onClick(View v) {
                timer.cancel();
                tiempoTerminado();
            }
        });
    } //llave de cierre del OnCreate

    private void startCronometro(){
        estadoCrono = EstadoCrono.ACTIVO;
        // ponerTiempoCronometro(); // 6 ESTA LINEA NO ESTA EN EL ORIGINAL

        timer = new CountDownTimer(secondsRemaining * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                secondsRemaining = millisUntilFinished / 1000;
                actualizarCronometro();
            }

            @Override
            public void onFinish() {
                //estadoCrono = EstadoCrono.STOP; // 7 ESTA LINEA NO ESTA EN EL ORIGINAL
                //progress_countdown.setProgress(0); // 8 ESTA LINEA NO ESTABA EN EL ORIGINAL
                ponerTiempoCronometro();

            }
        }.start();
    }

    private void actualizarCronometro(){
        Long minutosHastaFinalizar = secondsRemaining / 60;
        Long segundosEnMinutoHastaFinalizar = secondsRemaining - minutosHastaFinalizar * 60;
        String segundosStr = segundosEnMinutoHastaFinalizar.toString();

        //String segundosPantalla = (segundosStr.length() == 2) ? segundosStr : "0"+segundosStr;   // 00:00 ---> 00:09

        String segundosPantalla;

        if(segundosStr.length() != 2){
            segundosPantalla = "0"+segundosStr;
        }else{
            segundosPantalla = segundosStr;
        }

        textView.setText(String.format("%d:%s", minutosHastaFinalizar, segundosPantalla));

        progress_countdown.setProgress(timerLegthSeconds.intValue() - secondsRemaining.intValue());

    }

    private void tiempoTerminado(){ // 8 ESTE METODO NO ESTABA CREADO
        estadoCrono = EstadoCrono.STOP;
        ponerTiempoCronometro();
        progress_countdown.setProgress(0);
        secondsRemaining = timerLegthSeconds;
        actualizarCronometro();
    }

    private void ponerTiempoCronometro(){
        int longitudEnMinutos = 1;
        timerLegthSeconds = (longitudEnMinutos * 60L);
        progress_countdown.setMax(timerLegthSeconds.intValue());
    }
}