package org.firstinspires.ftc.teamcode.pedroPathing;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

public class MainAuto extends OpMode {

    /**Essa é a classe principal. Nela, vamos poder escolher qual quer modo de autônomo de acordo com a ordem
     * disponibilizada pela AprilTag, utilizando a Limelight3A. Assim, evitamos de criar 3 programações diferentes
     * e passamos a criar uma classe MAIN para todos os autônomos, sendo executados com base na resposta da leitura
     * da câmera.*/

    Limelight3A limelight;

    private static final boolean USE_WEBCAM = true;

    private AprilTagProcessor aprilTag;

    private VisionPortal visionPortal;

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

    private void initAprilTag() {

        // Create the AprilTag processor the easy way.
        aprilTag = AprilTagProcessor.easyCreateWithDefaults();

        // Create the vision portal the easy way.
        if (USE_WEBCAM) {
            visionPortal = VisionPortal.easyCreateWithDefaults(
                    hardwareMap.get(WebcamName.class, "webcam"), aprilTag);
        } else {
            visionPortal = VisionPortal.easyCreateWithDefaults(
                    BuiltinCameraDirection.BACK, aprilTag);
        }

    }


}
