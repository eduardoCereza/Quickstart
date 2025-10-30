package org.firstinspires.ftc.teamcode.pedroPathing.LimelightTestes;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;


//TODO:                                          ATENÇÃO!!!!!
//TODO:          ESSE CÓDIGO CONTÉM LITERALMENTE TODOS OS CALCULOS E DEFINIÇÕES NECESSÁRIAS PARA O FUNCIONAMENTO DA TURRET
//TODO:          LOGO, CASO SEJA ALTERADO DE QUALQUER MANEIRA, CONSULTAR SE FUNCIONA MESMO
//TODO:          SE PRECISAR SER COMENTADO CHAME O HENRIQUE QUE ELE COMENTA E EXPLICA
//TODO:          NÃO PEDIR PRA NENHUMA IA FAZER ALTERAÇÕES POIS ELÁ IRÁ ERRAR E NÃO VAI FUNCIONAR
//TODO:                                          CUIDADO

// Se quiser mudar algo pra testar, pode usar o panels pra ver em tempo real as mudanças que ocorrem de acordo com os valores
@Configurable
public class RobotDecodeOficial{

    // Servo que move a turret no eixo X
    Servo servoX;

    // Servo que move a turret no eixo Y
    Servo servoY;

    // Flywheels
    DcMotorEx flywheelA, flywheelB;

    // Limelight
    //TODO: TEM QUE TUNAR A LIMELIGHT MELHOR
    Limelight3A limelight;

    // Follower do Pedro Pathing
    private Follower follower;

    // Ângulo Y para mover a turret no eixo Y
    double angleY;

    // Definições e variáveis trigonométricas pra mexer a turret no eixo Y e controle das flywheels
    //TODO: a altura da turret tem q ser tirada desse triangulo1
    double alturatriangulo1 = 74, hiptriangulo1, base1;
    double alturatriangulo2 = 124, hiptriangulo2, base2;
    final double offsetBase2 = 20;
    final double k = 186.5409338456308;

    // Váriaveis para controlar a velocidade das flywheels
    //TODO: ver esssa parada do peso da turret
    //TODO: VERIFICAR O CALCULO NO PAPEL LA NA SALA NO ARMARIO
    double rpm, calculo1, calculo2, calculo3;
    final double g = 980, k_lip = 0.95, r = 4.5;
    final double pesoTurret = 90.7, pesoBola = 74.8;

    // Pose dos goals azul e vermelho
    //TODO: VERIFICAR A POSE DOS GOALS
    private final Pose goalRed = new Pose(122, 126, Math.toRadians(0));
    private final Pose goalBlue = new Pose(0,0,Math.toRadians(0));

    // Inicialização dos componentes do robô
    public RobotDecodeOficial(HardwareMap hardwareMap, int index, Telemetry telemetry){
        // Init dos servos X e Y
        servoX = hardwareMap.get(Servo.class, "servoX");
        servoY = hardwareMap.get(Servo.class, "servoY");

        // Init das Flywheels
        flywheelA = hardwareMap.get(DcMotorEx.class, "flywheelA");
        flywheelB = hardwareMap.get(DcMotorEx.class, "flywheelB");

        // Init da Limelight3A
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(index);

        // Init do Pedro Pathing
        follower = Constants.createFollower(hardwareMap);
        follower.update();
    }

    // Mira a turret no eixo X
    public void mirarX_turret(double x, double y, double heading, boolean blue, Telemetry telemetry){

        // Heading do robô
        double headingDeg = Math.toDegrees(heading);

        // Localização do robô no field
        x = x;
        y = y;

        // Localização X e Y dos goals
        double goalX = blue ? goalBlue.getX() : goalRed.getX();
        double goalY = blue ? goalBlue.getY() : goalRed.getY();

        // Ângulo pro goal
        double angleToGoal = Math.toDegrees(Math.atan2(goalX - x, goalY - y));

        // Ângulo da turret com base no angulo pro goal
        double turretAngle = angleToGoal + headingDeg;

        // Máximo e minimo do servo
        // TODO: VERIFICAR O MINIMO E MÁXIMO DO SERVO X
        turretAngle = Math.max(-150, Math.min(150, turretAngle));

        // Transforma em posição do servo mesmo
        double servoPos = (turretAngle + 90) / 180.0;

        // Minimo e máximo do servo, nao muda muita coisa isso
        servoPos = Math.max(0.0, Math.min(1.0, servoPos));

        // Seta a posição do servo X
        servoX.setPosition(-servoPos);

        // Telemetria
        telemetry.addData("AngleToGoal", angleToGoal);
        telemetry.addData("TurretAngle", turretAngle);
        telemetry.addData("ServoPos", servoPos);
        telemetry.update();
    }

    // Mira a turret no eixo Y
    public void mirarY_turret(){

        // Inicia a leitura da Limelight
        limelight.start();

        // Pega a leitura do Ta (área da tag) da Limelight
        double ta = limelight.getLatestResult().getTa();

        // Calculos trigonométricos
        hiptriangulo1 = k / Math.sqrt(ta);

        base1 = Math.sqrt(Math.pow(hiptriangulo1, 2) - Math.pow(alturatriangulo1, 2));

        base2 = base1 + offsetBase2;

        hiptriangulo2 = Math.sqrt(Math.pow(base2, 2) + Math.pow(alturatriangulo2, 2));

        angleY = Math.toDegrees(Math.atan2(alturatriangulo2, base2));

        // Seta a posição do servo
        //TODO: VERIFICAR A AMPLITUDE DO SERVO E MUDAR ESSE 60 SE NECESSÁRIO
        servoY.setPosition(angleY / 60);

        // Telemetria
        telemetry.addData("base 1:", base1);
        telemetry.addData("base 2:", base2);
        telemetry.addData("hiptenusa 1:", hiptriangulo1);
        telemetry.addData("hiptenusa 2:" ,hiptriangulo2);
        telemetry.addData("angle:", angleY);
        telemetry.addData("servoY pos:", servoY.getPosition());
        telemetry.update();
    }

    // Atira as bolas
    //TODO VERIFICAR
    public void shoot(){

        // Inicia a leitura da Limelight
        limelight.start();

        // Pega o leitura do Ta (área da tag) da Limelight
        double ta = limelight.getLatestResult().getTa();

        // Calculos trigonométricos
        hiptriangulo1 = k / Math.sqrt(ta);

        base1 = Math.sqrt(Math.pow(hiptriangulo1, 2) - Math.pow(alturatriangulo1, 2));

        base2 = base1 + offsetBase2;

        hiptriangulo2 = Math.sqrt(Math.pow(base2, 2) + Math.pow(alturatriangulo2, 2));

        angleY = Math.toDegrees(Math.atan2(alturatriangulo2, base2));


        // Calculos da velocidade das Flywheels pra lançar as bolas
        calculo1 = Math.sqrt((g * Math.pow(base2, 2)) / (2 * Math.pow(Math.cos(angleY), 2) * (base2 * Math.tan(angleY) - alturatriangulo2)));

        calculo2 = calculo1 / k_lip;

        calculo3 = calculo2 / (6.28 * r);

        rpm = calculo3 * 60;

        // seta o RPM das Flywheels
        // Tecnicamente funciona, mas tem q testar
        flywheelA.setVelocity(-rpm);
        flywheelB.setVelocity(-rpm);
    }
}