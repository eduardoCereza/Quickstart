package pre_temporada;

import com.pedropathing.localization.GoBildaPinpointDriver;
import com.pedropathing.localization.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.UnnormalizedAngleUnit;

import java.util.Locale;

public class Pinpoint extends LinearOpMode {

    GoBildaPinpointDriver odo; // Objeto que controla o módulo de odometria Pinpoint
    double oldTime = 0;        // Usado para calcular o tempo entre loops


    @Override
    public void runOpMode(){

        //Mapea para encontrar o pinpoint
        odo = hardwareMap.get(GoBildaPinpointDriver.class, "odopoint");

        /*
        Define as posições do pod de odometria em relação ao ponto em que o computador de odometria rastreia.
        O deslocamento do pod X refere-se à distância lateral do ponto de rastreamento do pod de odometria
        X (à frente). À esquerda do centro é um número positivo, à direita do centro é um número negativo.
        O deslocamento do pod Y refere-se à distância à frente do ponto de rastreamento do pod de odometria Y (à direita).
        À frente do centro é um número positivo, à trás é um número negativo.
        */

        /*

        Define onde os pods estão no robô. Por exemplo:

        -84 mm no eixo X (para a esquerda ou direita).

        -168 mm no eixo Y (frente/trás).

        */
        odo.setOffsets(-84.0, -168.0, DistanceUnit.MM);

        //Define o modelo do pod para calcular corretamente a distância com base nos ticks dos encoders.
        odo.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);

        //Define o sentido dos encoders. Ambos aumentam o valor quando o robô se move para frente e para a esquerda.
        odo.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);

        //Zera a posição (X=0, Y=0, heading=0) e recalibra o IMU. Ideal para usar no início de um modo autônomo.
        odo.resetPosAndIMU();

        waitForStart();
        resetRuntime();

        while (opModeIsActive()){

            //Faz a leitura dos sensores do Pinpoint e atualiza a posição.
            odo.update();

            /*

            A: zera a posição e recalibra.

            B: apenas recalibra a orientação.
            */
            if (gamepad1.a) {
                odo.resetPosAndIMU();
            }
            else if(gamepad1.b) {
                odo.recalibrateIMU();
            }

            double newTime = getRuntime();
            double loopTime = newTime - oldTime;
            double frequency = 1 / loopTime;
            oldTime = newTime;

            //Mostra a posição X, Y em mm e a orientação (heading) em graus.
            Pose2D pos = odo.getPosition();
            String data = String.format(Locale.US, "{X: %.3f, Y: %.3f, H: %.3f}",
                    pos.getX(DistanceUnit.MM),
                    pos.getY(DistanceUnit.MM),
                    pos.getHeading(AngleUnit.DEGREES));
            telemetry.addData("Position", data);

            //Mostra a velocidade em mm/s e graus/s.
            String velocity = String.format(Locale.US,
                    "{XVel: %.3f, YVel: %.3f, HVel: %.3f}",
                    odo.getVelX(DistanceUnit.MM),
                    odo.getVelY(DistanceUnit.MM),
                    odo.getHeadingVelocity(UnnormalizedAngleUnit.DEGREES));
            telemetry.addData("Velocity", velocity);

            telemetry.addData("Status", odo.getDeviceStatus());
            telemetry.addData("Pinpoint Frequency", odo.getFrequency()); // taxa de atualização do Pinpoint
            telemetry.addData("REV Hub Frequency: ", frequency);         // taxa de atualização do controlador
            telemetry.update();
        }
    }
}



        }
    }
}
