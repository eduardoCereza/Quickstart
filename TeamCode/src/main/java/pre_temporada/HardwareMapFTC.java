package pre_temporada;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DigitalChannel;

public class HardwareMapFTC {

    // Motores
    public DcMotorEx slideH, slideV;
    PID pid;

    // Inicializador
    public void init(HardwareMap hwMap) {

        // Motores de drive
        slideV = hwMap.get(DcMotorEx.class, "slidev");
        slideH = hwMap.get(DcMotorEx.class, "slideh");


        // Configurações comuns de motores
        DcMotorEx[] motor_atuador = {slideV, slideH};
        for (DcMotorEx atuador : motor_atuador) {
            atuador.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            atuador.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            atuador.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

    }
}
