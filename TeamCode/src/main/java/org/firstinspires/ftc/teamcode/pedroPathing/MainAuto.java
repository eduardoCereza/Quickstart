package org.firstinspires.ftc.teamcode.pedroPathing;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class MainAuto extends OpMode {

    /**Essa é a classe principal. Nela, vamos poder escolher qual quer modo de autônomo de acordo com a ordem
     * disponibilizada pela AprilTag, utilizando a Limelight3A. Assim, evitamos de criar 3 programações diferentes
     * e passamos a criar uma classe MAIN para todos os autônomos, sendo executados com base na resposta da leitura
     * da câmera.*/

    Limelight3A limelight;

    public void init(){
        //Inicializando Limelight3A
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(0);
        limelight.start();
    }

    public void loop(){
        /*Definir qual a ordem que está definida na April Tag lida pela limelight. Nessa parte, irá
        * chamar a função de definir ordem, para assim, executar o autônomo de cada caso;
        *
        * */

    }

    public void definirOrdem(String ordem){
        switch (ordem){
            case "PPG":
                break;

            case "PGP":
                break;

            case "GPP":
                break;

            default:
                break;
        }
    }


}
